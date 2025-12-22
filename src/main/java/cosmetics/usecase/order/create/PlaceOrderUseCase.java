package cosmetics.usecase.order.create;

import cosmetics.InputBoundary;
import cosmetics.entities.Cart;
import cosmetics.entities.CartItem;
import cosmetics.entities.Order;
import cosmetics.entities.OrderItem;
import cosmetics.entities.Product;
import repository.CartRepository;
import repository.OrderRepository;
import repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrderUseCase implements InputBoundary<PlaceOrderInputData> {

    // SỬA Ở ĐÂY: Dùng Interface riêng biệt
    private final PlaceOrderOutputBoundary outputBoundary;

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public PlaceOrderUseCase(PlaceOrderOutputBoundary outputBoundary,
                             CartRepository cartRepository,
                             OrderRepository orderRepository,
                             ProductRepository productRepository) {
        this.outputBoundary = outputBoundary;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void execute(PlaceOrderInputData input) {
        // 1. Validate Input
        if (input.getReceiverName() == null || input.getReceiverName().trim().isEmpty() ||
                input.getReceiverAddress() == null || input.getReceiverAddress().trim().isEmpty() ||
                input.getReceiverPhone() == null || input.getReceiverPhone().trim().isEmpty()) {
            outputBoundary.present(new PlaceOrderOutputData(false, "Vui lòng điền đầy đủ thông tin giao hàng!"));
            return;
        }

        // 2. Lấy Giỏ hàng
        Cart cart = cartRepository.findByUserId(input.getUserId());
        if (cart == null || cart.getItems().isEmpty()) {
            outputBoundary.present(new PlaceOrderOutputData(false, "Giỏ hàng đang trống!"));
            return;
        }

        // 3. Chuẩn bị dữ liệu Order
        Order order = new Order();
        order.setUserId(input.getUserId());
        order.setReceiverName(input.getReceiverName());
        order.setReceiverPhone(input.getReceiverPhone());
        order.setReceiverAddress(input.getReceiverAddress());
        order.setTotalPrice(cart.getTotalPrice());

        List<Product> productsToUpdate = new ArrayList<>();

        // 4. Kiểm tra tồn kho & Tạo Order Items
        for (CartItem cartItem : cart.getItems()) {
            Product product = productRepository.findById(cartItem.getProductId());

            if (product == null) {
                outputBoundary.present(new PlaceOrderOutputData(false, "Sản phẩm không tồn tại!"));
                return;
            }
            if (!product.isAvailable()) {
                outputBoundary.present(new PlaceOrderOutputData(false, "Sản phẩm '" + product.getName() + "' đã ngừng kinh doanh!"));
                return;
            }

            try {
                    product.setStockQuantity(cartItem.getQuantity());
            } catch (Exception e) {
                outputBoundary.present(new PlaceOrderOutputData(false, "Sản phẩm '" + product.getName() + "' không đủ hàng!"));
                return;
            }

            productsToUpdate.add(product);

            OrderItem orderItem = new OrderItem(
                    product.getId(),
                    cartItem.getQuantity(),
                    cartItem.getCurrentPrice()
            );
            // Lưu Snapshot
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getImage());

            order.addItem(orderItem);
        }

        try {
            // 5. Lưu & Trừ kho
            Integer newOrderId = orderRepository.createOrder(order);
            for (Product p : productsToUpdate) {
                productRepository.update(p);
            }
            cartRepository.clearCart(cart.getId());

            // 6. Output thành công
            outputBoundary.present(new PlaceOrderOutputData(true, "Đặt hàng thành công!", newOrderId));

        } catch (Exception e) {
            e.printStackTrace();
            outputBoundary.present(new PlaceOrderOutputData(false, "Lỗi hệ thống: " + e.getMessage()));
        }
    }
}