package cosmetics.usecase.login;

import cosmetics.ResponseData;

public class LoginOutputData implements ResponseData {
    private boolean success;
    private String message;
    private String fullname;
    private String role;

    public LoginOutputData(String message) {
        this.message = message;
        this.success = false;
    }

    public LoginOutputData( String fullname, String role) {
        this.success = success;
        this.fullname = fullname;
        this.role = role;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getFullName() { return fullname; }
    public String getRole() { return role; }
}
