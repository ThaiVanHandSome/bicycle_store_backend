package vn.iostar.springbootbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bicycles")
public class Bicycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bicycle")
    private Long idBicycle;

    @Column(name = "name", columnDefinition = "nvarchar(1000)")
    private String name;

    @Column(name = "description", columnDefinition = "nvarchar(10000)")
    private String description;

    @Column(name = "frame", columnDefinition = "nvarchar(1000)")
    private String frame;

    @Column(name = "fork", columnDefinition = "nvarchar(1000)")
    private String fork;

    @Column(name = "rims", columnDefinition = "nvarchar(1000)")
    private String rims;

    @Column(name = "hubs", columnDefinition = "nvarchar(1000)")
    private String hubs;

    @Column(name = "spokes", columnDefinition = "nvarchar(1000)")
    private String spokes;

    @Column(name = "tires", columnDefinition = "nvarchar(1000)")
    private String tires;

    @Column(name = "handlebar", columnDefinition = "nvarchar(1000)")
    private String handlebar;

    @Column(name = "price")
    private Long price;

    @OneToMany(mappedBy = "bicycle")
    private List<BicycleProduct> bicycleProducts;

    @OneToMany(mappedBy = "bicycle")
    private List<BicyclesOfCategory> bicyclesOfCategory;

    @OneToMany(mappedBy = "bicycle")
    private List<BicycleImage> bicycleImages;

    @OneToMany(mappedBy = "bicycle")
    private List<BicycleComment> bicycleComments;
}
