package cosmetics.usecase.order.admin;

import cosmetics.InputBoundary;
import cosmetics.entities.Order;
import cosmetics.usecase.order.dto.OrderDTO;
import repository.OrderRepository;
import java.util.ArrayList;
import java.util.List;

public class GetAllOrdersUseCase implements InputBoundary<GetAllOrdersInputData> {
    private final GetAllOrdersOutputBoundary outputBoundary;
    private final OrderRepository orderRepository;

    public GetAllOrdersUseCase(GetAllOrdersOutputBoundary outputBoundary, OrderRepository orderRepository) {
        this.outputBoundary = outputBoundary;
        this.orderRepository = orderRepository;
    }

    @Override
    public void execute(GetAllOrdersInputData input) {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> dtos = new ArrayList<>();

        for (Order o : orders) {
            // Convert Entity -> DTO (Items để rỗng cho nhẹ list)
            dtos.add(new OrderDTO(o.getId(), o.getCreatedAt(), o.getStatus(), o.getTotalPrice(),
                    o.getReceiverName(), o.getReceiverPhone(), o.getReceiverAddress(), new ArrayList<>()));
        }
        outputBoundary.present(new GetAllOrdersOutputData(dtos));
    }
}