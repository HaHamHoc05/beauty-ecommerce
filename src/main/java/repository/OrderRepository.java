package repository;

import cosmetics.entities.Order;

public interface OrderRepository {
    // Lưu đơn hàng và trả về ID của đơn hàng vừa tạo
    Integer createOrder(Order order);

    void clearCart(Integer cartId);
}
