package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iostar.springbootbackend.entity.OrderState;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailModel {
    private Long idOrder;
    private List<BicycleProductResponseModel> bicycleProductModels;
    private String payMethod;
    private Long totalQuantity;
    private Long totalPrice;
    private Long shipPrice;
    private String shipAddress;
    private String message;
    private String fullName;
    private String phoneNumber;
    private LocalDateTime orderedAt;
    private OrderState orderState;
}
