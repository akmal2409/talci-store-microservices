package tech.talci.searchservice.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
