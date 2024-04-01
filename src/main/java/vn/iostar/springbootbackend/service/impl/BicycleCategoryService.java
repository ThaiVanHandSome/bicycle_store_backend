package vn.iostar.springbootbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.entity.BicycleCategory;
import vn.iostar.springbootbackend.repository.BicycleCategoryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BicycleCategoryService {
    private BicycleCategoryRepository bicycleCategoryRepository;

    public List<BicycleCategory> findAll() {
        return bicycleCategoryRepository.findAll();
    }

}
