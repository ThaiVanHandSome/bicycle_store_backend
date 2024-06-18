package vn.iostar.springbootbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iostar.springbootbackend.auth.authentication.AuthenticationRequest;
import vn.iostar.springbootbackend.entity.ConfirmationToken;
import vn.iostar.springbootbackend.entity.User;
import vn.iostar.springbootbackend.model.ForgotPasswordRequest;
import vn.iostar.springbootbackend.model.response.BaseResponse;
import vn.iostar.springbootbackend.service.impl.ConfirmationTokenService;
import vn.iostar.springbootbackend.service.impl.UserService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/user/change-password")
    public ResponseEntity<?> changePasswordWhenForgotPassword(@RequestBody ForgotPasswordRequest payload) {
        Optional<User> optUserGetByEmail = userService.getUserByEmail(payload.getEmail());
        BaseResponse response = new BaseResponse();
        if (optUserGetByEmail.isEmpty()) {
            response = BaseResponse.builder().status("error").code(400).message("User not found!").build();
            return ResponseEntity.ok(response);
        }
        Optional<ConfirmationToken> optConfirmationToken = confirmationTokenService.getToken(payload.getToken());
        if (optConfirmationToken.isEmpty()) {
            response = BaseResponse.builder().status("not-author").code(401).message("You do not authorize!").build();
            return ResponseEntity.ok(response);
        }
        ConfirmationToken confirmationToken = optConfirmationToken.get();
        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if(expiredAt.isBefore(LocalDateTime.now())) {
            response = BaseResponse.builder().status("not-author").code(401).message("You do not authorize!").build();
            return ResponseEntity.ok(response);
        }
        User userGetByEmail = optUserGetByEmail.get();
        User userGetByToken = optConfirmationToken.get().getUser();
        if(!Objects.equals(userGetByEmail.getIdUser(), userGetByToken.getIdUser())) {
            response = BaseResponse.builder().status("not-author").code(401).message("You do not authorize!").build();
            return ResponseEntity.ok(response);
        }
        String passwordEncoded = passwordEncoder.encode(payload.getPassword());
        userGetByEmail.setPassword(passwordEncoded);
        userService.saveUser(userGetByEmail);
        response = BaseResponse.builder().status("success").code(200).message("Password changed!").build();
        return ResponseEntity.ok(response);
    }
}
