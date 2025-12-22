package cosmetics.usecase.login;

import cosmetics.InputBoundary;
import cosmetics.OutputBoundary;
import cosmetics.entities.User;
import repository.PasswordEncoder;
import repository.UserRepository;

public class LoginUseCase implements InputBoundary<LoginInputData> {

    private final OutputBoundary<LoginOutputData> outputBoundary;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginUseCase(OutputBoundary<LoginOutputData> outputBoundary,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
        this.outputBoundary = outputBoundary;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void execute(final LoginInputData input) {
        if (input.getUsername() == null || input.getUsername().isBlank() ||
                input.getPassword() == null || input.getPassword().isBlank()) {
            outputBoundary.present(new LoginOutputData("Vui lòng điền đầy đủ thông tin!"));
            return;
        }

        User user = userRepository.findByUsername(input.getUsername());
        if (user == null) {
            outputBoundary.present(new LoginOutputData("Tài khoản không tồn tại!"));
            return;
        }

        // Kiểm tra mật khẩu
        if (!user.verifyPassword(input.getPassword(), passwordEncoder)) {
            outputBoundary.present(new LoginOutputData("Mật khẩu không chính xác!"));
            return;
        }

        LoginOutputData successOutput = new LoginOutputData(user.getFullName(), user.getRole().toString());
        outputBoundary.present(successOutput);
    }
}
