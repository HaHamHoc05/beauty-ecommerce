package adapters.cart;

import adapters.Publisher;

import cosmetics.usecase.cart.get.GetCartOutputBoundary;
import cosmetics.usecase.cart.get.GetCartOutputData;
import cosmetics.usecase.cart.update.UpdateCartOutputBoundary;
import cosmetics.usecase.cart.update.UpdateCartOutputData;
import cosmetics.usecase.cart.remove.RemoveCartOutputBoundary;
import cosmetics.usecase.cart.remove.RemoveCartOutputData;
import cosmetics.usecase.cart.add.AddToCartOutputBoundary;
import cosmetics.usecase.cart.add.AddToCartOutputData;

public class CartPresenter extends Publisher implements
        GetCartOutputBoundary,
        UpdateCartOutputBoundary,
        RemoveCartOutputBoundary,
        AddToCartOutputBoundary { // <--- THÊM CÁI NÀY

    private final CartViewModel viewModel = new CartViewModel();

    public CartViewModel getViewModel() { return viewModel; }

    // 1. GET
    @Override
    public void present(GetCartOutputData output) {
        viewModel.setItems(output.getItems());
        viewModel.setTotalPrice(output.getTotalPrice());
    }

    // 2. UPDATE
    @Override
    public void present(UpdateCartOutputData output) {
        viewModel.setItems(output.getUpdatedItems());
        viewModel.setTotalPrice(output.getNewTotalPrice());
        viewModel.setMessage(output.getMessage());
    }

    // 3. REMOVE
    @Override
    public void present(RemoveCartOutputData output) {
        viewModel.setItems(output.getRemainingItems());
        viewModel.setTotalPrice(output.getNewTotalPrice());
        viewModel.setMessage(output.getMessage());
        notifySubscribers("CART_ITEM_REMOVED", output.getNewTotalPrice());
    }

    // 4. ADD (Logic của module Add cũ)
    @Override
    public void present(AddToCartOutputData output) {
        if (output.isSuccess()) {
            viewModel.setMessage(output.getMessage());
            // AddToCartOutputData có trả về totalItems để update icon
            notifySubscribers("CART_UPDATED", output.getTotalItems());
        } else {
            viewModel.setMessage(output.getMessage());
        }
    }
}