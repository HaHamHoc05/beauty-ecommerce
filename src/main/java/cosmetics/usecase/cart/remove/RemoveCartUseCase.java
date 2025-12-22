package cosmetics.usecase.cart.remove;

import cosmetics.InputBoundary;
import cosmetics.entities.Cart;
import cosmetics.entities.CartItem;
import repository.CartRepository;
import cosmetics.usecase.cart.dto.CartItemDTO;
import java.util.ArrayList;
import java.util.List;

public class RemoveCartUseCase implements InputBoundary<RemoveCartInputData> {
    private final RemoveCartOutputBoundary outputBoundary;
    private final CartRepository cartRepository;

    public RemoveCartUseCase(RemoveCartOutputBoundary outputBoundary, CartRepository cartRepository) {
        this.outputBoundary = outputBoundary;
        this.cartRepository = cartRepository;
    }

    @Override
    public void execute(RemoveCartInputData input) {
        Cart cart = cartRepository.findByUserId(input.getUserId());
        if (cart != null) {
            cartRepository.removeItem(cart.getId(), input.getProductId());
        }

        // Load lại dữ liệu sau khi xóa
        Cart updatedCart = cartRepository.findByUserId(input.getUserId());
        List<CartItemDTO> dtos = new ArrayList<>();
        java.math.BigDecimal total = (updatedCart != null) ? updatedCart.getTotalPrice() : java.math.BigDecimal.ZERO;

        if (updatedCart != null) {
            for (CartItem item : updatedCart.getItems()) {
                dtos.add(new CartItemDTO(item.getProductId(), item.getProductName(), item.getProductImage(), item.getQuantity(), item.getCurrentPrice(), item.getSubTotal()));
            }
        }
        outputBoundary.present(new RemoveCartOutputData("Đã xóa sản phẩm", dtos, total));
    }
}
