package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BicycleProductModel {
    private Long idBicycle;
    private Long idBicycleSize;
    private Long idBicycleColor;
    private Long quantity;
}
