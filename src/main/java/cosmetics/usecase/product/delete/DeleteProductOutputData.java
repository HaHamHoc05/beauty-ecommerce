package cosmetics.usecase.product.delete;

import cosmetics.ResponseData;

public class DeleteProductOutputData implements ResponseData {
    private final boolean success;
    private final String message;
    public DeleteProductOutputData(boolean success, String message) { this.success = success; this.message = message; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}