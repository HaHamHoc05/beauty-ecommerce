package repository;

import cosmetics.entities.Cart;
import cosmetics.entities.CartItem;

public interface CartRepository {
    // Tìm giỏ hàng
    Cart findByUserId(Integer userId);

    // Tạo giỏ hàng mới
    Integer createCart(Integer userId);

    // Kiểm tra xem sản phẩm X đã có trong giỏ hàng Y chưa?
    CartItem findItemInCart(Integer cartId, Integer productId);

    // Thêm sản phẩm vào giỏ
    void addItem(Integer cartId, Integer productId, Integer quantity);

    // Cập nhật số lượng
    void updateItemQuantity(Integer cartItemId, Integer newQuantity);

    // Đếm tổng số lượng sản phẩm trong giỏ để hiện lên icon
    int countItemsInCart(Integer cartId);

    // Xóa items trong giỏ hàng khi thêm thành công
    void clearCart(Integer cartId);

    void removeItem(Integer cartId, Integer productId);
}
