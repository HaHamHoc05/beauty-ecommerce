package cosmetics.usecase.order.admin.status;

import cosmetics.RequestData;

public class UpdateOrderStatusInputData implements RequestData {
    private final Integer orderId;
    private final String newStatus;

    public UpdateOrderStatusInputData(Integer orderId, String newStatus) {
        this.orderId = orderId;
        this.newStatus = newStatus;
    }
    public Integer getOrderId() { return orderId; }
    public String getNewStatus() { return newStatus; }
}