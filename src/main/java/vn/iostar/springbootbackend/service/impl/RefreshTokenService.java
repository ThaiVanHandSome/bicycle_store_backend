package vn.iostar.springbootbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.entity.RefreshToken;
import vn.iostar.springbootbackend.entity.User;
import vn.iostar.springbootbackend.repository.RefreshTokenRepository;
import vn.iostar.springbootbackend.repository.UserRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepository;
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String email) {
        Optional<User> optUser = userRepository.findByEmail(email);
        if(optUser.isPresent()) {
            User user = optUser.get();
            Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findByUser(user);
            optRefreshToken.ifPresent(token -> refreshTokenRepository.delete(token));
            Instant expiryDate = Instant.now().plus(365, ChronoUnit.DAYS);
            RefreshToken refreshToken = RefreshToken.builder()
                    .user(userRepository.findByEmail(email).get())
                    .token(UUID.randomUUID().toString())
                    .expiryDate(expiryDate)
                    .build();
            return refreshTokenRepository.save(refreshToken);
        }
        return null;
    }

    public Optional<RefreshToken> findByToken( String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + "Refresh Token was expired. Please make a new signin request!");
        }
        return token;
    }
}
