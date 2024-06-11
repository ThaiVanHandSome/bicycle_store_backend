package vn.iostar.springbootbackend.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iostar.springbootbackend.embeddedId.IdBicycleProduct;
import vn.iostar.springbootbackend.embeddedId.IdCartDetail;
import vn.iostar.springbootbackend.entity.CartDetail;
import vn.iostar.springbootbackend.model.response.BaseResponse;
import vn.iostar.springbootbackend.service.impl.BicycleProductService;
import vn.iostar.springbootbackend.service.impl.CartDetailService;

@RestController
@RequestMapping("/api/v1")
public class CartController {
    @Autowired
    private BicycleProductService bicycleProductService;

    @Autowired
    private CartDetailService cartDetailService;

    // đang test, sau này sẽ viết lại
    // lấy user từ SecurityContextHolder, và RequestBody truyền là IdBicycleProduct
    @PostMapping("/cart/add")
    public ResponseEntity<?> checkExist(@RequestBody IdCartDetail idCartDetail) {
        IdBicycleProduct idBicycleProduct = new IdBicycleProduct();
        BeanUtils.copyProperties(idCartDetail, idBicycleProduct);
        boolean checkExist = bicycleProductService.checkExistIdBicycleProduct(idBicycleProduct);
        BaseResponse response = new BaseResponse();
        if (!checkExist) {
            response = BaseResponse.builder().code(200).status("success").data(false).message("Sản phẩm này hiện đã hết hàng. Quý khách vui lòng chọn sản phẩm khác!").build();
            return ResponseEntity.ok(response);
        } else {
            boolean checkExistedInCart = cartDetailService.checkExistByIdCartDetail(idCartDetail);
            if (checkExistedInCart) {
                response = BaseResponse.builder().code(200).status("success").data(false).message("Sản phẩm này hiện đã có trong giỏ hàng!").build();
            } else {
                CartDetail cartDetail = new CartDetail();
                cartDetail.setIdCartDetail(idCartDetail);
                cartDetailService.addToCart(cartDetail);
                response = BaseResponse.builder().code(200).status("success").data(true).message("Thêm sản phẩm vào giỏ hàng thành công!").build();
            }
        }
        return ResponseEntity.ok(response);
    }
}
