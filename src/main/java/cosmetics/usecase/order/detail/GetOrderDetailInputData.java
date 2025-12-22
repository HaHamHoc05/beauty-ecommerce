package cosmetics.usecase.order.detail;

import cosmetics.RequestData;

public class GetOrderDetailInputData implements RequestData {
    private final Integer orderId;
    private final Integer userId; // Để check bảo mật
    private boolean isAdmin;

    public GetOrderDetailInputData(Integer orderId, Integer userId, boolean isAdmin) {
        this.orderId = orderId;
        this.userId = userId;
        this.isAdmin = isAdmin;
    }
    public Integer getOrderId() { return orderId; }
    public Integer getUserId() { return userId; }
    public boolean isAdmin() { return isAdmin; }
}