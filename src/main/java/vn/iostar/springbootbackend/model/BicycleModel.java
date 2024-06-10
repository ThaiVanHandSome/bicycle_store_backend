package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BicycleModel {
    private Long idBicycle;
    private String name;
    private String description;
    private String frame;
    private String fork;
    private String rims;
    private String hubs;
    private String spokes;
    private String tires;
    private String handlebar;
    private Long price;
    private String image;
    private List<String> thumbnails;
}
