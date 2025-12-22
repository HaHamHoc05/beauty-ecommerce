package cosmetics.usecase.cart.remove;

import cosmetics.ResponseData;
import cosmetics.usecase.cart.dto.CartItemDTO;
import java.math.BigDecimal;
import java.util.List;

public class RemoveCartOutputData implements ResponseData {
    private final String message;
    private final List<CartItemDTO> remainingItems;
    private final BigDecimal newTotalPrice;

    public RemoveCartOutputData(String message, List<CartItemDTO> remainingItems, BigDecimal newTotalPrice) {
        this.message = message;
        this.remainingItems = remainingItems;
        this.newTotalPrice = newTotalPrice;
    }
    public String getMessage() { return message; }
    public List<CartItemDTO> getRemainingItems() { return remainingItems; }
    public BigDecimal getNewTotalPrice() { return newTotalPrice; }
}