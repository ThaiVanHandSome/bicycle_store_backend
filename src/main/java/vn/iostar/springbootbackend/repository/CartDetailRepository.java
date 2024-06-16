package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iostar.springbootbackend.embeddedId.IdCartDetail;
import vn.iostar.springbootbackend.entity.CartDetail;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, IdCartDetail> {
    boolean existsByIdCartDetail(IdCartDetail idCartDetail);

    public List<CartDetail> findByIdCartDetail_IdCart(Long idCart);
}
