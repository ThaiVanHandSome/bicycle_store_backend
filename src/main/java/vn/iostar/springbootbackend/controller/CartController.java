package vn.iostar.springbootbackend.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import vn.iostar.springbootbackend.embeddedId.IdBicycleProduct;
import vn.iostar.springbootbackend.embeddedId.IdCartDetail;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.Cart;
import vn.iostar.springbootbackend.entity.CartDetail;
import vn.iostar.springbootbackend.entity.User;
import vn.iostar.springbootbackend.model.BicycleColorModel;
import vn.iostar.springbootbackend.model.BicycleModel;
import vn.iostar.springbootbackend.model.BicycleSizeModel;
import vn.iostar.springbootbackend.model.CartResponse;
import vn.iostar.springbootbackend.model.response.BaseResponse;
import vn.iostar.springbootbackend.service.impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CartController {
    @Autowired
    private BicycleProductService bicycleProductService;

    @Autowired
    private BicycleService bicycleService;

    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<?> checkExist(@RequestBody IdBicycleProduct idBicycleProduct) {
        boolean checkExist = bicycleProductService.checkExistIdBicycleProduct(idBicycleProduct);
        BaseResponse response = new BaseResponse();
        if (!checkExist) {
            response = BaseResponse.builder().code(200).status("success").data(false).message("Sản phẩm này hiện đã hết hàng. Quý khách vui lòng chọn sản phẩm khác!").build();
            return ResponseEntity.ok(response);
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String email = userDetails.getUsername();
                User user = userService.getUserByEmail(email).get();
                Optional<Cart> optCart = cartService.getCartByUser(user);
                if (optCart.isPresent()) {
                    IdCartDetail idCartDetail = new IdCartDetail();
                    BeanUtils.copyProperties(idBicycleProduct, idCartDetail);
                    idCartDetail.setIdCart(optCart.get().getIdCart());
                    boolean checkExistedInCart = cartDetailService.checkExistByIdCartDetail(idCartDetail);
                    if (checkExistedInCart) {
                        response = BaseResponse.builder().code(200).status("success").data(false).message("Sản phẩm này hiện đã có trong giỏ hàng!").build();
                    } else {
                        CartDetail cartDetail = new CartDetail();
                        cartDetail.setIdCartDetail(idCartDetail);
                        cartDetailService.addToCart(cartDetail);
                        response = BaseResponse.builder().code(200).status("success").data(true).message("Thêm sản phẩm vào giỏ hàng thành công!").build();
                    }
                } else {
                    response = BaseResponse.builder().code(401).status("error").data(false).message("Cart does not exist!").build();
                }

            } else {
                response = BaseResponse.builder().code(401).status("not-author").data(false).message("You do not authorize!").build();
            }

        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BaseResponse response = new BaseResponse();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            String email = ((UserDetails) authentication.getPrincipal()).getUsername();
            User user = userService.getUserByEmail(email).get();
            Optional<Cart> optCart = cartService.getCartByUser(user);
            if (optCart.isPresent()) {
                List<CartDetail> cartDetailList = cartDetailService.getAllProductsByIdCart(optCart.get().getIdCart());
                List<CartResponse> cartResponses = new ArrayList<>();
                for(CartDetail cartDetail : cartDetailList) {
                    BicycleModel bicycleModel = new BicycleModel();
                    BeanUtils.copyProperties(cartDetail.getBicycleProduct().getBicycle(), bicycleModel);
                    BicycleSizeModel bicycleSizeModel = new BicycleSizeModel();
                    BeanUtils.copyProperties(cartDetail.getBicycleProduct().getBicycleSize(), bicycleSizeModel);
                    BicycleColorModel bicycleColorModel = new BicycleColorModel();
                    BeanUtils.copyProperties(cartDetail.getBicycleProduct().getBicycleColor(), bicycleColorModel);
                    CartResponse cartResponse = CartResponse.builder().bicycle(bicycleModel).bicycleSize(bicycleSizeModel).bicycleColor(bicycleColorModel).build();
                    cartResponse.setQuantity(cartDetail.getBicycleProduct().getRemainQuantity());
                    cartResponses.add(cartResponse);
                }
                response = BaseResponse.builder().code(200).status("success").data(cartResponses).message("Get All Products Of Cart Successfully!").build();
                return ResponseEntity.ok(response);
            }
            response = BaseResponse.builder().status("error").code(400).message("Cart not found!").build();
            return ResponseEntity.ok(response);
        }
        response = BaseResponse.builder().status("not-author").code(401).message("You do not authorize!").build();
        return ResponseEntity.ok(response);
    }
}
