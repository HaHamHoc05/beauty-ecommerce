package adapters.order.create;

import cosmetics.InputBoundary;
import cosmetics.usecase.order.create.PlaceOrderInputData;

public class PlaceOrderController {
    private final InputBoundary<PlaceOrderInputData> inputBoundary;

    public PlaceOrderController(InputBoundary<PlaceOrderInputData> inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void execute(Integer userId, String name, String phone, String address) {
        inputBoundary.execute(new PlaceOrderInputData(userId, name, phone, address));
    }
}