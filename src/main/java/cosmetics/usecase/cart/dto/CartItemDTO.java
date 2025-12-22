package cosmetics.usecase.cart.dto;

import java.math.BigDecimal;

public class CartItemDTO {
    private Integer productId;
    private String productName;
    private String productImage;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;

    public CartItemDTO(Integer productId, String productName, String productImage, Integer quantity, BigDecimal price, BigDecimal subTotal) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
    }

    public Integer getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getProductImage() { return productImage; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getSubTotal() { return subTotal; }
}