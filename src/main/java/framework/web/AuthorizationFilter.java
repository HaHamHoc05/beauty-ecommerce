package framework.web;

import cosmetics.entities.User;
import repository.DB.MySQLUserRepository;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// Áp dụng bộ lọc cho TOÀN BỘ hệ thống
@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    // Danh sách các đường dẫn "CÔNG KHAI" (Ai cũng vào được, không cần login)
    private static final List<String> PUBLIC_URLS = Arrays.asList(
            "/login",
            "/register",
            "/home",
            "/product-detail",
            "/cart",
            "/css",
            "/js",
            "/images",
            "/logout"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();

        // 1. Cho phép các trang Công khai đi qua luôn (Home, Login, CSS, JS...)
        // Nếu path nằm trong danh sách PUBLIC hoặc là file tĩnh (đuôi .css, .js...) -> Cho qua
        if (isPublicUrl(path) || path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Kiểm tra Đăng nhập (Lấy session)
        HttpSession session = req.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("user") : null;
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (username == null) {
            // Chưa đăng nhập mà đòi vào trang bảo mật -> Đá về login
            resp.sendRedirect(req.getContextPath() + "/login?message=access_denied");
            return;
        }

        // 3. Phân quyền (Role-based Access Control)

        // Nếu đang cố vào trang Admin (/admin/...)
        if (path.startsWith("/admin")) {
            if ("ADMIN".equals(role)) {
                // Là Admin -> Cho qua
                chain.doFilter(request, response);
            } else {
                // Là User thường mà đòi vào Admin -> Chặn, đá về Home
                resp.sendRedirect(req.getContextPath() + "/home?error=no_permission");
            }
            return;
        }

        // Nếu là User đã đăng nhập -> Cho phép vào các trang còn lại (Checkout, MyOrders...)
        chain.doFilter(request, response);
    }

    // Hàm phụ trợ kiểm tra URL công khai
    private boolean isPublicUrl(String path) {
        for (String url : PUBLIC_URLS) {
            if (path.equals(url) || path.startsWith(url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}