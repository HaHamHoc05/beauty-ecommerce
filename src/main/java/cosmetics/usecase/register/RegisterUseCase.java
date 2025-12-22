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
        if (!input.getPassword().equals(input.getConfirmPassword())) {
            outputBoundary.present(new RegisterOutputData(false, "Mật khẩu đăng nhập không khớp"));
            return;
        }
        if (userRepository.existsByEmail(input.getEmail())) {
            outputBoundary.present(new RegisterOutputData(false,"Email đã được sử dụng"));
        }
        if (userRepository.existsByUsername(input.getUsername())) {
            outputBoundary.present(new RegisterOutputData(false,"Tên đăng nhập đã tồn tại"));
        }

        User newUser = new User();
        newUser.setEmail(input.getEmail());
        newUser.setUsername(input.getUsername());
        newUser.setFullName(input.getFullName());
        newUser.setRole(Role.CUSTOMER);

        // ma hoa mk
        try {
            newUser.changePassword(input.getPassword(), passwordEncoder);
        } catch (IllegalArgumentException e) {
            outputBoundary.present(new RegisterOutputData(false, e.getMessage()));
            return;
        }
        userRepository.save(newUser);
        outputBoundary.present(new RegisterOutputData(true, "Đăng kí thành công"));
    }
}
