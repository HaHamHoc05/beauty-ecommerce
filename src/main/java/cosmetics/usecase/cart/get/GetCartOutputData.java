package cosmetics.usecase.cart.get;
import cosmetics.ResponseData;
import cosmetics.usecase.cart.dto.CartItemDTO;
import java.math.BigDecimal;
import java.util.List;

public class GetCartOutputData implements ResponseData {
    private final List<CartItemDTO> items;
    private final BigDecimal totalPrice;

    public GetCartOutputData(List<CartItemDTO> items, BigDecimal totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }
    public List<CartItemDTO> getItems() { return items; }
    public BigDecimal getTotalPrice() { return totalPrice; }
}