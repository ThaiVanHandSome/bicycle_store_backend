package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iostar.springbootbackend.entity.Cart;
import vn.iostar.springbootbackend.entity.CartDetail;
import vn.iostar.springbootbackend.entity.User;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    public Optional<Cart> findByUser(User user);
}
