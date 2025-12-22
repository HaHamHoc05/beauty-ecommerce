package cosmetics.usecase.register;

import cosmetics.RequestData;

public class RegisterInputData implements RequestData {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public RegisterInputData(String username, String email, String password, String confirmPassword, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;

    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getConfirmPassword() { return confirmPassword; }
    public String getFullName() { return fullName; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    public void setFullName(String fullName) { this.fullName = fullName; }

}
