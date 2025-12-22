package cosmetics.usecase.order.admin;

import cosmetics.ResponseData;
import cosmetics.usecase.order.dto.OrderDTO;

import java.util.List;

public class GetAllOrdersOutputData implements ResponseData {
    private final List<OrderDTO> orders;
    public GetAllOrdersOutputData(List<OrderDTO> orders) { this.orders = orders; }
    public List<OrderDTO> getOrders() { return orders; }
}