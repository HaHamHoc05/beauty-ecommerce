package cosmetics.usecase.product.get;

import cosmetics.ResponseData;
import cosmetics.usecase.product.dto.ProductDTO;

import java.util.List;

public class GetProductListOutputData implements ResponseData {
    private final List<ProductDTO> products;
    public GetProductListOutputData(List<ProductDTO> products) { this.products = products; }
    public List<ProductDTO> getProducts() { return products; }
}