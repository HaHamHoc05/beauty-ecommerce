package adapters.order.create;

import adapters.Publisher;
import cosmetics.OutputBoundary;
import cosmetics.usecase.order.create.PlaceOrderOutputData;

public class PlaceOrderPresenter extends Publisher implements OutputBoundary<PlaceOrderOutputData> {

    private final PlaceOrderViewModel viewModel = new PlaceOrderViewModel();

    public PlaceOrderViewModel getViewModel() { return viewModel; }

    @Override
    public void present(PlaceOrderOutputData output) {
        // Web
        if (output.isSuccess()) {
            viewModel.setStatus("SUCCESS");
            viewModel.setOrderId(output.getOrderId());
            viewModel.setMessage(output.getMessage());
        } else {
            viewModel.setStatus("ERROR");
            viewModel.setMessage(output.getMessage());
        }

        // Desktop
        if (output.isSuccess()) {
            notifySubscribers("ORDER_SUCCESS", output.getOrderId());
        } else {
            notifySubscribers("ORDER_ERROR", output.getMessage());
        }
    }
}