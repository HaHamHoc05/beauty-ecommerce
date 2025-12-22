package adapters.cart;

import cosmetics.entities.Cart;
import cosmetics.usecase.cart.dto.CartItemDTO;
import java.math.BigDecimal;
import java.util.List;

public class CartViewModel {
    // 1. Dữ liệu hiển thị danh sách (Dùng cho Get, Update, Remove)
    private List<CartItemDTO> items;
    private BigDecimal totalPrice;

    // 2. Dữ liệu thông báo (Dùng cho Add, Update, Remove)
    private String message;
    private String status; // "SUCCESS" hoặc "ERROR"

    // 3. Dữ liệu cập nhật icon (Dùng cho Add)
    private int totalItems;

    private Cart cart;

    // --- Getters & Setters ---
    public List<CartItemDTO> getItems() { return items; }
    public void setItems(List<CartItemDTO> items) { this.items = items; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getTotalItems() { return totalItems; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
}