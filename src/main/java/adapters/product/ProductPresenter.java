package adapters.product;

import adapters.Publisher;
import cosmetics.usecase.product.add.AddProductOutputBoundary;
import cosmetics.usecase.product.add.AddProductOutputData;
import cosmetics.usecase.product.delete.DeleteProductOutputBoundary;
import cosmetics.usecase.product.delete.DeleteProductOutputData;
import cosmetics.usecase.product.edit.EditProductOutputBoundary;
import cosmetics.usecase.product.edit.EditProductOutputData;
import cosmetics.usecase.product.get.GetProductListOutputBoundary;
import cosmetics.usecase.product.get.GetProductListOutputData;

public class ProductPresenter extends Publisher implements
        AddProductOutputBoundary,
        GetProductListOutputBoundary,
        EditProductOutputBoundary,
        DeleteProductOutputBoundary {

    private final ProductViewModel viewModel = new ProductViewModel();

    public ProductViewModel getViewModel() { return viewModel; }

    // Xử lý ADD
    @Override
    public void present(AddProductOutputData output) {
        viewModel.setSuccess(output.isSuccess());
        viewModel.setMessage(output.getMessage());
    }

    // Xử lý GET LIST
    @Override
    public void present(GetProductListOutputData output) {
        viewModel.setProducts(output.getProducts());
    }

    // Xử lý EDIT
    @Override
    public void present(EditProductOutputData output) {
        viewModel.setSuccess(output.isSuccess());
        viewModel.setMessage(output.getMessage());
    }

    // Xử lý DELETE
    @Override
    public void present(DeleteProductOutputData output) {
        viewModel.setSuccess(output.isSuccess());
        viewModel.setMessage(output.getMessage());
    }
}