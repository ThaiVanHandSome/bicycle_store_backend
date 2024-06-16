package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private BicycleModel bicycle;
    private BicycleColorModel bicycleColor;
    private BicycleSizeModel bicycleSize;
    private int quantity;
}
