package tech.talci.searchservice.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Category {

    private String id;
    private String name;
    private String description;
    private Integer numberOfProducts;
    private String imagePath;
    private List<Product> products;
}
