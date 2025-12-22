package cosmetics.usecase.product.edit;

import cosmetics.InputBoundary;
import cosmetics.entities.Product;
import repository.ProductRepository;

public class EditProductUseCase implements InputBoundary<EditProductInputData> {
    private final EditProductOutputBoundary outputBoundary;
    private final ProductRepository productRepository;

    public EditProductUseCase(EditProductOutputBoundary outputBoundary, ProductRepository productRepository) {
        this.outputBoundary = outputBoundary;
        this.productRepository = productRepository;
    }

    @Override
    public void execute(EditProductInputData input) {
        try {
            Product p = new Product(input.getId(), input.getName(), input.getPrice(), input.getDescription(), input.getImage(), input.getStock(), input.getCategoryId());
            p.setStatus(input.getStatus());

            productRepository.update(p);
            outputBoundary.present(new EditProductOutputData(true, "Cập nhật thành công!"));
        } catch (Exception e) {
            outputBoundary.present(new EditProductOutputData(false, "Lỗi cập nhật: " + e.getMessage()));
        }
    }
}