package cosmetics.usecase.cart.remove;

import cosmetics.RequestData;

public class RemoveCartInputData implements RequestData {
    private final Integer userId;
    private final Integer productId;

    public RemoveCartInputData(Integer userId, Integer productId) {
        this.userId = userId;
        this.productId = productId;
    }
    public Integer getUserId() { return userId; }
    public Integer getProductId() { return productId; }
}