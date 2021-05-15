package tech.talci.searchservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.talci.searchservice.repositories.ProductRepository;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class Test {

    private final ProductRepository productRepository;

    @GetMapping("/count")
    public long count() {
        return this.productRepository.count();
    }
}
