package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BicycleSizeModel {
    private Long idBicycleSize;
    private String name;
}
