package vn.iostar.springbootbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.embeddedId.IdBicycleProduct;
import vn.iostar.springbootbackend.repository.BicycleProductRepository;

@Service
@AllArgsConstructor
public class BicycleProductService {
    private BicycleProductRepository bicycleProductRepository;

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
