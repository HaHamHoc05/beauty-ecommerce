package cosmetics.usecase.order.create;


import cosmetics.InputBoundary;
import cosmetics.OutputBoundary;
import cosmetics.entities.Cart;
import cosmetics.entities.CartItem;
import cosmetics.entities.Order;
import cosmetics.entities.OrderItem;
import repository.CartRepository;
import repository.OrderRepository;


public class PlaceOrderUseCase implements InputBoundary<PlaceOrderInputData> {

        private final OutputBoundary<PlaceOrderOutputData> outputBoundary;
        private final CartRepository cartRepository;
        private final OrderRepository orderRepository;

        public PlaceOrderUseCase(OutputBoundary<PlaceOrderOutputData> outputBoundary,
                                 CartRepository cartRepository,
                                 OrderRepository orderRepository) {
            this.outputBoundary = outputBoundary;
            this.cartRepository = cartRepository;
            this.orderRepository = orderRepository;
        }

        @Override
        public void execute(PlaceOrderInputData input) {
            // Validate Input
            if (input.getReceiverName().isEmpty() || input.getReceiverAddress().isEmpty() || input.getReceiverPhone().isEmpty()) {
                outputBoundary.present(new PlaceOrderOutputData(false, "Vui lòng điền đầy đủ thông tin giao hàng!", null));
                return;
            }

            // Lấy Giỏ hàng hiện tại
            Cart cart = cartRepository.findByUserId(input.getUserId());
            if (cart == null || cart.getItems().isEmpty()) {
                outputBoundary.present(new PlaceOrderOutputData(false, "Giỏ hàng đang trống!", null));
                return;
            }

            // Tạo Object Order từ Cart
            Order order = new Order();
            order.setUserId(input.getUserId());
            order.setReceiverName(input.getReceiverName());
            order.setReceiverPhone(input.getReceiverPhone());
            order.setReceiverAddress(input.getReceiverAddress());
            order.setTotalPrice(cart.getTotalPrice()); // Lấy tổng tiền từ Cart

            // Chuyển đổi từng CartItem thành OrderItem
            for (CartItem cartItem : cart.getItems()) {
                OrderItem orderItem = new OrderItem(
                        cartItem.getProductId(),
                        cartItem.getQuantity(),
                        cartItem.getCurrentPrice() // Lấy giá tại thời điểm hiện tại để chốt đơn
                );
                order.addItem(orderItem);
            }

            try {
                // Lưu Order xuống Database
                Integer newOrderId = orderRepository.createOrder(order);

                // Xóa sạch Giỏ hàng
                cartRepository.clearCart(cart.getId());

                // 7. Thành công
                outputBoundary.present(new PlaceOrderOutputData(true, "Đặt hàng thành công!", newOrderId));

            } catch (Exception e) {
                e.printStackTrace();
                outputBoundary.present(new PlaceOrderOutputData(false, "Lỗi hệ thống: " + e.getMessage(), null));
            }
        }
}
