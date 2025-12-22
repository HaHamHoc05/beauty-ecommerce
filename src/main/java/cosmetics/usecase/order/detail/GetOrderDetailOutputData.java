package cosmetics.usecase.order.detail;

import cosmetics.ResponseData;
import cosmetics.usecase.order.dto.OrderDTO;

public class GetOrderDetailOutputData implements ResponseData {
    private final OrderDTO order;
    private final String errorMessage;

    public GetOrderDetailOutputData(OrderDTO order) {
        this.order = order;
        this.errorMessage = null;
    }

    public GetOrderDetailOutputData(String errorMessage) {
        this.order = null;
        this.errorMessage = errorMessage;
    }

    public OrderDTO getOrder() { return order; }
    public String getErrorMessage() { return errorMessage; }
}