package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BicycleFilterModel {
    private List<Long> bicycleColorsId;
    private List<Long> bicycleSizesId;
    private List<Long> bicycleCategoriesId;
    private Long maxPrice;
}
