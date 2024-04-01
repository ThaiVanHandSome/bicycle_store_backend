package vn.iostar.springbootbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.BicycleImage;
import vn.iostar.springbootbackend.model.Response;
import vn.iostar.springbootbackend.service.impl.BicycleImageService;
import vn.iostar.springbootbackend.service.impl.BicycleService;

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

}
