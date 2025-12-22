package cosmetics.usecase.order.get;

import cosmetics.RequestData;

public class GetMyOrdersInputData implements RequestData {
    private final Integer userId;
    public GetMyOrdersInputData(Integer userId) { this.userId = userId; }
    public Integer getUserId() { return userId; }
}