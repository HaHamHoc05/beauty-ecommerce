package adapters.order;

import cosmetics.usecase.order.create.*;
import cosmetics.usecase.order.detail.GetOrderDetailInputData;
import cosmetics.usecase.order.detail.GetOrderDetailUseCase;
import cosmetics.usecase.order.get.GetMyOrdersInputData;
import cosmetics.usecase.order.get.GetMyOrdersUseCase;


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
    public void viewOrderDetail(Integer orderId, Integer userId, boolean isAdmin) {
        // Truyền isAdmin vào InputData
        GetOrderDetailInputData input = new GetOrderDetailInputData(orderId, userId, isAdmin);
        getOrderDetailUseCase.execute(input);
    }
}