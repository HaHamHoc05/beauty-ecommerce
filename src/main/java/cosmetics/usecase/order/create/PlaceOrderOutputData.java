package cosmetics.usecase.order.create;

import cosmetics.ResponseData;

public class PlaceOrderOutputData implements ResponseData {
    private boolean success;
    private String message;
    private Integer orderId; // Trả về mã đơn hàng để in hóa đơn

    public PlaceOrderOutputData(boolean success, String message, Integer orderId) {
        this.success = success;
        this.message = message;
        this.orderId = orderId;
    }
    // Getters...
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Integer getOrderId() { return orderId; }
}