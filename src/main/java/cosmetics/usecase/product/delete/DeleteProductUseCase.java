package cosmetics.usecase.product.delete;

import cosmetics.InputBoundary;
import repository.ProductRepository;

public class DeleteProductUseCase implements InputBoundary<DeleteProductInputData> {
    private final DeleteProductOutputBoundary outputBoundary;
    private final ProductRepository productRepository;

    public DeleteProductUseCase(DeleteProductOutputBoundary outputBoundary, ProductRepository productRepository) {
        this.outputBoundary = outputBoundary;
        this.productRepository = productRepository;
    }

    @Override
    public void execute(DeleteProductInputData input) {
        try {
            productRepository.delete(input.getId());
            outputBoundary.present(new DeleteProductOutputData(true, "Đã xóa sản phẩm!"));
        } catch (Exception e) {
            outputBoundary.present(new DeleteProductOutputData(false, "Lỗi khi xóa: " + e.getMessage()));
        }
    }
}