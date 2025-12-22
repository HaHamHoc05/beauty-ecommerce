package adapters.category.add;

import cosmetics.InputBoundary;
import cosmetics.usecase.category.add.AddCategoryInputData;

public class AddCategoryController {
    private final InputBoundary<AddCategoryInputData> inputBoundary;

    public AddCategoryController(InputBoundary<AddCategoryInputData> inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void addCategory(String name) {
        AddCategoryInputData input = new AddCategoryInputData(name);
        inputBoundary.execute(input);
    }
}