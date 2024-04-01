package vn.iostar.springbootbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bicycle_images")
public class BicycleImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bicycle_image")
    private Long idBicycleImage;

    @Column(name = "source", columnDefinition = "varchar(10000)")
    private String source;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_bicycle", referencedColumnName = "id_bicycle", insertable = false, updatable = false)
    private Bicycle bicycle;
}
