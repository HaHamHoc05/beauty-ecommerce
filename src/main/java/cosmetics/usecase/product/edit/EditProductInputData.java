package cosmetics.usecase.product.edit;

import cosmetics.RequestData;
import java.math.BigDecimal;

public class EditProductInputData implements RequestData {
    private final Integer id;
    private final String name;
    private final BigDecimal price;
    private final String image;
    private final String description;
    private final Integer stock;
    private final Integer categoryId;
    private final String status;

    public EditProductInputData(Integer id, String name, BigDecimal price, String image, String description, Integer stock, Integer categoryId, String status) {
        this.id = id; this.name = name; this.price = price; this.image = image; this.description = description; this.stock = stock; this.categoryId = categoryId; this.status = status;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getImage() { return image; }
    public String getDescription() { return description; }
    public Integer getStock() { return stock; }
    public Integer getCategoryId() { return categoryId; }
    public String getStatus() { return status; }
}