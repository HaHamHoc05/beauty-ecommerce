package framework.web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();

        // 1. Cho phép tài nguyên tĩnh và trang công khai
        if (path.startsWith("/assets") || path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/images") ||
                path.equals("/login") || path.equals("/register") || path.equals("/home") || path.equals("/product-detail")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        // 2. Bảo vệ trang Admin
        if (path.startsWith("/admin")) {
            if (!isLoggedIn || !"ADMIN".equals(role)) {
                // Nếu chưa login hoặc không phải admin -> Về trang login
                resp.sendRedirect(req.getContextPath() + "/login?message=access_denied");
                return;
            }
        }

        // 3. Bảo vệ trang cá nhân (cần login)
        if (path.startsWith("/cart") || path.startsWith("/checkout") || path.startsWith("/my-orders") || path.startsWith("/order-detail")) {
            if (!isLoggedIn) {
                resp.sendRedirect(req.getContextPath() + "/login?message=please_login");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}