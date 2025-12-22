package repository.DB;
import cosmetics.entities.Order;
import cosmetics.entities.OrderItem;
import repository.OrderRepository;

import java.sql.*;

public class MySQLOrderRepository implements OrderRepository {

    @Override
    public Integer createOrder(Order order) {
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement itemStmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = DBConnection.getConnection();

            // TẮT TỰ ĐỘNG LƯU (Bắt đầu Transaction)
            conn.setAutoCommit(false);

            // Lưu bảng ORDERS
            String sqlOrder = "INSERT INTO orders (user_id, total_price, status, receiver_name, receiver_phone, receiver_address, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

            orderStmt = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, order.getUserId());
            orderStmt.setBigDecimal(2, order.getTotalPrice());
            orderStmt.setString(3, order.getStatus());
            orderStmt.setString(4, order.getReceiverName());
            orderStmt.setString(5, order.getReceiverPhone());
            orderStmt.setString(6, order.getReceiverAddress());
            orderStmt.setTimestamp(7, Timestamp.valueOf(order.getCreatedAt()));

            int affectedRows = orderStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Lưu đơn hàng thất bại, không có dòng nào được thêm.");
            }

            // Lấy ID đơn hàng vừa tạo
            int orderId;
            generatedKeys = orderStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Lưu đơn hàng thất bại, không lấy được ID.");
            }

            // 3. Lưu bảng ORDER_ITEMS
            String sqlItem = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            itemStmt = conn.prepareStatement(sqlItem);

            for (OrderItem item : order.getItems()) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getProductId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setBigDecimal(4, item.getPriceAtPurchase()); // Giá chốt đơn

                itemStmt.addBatch(); // Gom lại
            }

            itemStmt.executeBatch(); // Thực thi một lần

            //  CHỐT GIAO DỊCH
            conn.commit();
            return orderId;

        } catch (SQLException e) {
            // CÓ LỖI -> HOÀN TÁC TẤT CẢ
            if (conn != null) {
                try {
                    System.err.println("Transaction is being rolled back due to error: " + e.getMessage());
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tạo đơn hàng: " + e.getMessage());
        } finally {
            // Đóng kết nối và bật lại AutoCommit
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (orderStmt != null) orderStmt.close();
                if (itemStmt != null) itemStmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void clearCart(Integer cartId) {
        // Chỉ xóa items, giữ lại header Cart để lần sau user mua tiếp đỡ phải tạo mới
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