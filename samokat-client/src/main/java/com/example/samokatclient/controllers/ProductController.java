package com.example.samokatclient.controllers;

import com.example.samokatclient.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Методы для получения информации о продуктах")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "Вывести все категории",
            description = "Категории выводятся иерархической структурой"
    )
    @GetMapping("/categories")
    private ResponseEntity<?>getCategories() {
        return new ResponseEntity<>(productService.getAllCategories(), HttpStatus.OK);
    }

    @Operation(
            summary = "Вывести продукты категории",
            description =
                    "Выводит все продукты для данной категории. Если у категории есть дочерние," +
                    " возьмёт продукты из каждой, объединив их в список"
    )
    @GetMapping ("/categories/{category_id}")
    private ResponseEntity<?> getCategories(
            @Parameter(description = "ID категории")
            @PathVariable("category_id") Long category_id,

            @Parameter(description = "Номер страницы")
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,

            @Parameter(description = "Размер страницы")
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(
                productService.getAllProductsFromCategory(category_id, pageNumber, pageSize),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Вывести продукты",
            description =
                    "Выводит все продукты, которые представлены в каталоге"
    )
    @GetMapping ("/products")
    private ResponseEntity<?> getProducts(
            @Parameter(description = "Номер страницы")
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,

            @Parameter(description = "Размер страницы")
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(productService.getAllProductsPage(pageNumber, pageSize), HttpStatus. OK);
    }

    @Operation(
            summary = "Вывести продукт",
            description = "Выводит информацию о конкретном продукте"
    )
    @GetMapping ("/products/{product_id}")
    private ResponseEntity<?> getProduct(
            @Parameter(description = "ID продукта")
            @PathVariable("product_id") Long product_id) {

        return new ResponseEntity<>(productService.getProductById(product_id), HttpStatus.OK);
    }

    @Operation(
            summary = "Поиск продукта",
            description = "Ищет продукты по ключевым словам запроса"
    )
    @GetMapping ("/products/find")
    private ResponseEntity<?> getProducts(
            @Parameter(description = "Поисковой запрос")
            @RequestParam("search") String search,

            @Parameter(description = "Номер страницы")
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,

            @Parameter(description = "Размер страницы")
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return new ResponseEntity<>(productService.searchProductsByKeywords(search, pageNumber, pageSize), HttpStatus.OK);
    }


}
