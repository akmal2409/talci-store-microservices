package tech.talci.productcatalogservice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.talci.productcatalogservice.dto.ProductDto;
import tech.talci.productcatalogservice.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto mapToDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addedOn", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    @Mapping(target = "skuCode", ignore = true)
    @Mapping(target = "onSale", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "orderCount", ignore = true)
    @Mapping(target = "promotions", ignore = true)
    Product mapToProduct(ProductDto productDto);
}
