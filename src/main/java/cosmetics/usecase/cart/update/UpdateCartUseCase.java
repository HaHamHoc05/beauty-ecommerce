package cosmetics.usecase.cart.update;

import cosmetics.InputBoundary;
import cosmetics.entities.Cart;
import cosmetics.entities.CartItem;
import repository.CartRepository;
import cosmetics.usecase.cart.dto.CartItemDTO;
import java.util.ArrayList;
import java.util.List;

public class UpdateCartUseCase implements InputBoundary<UpdateCartInputData> {
    private final UpdateCartOutputBoundary outputBoundary;
    private final CartRepository cartRepository;

    public UpdateCartUseCase(UpdateCartOutputBoundary outputBoundary, CartRepository cartRepository) {
        this.outputBoundary = outputBoundary;
        this.cartRepository = cartRepository;
    }

    @Override
    public void execute(UpdateCartInputData input) {
        Cart cart = cartRepository.findByUserId(input.getUserId());
        if (cart != null) {
            CartItem item = cartRepository.findItemInCart(cart.getId(), input.getProductId());
            if (item != null) {
                if (input.getQuantity() <= 0) {
                    cartRepository.removeItem(cart.getId(), input.getProductId());
                } else {
                    cartRepository.updateItemQuantity(item.getId(), input.getQuantity());
                }
            }
        }

        // Load lại dữ liệu mới nhất để trả về
        Cart updatedCart = cartRepository.findByUserId(input.getUserId());
        List<CartItemDTO> dtos = new ArrayList<>();
        java.math.BigDecimal total = (updatedCart != null) ? updatedCart.getTotalPrice() : java.math.BigDecimal.ZERO;

        if (updatedCart != null) {
            for (CartItem item : updatedCart.getItems()) {
                dtos.add(new CartItemDTO(item.getProductId(), item.getProductName(), item.getProductImage(), item.getQuantity(), item.getCurrentPrice(), item.getSubTotal()));
            }
        }
        outputBoundary.present(new UpdateCartOutputData(true, "Cập nhật thành công", dtos, total));
    }
}