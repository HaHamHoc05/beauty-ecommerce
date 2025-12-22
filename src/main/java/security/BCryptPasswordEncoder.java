package security;

import repository.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String rawPassword) {
        // Mã hóa password kèm salt
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        if (encodedPassword == null || !encodedPassword.startsWith("$2a$")) {
            return false;
        }
        // Kiểm tra xem rawPassword khi hash có khớp với encodedPassword không
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
