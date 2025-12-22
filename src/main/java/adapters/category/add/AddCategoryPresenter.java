package adapters.category.add;

import cosmetics.OutputBoundary;
import adapters.Publisher;
import cosmetics.usecase.category.add.AddCategoryOutputData;

public class AddCategoryPresenter extends Publisher implements OutputBoundary<AddCategoryOutputData> {

    private final AddCategoryViewModel viewModel = new AddCategoryViewModel();

    public AddCategoryViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void present(AddCategoryOutputData output) {
        // WEB
        if (output.isSuccess()) {
            viewModel.setStatus("SUCCESS");
            viewModel.setMessage(output.getMessage());
        } else {
            viewModel.setStatus("ERROR");
            viewModel.setMessage(output.getMessage());
        }

        // DESKTOP
        if (output.isSuccess()) {
            notifySubscribers("Thêm danh mục thành công", output.getCategoryName());
        } else {
            notifySubscribers("Thêm danh mục không thành công", output.getMessage());
        }
    }
}