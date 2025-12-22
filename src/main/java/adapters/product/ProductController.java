package adapters.product;

import cosmetics.usecase.product.add.*;
import cosmetics.usecase.product.get.*;
import cosmetics.usecase.product.edit.*;
import cosmetics.usecase.product.delete.*;
import java.math.BigDecimal;

public class ProductController {
    private final AddProductUseCase addUseCase;
    private final GetProductListUseCase listUseCase;
    private final EditProductUseCase editUseCase;
    private final DeleteProductUseCase deleteUseCase;

    public ProductController(AddProductUseCase addUC, GetProductListUseCase listUC, EditProductUseCase editUC, DeleteProductUseCase deleteUC) {
        this.addUseCase = addUC;
        this.listUseCase = listUC;
        this.editUseCase = editUC;
        this.deleteUseCase = deleteUC;
    }

    // ADD
    public void addProduct(String name, String priceStr, String image, String desc, String stockStr, String catIdStr) {
        try {
            // Xử lý dữ liệu rỗng hoặc sai định dạng
            BigDecimal price = (priceStr == null || priceStr.isEmpty()) ? BigDecimal.ZERO : new BigDecimal(priceStr);
            Integer stock = (stockStr == null || stockStr.isEmpty()) ? 0 : Integer.parseInt(stockStr);
            Integer catId = (catIdStr == null || catIdStr.isEmpty()) ? null : Integer.parseInt(catIdStr);

            addUseCase.execute(new AddProductInputData(name, price, image, desc, stock, catId));
        } catch (NumberFormatException e) {
            // Nếu parse lỗi, ném ngoại lệ để Servlet bắt
            throw new IllegalArgumentException("Giá hoặc số lượng phải là số hợp lệ!");
        }
    }

    public void viewList() {
        listUseCase.execute(new GetProductListInputData());
    }

    // EDIT
    public void editProduct(String idStr, String name, String priceStr, String image, String desc, String stockStr, String catIdStr, String status) {
        try {
            Integer id = Integer.parseInt(idStr);
            BigDecimal price = new BigDecimal(priceStr);
            Integer stock = Integer.parseInt(stockStr);
            Integer catId = Integer.parseInt(catIdStr);

            editUseCase.execute(new EditProductInputData(id, name, price, image, desc, stock, catId, status));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Dữ liệu nhập vào không đúng định dạng số!");
        }
    }

    // DELETE
    public void deleteProduct(String idStr) {
        try {
            Integer id = Integer.parseInt(idStr);
            deleteUseCase.execute(new DeleteProductInputData(id));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID sản phẩm không hợp lệ!");
        }
    }
}