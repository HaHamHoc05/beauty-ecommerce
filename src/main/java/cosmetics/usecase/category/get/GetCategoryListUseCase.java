package cosmetics.usecase.category.get;

import cosmetics.entities.Category;
import repository.CategoryRepository;
import java.util.List;


public class GetCategoryListUseCase {
    private final CategoryRepository repository;

    public GetCategoryListUseCase(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> execute() {
        return repository.findAll();
    }
}