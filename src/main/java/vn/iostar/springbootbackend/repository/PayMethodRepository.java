package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iostar.springbootbackend.entity.PayMethod;

public interface PayMethodRepository extends JpaRepository<PayMethod, Long> {
}
