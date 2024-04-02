package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iostar.springbootbackend.entity.BicycleColor;

@Repository
public interface BicycleColorRepository extends JpaRepository<BicycleColor, Long> {
}
