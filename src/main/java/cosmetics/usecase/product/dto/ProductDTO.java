package cosmetics.usecase.product.dto;

import java.math.BigDecimal;

public class ProductDTO {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String image;
    private String description;
    private Integer stockQuantity;
    private Integer categoryId;
    private String status;

    public ProductDTO(Integer id, String name, BigDecimal price, String image, String description, Integer stockQuantity, Integer categoryId, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.status = status;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getImage() { return image; }
    public String getDescription() { return description; }
    public Integer getStockQuantity() { return stockQuantity; }
    public Integer getCategoryId() { return categoryId; }
    public String getStatus() { return status; }
}