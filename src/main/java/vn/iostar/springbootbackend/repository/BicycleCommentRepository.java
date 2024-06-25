package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.BicycleComment;

import java.util.List;

public interface BicycleCommentRepository extends JpaRepository<BicycleComment, Long> {
    public List<BicycleComment> findByBicycle(Bicycle bicycle);
}
