package vn.iostar.springbootbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.embeddedId.IdBicyclesOfCategory;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.BicycleCategory;
import vn.iostar.springbootbackend.entity.BicyclesOfCategory;
import vn.iostar.springbootbackend.repository.BicycleCategoryRepository;
import vn.iostar.springbootbackend.repository.BicyclesOfCategoryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BicyclesOfCategoryService {
    private BicyclesOfCategoryRepository bicyclesOfCategoryRepository;

    public List<BicyclesOfCategory> getBicyclesByCategory(Long idCategory) {
        return bicyclesOfCategoryRepository.findByIdBicyclesOfCategory_IdBicycleCategory(idCategory);
    }

    public int getCountBicyclesOfCategory(BicycleCategory category) {
        return bicyclesOfCategoryRepository.countByBicycleCategory(category);
    }

    public boolean checkBicyclesOfCategoryExist(IdBicyclesOfCategory idBicyclesOfCategory) {
        return bicyclesOfCategoryRepository.existsByIdBicyclesOfCategory(idBicyclesOfCategory);
    }

    public List<BicyclesOfCategory> getAllCategoriesOfBicycle(Long idBicycle) {
        return bicyclesOfCategoryRepository.findByIdBicyclesOfCategory_IdBicycle(idBicycle);
    }

    public List<BicyclesOfCategory> findByIdBicycleNotAndIdBicycleCategory(Long idBicycle, Long idBicycleCategory) {
        return bicyclesOfCategoryRepository.findByIdBicycleNotAndIdBicycleCategory(idBicycle, idBicycleCategory);
    }
}
