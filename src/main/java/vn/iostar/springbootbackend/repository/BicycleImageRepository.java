package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.BicycleThumbnail;

import java.util.List;

@Repository
public interface BicycleImageRepository extends JpaRepository<BicycleThumbnail, Long> {
    List<BicycleThumbnail> findByBicycle(Bicycle bicycle);
}
