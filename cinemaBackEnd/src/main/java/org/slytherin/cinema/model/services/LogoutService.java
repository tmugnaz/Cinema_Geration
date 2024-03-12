package org.slytherin.cinema.model.services;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slytherin.cinema.model.repositories.abstractions.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    public LogoutService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            try {
                response.sendError(403);
            } catch (IOException e) {
                try {
                    response.sendError(500);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt).orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();// Elimina autorizzazioni dell'utente
        } else {
            try {
                response.sendError(403);
            } catch (IOException e) {
                try {
                    response.sendError(500);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
