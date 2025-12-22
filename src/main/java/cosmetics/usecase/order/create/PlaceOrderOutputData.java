package cosmetics.usecase.order.create;

import cosmetics.ResponseData;

public class PlaceOrderOutputData implements ResponseData {
    private final boolean success;
    private final String message;
    private final Integer orderId; // Trả về ID đơn hàng nếu thành công

    // Constructor cho trường hợp Thất bại
    public PlaceOrderOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.orderId = null;
    }

    // Constructor cho trường hợp Thành công
    public PlaceOrderOutputData(boolean success, String message, Integer orderId) {
        this.success = success;
        this.message = message;
        this.orderId = orderId;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Integer getOrderId() { return orderId; }
}