package framework.web;

import adapters.login.LoginController;
import adapters.login.LoginPresenter;
import cosmetics.usecase.login.LoginUseCase;
import repository.DB.MySQLUserRepository;
import security.BCryptPasswordEncoder; // Phải dùng cái này

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private LoginController controller;
    private LoginPresenter presenter;

    @Override
    public void init() {
        MySQLUserRepository userRepo = new MySQLUserRepository();
        // 1. Khởi tạo mã hóa mật khẩu
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.presenter = new LoginPresenter();

        // 2. Truyền passwordEncoder vào LoginUseCase
        LoginUseCase useCase = new LoginUseCase(presenter, userRepo, passwordEncoder);
        this.controller = new LoginController(useCase);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password =  req.getParameter("password");

        controller.login(username, password);

        var vm = presenter.getViewModel();

        if ("SUCCESS".equals(vm.getStatus())) {

            HttpSession session = req.getSession();
            // Lưu thông tin vào session (Lấy từ ViewModel)
            session.setAttribute("user", vm.getFullName()); // ViewModel có getFullName()
            session.setAttribute("role", vm.getRole());     // ViewModel có getRole()

            // Điều hướng
            if ("ADMIN".equals(vm.getRole())) {
                resp.sendRedirect("admin/orders");
            } else {
                resp.sendRedirect("home");
            }

        } else {
            // Thất bại
            // Thay vì vm.getMessage() -> Hãy dùng getErrorMessage()
            req.setAttribute("error", vm.getErrorMessage());

            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}