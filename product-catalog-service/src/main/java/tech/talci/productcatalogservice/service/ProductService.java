package tech.talci.productcatalogservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
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
    private final StreamBridge streamBridge;

    public List<ProductDto> findAll() {
        return this.productRepository
                .findAll()
                .stream()
                .map(this.productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.mapToProduct(productDto);

        // until we get a confirmation from the warehouse service
        // that the product is available.
        product.setStatus(Product.Status.OUT_OF_STOCK);
        product = productRepository.save(product);

        // notify warehouse service
        this.streamBridge
                .send("productAddedEventSupplier-out-0", product.getSkuCode());

        return productMapper.mapToDto(product);
    }
}
