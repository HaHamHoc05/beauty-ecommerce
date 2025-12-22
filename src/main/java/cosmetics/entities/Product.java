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
        setName(name);           // Gọi setter để validate
        setPrice(price);         // Gọi setter để validate
        this.description = description;
        this.image = image;
        setStockQuantity(stockQuantity); // Gọi setter để validate
        this.categoryId = categoryId;
        this.status = "ACTIVE";
        this.createdAt = LocalDateTime.now();
    }

    // --- Business Rules (Validation) ---

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không được để trống!");
        }
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        if (price == null) throw new IllegalArgumentException("Giá không được để trống!");
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Giá sản phẩm không được âm!");
        }
        this.price = price;
    }

    public void setStockQuantity(Integer stockQuantity) {
        if (stockQuantity == null) stockQuantity = 0;
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Tồn kho không được âm!");
        }
        this.stockQuantity = stockQuantity;
    }

    // --- Các Getter/Setter cơ bản (Không Validation) ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    // setName đã viết ở trên, KHÔNG viết lại ở đây nữa

    public BigDecimal getPrice() { return price; }
    // setPrice đã viết ở trên

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Integer getStockQuantity() { return stockQuantity; }
    // setStockQuantity đã viết ở trên

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Logic nghiệp vụ bổ sung
    public boolean isAvailable() {
        return "ACTIVE".equals(this.status) && this.stockQuantity > 0;
    }
}