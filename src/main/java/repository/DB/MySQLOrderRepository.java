package repository.DB;
import cosmetics.entities.Order;
import cosmetics.entities.OrderItem;
import repository.OrderRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            String sqlItem = "INSERT INTO order_items (order_id, product_id, quantity, price, product_name, product_image) VALUES (?, ?, ?, ?, ?, ?)";
            itemStmt = conn.prepareStatement(sqlItem);

            for (OrderItem item : order.getItems()) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getProductId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setBigDecimal(4, item.getPriceAtPurchase());

                // Lưu Snapshot tên và ảnh tại thời điểm mua
                itemStmt.setString(5, item.getProductName());
                itemStmt.setString(6, item.getProductImage());

                itemStmt.addBatch();
            }

            itemStmt.executeBatch();// Thực thi một lần

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
    @Override
    public List<Order> findByUserId(Integer userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order o = mapRowToOrder(rs);
                    list.add(o);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public Order findById(Integer orderId) {
        Order order = null;

        // 1. Lấy thông tin Header
        String sqlOrder = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlOrder)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    order = mapRowToOrder(rs);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }

        if (order == null) return null;

        // 2. Lấy thông tin Items (KÈM TÊN VÀ ẢNH SP)
        String sqlItems = "SELECT oi.*, p.name, p.image " +
                "FROM order_items oi " +
                "JOIN products p ON oi.product_id = p.id " +
                "WHERE oi.order_id = ?";

        List<OrderItem> items = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlItems)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPriceAtPurchase(rs.getBigDecimal("price"));

                    // Set dữ liệu hiển thị (từ bảng Product JOIN sang)
                    item.setProductName(rs.getString("name"));
                    item.setProductImage(rs.getString("image"));

                    items.add(item);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }

        order.setItems(items);
        return order;
    }

    private Order mapRowToOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setId(rs.getInt("id"));
        o.setUserId(rs.getInt("user_id"));
        o.setTotalPrice(rs.getBigDecimal("total_price"));
        o.setStatus(rs.getString("status"));
        o.setReceiverName(rs.getString("receiver_name"));
        o.setReceiverPhone(rs.getString("receiver_phone"));
        o.setReceiverAddress(rs.getString("receiver_address"));
        o.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return o;
    }
}