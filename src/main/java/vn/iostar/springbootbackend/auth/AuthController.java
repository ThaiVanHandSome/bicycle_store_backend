package vn.iostar.springbootbackend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iostar.springbootbackend.auth.authentication.AuthenticationRequest;
import vn.iostar.springbootbackend.auth.email.EmailService;
import vn.iostar.springbootbackend.auth.refreshToken.RefreshTokenRequest;
import vn.iostar.springbootbackend.auth.registration.RegisterRequest;
import vn.iostar.springbootbackend.service.impl.RefreshTokenService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        return ResponseEntity.ok(authService.confirmToken(token));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

//    @PostMapping("/authenticate-oauth")
//    public ResponseEntity<?> authenticateOauth(@RequestBody RegisterRequest request) {
//        return ResponseEntity.ok(authService.OAuthLogin(request));
//    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(authService.sendOtpToEmail(email));
    }
}
