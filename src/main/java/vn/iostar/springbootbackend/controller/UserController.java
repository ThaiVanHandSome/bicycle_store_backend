package vn.iostar.springbootbackend.controller;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.iostar.springbootbackend.auth.authentication.AuthenticationRequest;
import vn.iostar.springbootbackend.entity.ConfirmationToken;
import vn.iostar.springbootbackend.entity.User;
import vn.iostar.springbootbackend.model.ForgotPasswordRequest;
import vn.iostar.springbootbackend.model.UserModel;
import vn.iostar.springbootbackend.model.response.BaseResponse;
import vn.iostar.springbootbackend.service.impl.ConfirmationTokenService;
import vn.iostar.springbootbackend.service.impl.ImageService;
import vn.iostar.springbootbackend.service.impl.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private ImageService imageService;

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

    @GetMapping("/user")
    public ResponseEntity<?> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BaseResponse response = new BaseResponse();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            User user = userService.getUserByEmail(email).get();
            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(user, userModel);
            if(user.getBirthDay() != null) {
                userModel.setBirthDay(user.getBirthDay().toString());
            }
            response = BaseResponse.builder().status("success").code(200).message("Get user successfully!").data(userModel).build();
            return ResponseEntity.ok(response);
        }
        response = BaseResponse.builder().status("error").code(400).message("User not found!").build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestPart("avatarUpload") @Nullable MultipartFile avatarUpload,
                                        @RequestParam("firstName") String firstName,
                                        @RequestParam("lastName") String lastName,
                                        @RequestParam("phoneNumber") String phoneNumber,
                                        @RequestParam("gender") int gender,
                                        @RequestParam("birthDay") String birthDay) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BaseResponse response = new BaseResponse();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            User user = userService.getUserByEmail(email).get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
            user.setGender(gender);
            if(birthDay != null && !birthDay.trim().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate birthDayLocalDate = LocalDate.parse(birthDay, formatter);
                user.setBirthDay(birthDayLocalDate);
            }
            if(avatarUpload != null && !avatarUpload.isEmpty()) {
                String imageUrl = imageService.uploadImage(avatarUpload);
                user.setAvatar(imageUrl);
            }
            userService.saveUser(user);
            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(user, userModel);
            response = BaseResponse.builder().status("success").code(200).message("Update user successfully!").data(userModel).build();
            return ResponseEntity.ok(response);
        }
        response = BaseResponse.builder().status("error").code(400).message("User not found!").build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/user/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody String password) {
        BaseResponse response = new BaseResponse();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            User user = userService.getUserByEmail(email).get();
            password = password.replaceAll("\"", "");
            user.setPassword(passwordEncoder.encode(password));
            userService.saveUser(user);
            response = BaseResponse.builder().status("success").code(200).message("Update password successfully!").build();
            return ResponseEntity.ok(response);
        }
        response = BaseResponse.builder().status("error").code(400).message("User not found!").build();
        return ResponseEntity.ok(response);
    }
}
