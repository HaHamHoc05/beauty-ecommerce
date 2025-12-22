package adapters.register;

import cosmetics.InputBoundary;
import cosmetics.usecase.register.RegisterInputData;

public class RegisterController {
    private final InputBoundary<RegisterInputData> inputBoundary;

    public RegisterController(InputBoundary<RegisterInputData> inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void register(String username, String password, String confirm, String fullname, String email) {
        RegisterInputData input = new RegisterInputData(username, password, confirm, fullname, email);
        inputBoundary.execute(input);
    }
}