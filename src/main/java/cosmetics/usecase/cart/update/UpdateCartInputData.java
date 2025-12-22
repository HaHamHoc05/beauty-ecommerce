package cosmetics.usecase.cart.update;

import cosmetics.RequestData;

public class UpdateCartInputData implements RequestData {
    private final Integer userId;
    private final Integer productId;
    private final Integer quantity;

    public UpdateCartInputData(Integer userId, Integer productId, Integer quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
    public Integer getUserId() { return userId; }
    public Integer getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
}