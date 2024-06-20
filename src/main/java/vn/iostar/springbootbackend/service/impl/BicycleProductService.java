package vn.iostar.springbootbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.embeddedId.IdBicycleProduct;
import vn.iostar.springbootbackend.entity.BicycleProduct;
import vn.iostar.springbootbackend.repository.BicycleProductRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BicycleProductService {
    private BicycleProductRepository bicycleProductRepository;

    public Optional<BicycleProduct> getBicycyleProductById(IdBicycleProduct idBicycleProduct) {
        return bicycleProductRepository.findById(idBicycleProduct);
    }

    public boolean checkExistIdBicycleProduct(IdBicycleProduct idBicycleProduct) {
        return bicycleProductRepository.existsByIdBicycleProduct(idBicycleProduct);
    }

    public boolean checkExistByBicycleAndSize(Long idBicycle, Long idSize) {
        return bicycleProductRepository.existsByIdBicycleProduct_IdBicycleAndIdBicycleProduct_IdBicycleSize(idBicycle, idSize);
    }

    public boolean checkExistByBicycleAndColor(Long idBicycle, Long idColor) {
        return bicycleProductRepository.existsByIdBicycleProduct_IdBicycleAndIdBicycleProduct_IdBicycleColor(idBicycle, idColor);
    }
}
