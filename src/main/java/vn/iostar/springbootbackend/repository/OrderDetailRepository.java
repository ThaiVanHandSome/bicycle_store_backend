package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iostar.springbootbackend.embeddedId.IdOrderDetail;
import vn.iostar.springbootbackend.entity.OrderDetail;
import vn.iostar.springbootbackend.entity.User;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    public List<OrderDetail> findByIdOrderDetail_IdOrder(Long idOrder);
}
