package tech.talci.productcatalogservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "Category")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    private String id;
    private String name;
    private String description;
    private Integer numberOfProducts;
    private String imagePath;
    @DBRef(lazy = true)
    private List<Product> products;
}
