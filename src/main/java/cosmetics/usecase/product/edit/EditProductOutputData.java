package cosmetics.usecase.product.edit;

import cosmetics.ResponseData;

public class EditProductOutputData implements ResponseData {
    private final boolean success;
    private final String message;
    public EditProductOutputData(boolean success, String message) { this.success = success; this.message = message; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}