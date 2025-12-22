package repository;

import cosmetics.entities.Order;

import java.util.List;

public interface OrderRepository {
    // Lưu đơn hàng và trả về ID của đơn hàng vừa tạo
    Integer createOrder(Order order);

    void clearCart(Integer cartId);

    List<Order> findByUserId(Integer userId); // Cho khách xem lịch sử

    List<Order> findAll(); // Cho Admin quản lý

    Order findById(Integer orderId); // Xem chi tiết đơn

    void updateStatus(Integer orderId, String status); // Admin duyệt đơn


}
