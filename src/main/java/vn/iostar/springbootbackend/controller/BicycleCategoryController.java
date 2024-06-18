package vn.iostar.springbootbackend.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.BicycleCategory;
import vn.iostar.springbootbackend.entity.BicyclesOfCategory;
import vn.iostar.springbootbackend.model.BicycleCategoryModel;
import vn.iostar.springbootbackend.model.BicycleModel;
import vn.iostar.springbootbackend.model.response.BaseResponse;
import vn.iostar.springbootbackend.service.impl.BicycleCategoryService;
import vn.iostar.springbootbackend.service.impl.BicyclesOfCategoryService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BicycleCategoryController {
    @Autowired
    private BicycleCategoryService bicycleCategoryService;

    @Autowired
    private BicyclesOfCategoryService bicyclesOfCategoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        List<BicycleCategory> categories = bicycleCategoryService.findAll();
        List<BicycleCategoryModel> listCategories = new ArrayList<>();
        for(BicycleCategory category : categories) {
            BicycleCategoryModel model = new BicycleCategoryModel();
            BeanUtils.copyProperties(category, model);
            int cnt = bicyclesOfCategoryService.getCountBicyclesOfCategory(category);
            model.setCountOfBicycles(cnt);
            listCategories.add(model);
        }
        BaseResponse response = BaseResponse.builder().status("success").code(200).data(listCategories).message("Get all categories successfully!").errors(null).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{id}/bicycles")
    public ResponseEntity<?> getBicyclesByCategory(@PathVariable("id") Long id) {
        List<BicyclesOfCategory> bicyclesOfCategory = bicyclesOfCategoryService.getBicyclesByCategory(id);
        List<BicycleModel> bicyclesResponse = new ArrayList<>();
        for (BicyclesOfCategory bicycleOfCategory : bicyclesOfCategory) {
            Bicycle bicycle = bicycleOfCategory.getBicycle();
            BicycleModel bicycleModel = new BicycleModel();
            BeanUtils.copyProperties(bicycle, bicycleModel);
            bicyclesResponse.add(bicycleModel);
        }
        BaseResponse response = BaseResponse.builder().status("success").code(200).data(bicyclesResponse).message("Get all bicycles of category successfully!").build();
        return ResponseEntity.ok(response);
    }
}
