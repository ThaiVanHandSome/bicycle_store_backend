package vn.iostar.springbootbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.repository.BicycleRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BicycleService {
    private BicycleRepository bicycleRepository;

    public Optional<Bicycle> getBicycleById(Long id) {
        return bicycleRepository.findById(id);
    }

    public List<Bicycle> getAllBicycles() {
        return bicycleRepository.findAll();
    }

    public List<Bicycle> getBicyclesWithSortingAscending(String field) {
        return bicycleRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    public List<Bicycle> getBicyclesWithSortingDescending(String field) {
        return bicycleRepository.findAll(Sort.by(Sort.Direction.DESC, field));
    }

    public List<Bicycle> getBicyclesLessOrEqualThan(Long price) {
        return bicycleRepository.findByPriceLessThanEqual(price);
    }

    public Page<Bicycle> getBicyclesWithPagination(int offset, int pageSize) {
        Page<Bicycle> bicycles = bicycleRepository.findAll(PageRequest.of(offset, pageSize));
        return bicycles;
    }

    public Page<Bicycle> getBicyclesWithPaginationAndSorting(int offset, int pageSize, String field, String type) {
        Page<Bicycle> bicycles;
        if(type.equals("asc")) {
             bicycles = bicycleRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(Sort.Direction.ASC, field)));
        } else {
            bicycles = bicycleRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(Sort.Direction.DESC, field)));
        }
        return bicycles;
    }

}
