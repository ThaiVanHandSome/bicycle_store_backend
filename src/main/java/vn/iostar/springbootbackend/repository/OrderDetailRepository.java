package vn.iostar.springbootbackend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import vn.iostar.springbootbackend.embeddedId.IdOrderDetail;
import vn.iostar.springbootbackend.entity.OrderDetail;
import vn.iostar.springbootbackend.entity.User;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, IdOrderDetail> {
    public List<OrderDetail> findByIdOrderDetail_IdOrder(Long idOrder);
}
