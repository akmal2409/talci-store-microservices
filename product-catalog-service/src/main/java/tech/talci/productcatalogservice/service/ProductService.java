package tech.talci.productcatalogservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.talci.productcatalogservice.dto.ProductDto;
import tech.talci.productcatalogservice.dto.mapper.ProductMapper;
import tech.talci.productcatalogservice.model.Product;
import tech.talci.productcatalogservice.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> findAll() {
        return this.productRepository
                .findAll()
                .stream()
                .map(this.productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.mapToProduct(productDto);

        product = productRepository.save(product);

        return productMapper.mapToDto(product);
    }
}
