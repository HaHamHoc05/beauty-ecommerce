package adapters.register;

import cosmetics.InputBoundary;
import cosmetics.usecase.register.RegisterInputData;

public class RegisterController {
    private final InputBoundary<RegisterInputData> inputBoundary;

    public RegisterController(InputBoundary<RegisterInputData> inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void execute(String username, String password, String confirm, String fullname, String email, String phone) {


        RegisterInputData input = new RegisterInputData(username, email, password, confirm, fullname, phone);

        inputBoundary.execute(input);
    }
}