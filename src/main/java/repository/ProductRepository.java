package repository;

import cosmetics.entities.Product;

public interface ProductRepository {
    void save(Product product);
    boolean existsByName(String name);

    Product findById(Integer id);

    void update(Product p);

    void delete(Integer id);
}