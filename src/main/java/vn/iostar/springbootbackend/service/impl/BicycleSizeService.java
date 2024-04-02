package vn.iostar.springbootbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.entity.BicycleSize;
import vn.iostar.springbootbackend.repository.BicycleSizeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BicycleSizeService {
    private BicycleSizeRepository bicycleSizeRepository;

    public List<BicycleSize> getAllSizes() {
        return bicycleSizeRepository.findAll();
    }
}
