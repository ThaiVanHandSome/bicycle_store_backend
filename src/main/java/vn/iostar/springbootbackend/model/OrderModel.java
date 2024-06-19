package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iostar.springbootbackend.embeddedId.IdBicycleProduct;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {
    private List<BicycleProductModel> bicycleProductModels;
    private Long idPayMethod;
    private Long totalQuantity;
    private Long totalPrice;
    private Long shipPrice;
    private String shipAddress;
    private String message;
    private String fullName;
    private String phoneNumber;
}
