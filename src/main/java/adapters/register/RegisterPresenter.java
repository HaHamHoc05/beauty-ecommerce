package adapters.register;

import adapters.Publisher;
import cosmetics.OutputBoundary;
import cosmetics.usecase.register.RegisterOutputData;

public class RegisterPresenter extends Publisher implements OutputBoundary<RegisterOutputData> {

    private final RegisterViewModel viewModel = new RegisterViewModel();

    public RegisterViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void present(RegisterOutputData output) {
        // WEB
        if (output.isSuccess()) {
            viewModel.setStatus("SUCCESS");
            viewModel.setMessage(output.getMessage());
        } else {
            viewModel.setStatus("ERROR");
            viewModel.setMessage(output.getMessage());
        }

        // DESKTOP
        if (output.isSuccess()) {
            notifySubscribers("REGISTER_SUCCESS", output.getMessage());
        } else {
            notifySubscribers("REGISTER_ERROR", output.getMessage());
        }
    }
}