package vn.iostar.springbootbackend.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.BicycleCategory;
import vn.iostar.springbootbackend.entity.BicyclesOfCategory;
import vn.iostar.springbootbackend.model.BicycleCategoryModel;
import vn.iostar.springbootbackend.model.Response;
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
        Response res = new Response();
        res.setStatus(200);
        res.setMessage("Get Categories Successfully");
        res.setData(listCategories);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/category/{id}/bicycles")
    public ResponseEntity<?> getBicyclesByCategory(@PathVariable("id") Long id) {
        List<BicyclesOfCategory> bicyclesOfCategory = bicyclesOfCategoryService.getBicyclesByCategory(id);
        List<Bicycle> bicycles = new ArrayList<>();
        for (BicyclesOfCategory bicycleOfCategory : bicyclesOfCategory) {
            Bicycle bicycle = bicycleOfCategory.getBicycle();
            bicycles.add(bicycle);
        }
        Response res = new Response();
        res.setStatus(200);
        res.setMessage("Get Bicycles Of Category Successfully!");
        res.setData(bicycles);
        return ResponseEntity.ok(res);
    }
}
