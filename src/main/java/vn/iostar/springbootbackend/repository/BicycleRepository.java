package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.iostar.springbootbackend.entity.Bicycle;

import java.util.List;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, Long> {
    List<Bicycle> findByPriceLessThanEqual(Long price);

    @Query(value = "SELECT * " +
            "FROM bicycles " +
            "WHERE LOWER(name) LIKE CONCAT('%', LOWER(:name), '%') COLLATE utf8mb4_unicode_ci", nativeQuery = true)
    List<Bicycle> findByNameIgnoreCaseAndIgnoreAccents(String name);
}
