package adapters.order.admin;

import cosmetics.usecase.order.admin.GetAllOrdersInputData;
import cosmetics.usecase.order.admin.GetAllOrdersUseCase;
import cosmetics.usecase.order.admin.status.UpdateOrderStatusInputData;
import cosmetics.usecase.order.admin.status.UpdateOrderStatusUseCase;

public class AdminOrderController {
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final UpdateOrderStatusUseCase updateStatusUseCase;

    public AdminOrderController(GetAllOrdersUseCase getAllOrdersUseCase, UpdateOrderStatusUseCase updateStatusUseCase) {
        this.getAllOrdersUseCase = getAllOrdersUseCase;
        this.updateStatusUseCase = updateStatusUseCase;
    }

    public void viewAllOrders() {
        getAllOrdersUseCase.execute(new GetAllOrdersInputData());
    }

    public void updateStatus(Integer orderId, String newStatus) {
        updateStatusUseCase.execute(new UpdateOrderStatusInputData(orderId, newStatus));
    }
}