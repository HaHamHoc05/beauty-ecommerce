package cosmetics.usecase.register;

import cosmetics.RequestData;

import java.util.regex.Pattern;

public class RegisterInputData implements RequestData {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String phone;

    public RegisterInputData(String username, String email, String password, String confirmPassword, String fullName,  String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        this.phone = phone;

    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getConfirmPassword() { return confirmPassword; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhone(String phone) { this.phone = phone; }

    public boolean isValidEmail() {
        // Regex email cơ bản
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, this.email);
    }

    // 5. THÊM HÀM KIỂM TRA ĐỊNH DẠNG SĐT (VN)
    public boolean isValidPhone() {
        // Bắt đầu bằng số 0, theo sau là 9 chữ số (Tổng 10 số)
        String phoneRegex = "^0\\d{9}$";
        return Pattern.matches(phoneRegex, this.phone);
    }
}
