package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String birthDay;
    private int gender;
    private String avatar;
}
