package org.slytherin.cinema.api.restcontrollers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slytherin.cinema.dtos.AuthenticationRequestDto;
import org.slytherin.cinema.dtos.AuthenticationResponseDto;
import org.slytherin.cinema.dtos.ErrorResponseDto;
import org.slytherin.cinema.dtos.RegisterRequestDto;
import org.slytherin.cinema.model.exceptions.AuthErrorCode;
import org.slytherin.cinema.model.exceptions.DuplicateEmailException;
import org.slytherin.cinema.model.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register
            (@RequestBody RegisterRequestDto regRequestDto){
        try {
            return ResponseEntity.ok(authenticationService.register(regRequestDto));
        } catch (DuplicateEmailException de) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(AuthErrorCode.EMAIL_EXIST.toString(), AuthErrorCode.EMAIL_EXIST.getCode()));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate
            (@RequestBody AuthenticationRequestDto authRequestDto){
        return ResponseEntity.ok(authenticationService.login(authRequestDto));
    }
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
