package tech.talci.productcatalogservice.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import tech.talci.productcatalogservice.dto.CategoryDto;
import tech.talci.productcatalogservice.dto.ProductDto;
import tech.talci.productcatalogservice.model.Category;
import tech.talci.productcatalogservice.model.Product;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    CategoryMapper categoryMapper;

    public abstract ProductDto mapToDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addedOn", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "orderCount", ignore = true)
    @Mapping(target = "promotions", ignore = true)
    @Mapping(target = "categories", expression = "java(mapCategories(productDto.getCategories()))")
    public abstract Product mapToProduct(ProductDto productDto);

    public List<Category> mapCategories(List<CategoryDto> categoryDtos) {
        return categoryDtos.stream()
            .map(categoryMapper::mapToCategory)
            .collect(Collectors.toList());
    }
}
