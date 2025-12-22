package cosmetics.usecase.cart.get;

import cosmetics.InputBoundary;
import cosmetics.entities.Cart;
import cosmetics.entities.CartItem;
import repository.CartRepository;
import cosmetics.usecase.cart.dto.CartItemDTO;
import java.util.ArrayList;
import java.util.List;

public class GetCartUseCase implements InputBoundary<GetCartInputData> {
    private final GetCartOutputBoundary outputBoundary;
    private final CartRepository cartRepository;

    public GetCartUseCase(GetCartOutputBoundary outputBoundary, CartRepository cartRepository) {
        this.outputBoundary = outputBoundary;
        this.cartRepository = cartRepository;
    }

    @Override
    public void execute(GetCartInputData input) {
        Cart cart = cartRepository.findByUserId(input.getUserId());
        List<CartItemDTO> dtos = new ArrayList<>();
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;

        if (cart != null) {
            total = cart.getTotalPrice();
            for (CartItem item : cart.getItems()) {
                dtos.add(new CartItemDTO(item.getProductId(), item.getProductName(), item.getProductImage(), item.getQuantity(), item.getCurrentPrice(), item.getSubTotal()));
            }
        }
        outputBoundary.present(new GetCartOutputData(dtos, total));
    }
}