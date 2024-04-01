package vn.iostar.springbootbackend.embeddedId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IdBicyclesOfCategory implements Serializable {
    @Column(name = "id_bicycle")
    private Long idBicycle;

    @Column(name = "id_bicycle_category")
    private Long idBicycleCategory;
}
