package vn.iostar.springbootbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long idOrder;

    @Column(name = "total_quantity")
    private Long totalQuantity;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "day_ordered")
    private LocalDateTime dayOrdered;

    @Column(name = "ship_day")
    private LocalDateTime shipDay;

    @Column(name = "ship_address", columnDefinition = "nvarchar(1000)")
    private String shipAddress;

    @Column(name = "order_state")
    private int orderState;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

}
