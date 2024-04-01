package vn.iostar.springbootbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.BicycleImage;
import vn.iostar.springbootbackend.repository.BicycleImageRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BicycleImageService {
    private BicycleImageRepository bicycleImageRepository;

    public List<BicycleImage> getImagesByBicycle(Bicycle bicycle) {
        return bicycleImageRepository.findByBicycle(bicycle);
    }
}
