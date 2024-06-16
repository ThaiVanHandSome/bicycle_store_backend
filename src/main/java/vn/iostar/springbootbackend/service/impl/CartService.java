package vn.iostar.springbootbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.entity.Cart;
import vn.iostar.springbootbackend.entity.CartDetail;
import vn.iostar.springbootbackend.entity.User;
import vn.iostar.springbootbackend.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Optional<Cart> getCartByUser(User user) {
        return cartRepository.findByUser(user);
    }
}
