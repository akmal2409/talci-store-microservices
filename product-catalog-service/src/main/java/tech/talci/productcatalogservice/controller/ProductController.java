package tech.talci.productcatalogservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.talci.productcatalogservice.dto.ProductDto;
import tech.talci.productcatalogservice.service.ProductService;

import javax.validation.Valid;
import java.util.List;

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
