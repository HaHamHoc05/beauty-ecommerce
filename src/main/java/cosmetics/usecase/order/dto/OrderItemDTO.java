package cosmetics.usecase.order.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private String productName;
    private String productImage;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subTotal;

    public OrderItemDTO(String productName, String productImage, int quantity, BigDecimal price, BigDecimal subTotal) {
        this.productName = productName;
        this.productImage = productImage;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
    }
    // Getters
    public String getProductName() { return productName; }
    public String getProductImage() { return productImage; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getSubTotal() { return subTotal; }
}