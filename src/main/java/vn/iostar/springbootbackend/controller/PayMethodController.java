package vn.iostar.springbootbackend.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iostar.springbootbackend.entity.PayMethod;
import vn.iostar.springbootbackend.model.PayMethodModel;
import vn.iostar.springbootbackend.model.response.BaseResponse;
import vn.iostar.springbootbackend.service.impl.PayMethodService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PayMethodController {

    @Autowired
    private PayMethodService payMethodService;

    @GetMapping("/pay-methods")
    public ResponseEntity<?> findAllPayMethods() {
        List<PayMethod> payMethods = payMethodService.getAllPayMethods();
        List<PayMethodModel> payMethodModels = new ArrayList<>();
        for(PayMethod payMethod : payMethods) {
            PayMethodModel payMethodModel = new PayMethodModel();
            BeanUtils.copyProperties(payMethod, payMethodModel);
            payMethodModels.add(payMethodModel);
        }
        BaseResponse response = BaseResponse.builder().code(200).status("success").message("Get all pay methods successfully!").data(payMethodModels).build();
        return ResponseEntity.ok(response);
    }
}
