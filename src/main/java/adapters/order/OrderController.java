package adapters.order;

import cosmetics.usecase.order.create.*;
import cosmetics.usecase.order.get.list.*;
import cosmetics.usecase.order.get.detail.*;

public class OrderController {
    private final PlaceOrderUseCase placeOrderUseCase;
    private final GetMyOrdersUseCase getMyOrdersUseCase;
    private final GetOrderDetailUseCase getOrderDetailUseCase;

    public OrderController(PlaceOrderUseCase placeOrderUseCase,
                           GetMyOrdersUseCase getMyOrdersUseCase,
                           GetOrderDetailUseCase getOrderDetailUseCase) {
        this.placeOrderUseCase = placeOrderUseCase;
        this.getMyOrdersUseCase = getMyOrdersUseCase;
        this.getOrderDetailUseCase = getOrderDetailUseCase;
    }

    // Hành động 1: Đặt hàng
    public void placeOrder(Integer userId, String name, String phone, String address) {
        placeOrderUseCase.execute(new PlaceOrderInputData(userId, name, phone, address));
    }

    // Hành động 2: Xem lịch sử
    public void viewMyOrders(Integer userId) {
        getMyOrdersUseCase.execute(new GetMyOrdersInputData(userId));
    }

    // Hành động 3: Xem chi tiết
    public void viewOrderDetail(Integer orderId, Integer userId) {
        getOrderDetailUseCase.execute(new GetOrderDetailInputData(orderId, userId));
    }
}