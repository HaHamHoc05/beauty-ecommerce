package adapters.order;

import adapters.Publisher;
// Import 3 bộ Output Boundary & Output Data
import cosmetics.usecase.order.create.PlaceOrderOutputBoundary;
import cosmetics.usecase.order.create.PlaceOrderOutputData;
import cosmetics.usecase.order.get.GetMyOrdersOutputBoundary;
import cosmetics.usecase.order.get.GetMyOrdersOutputData;
import cosmetics.usecase.order.detail.GetOrderDetailOutputBoundary;
import cosmetics.usecase.order.detail.GetOrderDetailOutputData;

public class OrderPresenter extends Publisher implements
        PlaceOrderOutputBoundary,
        GetMyOrdersOutputBoundary,
        GetOrderDetailOutputBoundary {

    private final OrderViewModel viewModel = new OrderViewModel();

    public OrderViewModel getViewModel() {
        return viewModel;
    }

    // ============================================================
    // 1. XỬ LÝ KẾT QUẢ: ĐẶT HÀNG (PlaceOrder)
    // ============================================================
    @Override
    public void present(PlaceOrderOutputData output) {
        if (output.isSuccess()) {
            // Cập nhật ViewModel cho Web
            viewModel.setStatus("SUCCESS");
            viewModel.setNewOrderId(output.getOrderId());
            viewModel.setMessage(output.getMessage());

            // Bắn sự kiện cho Desktop/Mobile App (Observer Pattern)
            notifySubscribers("ORDER_PLACED_SUCCESS", output.getOrderId());
        } else {
            viewModel.setStatus("ERROR");
            viewModel.setMessage(output.getMessage());

            notifySubscribers("ORDER_PLACED_FAILED", output.getMessage());
        }
    }

    // ============================================================
    // 2. XỬ LÝ KẾT QUẢ: XEM DANH SÁCH (GetMyOrders)
    // ============================================================
    @Override
    public void present(GetMyOrdersOutputData output) {
        // Cập nhật ViewModel
        viewModel.setOrders(output.getOrders());
        viewModel.setStatus("SUCCESS"); // Đánh dấu là lấy dữ liệu thành công

        // Reset các trường không liên quan để tránh hiển thị nhầm
        viewModel.setMessage(null);
        viewModel.setCurrentOrder(null);
    }

    // ============================================================
    // 3. XỬ LÝ KẾT QUẢ: XEM CHI TIẾT (GetOrderDetail)
    // ============================================================
    @Override
    public void present(GetOrderDetailOutputData output) {
        if (output.getOrder() != null) {
            // Thành công: Có dữ liệu đơn hàng
            viewModel.setCurrentOrder(output.getOrder());
            viewModel.setStatus("SUCCESS");
            viewModel.setMessage(null);
        } else {
            // Thất bại: Không tìm thấy đơn hoặc không có quyền xem
            viewModel.setStatus("ERROR");
            viewModel.setMessage(output.getErrorMessage());
            viewModel.setCurrentOrder(null);
        }
    }
}