package vn.iostar.springbootbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iostar.springbootbackend.embeddedId.IdBicyclesOfCategory;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bicycles_of_category")
public class BicyclesOfCategory {

    @EmbeddedId
    private IdBicyclesOfCategory idBicyclesOfCategory;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_bicycle", referencedColumnName = "id_bicycle", insertable = false, updatable = false)
    private Bicycle bicycle;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_bicycle_category", referencedColumnName = "id_bicycle_category", insertable = false, updatable = false)
    private BicycleCategory bicycleCategory;
}
