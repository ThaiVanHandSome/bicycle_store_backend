package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iostar.springbootbackend.embeddedId.IdBicyclesOfCategory;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.BicycleCategory;

import java.util.List;

@Repository
public interface BicycleCategoryRepository extends JpaRepository<BicycleCategory, Long> {
}
