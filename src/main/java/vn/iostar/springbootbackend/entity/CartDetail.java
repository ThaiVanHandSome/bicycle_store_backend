package vn.iostar.springbootbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iostar.springbootbackend.embeddedId.IdCartDetail;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_detail")
public class CartDetail {
    @EmbeddedId
    private IdCartDetail idCartDetail;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_cart", referencedColumnName = "id_cart", insertable = false, updatable = false)
    private Cart cart;

//    @ManyToOne
//    @JsonBackReference
//    @JoinColumn(name = "idBicycleProduct", referencedColumnName = "idBicycleProduct")
//    private BicycleProduct bicycleProduct;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_bicycle", referencedColumnName = "id_bicycle", insertable = false, updatable = false),
            @JoinColumn(name = "id_bicycle_size", referencedColumnName = "id_bicycle_size", insertable = false, updatable = false),
            @JoinColumn(name = "id_bicycle_color", referencedColumnName = "id_bicycle_color", insertable = false, updatable = false)
    })
    @JsonBackReference
    private BicycleProduct bicycleProduct;
}
