package org.slytherin.cinema.model.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slytherin.cinema.dtos.AuthenticationRequestDto;
import org.slytherin.cinema.dtos.AuthenticationResponseDto;
import org.slytherin.cinema.dtos.RegisterRequestDto;
import org.slytherin.cinema.model.entities.Token;
import org.slytherin.cinema.model.entities.TokenType;
import org.slytherin.cinema.model.entities.User;
import org.slytherin.cinema.model.exceptions.AuthErrorCode;
import org.slytherin.cinema.model.exceptions.DuplicateEmailException;
import org.slytherin.cinema.model.repositories.abstractions.TokenRepository;
import org.slytherin.cinema.model.repositories.abstractions.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    public AuthenticationService(UserRepository repository,
                                 TokenRepository tokenRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager,
                                 UserRepository userRepository) {
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository= userRepository;

    }

    public AuthenticationResponseDto register(RegisterRequestDto request) throws DuplicateEmailException {
        var user = new User(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                request.getBirthdate(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole()
        );
        var userOpt = repository.findByEmail(request.getEmail());
        if (userOpt.isPresent()){
            throw new DuplicateEmailException(AuthErrorCode.EMAIL_EXIST + " Email già esistente " + request.getEmail());
        }
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        Date d = new Date(System.currentTimeMillis()+jwtExpiration);
        return new AuthenticationResponseDto(jwtToken, refreshToken,user,d);
    }


    public AuthenticationResponseDto login(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        Date d = new Date(System.currentTimeMillis()+jwtExpiration);
        return new AuthenticationResponseDto(jwtToken, refreshToken,user,d);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = new Token(user, jwtToken, TokenType.BEARER, false, false);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                Date d = new Date(System.currentTimeMillis()+jwtExpiration);
                var authResponse = new AuthenticationResponseDto(accessToken, refreshToken,user,d);
                // Manda nel flusso della risposta al client l'oggetto authResponse serializzato in JSON
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}