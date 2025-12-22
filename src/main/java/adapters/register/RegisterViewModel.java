package adapters.register;

public class RegisterViewModel {
    private String status = "PENDING";
    private String message;

    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }

    public void setMessage(String message) { this.message = message; }
    public String getMessage() { return message; }

}
