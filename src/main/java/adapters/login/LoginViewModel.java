package adapters.login;

public class LoginViewModel {
    private String status;
    private String errorMessage;
    private String fullname;
    private String role;

    public LoginViewModel() {
        this.status = "PENDING";
    }

    public void setStatus(String status) { this.status = status; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public void setFullName(String fullName) { this.fullname = fullName; }
    public void setRole(String role) { this.role = role; }


    public String getStatus() { return status; }
    public String getErrorMessage() { return errorMessage; }
    public String getFullName() { return fullname; }
    public String getRole() { return role; }

    public boolean isSuccess() {
        return "SUCCESS".equals(this.status);
    }
}
