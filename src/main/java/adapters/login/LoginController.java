package adapters.login;

import cosmetics.InputBoundary;
import cosmetics.usecase.login.LoginInputData;

public class LoginController {
    private final InputBoundary<LoginInputData> inputBoundary;

    public LoginController(InputBoundary<LoginInputData> inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void login(String username, String password) {
        LoginInputData inputData = new LoginInputData(username, password);
        inputBoundary.execute(inputData);
    }
}
