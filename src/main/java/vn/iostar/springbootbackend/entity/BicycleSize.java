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
@Table(name = "bicycle_sizes")
public class BicycleSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bicycle_size")
    private Long idBicycleSize;

    @Column(name = "name", columnDefinition = "nvarchar(1000)")
    private String name;

    @OneToMany(mappedBy = "bicycleSize", cascade = CascadeType.ALL)
    private List<BicycleProduct> bicycleProducts;
}
