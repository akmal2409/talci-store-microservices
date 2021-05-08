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
public abstract class CategoryMapper {

    @Autowired
    ProductMapper productMapper;

    @Mapping(target = "numberOfProducts", expression = "java(getNumberOfProducts(category.getProducts()))")
    @Mapping(target = "products", expression = "java(mapProductsToDto(category.getProducts()))")
    public abstract CategoryDto mapToDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numberOfProducts", ignore = true)
    @Mapping(target = "products", ignore = true)
    public abstract Category mapToCategory(CategoryDto categoryDto);

    public Integer getNumberOfProducts(List<Product> products) {
        return products.size();
    }

    public List<ProductDto> mapProductsToDto(List<Product> products) {
        return products.stream()
            .map(productMapper::mapToDto)
            .collect(Collectors.toList());
    }
}
