package cosmetics.usecase.order.detail;

import cosmetics.RequestData;

public class GetOrderDetailInputData implements RequestData {
    private final Integer orderId;
    private final Integer userId; // Để check bảo mật
    public GetOrderDetailInputData(Integer orderId, Integer userId) {
        this.orderId = orderId; this.userId = userId;
    }
    public Integer getOrderId() { return orderId; }
    public Integer getUserId() { return userId; }
}