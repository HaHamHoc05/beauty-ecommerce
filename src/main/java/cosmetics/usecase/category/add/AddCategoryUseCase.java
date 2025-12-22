package cosmetics.usecase.category.add;

import cosmetics.InputBoundary;
import cosmetics.OutputBoundary;
import cosmetics.entities.Category;
import repository.CategoryRepository;

public class AddCategoryUseCase implements InputBoundary<AddCategoryInputData> {

    private final OutputBoundary<AddCategoryOutputData> outputBoundary;
    private final CategoryRepository categoryRepository;

    public AddCategoryUseCase(OutputBoundary<AddCategoryOutputData> outputBoundary,
                              CategoryRepository categoryRepository) {
        this.outputBoundary = outputBoundary;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public void execute(AddCategoryInputData input) {
        // Validate tên rỗng
        if (input.getName() == null || input.getName().isBlank()) {
            outputBoundary.present(new AddCategoryOutputData("Tên danh mục không được để trống!"));
            return;
        }

        // Kiểm tra trùng tên
        if (categoryRepository.existsByName(input.getName())) {
            outputBoundary.present(new AddCategoryOutputData("Danh mục này đã tồn tại!"));
            return;
        }


        Category newCategory = new Category();
        newCategory.setName(input.getName());


        categoryRepository.save(newCategory);


        outputBoundary.present(new AddCategoryOutputData(input.getName(), "Thêm danh mục thành công!"));
    }
}
