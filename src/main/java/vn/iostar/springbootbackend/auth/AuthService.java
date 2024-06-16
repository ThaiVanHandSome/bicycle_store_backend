package vn.iostar.springbootbackend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.auth.authentication.AuthenticationRequest;
import vn.iostar.springbootbackend.auth.authentication.AuthenticationResponse;
import vn.iostar.springbootbackend.auth.email.EmailService;
import vn.iostar.springbootbackend.auth.email.EmailValidator;
import vn.iostar.springbootbackend.auth.otp.OtpResponse;
import vn.iostar.springbootbackend.auth.refreshToken.RefreshTokenRequest;
import vn.iostar.springbootbackend.auth.refreshToken.RefreshTokenResponse;
import vn.iostar.springbootbackend.auth.registration.RegisterRequest;
import vn.iostar.springbootbackend.auth.registration.RegisterResponse;
import vn.iostar.springbootbackend.entity.*;
import vn.iostar.springbootbackend.model.response.BaseResponse;
import vn.iostar.springbootbackend.repository.UserRepository;
import vn.iostar.springbootbackend.security.JWTService;
import vn.iostar.springbootbackend.service.impl.ConfirmationTokenService;
import vn.iostar.springbootbackend.service.impl.RefreshTokenService;
import vn.iostar.springbootbackend.service.impl.UserService;

import javax.naming.AuthenticationException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private AuthenticationManager authenticationManager;

    public BaseResponse register(RegisterRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail) {
            return BaseResponse.builder().message("Email Not Valid!").code(400).status("error").build();
        }
        Optional<User> optUser = repository.findByEmail(request.getEmail());
        if(optUser.isEmpty()) {
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .avatar("https://th.bing.com/th/id/OIP.bWllJuCbia6Vbt18CzJWQQHaHY?w=1005&h=1002&rs=1&pid=ImgDetMain")
                    .role(Role.USER)
                    .provider(Provider.DATABASE)
                    .isActive(false)
                    .build();
            repository.save(user);

            String token = generateOTP();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    user

            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            emailService.send(request.getEmail(), buildEmailOTP(request.getFirstName() + " " + request.getLastName(), token));
            return BaseResponse.builder().code(200).status("success").message("Register Successfully! Please Check Email To See OTP!").build();
        }
        else {
            return BaseResponse.builder().code(400).status("error").message("Email Already Token!").build();
        }
    }

    public BaseResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception ex) {
            return BaseResponse.builder().code(400).status("error").message("Email Or Password Wrong!").build();
        }
        Optional<User> opt = repository.findByEmail(request.getEmail());
        if(opt.isEmpty()) {
            return BaseResponse.builder().code(400).status("error").message("Email Or Password Wrong!").build();
        }
        User user = opt.get();
        if(!user.isActive()) {
            return BaseResponse.builder().code(400).status("error").message("Account Not Confirm!").build();
        }
        var jwtToken = jwtService.generateAccessToken(user);
        var jwtRefreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(jwtRefreshToken.getToken())
                .idUser(user.getIdUser())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .gender(user.getGender())
                .provider(user.getProvider())
                .build();
        return BaseResponse.builder().code(200).status("success").message("Authenticate Successfully!").data(authResponse).build();
    }

    public BaseResponse confirmToken(String token) {
        Optional<ConfirmationToken> otpConfirmationToken = confirmationTokenService.getToken(token);
        if(otpConfirmationToken.isPresent()) {
            ConfirmationToken confirmationToken = otpConfirmationToken.get();
            if(confirmationToken.getConfirmedAt() != null) {
                return BaseResponse.builder().code(400).status("error").message("Email Already Confirmed!").build();
            }
            LocalDateTime expiredAt = confirmationToken.getExpiredAt();
            if(expiredAt.isBefore(LocalDateTime.now())) {
                return BaseResponse.builder().code(400).status("error").message("Token Expired! Please Use New Token!").build();
            }
            confirmationTokenService.setConfirmedAt(token);
            userService.enableUser(confirmationToken.getUser().getEmail());
            return BaseResponse.builder().code(200).status("success").message("Successfully! Confirmed!").build();
        }
        return BaseResponse.builder().code(400).status("error").message("Token Not Valid!").build();
    }

    private String buildEmailOTP(String name, String otp) {
        return "<html>\n" +
                "  <head>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        font-family: Arial, sans-serif;\n" +
                "        background-color: #f4f4f4;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "      .container {\n" +
                "        max-width: 600px;\n" +
                "        margin: 50px auto;\n" +
                "        padding: 20px;\n" +
                "        background-color: #fff;\n" +
                "        border-radius: 10px;\n" +
                "        box-shadow: 0 0 10px rgba(0,0,0,0.1);\n" +
                "      }\n" +
                "      h1 {\n" +
                "        color: #333;\n" +
                "      }\n" +
                "      h2 {\n" +
                "        color: #555;\n" +
                "      }\n" +
                "      span {\n" +
                "        color: #ff0000;\n" +
                "        font-weight: bold;\n" +
                "        letter-spacing: 2px;\n" +
                "        font-size: 24px;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"container\">\n" +
                "      <h1>Xin Chào, " + name + "!</h1>\n" +
                "      <h3>Cảm ơn bạn đã đăng ký tài khoản cho ứng dụng của chúng tôi!</h3>\n" +
                "      <h2>Mã OTP để xác nhận tài khoản của bạn là: <span>" + otp + "</span></h2>\n" +
                "      <h3>Trân trọng!</h3>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }


    public BaseResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateAccessToken(user);
                    RefreshTokenResponse refreshTokenResponse = RefreshTokenResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getToken())
                            .build();
                    return BaseResponse.builder().code(200).status("success").message("Refresh Token Successfully!").data(refreshTokenResponse).build();
                })
                .orElseThrow(() -> new RuntimeException(
                        "Refresh Token not in database!"));
    }

    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

    public BaseResponse sendOtpToEmail(String email) {
        Optional<User> optUser = userService.getUserByEmail(email);
        if(optUser.isPresent()) {
            User user = optUser.get();
            String token = generateOTP();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    user
            );
            Optional<ConfirmationToken> optConfirmationToken = confirmationTokenService.getTokenByUser(user);
            optConfirmationToken.ifPresent(value -> confirmationTokenService.delete(value));
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            emailService.send(email, buildEmailOTP(user.getFirstName() + " " + user.getLastName(), token));
            return BaseResponse.builder().code(200).status("success").message("Successfully! Please Check Email To See OTP!").build();
        }
        return BaseResponse.builder().code(400).status("error").message("Email Not Register! Please Enter Another Email!").build();
    }

