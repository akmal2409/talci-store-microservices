package tech.talci.productcatalogservice.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.talci.productcatalogservice.dto.ProductDto;
import tech.talci.productcatalogservice.service.ProductService;

@RestController
@RequestMapping(ProductController.BASE_URL)
@RequiredArgsConstructor
public class ProductController {

    public static final String BASE_URL = "/api/product";

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return this.productService.findAll();
    }

    @PostMapping
    public ProductDto addProduct(@RequestBody @Valid ProductDto productDto) {
        return this.productService.save(productDto);
    }
}
