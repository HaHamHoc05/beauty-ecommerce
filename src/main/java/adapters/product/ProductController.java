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

    public ProductController(AddProductUseCase addUseCase,
                             GetProductListUseCase listUseCase,
                             EditProductUseCase editUseCase,
                             DeleteProductUseCase deleteUseCase) {
        this.addUseCase = addUseCase;
        this.listUseCase = listUseCase;
        this.editUseCase = editUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    // 1. Thêm
    public void addProduct(String name, BigDecimal price, String image, String desc, Integer stock, Integer catId) {
        addUseCase.execute(new AddProductInputData(name, price, image, desc, stock, catId));
    }

    // 2. Xem danh sách
    public void viewList() {
        listUseCase.execute(new GetProductListInputData());
    }

    // 3. Sửa
    public void editProduct(Integer id, String name, BigDecimal price, String image, String desc, Integer stock, Integer catId, String status) {
        editUseCase.execute(new EditProductInputData(id, name, price, image, desc, stock, catId, status));
    }

    // 4. Xóa
    public void deleteProduct(Integer id) {
        deleteUseCase.execute(new DeleteProductInputData(id));
    }
}