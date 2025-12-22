package cosmetics.usecase.category.add;

import cosmetics.ResponseData;

import java.io.Serializable;

public class AddCategoryOutputData implements ResponseData {
    private boolean success;
    private String message;
    private String categoryName;

    public AddCategoryOutputData(String message) {
        this.success = false;
        this.message = message;
    }
    public AddCategoryOutputData(String categoryName, String message) {
        this.success = true;
        this.categoryName = categoryName;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getCategoryName() { return categoryName; }
}
