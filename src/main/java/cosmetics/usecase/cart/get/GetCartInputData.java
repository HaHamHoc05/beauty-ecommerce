package cosmetics.usecase.cart.get;
import cosmetics.RequestData;

public class GetCartInputData implements RequestData {
    private Integer userId;
    public GetCartInputData(Integer userId) { this.userId = userId; }
    public Integer getUserId() { return userId; }
}