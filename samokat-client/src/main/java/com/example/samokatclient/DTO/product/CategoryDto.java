package com.example.samokatclient.DTO.product;

import com.example.samokatclient.entities.product.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CategoryDto {
    public Long id;
    public String name;
    public String categoryImage_url;
    public List<CategoryDto> children;

    public CategoryDto(Category category){
        id = category.getId();
        name = category.getName();
        categoryImage_url = category.getImage_url();
        children = new ArrayList<>();
        for (Category child : category.getChildren()){
            children.add(new CategoryDto(child));
        }
    }
}
