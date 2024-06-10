package vn.iostar.springbootbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.entity.BicycleCategory;
import vn.iostar.springbootbackend.repository.BicycleCategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BicycleCategoryService {
    private BicycleCategoryRepository bicycleCategoryRepository;

    public List<BicycleCategory> findAll() {
        return bicycleCategoryRepository.findAll();
    }

    public Optional<BicycleCategory> getCategoryById(Long idBicycleCategory) {
        return bicycleCategoryRepository.findById(idBicycleCategory);
    }

}
