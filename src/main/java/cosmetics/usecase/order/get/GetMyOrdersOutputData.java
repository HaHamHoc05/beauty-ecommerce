package cosmetics.usecase.order.get;
import cosmetics.ResponseData;
import cosmetics.usecase.order.dto.OrderDTO;
import java.util.List;

public class GetMyOrdersOutputData implements ResponseData {
    private final List<OrderDTO> orders;
    public GetMyOrdersOutputData(List<OrderDTO> orders) { this.orders = orders; }
    public List<OrderDTO> getOrders() { return orders; }
}