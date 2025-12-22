package adapters.login;

import cosmetics.OutputBoundary;
import adapters.Publisher;
import cosmetics.usecase.login.LoginOutputData;

public class LoginPresenter extends Publisher implements OutputBoundary<LoginOutputData> {
    private final LoginViewModel viewModel;

    public LoginPresenter() {
        this.viewModel = new LoginViewModel();
    }

    public LoginViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void present(LoginOutputData outputData) {
        if (outputData.isSuccess()) {
            viewModel.setStatus("SUCCESS");
            viewModel.setFullName(outputData.getFullName());
            viewModel.setRole(outputData.getRole());
            viewModel.setErrorMessage(null);
        } else {
            viewModel.setStatus("ERROR");
            viewModel.setErrorMessage(outputData.getMessage());
            viewModel.setFullName(null);
            viewModel.setRole(null);
        }
        if (outputData.isSuccess()) {
            notifySubscribers("LOGIN_SUCCESS", outputData.getFullName());
        } else {
            notifySubscribers("LOGIN_ERROR", outputData.getMessage());
        }
    }
}

