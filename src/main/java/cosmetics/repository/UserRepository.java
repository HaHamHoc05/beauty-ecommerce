package cosmetics.repository;

import cosmetics.entities.User;

public interface UserRepository {
    // tìm user theo username
    User findByUsername(String username);
}
