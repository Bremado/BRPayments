package me.toddydev.core.cache.products;

import me.toddydev.core.model.product.Product;
import me.toddydev.core.model.product.categories.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCache {

    private List<Product> products = new ArrayList<>();

    public void add(Product product) {
        products.add(product);
    }

    public void remove(Product product) {
        products.remove(product);
    }

    public Product findById(String id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Product> findByPrice(double price) {
        return products.stream().filter(p -> p.getPrice() == price).collect(Collectors.toList());
    }

    public List<Product> findByCategory(Category category) {
        return products.stream().filter(p -> p.getCategory().equals(category)).collect(Collectors.toList());
    }

    public List<Product> findAll() {
        return products;
    }
}
