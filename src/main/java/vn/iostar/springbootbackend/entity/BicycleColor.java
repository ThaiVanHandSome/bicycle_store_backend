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
@Table(name = "bicycle_colors")
public class BicycleColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bicycle_color")
    private Long idBicycleColor;

    @Column(name = "name", columnDefinition = "nvarchar(1000)")
    private String name;

    @OneToMany(mappedBy = "bicycleColor", cascade = CascadeType.ALL)
    private List<BicycleProduct> bicycleProducts;
}
