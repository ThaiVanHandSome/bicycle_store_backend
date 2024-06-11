package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iostar.springbootbackend.embeddedId.IdCartDetail;
import vn.iostar.springbootbackend.entity.CartDetail;

public interface CartDetailRepository extends JpaRepository<CartDetail, IdCartDetail> {
    boolean existsByIdCartDetail(IdCartDetail idCartDetail);
}
