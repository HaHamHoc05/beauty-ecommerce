package framework.web;

import adapters.register.RegisterController;
import adapters.register.RegisterPresenter;
import cosmetics.usecase.register.RegisterUseCase;
import repository.DB.MySQLUserRepository;
import security.BCryptPasswordEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private RegisterController controller;
    private RegisterPresenter presenter;

    @Override
    public void init() {
        MySQLUserRepository userRepo = new MySQLUserRepository();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.presenter = new RegisterPresenter();

        RegisterUseCase useCase = new RegisterUseCase(presenter, userRepo, passwordEncoder);
        this.controller = new RegisterController(useCase);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirmPassword");
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");

        if (!password.equals(confirm)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        controller.register(username, password, confirm, fullName, email);
        var vm = presenter.getViewModel();
        if ("SUCCESS".equals(vm.getStatus())) {
            // Thành công -> Chuyển qua trang Login
            resp.sendRedirect("login?message=register_success");
        } else {
            // Thất bại -> Hiện lỗi (Dùng getMessage() hoặc getErrorMessage() tùy ViewModel của bạn)
            req.setAttribute("error", vm.getMessage());
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }
}