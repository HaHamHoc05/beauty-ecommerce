package cosmetics.usecase.register;

import cosmetics.ResponseData;

import java.io.Serializable;

public class RegisterOutputData implements ResponseData {
    private boolean success;
    private String message;

    public  RegisterOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;

    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
