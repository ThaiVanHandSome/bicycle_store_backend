package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iostar.springbootbackend.entity.BicycleSize;

@Repository
public interface BicycleSizeRepository extends JpaRepository<BicycleSize, Long> {
}
