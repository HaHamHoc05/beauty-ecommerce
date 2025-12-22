package cosmetics.usecase.cart.add;

import cosmetics.RequestData; // Import interface chung

public class AddToCartInputData implements RequestData {
    private final Integer userId;
    private final Integer productId;
    private final Integer quantity;

    public AddToCartInputData(Integer userId, Integer productId, Integer quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getUserId() { return userId; }
    public Integer getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
}