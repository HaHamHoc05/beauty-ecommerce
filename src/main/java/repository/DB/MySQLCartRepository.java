package repository.DB;

import cosmetics.entities.Cart;
import cosmetics.entities.CartItem;
import repository.CartRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLCartRepository implements CartRepository {

    @Override
    public Cart findByUserId(Integer userId) {
        Cart cart = null;
        // 1. Tìm thông tin Giỏ hàng (Header)
        String cartSql = "SELECT * FROM carts WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(cartSql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cart = new Cart();
                    cart.setId(rs.getInt("id"));
                    cart.setUserId(rs.getInt("user_id"));
                    // Có thể map thêm created_at, updated_at nếu cần
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Nếu chưa có giỏ thì trả về null luôn
        if (cart == null) return null;

        // 2. Tìm chi tiết sản phẩm trong giỏ (Items)
        // JOIN với bảng products để lấy Tên, Ảnh và Giá hiện tại
        String itemSql = "SELECT ci.*, p.name AS p_name, p.image AS p_image, p.price AS p_price " +
                "FROM cart_items ci " +
                "JOIN products p ON ci.product_id = p.id " +
                "WHERE ci.cart_id = ?";

        List<CartItem> items = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(itemSql)) {
            stmt.setInt(1, cart.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CartItem item = new CartItem();
                    item.setId(rs.getInt("id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setQuantity(rs.getInt("quantity"));

                    // Lấy thông tin từ bảng Product (để hiển thị)
                    item.setProductName(rs.getString("p_name"));
                    item.setProductImage(rs.getString("p_image"));
                    item.setCurrentPrice(rs.getBigDecimal("p_price"));

                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set items vào cart (Entity Cart sẽ tự tính tổng tiền)
        cart.setItems(items);
        return cart;
    }

    @Override
    public Integer createCart(Integer userId) {
        String sql = "INSERT INTO carts (user_id) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Trả về ID giỏ hàng mới tạo
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tạo giỏ hàng: " + e.getMessage());
        }
        return null;
    }

    @Override
    public CartItem findItemInCart(Integer cartId, Integer productId) {
        String sql = "SELECT * FROM cart_items WHERE cart_id = ? AND product_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CartItem item = new CartItem();
                    item.setId(rs.getInt("id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    return item;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addItem(Integer cartId, Integer productId, Integer quantity) {
        String sql = "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItemQuantity(Integer cartItemId, Integer newQuantity) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, cartItemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int countItemsInCart(Integer cartId) {
        // Tính tổng số lượng sản phẩm (SUM quantity)
        String sql = "SELECT SUM(quantity) FROM cart_items WHERE cart_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void removeItem(Integer cartId, Integer productId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ? AND product_id = ?";
        try (java.sql.Connection conn = DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearCart(Integer cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}