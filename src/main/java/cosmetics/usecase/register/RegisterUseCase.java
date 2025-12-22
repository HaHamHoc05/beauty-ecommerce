package cosmetics.usecase.register;

import cosmetics.InputBoundary;
import cosmetics.OutputBoundary;
import cosmetics.entities.Role;
import cosmetics.entities.User;
import repository.PasswordEncoder;
import repository.UserRepository;

public class RegisterUseCase implements InputBoundary<RegisterInputData> {

    private final OutputBoundary<RegisterOutputData> outputBoundary;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUseCase(OutputBoundary<RegisterOutputData> outputBoundary,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.outputBoundary = outputBoundary;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void execute(RegisterInputData input) {
        // 1. Kiểm tra mật khẩu khớp (Code cũ)
        if (!input.getPassword().equals(input.getConfirmPassword())) {
            outputBoundary.present(new RegisterOutputData(false, "Mật khẩu xác nhận không khớp"));
            return;
        }

        // 2. KIỂM TRA ĐỊNH DẠNG EMAIL (Mới)
        if (!input.isValidEmail()) {
            outputBoundary.present(new RegisterOutputData(false, "Email không đúng định dạng (Ví dụ: abc@gmail.com)"));
            return;
        }

        // 3. KIỂM TRA ĐỊNH DẠNG SĐT (Mới)
        if (!input.isValidPhone()) {
            outputBoundary.present(new RegisterOutputData(false, "Số điện thoại không hợp lệ (Phải là 10 số, bắt đầu bằng 0)"));
            return;
        }

        // 4. Kiểm tra tồn tại trong DB (Code cũ)
        if (userRepository.existsByEmail(input.getEmail())) {
            outputBoundary.present(new RegisterOutputData(false,"Email đã được sử dụng"));
            return;
        }
        if (userRepository.existsByUsername(input.getUsername())) {
            outputBoundary.present(new RegisterOutputData(false,"Tên đăng nhập đã tồn tại"));
            return;
        }

        // 5. Tạo User và SET PHONE
        User newUser = new User();
        newUser.setUsername(input.getUsername());
        newUser.setEmail(input.getEmail());
        newUser.setFullName(input.getFullName());
        newUser.setPhone(input.getPhone()); // <--- LƯU SĐT VÀO ENTITY
        newUser.setRole(Role.CUSTOMER);

        try {
            newUser.changePassword(input.getPassword(), passwordEncoder);
        } catch (IllegalArgumentException e) {
            outputBoundary.present(new RegisterOutputData(false, e.getMessage()));
            return;
        }

        userRepository.save(newUser);
        outputBoundary.present(new RegisterOutputData(true, "Đăng ký thành công"));
    }
}
