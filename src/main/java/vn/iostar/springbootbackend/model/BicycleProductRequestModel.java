package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BicycleProductRequestModel {
    private Long idBicycle;
    private Long idBicycleSize;
    private Long idBicycleColor;
    private Long quantity;
}
