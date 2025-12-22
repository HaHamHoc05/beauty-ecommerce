package cosmetics.usecase.product.get;

import cosmetics.InputBoundary;
import cosmetics.entities.Product;
import cosmetics.usecase.product.dto.ProductDTO;
import repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class GetProductListUseCase implements InputBoundary<GetProductListInputData> {
    private final GetProductListOutputBoundary outputBoundary;
    private final ProductRepository productRepository;

    public GetProductListUseCase(GetProductListOutputBoundary outputBoundary, ProductRepository productRepository) {
        this.outputBoundary = outputBoundary;
        this.productRepository = productRepository;
    }

    @Override
    public void execute(GetProductListInputData input) {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> dtos = new ArrayList<>();
        for (Product p : products) {
            dtos.add(new ProductDTO(p.getId(), p.getName(), p.getPrice(), p.getImage(), p.getDescription(), p.getStockQuantity(), p.getCategoryId(), p.getStatus()));
        }
        outputBoundary.present(new GetProductListOutputData(dtos));
    }
}