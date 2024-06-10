package vn.iostar.springbootbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iostar.springbootbackend.embeddedId.IdBicycleProduct;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bicycle_products")
public class BicycleProduct {

    @EmbeddedId
    private IdBicycleProduct idBicycleProduct;

    @Column(name = "remain_quantity")
    private int remainQuantity;

    @OneToMany(mappedBy = "bicycleProduct", cascade = CascadeType.ALL)
    private List<CartDetail> cartDetails;

    @OneToMany(mappedBy = "bicycleProduct", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_bicycle", referencedColumnName = "id_bicycle", insertable = false, updatable = false)
    private Bicycle bicycle;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_bicycle_color", referencedColumnName = "id_bicycle_color", insertable = false, updatable = false)
    private BicycleColor bicycleColor;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_bicycle_size", referencedColumnName = "id_bicycle_size", insertable = false, updatable = false)
    private BicycleSize bicycleSize;

}
