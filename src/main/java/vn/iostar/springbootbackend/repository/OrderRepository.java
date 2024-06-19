package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iostar.springbootbackend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
