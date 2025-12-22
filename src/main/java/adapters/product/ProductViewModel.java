package adapters.product;

import cosmetics.usecase.product.dto.ProductDTO;
import java.util.List;

public class ProductViewModel {
    private List<ProductDTO> products; // Dùng cho Get List
    private String message;            // Dùng cho Add, Edit, Delete
    private boolean success;           // Dùng để check thành công/thất bại
    private String status;      // "SUCCESS", "ERROR"



    public List<ProductDTO> getProducts() { return products; }
    public void setProducts(List<ProductDTO> products) { this.products = products; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}