package cosmetics.usecase.order.detail;

import cosmetics.InputBoundary;
import cosmetics.entities.Order;
import cosmetics.entities.OrderItem;
import cosmetics.usecase.order.dto.OrderDTO;
import cosmetics.usecase.order.dto.OrderItemDTO;
import repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class GetOrderDetailUseCase implements InputBoundary<GetOrderDetailInputData> {
    private final GetOrderDetailOutputBoundary outputBoundary;
    private final OrderRepository orderRepository;

    public GetOrderDetailUseCase(GetOrderDetailOutputBoundary outputBoundary, OrderRepository orderRepository) {
        this.outputBoundary = outputBoundary;
        this.orderRepository = orderRepository;
    }

    @Override
    public void execute(GetOrderDetailInputData input) {
        Order order = orderRepository.findById(input.getOrderId());

        if (order == null) {
            outputBoundary.present(new GetOrderDetailOutputData("Đơn hàng không tồn tại!"));
            return;
        }
        // Check xem đơn hàng có phải của user này không
        if (!input.isAdmin() && !order.getUserId().equals(input.getUserId())) {
            outputBoundary.present(new GetOrderDetailOutputData("Bạn không có quyền xem đơn hàng này!"));
            return;
        }

        // Convert sang DTO đầy đủ Items
        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                itemDTOs.add(new OrderItemDTO(
                        item.getProductName(), item.getProductImage(),
                        item.getQuantity(), item.getPriceAtPurchase(), item.getSubTotal()
                ));
            }
        }

        OrderDTO dto = new OrderDTO(order.getId(), order.getCreatedAt(), order.getStatus(),
                order.getTotalPrice(), order.getReceiverName(), order.getReceiverPhone(),
                order.getReceiverAddress(), itemDTOs);

        outputBoundary.present(new GetOrderDetailOutputData(dto));
    }
}