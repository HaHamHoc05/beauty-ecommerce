package repository;

import cosmetics.entities.Category;

import java.util.List;

public interface CategoryRepository {
    // luu danh muc
    void save(Category category);
    // lay tat ca danh muc
    List<Category> findAll();
    // kiem tra ten trung
    boolean existsByName(String name);
}
