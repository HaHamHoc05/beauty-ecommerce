package cosmetics.usecase.order.admin.status;

import cosmetics.ResponseData;

public class UpdateOrderStatusOutputData implements ResponseData {
    private final boolean success;
    private final String message;

    public UpdateOrderStatusOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}