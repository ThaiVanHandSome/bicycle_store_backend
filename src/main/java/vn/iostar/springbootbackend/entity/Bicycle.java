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

    @Column(name = "image", columnDefinition = "varchar(2000)")
    private String image;

    @OneToMany(mappedBy = "bicycle", cascade = CascadeType.ALL)
    private List<BicycleProduct> bicycleProducts;

    @OneToMany(mappedBy = "bicycle", cascade = CascadeType.ALL)
    private List<BicyclesOfCategory> bicyclesOfCategory;

    @OneToMany(mappedBy = "bicycle", cascade = CascadeType.ALL)
    private List<BicycleThumbnail> bicycleThumbnails;

    @OneToMany(mappedBy = "bicycle", cascade = CascadeType.ALL)
    private List<BicycleComment> bicycleComments;

}
