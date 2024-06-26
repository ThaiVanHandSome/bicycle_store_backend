package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.iostar.springbootbackend.embeddedId.IdBicyclesOfCategory;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.BicycleCategory;
import vn.iostar.springbootbackend.entity.BicyclesOfCategory;

import java.util.List;

@Repository
public interface BicyclesOfCategoryRepository extends JpaRepository<BicyclesOfCategory, IdBicyclesOfCategory> {
    List<BicyclesOfCategory> findByIdBicyclesOfCategory_IdBicycleCategory(Long idCategory);

    @Query("SELECT COUNT(bc) FROM BicyclesOfCategory bc WHERE bc.bicycleCategory = :category")
    int countByBicycleCategory(BicycleCategory category);

    boolean existsByIdBicyclesOfCategory(IdBicyclesOfCategory idBicyclesOfCategory);

    List<BicyclesOfCategory> findByIdBicyclesOfCategory_IdBicycle(Long idBicycle);

    @Query("SELECT b FROM BicyclesOfCategory b WHERE b.idBicyclesOfCategory.idBicycle != :idBicycle AND b.idBicyclesOfCategory.idBicycleCategory = :idBicycleCategory")
    List<BicyclesOfCategory> findByIdBicycleNotAndIdBicycleCategory(Long idBicycle, Long idBicycleCategory);
}
