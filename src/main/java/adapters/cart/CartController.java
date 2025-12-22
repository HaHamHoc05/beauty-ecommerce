package adapters.cart;

import cosmetics.usecase.cart.add.AddToCartInputData;
import cosmetics.usecase.cart.add.AddToCartUseCase;
import cosmetics.usecase.cart.get.GetCartInputData;
import cosmetics.usecase.cart.get.GetCartUseCase;
import cosmetics.usecase.cart.remove.RemoveCartInputData;
import cosmetics.usecase.cart.remove.RemoveCartUseCase;
import cosmetics.usecase.cart.update.UpdateCartInputData;
import cosmetics.usecase.cart.update.UpdateCartUseCase;

public class CartController {

    private final AddToCartUseCase addUseCase;
    private final GetCartUseCase getUseCase;
    private final UpdateCartUseCase updateUseCase;
    private final RemoveCartUseCase removeUseCase;

    // Constructor nhận đủ 4 UseCase (được khởi tạo bên ngoài Servlet)
    public CartController(AddToCartUseCase addUseCase,
                          GetCartUseCase getUseCase,
                          UpdateCartUseCase updateUseCase,
                          RemoveCartUseCase removeUseCase) {
        this.addUseCase = addUseCase;
        this.getUseCase = getUseCase;
        this.updateUseCase = updateUseCase;
        this.removeUseCase = removeUseCase;
    }

    // THÊM VÀO GIỎ ---
    public void addToCart(Integer userId, Integer productId, Integer quantity) {
        AddToCartInputData input = new AddToCartInputData(userId, productId, quantity);
        addUseCase.execute(input);
    }

    // XEM GIỎ HÀNG ---
    public void viewCart(Integer userId) {
        GetCartInputData input = new GetCartInputData(userId);
        getUseCase.execute(input);
    }

    // CẬP NHẬT SỐ LƯỢNG ---
    public void updateCart(Integer userId, Integer productId, Integer quantity) {
        UpdateCartInputData input = new UpdateCartInputData(userId, productId, quantity);
        updateUseCase.execute(input);
    }

    // XÓA SẢN PHẨM ---
    public void removeFromCart(Integer userId, Integer productId) {
        RemoveCartInputData input = new RemoveCartInputData(userId, productId);
        removeUseCase.execute(input);
    }
}