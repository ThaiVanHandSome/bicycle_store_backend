package vn.iostar.springbootbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long idOrder;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "total_quantity")
    private Long totalQuantity;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "ship_price")
    private Long shipPrice;

    @Column(name = "day_ordered")
    private LocalDateTime dayOrdered;

    @Column(name = "ship_day")
    private LocalDateTime shipDay;

    @Column(name = "ship_address", columnDefinition = "nvarchar(1000)")
    private String shipAddress;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_pay_method", referencedColumnName = "id_pay_method")
    private PayMethod payMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

}
