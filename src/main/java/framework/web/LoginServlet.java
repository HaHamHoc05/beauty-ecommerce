package framework.web;

import adapters.login.LoginController;
import adapters.login.LoginPresenter;
import cosmetics.usecase.login.LoginUseCase;
import repository.DB.MySQLUserRepository;
import security.BCryptPasswordEncoder; // Import class bạn đã có

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private LoginController controller;
    private LoginPresenter presenter;

    @Override
    public void init() throws ServletException {
        super.init();

        // 1. Khởi tạo các thành phần Infrastructure
        MySQLUserRepository userRepo = new MySQLUserRepository();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 2. Khởi tạo Adapter (Presenter)
        this.presenter = new LoginPresenter();

        // 3. Khởi tạo Core (UseCase) - Đấu dây Repository và Presenter vào
        LoginUseCase loginUseCase = new LoginUseCase(presenter, userRepo, passwordEncoder);

        // 4. Khởi tạo Controller - Đấu dây UseCase vào
        this.controller = new LoginController(loginUseCase);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Hiển thị trang login.jsp khi vào đường dẫn /login
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Lấy dữ liệu từ Form
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 2. Gọi Controller xử lý
        controller.execute(username, password);

        // 3. Lấy kết quả từ Presenter (ViewModel)
        var viewModel = presenter.getViewModel();

        // 4. Điều hướng dựa trên kết quả
        if ("SUCCESS".equals(viewModel.getStatus())) {
            // Đăng nhập thành công -> Lưu vào Session -> Chuyển sang trang chủ
            req.getSession().setAttribute("user", viewModel.getFullName());
            req.getSession().setAttribute("role", viewModel.getRole());
            resp.sendRedirect(req.getContextPath() + "/home"); // Giả sử có trang /home
        } else {
            // Đăng nhập thất bại -> Gửi lỗi về lại trang JSP
            req.setAttribute("errorMessage", viewModel.getErrorMessage());
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}