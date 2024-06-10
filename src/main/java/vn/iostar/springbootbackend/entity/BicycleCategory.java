package vn.iostar.springbootbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bicycle_categories")
public class BicycleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bicycle_category")
    private Long idBicycleCategory;

    @Column(name = "name", columnDefinition = "nvarchar(1000)")
    private String name;

    @OneToMany(mappedBy = "bicycleCategory", cascade = CascadeType.ALL)
    private List<BicyclesOfCategory> bicyclesOfCategory;
}
