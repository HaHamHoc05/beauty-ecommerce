package cosmetics.usecase.cart.update;

import cosmetics.ResponseData;
import cosmetics.usecase.cart.dto.CartItemDTO;
import java.math.BigDecimal;
import java.util.List;

public class UpdateCartOutputData implements ResponseData {
    private final boolean success;
    private final String message;
    private final List<CartItemDTO> updatedItems; // Để load lại bảng
    private final BigDecimal newTotalPrice;

    public UpdateCartOutputData(boolean success, String message, List<CartItemDTO> updatedItems, BigDecimal newTotalPrice) {
        this.success = success;
        this.message = message;
        this.updatedItems = updatedItems;
        this.newTotalPrice = newTotalPrice;
    }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<CartItemDTO> getUpdatedItems() { return updatedItems; }
    public BigDecimal getNewTotalPrice() { return newTotalPrice; }
}