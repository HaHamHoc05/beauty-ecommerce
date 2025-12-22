package cosmetics.usecase.product.add;

import cosmetics.InputBoundary;
import cosmetics.OutputBoundary;
import cosmetics.entities.Product;
import repository.ProductRepository;
import java.math.BigDecimal;

public class AddProductUseCase implements InputBoundary<AddProductInputData> {

    private final OutputBoundary<AddProductOutputData> outputBoundary;
    private final ProductRepository productRepository;

    public AddProductUseCase(OutputBoundary<AddProductOutputData> outputBoundary,
                             ProductRepository productRepository) {
        this.outputBoundary = outputBoundary;
        this.productRepository = productRepository;
    }

    @Override
    public void execute(AddProductInputData input) {
        try {
            if (productRepository.existsByName(input.getName())) {
                outputBoundary.present(new AddProductOutputData(false, "Tên sản phẩm đã tồn tại!"));
                return;
            }

            // Entity sẽ tự validate, nếu sai sẽ ném IllegalArgumentException
            Product p = new Product(null, input.getName(), input.getPrice(), input.getDescription(), input.getImage(), input.getStock(), input.getCategoryId());

            productRepository.save(p);
            outputBoundary.present(new AddProductOutputData(true, "Thêm sản phẩm thành công!"));

        } catch (IllegalArgumentException e) {
            // Bắt lỗi nghiệp vụ từ Entity
            outputBoundary.present(new AddProductOutputData(false, e.getMessage()));
        } catch (Exception e) {
            outputBoundary.present(new AddProductOutputData(false, "Lỗi hệ thống: " + e.getMessage()));
        }
    }
}