package tech.talci.searchservice.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@ToString
@Document(indexName = "categoryIndex")
public class Category {

    @Id
    private String id;
    private String name;
    private String description;
    private Integer numberOfProducts;
    private String imagePath;
}
