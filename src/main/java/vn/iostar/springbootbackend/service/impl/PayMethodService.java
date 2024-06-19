package vn.iostar.springbootbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.entity.PayMethod;
import vn.iostar.springbootbackend.repository.PayMethodRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PayMethodService {
    @Autowired
    private PayMethodRepository payMethodRepository;

    public List<PayMethod> getAllPayMethods() {
        return payMethodRepository.findAll();
    }

    public Optional<PayMethod> getById(Long idPayMethod) {
        return payMethodRepository.findById(idPayMethod);
    }
}
