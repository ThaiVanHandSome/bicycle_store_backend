package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BicycleCommentResponseModel {
    private String fullName;
    private String avatar;
    private String content;
    private LocalDateTime createAt;
}
