package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iostar.springbootbackend.embeddedId.IdBicycleProduct;
import vn.iostar.springbootbackend.entity.BicycleProduct;

import java.util.List;

public interface BicycleProductRepository extends JpaRepository<BicycleProduct, Long> {
    public boolean existsByIdBicycleProduct(IdBicycleProduct idBicycleProduct);

    boolean existsByIdBicycleProduct_IdBicycleAndIdBicycleProduct_IdBicycleSize(Long idBicycle, Long idBicycleSize);

    boolean existsByIdBicycleProduct_IdBicycleAndIdBicycleProduct_IdBicycleColor(Long idBicycle, Long idBicycleColor);
}
