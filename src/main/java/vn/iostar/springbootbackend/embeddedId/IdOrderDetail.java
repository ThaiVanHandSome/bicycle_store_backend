package vn.iostar.springbootbackend.embeddedId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IdOrderDetail implements Serializable {
    @Column(name = "id_order")
    private Long idOrder;

    @Column(name = "id_bicycle")
    private Long idBicycle;

    @Column(name = "id_bicycle_size")
    private Long idBicycleSize;

    @Column(name = "id_bicycle_color")
    private Long idBicycleColor;
}
