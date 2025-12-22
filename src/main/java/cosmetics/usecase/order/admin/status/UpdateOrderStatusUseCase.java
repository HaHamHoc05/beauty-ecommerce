package cosmetics.usecase.order.admin.status;

import cosmetics.InputBoundary;
import cosmetics.entities.Order;
import repository.OrderRepository;

public class UpdateOrderStatusUseCase implements InputBoundary<UpdateOrderStatusInputData> {
    private final UpdateOrderStatusOutputBoundary outputBoundary;
    private final OrderRepository orderRepository;

    public UpdateOrderStatusUseCase(UpdateOrderStatusOutputBoundary outputBoundary, OrderRepository orderRepository) {
        this.outputBoundary = outputBoundary;
        this.orderRepository = orderRepository;
    }

    @Override
    public void execute(UpdateOrderStatusInputData input) {
        try {
            Order order = orderRepository.findById(input.getOrderId());
            if (order == null) {
                outputBoundary.present(new UpdateOrderStatusOutputData(false, "Đơn hàng không tồn tại!"));
                return;
            }
            // Có thể thêm logic kiểm tra: ví dụ đã "Huỷ" thì không được đổi lại, v.v.

            orderRepository.updateStatus(input.getOrderId(), input.getNewStatus());
            outputBoundary.present(new UpdateOrderStatusOutputData(true, "Cập nhật trạng thái thành công!"));
        } catch (Exception e) {
            outputBoundary.present(new UpdateOrderStatusOutputData(false, "Lỗi: " + e.getMessage()));
        }
    }
}