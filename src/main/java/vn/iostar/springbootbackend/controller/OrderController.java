package vn.iostar.springbootbackend.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import vn.iostar.springbootbackend.embeddedId.IdBicycleProduct;
import vn.iostar.springbootbackend.embeddedId.IdOrderDetail;
import vn.iostar.springbootbackend.entity.*;
import vn.iostar.springbootbackend.model.BicycleProductModel;
import vn.iostar.springbootbackend.model.OrderModel;
import vn.iostar.springbootbackend.model.response.BaseResponse;
import vn.iostar.springbootbackend.service.impl.*;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private PayMethodService payMethodService;

    @Autowired
    private BicycleProductService bicycleProductService;

    @GetMapping("/checkout-processor")
    public ResponseEntity<?> checkoutProcessor(@RequestParam("idBicycle") Long idBicycle, @RequestParam("idBicycleColor") Long idBicycleColor, @RequestParam("idBicycleSize") Long idBicycleSize, @RequestParam("quantity") Long quantity) {
        BaseResponse response = new BaseResponse();
        IdBicycleProduct idBicycleProduct = new IdBicycleProduct();
        idBicycleProduct.setIdBicycle(idBicycle);
        idBicycleProduct.setIdBicycleColor(idBicycleColor);
        idBicycleProduct.setIdBicycleSize(idBicycleSize);
        Optional<BicycleProduct> bicycleProduct = bicycleProductService.getBicycyleProductById(idBicycleProduct);
        boolean checkValid = bicycleProductService.checkExistIdBicycleProduct(idBicycleProduct);
        if(!checkValid || bicycleProduct.get().getRemainQuantity() < quantity) {
            response = BaseResponse.builder().code(200).status("success").data(false).message("This product is currently out of stock. Please choose another product!").build();
            return ResponseEntity.ok(response);
        }
        response = BaseResponse.builder().code(200).status("success").data(true).message("Valid").build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut(@RequestBody OrderModel orderModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.getUserByEmail(email).get();
        Order order = Order.builder()
                .user(user)
                .fullName(orderModel.getFullName())
                .phoneNumber(orderModel.getPhoneNumber())
                .totalQuantity(orderModel.getTotalQuantity())
                .totalPrice(orderModel.getTotalPrice())
                .shipPrice(orderModel.getShipPrice())
                .dayOrdered(LocalDateTime.now())
                .shipDay(LocalDateTime.now())
                .shipAddress(orderModel.getShipAddress())
                .message(orderModel.getMessage())
                .orderState(OrderState.WAITING)
                .payMethod(payMethodService.getById(orderModel.getIdPayMethod()).get())
                .build();
        Order newOrder = orderService.saveOrder(order);
        for(BicycleProductModel bicycleProductModel : orderModel.getBicycleProductModels()) {
            IdOrderDetail idOrderDetail = new IdOrderDetail();
            BeanUtils.copyProperties(bicycleProductModel, idOrderDetail);
            idOrderDetail.setIdOrder(newOrder.getIdOrder());
            OrderDetail orderDetail = OrderDetail.builder().idOrderDetail(idOrderDetail).quantity(bicycleProductModel.getQuantity()).build();
            orderDetailService.saveOrderDetail(orderDetail);
        }
        BaseResponse response = BaseResponse.builder().code(200).status("success").message("Order Successfully!").build();
        return ResponseEntity.ok(response);
    }
}
