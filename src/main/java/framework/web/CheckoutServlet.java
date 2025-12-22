package framework.web;

import adapters.order.OrderController;
import adapters.order.OrderPresenter;
import cosmetics.usecase.order.create.PlaceOrderUseCase;
import cosmetics.usecase.order.get.GetMyOrdersUseCase;
import cosmetics.usecase.order.detail.GetOrderDetailUseCase;
import repository.DB.MySQLCartRepository;
import repository.DB.MySQLOrderRepository;
import repository.DB.MySQLProductRepository; // Import mới
import repository.DB.MySQLUserRepository;
import cosmetics.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private OrderController controller;
    private OrderPresenter presenter;
    private MySQLCartRepository cartRepo;
    private MySQLUserRepository userRepo;

    @Override
    public void init() {
        this.cartRepo = new MySQLCartRepository();
        this.userRepo = new MySQLUserRepository();
        MySQLOrderRepository orderRepo = new MySQLOrderRepository();

        // 1. QUAN TRỌNG: Khởi tạo ProductRepo để trừ kho
        MySQLProductRepository productRepo = new MySQLProductRepository();

        this.presenter = new OrderPresenter();

        // 2. Khởi tạo PlaceOrderUseCase với đủ 4 tham số
        PlaceOrderUseCase placeOrderUseCase = new PlaceOrderUseCase(presenter, cartRepo, orderRepo, productRepo);

        // (Optional) Các UseCase khác có thể để null nếu Servlet này không dùng,
        // nhưng để tránh lỗi NullPointer trong Controller, ta cứ khởi tạo hoặc truyền null có chủ đích.
        // Ở đây OrderController cần 3 tham số.
        this.controller = new OrderController(placeOrderUseCase, null, null);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("user");
        if (username == null) {
            resp.sendRedirect("login");
            return;
        }
        User user = userRepo.findByUsername(username);

        // Lấy giỏ hàng để hiển thị tạm tính
        var cart = cartRepo.findByUserId(user.getId());
        req.setAttribute("cart", cart);

        req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("user");
        User user = userRepo.findByUsername(username);

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        // Gọi Controller xử lý
        controller.placeOrder(user.getId(), name, phone, address);

        // Lấy kết quả từ ViewModel
        var vm = presenter.getViewModel();

        if ("SUCCESS".equals(vm.getStatus())) {
            // Đặt hàng thành công -> Chuyển sang trang thông báo hoặc danh sách đơn
            resp.sendRedirect("my-orders");
        } else {
            // Thất bại -> Quay lại trang checkout và báo lỗi
            req.setAttribute("error", vm.getMessage());
            req.setAttribute("cart", cartRepo.findByUserId(user.getId())); // Load lại giỏ
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
        }
    }
}