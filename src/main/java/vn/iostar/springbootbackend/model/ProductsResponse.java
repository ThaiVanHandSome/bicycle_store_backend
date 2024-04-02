package vn.iostar.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponse {
    private int productsCount;
    private Object data;
    private String message;
    private int status;
}
