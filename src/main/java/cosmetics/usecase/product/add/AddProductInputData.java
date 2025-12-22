package cosmetics.usecase.product.add;

import cosmetics.RequestData;
import java.math.BigDecimal;

public class AddProductInputData implements RequestData {
    private final String name;
    private final BigDecimal price;
    private final String image;
    private final String description;
    private final Integer stock;
    private final Integer categoryId;


    public AddProductInputData(String name, BigDecimal price, String image, String description, Integer stock, Integer categoryId) {
        this.name = name; this.price = price; this.image = image; this.description = description; this.stock = stock; this.categoryId = categoryId;
    }


    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getImage() { return image; }
    public String getDescription() { return description; }
    public Integer getStock() { return stock; }
    public Integer getCategoryId() { return categoryId; }
}