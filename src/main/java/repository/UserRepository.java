package repository;

import cosmetics.entities.User;

public interface UserRepository {
    // tìm user theo username
    User findByUsername(String username);

    // kiểm tra trùng tên đăng nhập, trùng email, và lưu vào db
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void save(User user);
}
