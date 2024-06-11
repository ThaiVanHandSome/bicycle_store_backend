package vn.iostar.springbootbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.embeddedId.IdCartDetail;
import vn.iostar.springbootbackend.entity.CartDetail;
import vn.iostar.springbootbackend.repository.CartDetailRepository;

@Service
public class CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    public boolean checkExistByIdCartDetail(IdCartDetail idCartDetail) {
        return cartDetailRepository.existsByIdCartDetail(idCartDetail);
    }

    public void addToCart(CartDetail cartDetail) {
        cartDetailRepository.save(cartDetail);
    }
}
