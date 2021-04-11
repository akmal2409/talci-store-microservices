package tech.talci.productcatalogservice.dto;

import lombok.*;
import tech.talci.productcatalogservice.model.Product;

import java.util.List;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String id;
    private String name;
    private String description;
    private Integer numberOfProducts;
    private String imagePath;
    private List<ProductDto> products;
}
