package vn.iostar.springbootbackend.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.BicycleColor;
import vn.iostar.springbootbackend.entity.BicycleImage;
import vn.iostar.springbootbackend.entity.BicycleSize;
import vn.iostar.springbootbackend.model.BicycleColorModel;
import vn.iostar.springbootbackend.model.BicycleSizeModel;
import vn.iostar.springbootbackend.model.ProductsResponse;
import vn.iostar.springbootbackend.model.Response;
import vn.iostar.springbootbackend.service.impl.BicycleColorService;
import vn.iostar.springbootbackend.service.impl.BicycleImageService;
import vn.iostar.springbootbackend.service.impl.BicycleService;
import vn.iostar.springbootbackend.service.impl.BicycleSizeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BicycleController {
    @Autowired
    private BicycleImageService bicycleImageService;

    @Autowired
    private BicycleService bicycleService;

    @Autowired
    private BicycleSizeService bicycleSizeService;

    @Autowired
    private BicycleColorService bicycleColorService;

    @GetMapping("/bicycle/{id}/images")
    public ResponseEntity<?> getImagesByBicycle(@PathVariable("id") Long id) {
        Optional<Bicycle> opt = bicycleService.getBicycleById(id);
        if(opt.isPresent()) {
            List<BicycleImage> bicycleImages = bicycleImageService.getImagesByBicycle(opt.get());
            Response res = new Response();
            res.setStatus(200);
            res.setMessage("Get Images Successfully!");
            res.setData(bicycleImages);
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/bicycle/sizes")
    public ResponseEntity<?> getAllSizes() {
        List<BicycleSize> sizes = bicycleSizeService.getAllSizes();
        List<BicycleSizeModel> listSizes = new ArrayList<>();
        for(BicycleSize size : sizes) {
            BicycleSizeModel model = new BicycleSizeModel();
            BeanUtils.copyProperties(size, model);
            listSizes.add(model);
        }
        Response res = new Response();
        res.setStatus(200);
        res.setMessage("Get All Sizes Successfully!");
        res.setData(listSizes);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/bicycle/colors")
    public ResponseEntity<?> getAllColors() {
        List<BicycleColor> colors = bicycleColorService.getAllColors();
        List<BicycleColorModel> listColors = new ArrayList<>();
        for(BicycleColor color : colors) {
            BicycleColorModel model = new BicycleColorModel();
            BeanUtils.copyProperties(color, model);
            listColors.add(model);
        }
        Response res = new Response();
        res.setStatus(200);
        res.setMessage("Get All Colors Successfully!");
        res.setData(listColors);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/bicycles")
    public ResponseEntity<?> getAllBicycles() {
        List<Bicycle> bicycles = bicycleService.getAllBicycles();
        ProductsResponse proRes = new ProductsResponse();
        proRes.setData(bicycles);
        proRes.setMessage("Get Bicycles Successfully!");
        proRes.setStatus(200);
        proRes.setProductsCount(bicycles.size());
        return ResponseEntity.ok(proRes);
    }

    @GetMapping("/bicycles/pagination/{offset}/{pageSize}")
    public ResponseEntity<?> getBicyclesWithPagination(@PathVariable("offset") int offset, @PathVariable("pageSize") int pageSize) {
        Page<Bicycle> bicycles = bicycleService.getBicyclesWithPagination(offset, pageSize);
        ProductsResponse proRes = new ProductsResponse();
        proRes.setProductsCount(bicycles.getSize());
        proRes.setData(bicycles);
        proRes.setMessage("Get Bicycles Successfully!");
        proRes.setStatus(200);
        return ResponseEntity.ok(proRes);
    }

    @GetMapping("/bicycles/paginationAndSort/{type}/{offset}/{pageSize}/{field}")
    public ResponseEntity<?> getBicyclesWithPaginationAndSorting(@PathVariable("type") String type,@PathVariable("offset") int offset, @PathVariable("pageSize") int pageSize, @PathVariable("field") String field) {
        Page<Bicycle> bicycles = bicycleService.getBicyclesWithPaginationAndSorting(offset, pageSize, field, type);
        ProductsResponse proRes = new ProductsResponse();
        proRes.setProductsCount(bicycles.getSize());
        proRes.setData(bicycles);
        proRes.setMessage("Get Bicycles Successfully!");
        proRes.setStatus(200);
        return ResponseEntity.ok(proRes);
    }

}
