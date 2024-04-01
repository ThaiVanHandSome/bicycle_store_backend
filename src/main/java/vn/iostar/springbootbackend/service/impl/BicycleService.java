package vn.iostar.springbootbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.repository.BicycleRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BicycleService {
    private BicycleRepository bicycleRepository;

    public Optional<Bicycle> getBicycleById(Long id) {
        return bicycleRepository.findById(id);
    }

}
