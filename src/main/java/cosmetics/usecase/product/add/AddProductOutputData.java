package cosmetics.usecase.product.add;

import cosmetics.ResponseData;

public class AddProductOutputData implements ResponseData {
    private final boolean success;
    private final String message;
    public AddProductOutputData(boolean success, String message) { this.success = success; this.message = message; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}