//    public AuthenticationResponse OAuthLogin(RegisterRequest request) {
//        Optional<User> optUser = userService.getUserByEmail(request.getEmail());
//        if(optUser.isPresent()) {
//            User user = optUser.get();
//            String provider = String.valueOf(user.getProvider());
//            if (Objects.equals(provider, "DATABASE")){
//                return AuthenticationResponse.builder().error(true).success(false).message("Email Already Existed!").build();
//            }
//            var jwtToken = jwtService.generateAccessToken(user);
//            var jwtRefreshToken = refreshTokenService.createRefreshToken(user.getEmail());
//            return AuthenticationResponse.builder()
//                    .accessToken(jwtToken)
//                    .refreshToken(jwtRefreshToken.getToken())
//                    .id(user.getIdUser())
//                    .firstName(user.getFirstName())
//                    .lastName(user.getLastName())
//                    .email(user.getEmail())
//                    .avatar(user.getAvatar())
//                    .gender(user.getGender())
//                    .provider(user.getProvider())
//                    .error(false)
//                    .success(true)
//                    .message("Login Successfully!")
//                    .build();
//        }
//
//        var user = User.builder()
//                .nickname(request.getNickName())
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .phoneNumber(request.getPhoneNumber())
//                .avatar(request.getAvatar())
//                .role(Role.USER)
//                .provider(Provider.GOOGLE)
//                .isActive(false)
//                .build();
//        repository.save(user);
//        var jwtToken = jwtService.generateAccessToken(user);
//        var jwtRefreshToken = refreshTokenService.createRefreshToken(user.getEmail());
//        return AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .refreshToken(jwtRefreshToken.getToken())
//                .id(user.getIdUser())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .email(user.getEmail())
//                .avatar(user.getAvatar())
//                .gender(user.getGender())
//                .provider(user.getProvider())
//                .error(false)
//                .success(true)
//                .message("Login Successfully!")
//                .build();
//    }
}
