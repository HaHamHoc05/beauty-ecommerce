package cosmetics.entities;

import repository.PasswordEncoder;

import java.time.LocalDateTime;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private LocalDateTime createdAt;

    public User() {
        this.createdAt = LocalDateTime.now();
        this.role = Role.CUSTOMER;
    }

    public User(Integer id, String username, String password, String fullname,String email, String phone, String address, Role role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.createdAt = createdAt;

    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullname; }
    public void setFullName(String fullName) { this.fullname = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }


    public boolean verifyPassword(String rawPassword, PasswordEncoder encoder) {
        if (this.password == null || rawPassword == null) return false;
        return encoder.matches(rawPassword, this.password);
    }

    public void changePassword(String newPassword, PasswordEncoder encoder) {
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("Password quá ngắn");
        }
        this.password = encoder.encode(newPassword);
    }

    public boolean isAdmin() {
        return Role.ADMIN.equals(this.role);
    }
}


