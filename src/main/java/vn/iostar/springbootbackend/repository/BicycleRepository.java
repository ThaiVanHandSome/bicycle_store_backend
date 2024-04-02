package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iostar.springbootbackend.entity.Bicycle;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, Long> {
}