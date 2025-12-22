package cosmetics.usecase.product.delete;

import cosmetics.InputBoundary;
import cosmetics.entities.Product;
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
            Product product = productRepository.findById(input.getId());
            if (product == null) {
                outputBoundary.present(new DeleteProductOutputData(false, "Sản phẩm không tìm thấy!"));
                return;
            }


            // Thay vì xóa cứng, ta chuyển trạng thái thành INACTIVE
            product.setStatus("INACTIVE");
            productRepository.update(product);

            outputBoundary.present(new DeleteProductOutputData(true, "Đã xóa (ẩn) sản phẩm thành công!"));
        } catch (Exception e) {
            outputBoundary.present(new DeleteProductOutputData(false, "Lỗi khi xóa: " + e.getMessage()));
        }
    }
}