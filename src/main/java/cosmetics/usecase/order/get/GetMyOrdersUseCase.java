package cosmetics.usecase.order.get;

import cosmetics.InputBoundary;
import cosmetics.entities.Order;
import repository.OrderRepository;
import cosmetics.usecase.order.dto.OrderDTO;
import java.util.ArrayList;
import java.util.List;

public class GetMyOrdersUseCase implements InputBoundary<GetMyOrdersInputData> {
    private final GetMyOrdersOutputBoundary outputBoundary;
    private final OrderRepository orderRepository;

    public GetMyOrdersUseCase(GetMyOrdersOutputBoundary outputBoundary, OrderRepository orderRepository) {
        this.outputBoundary = outputBoundary;
        this.orderRepository = orderRepository;
    }

    @Override
    public void execute(GetMyOrdersInputData input) {
        List<Order> orders = orderRepository.findByUserId(input.getUserId());
        List<OrderDTO> dtos = new ArrayList<>();

        for (Order o : orders) {
            // Danh sách bên ngoài chỉ cần thông tin chung, items để null hoặc rỗng
            dtos.add(new OrderDTO(
                    o.getId(), o.getCreatedAt(), o.getStatus(), o.getTotalPrice(),
                    o.getReceiverName(), o.getReceiverPhone(), o.getReceiverAddress(),
                    new ArrayList<>() // List items rỗng
            ));
        }
        outputBoundary.present(new GetMyOrdersOutputData(dtos));
    }
}