package vn.iostar.springbootbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iostar.springbootbackend.embeddedId.IdOrderDetail;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@Entity(name = "order_detail")
public class OrderDetail {
    @EmbeddedId
    private IdOrderDetail idOrderDetail;

    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_order", referencedColumnName = "id_order", insertable = false, updatable = false)
    private Order order;


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_bicycle", referencedColumnName = "id_bicycle", insertable = false, updatable = false),
            @JoinColumn(name = "id_bicycle_size", referencedColumnName = "id_bicycle_size", insertable = false, updatable = false),
            @JoinColumn(name = "id_bicycle_color", referencedColumnName = "id_bicycle_color", insertable = false, updatable = false)
    })
    @JsonBackReference
    private BicycleProduct bicycleProduct;
}
