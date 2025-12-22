package adapters.order;

import cosmetics.usecase.order.dto.OrderDTO;
import java.util.List;

public class OrderViewModel {
    // 1. Dùng cho PlaceOrder
    private String status;      // SUCCESS, ERROR
    private String message;     // Thông báo lỗi/thành công
    private Integer newOrderId; // ID đơn hàng vừa tạo

    // 2. Dùng cho GetHistory & GetDetail
    private List<OrderDTO> orders; // Danh sách đơn hàng
    private OrderDTO currentOrder; // Chi tiết đơn hàng đang xem

    // --- Getters & Setters ---
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getNewOrderId() { return newOrderId; }
    public void setNewOrderId(Integer newOrderId) { this.newOrderId = newOrderId; }

    public List<OrderDTO> getOrders() { return orders; }
    public void setOrders(List<OrderDTO> orders) { this.orders = orders; }

    public OrderDTO getCurrentOrder() { return currentOrder; }
    public void setCurrentOrder(OrderDTO currentOrder) { this.currentOrder = currentOrder; }
}