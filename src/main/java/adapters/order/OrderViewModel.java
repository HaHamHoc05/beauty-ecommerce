package adapters.order;

import cosmetics.usecase.order.dto.OrderDTO;
import java.util.List;

public class OrderViewModel {

    // 1. Dùng cho: PlaceOrder (Đặt hàng) & Admin Update Status (Cập nhật trạng thái)
    // - Lưu trạng thái: "SUCCESS" hoặc "ERROR"
    private String status;
    // - Lưu thông báo: "Đặt hàng thành công" hoặc "Cập nhật thất bại"
    private String message;

    // - Riêng cho PlaceOrder: ID đơn vừa tạo
    private Integer newOrderId;

    // 2. Dùng cho: GetMyOrders (Khách) & GetAllOrders (Admin)
    // - Lưu danh sách đơn hàng để hiển thị ra bảng
    private List<OrderDTO> orders;

    // 3. Dùng cho: GetOrderDetail (Xem chi tiết)
    // - Lưu chi tiết 1 đơn hàng cụ thể
    private OrderDTO currentOrder;


    // ================= GETTERS & SETTERS =================

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