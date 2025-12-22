package cosmetics.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
    private String image;
    private Integer stockQuantity;
    private Integer categoryId;
    private String status; // "ACTIVE", "INACTIVE"
    private LocalDateTime createdAt;

    public Product() {
        this.createdAt = LocalDateTime.now();
        this.status = "ACTIVE";
    }


    public Product(Integer id, String name, BigDecimal price, String description, String image, Integer stockQuantity, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.status = "ACTIVE";
        this.createdAt = LocalDateTime.now();
    }

    public boolean isAvailable() {
        return "ACTIVE".equals(this.status) && this.stockQuantity > 0;
    }

    //Trừ tồn kho (Khi có đơn hàng)
    public void reduceStock(int quantityToReduce) {
        if (quantityToReduce <= 0) {
            throw new IllegalArgumentException("Số lượng mua phải lớn hơn 0");
        }
        if (this.stockQuantity < quantityToReduce) {
            throw new IllegalStateException("Không đủ hàng trong kho! (Còn: " + this.stockQuantity + ")");
        }
        this.stockQuantity -= quantityToReduce;
    }

    //Nhập thêm hàng
    public void restock(int quantityToAdd) {
        if (quantityToAdd <= 0) {
            throw new IllegalArgumentException("Số lượng nhập phải lớn hơn 0");
        }
        this.stockQuantity += quantityToAdd;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Giá sản phẩm không được âm!");
        }
        this.price = price;
    }

    public void setStockQuantity(Integer stockQuantity) {
        if (stockQuantity == null || stockQuantity < 0) {
            throw new IllegalArgumentException("Tồn kho không được âm!");
        }
        this.stockQuantity = stockQuantity;
    }

    // --- Getters & Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

