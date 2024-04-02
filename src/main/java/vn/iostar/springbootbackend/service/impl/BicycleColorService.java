package vn.iostar.springbootbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.entity.BicycleColor;
import vn.iostar.springbootbackend.repository.BicycleColorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BicycleColorService {
    private BicycleColorRepository bicycleColorRepository;

    public List<BicycleColor> getAllColors() {
        return bicycleColorRepository.findAll();
    }
}
