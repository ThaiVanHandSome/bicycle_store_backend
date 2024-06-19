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
@Table(name = "pay_method")
public class PayMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pay_method")
    private Long idPayMethod;

    @Column(name = "name", columnDefinition = "nvarchar(100)")
    private String name;

    @OneToMany(mappedBy = "payMethod")
    private List<Order> orders;
}
