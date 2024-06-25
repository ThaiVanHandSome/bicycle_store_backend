package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BicycleProductResponseModel {
    private String bicycleImage;
    private Long bicyclePrice;
    private String bicycleName;
    private String bicycleColorName;
    private String bicycleSizeName;
    private Long totalQuantity;
}
