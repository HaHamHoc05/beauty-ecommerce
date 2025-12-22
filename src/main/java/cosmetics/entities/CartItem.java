package cosmetics.entities;

import java.math.BigDecimal;

public class CartItem {
    private Integer id;
    private Integer productId;
    private String productName;
    private String productImage;
    private Integer quantity;
    private BigDecimal currentPrice;

    public CartItem() {}

    public CartItem(Integer productId, String productName, String productImage, Integer quantity, BigDecimal currentPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
    }

    // Tính tổng tiền tạm tính của item này
    public BigDecimal getSubTotal() {
        if (currentPrice == null) return BigDecimal.ZERO;
        return currentPrice.multiply(new BigDecimal(quantity));
    }


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }
}