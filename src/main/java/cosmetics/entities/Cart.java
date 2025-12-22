package cosmetics.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Integer id;
    private Integer userId;
    private LocalDateTime updatedAt;
    private List<CartItem> items = new ArrayList<>();

    // Biến tạm để hiển thị tổng tiền
    private BigDecimal totalPrice;

    // Tính tổng tiền giỏ hàng
    public void calculateTotalPrice() {
        this.totalPrice = BigDecimal.ZERO;
        for (CartItem item : items) {
            this.totalPrice = this.totalPrice.add(item.getSubTotal());
        }
    }


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) {
        this.items = items;
        calculateTotalPrice(); // Tính tiền ngay khi set
    }

    public BigDecimal getTotalPrice() { return totalPrice; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}