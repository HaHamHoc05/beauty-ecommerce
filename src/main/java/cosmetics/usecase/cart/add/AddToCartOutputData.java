package cosmetics.usecase.cart.add;

import cosmetics.ResponseData; // Import interface chung

public class AddToCartOutputData implements ResponseData {
    private final boolean success;
    private final String message;
    private final int totalItems; // Để update icon giỏ hàng (VD: Giỏ hàng (5))

    // Constructor khi thất bại (totalItems = 0 hoặc giữ nguyên)
    public AddToCartOutputData(String message) {
        this.success = false;
        this.message = message;
        this.totalItems = 0;
    }

    // Constructor khi thành công
    public AddToCartOutputData(String message, int totalItems) {
        this.success = true;
        this.message = message;
        this.totalItems = totalItems;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public int getTotalItems() { return totalItems; }
}