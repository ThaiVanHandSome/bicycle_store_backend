package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iostar.springbootbackend.entity.BicyclesOfCategory;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BicycleCategoryModel {
    private Long idBicycleCategory;
    private String name;
    private List<BicyclesOfCategory> bicyclesOfCategory;
    private int countOfBicycles;
}
