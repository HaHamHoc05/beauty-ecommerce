package framework.web;

import adapters.order.create.PlaceOrderController;
import adapters.order.create.PlaceOrderPresenter;
import cosmetics.usecase.order.create.PlaceOrderUseCase;
import repository.DB.MySQLCartRepository;
import repository.DB.MySQLOrderRepository;
import repository.DB.MySQLUserRepository;
import cosmetics.entities.Cart;
import cosmetics.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private PlaceOrderController controller;
    private PlaceOrderPresenter presenter;
    private MySQLCartRepository cartRepo;
    private MySQLUserRepository userRepo;

    @Override
    public void init() {
        this.cartRepo = new MySQLCartRepository();
        MySQLOrderRepository orderRepo = new MySQLOrderRepository();
        this.userRepo = new MySQLUserRepository();

        this.presenter = new PlaceOrderPresenter();
        PlaceOrderUseCase useCase = new PlaceOrderUseCase(presenter, cartRepo, orderRepo);
        this.controller = new PlaceOrderController(useCase);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("user");

        if (username == null) {
            resp.sendRedirect("login");
            return;
        }

        User user = userRepo.findByUsername(username);

        // Lấy giỏ hàng để hiển thị tổng tiền
        Cart cart = cartRepo.findByUserId(user.getId());
        if (cart == null || cart.getItems().isEmpty()) {
            // Giỏ hàng trống thì không cho vào trang thanh toán
            resp.sendRedirect("home?msg=CartEmpty");
            return;
        }

        req.setAttribute("cart", cart); // Để hiển thị danh sách item và tổng tiền
        req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("user");
        User user = userRepo.findByUsername(username);

        // 1. Lấy dữ liệu từ Form
        String name = req.getParameter("receiverName");
        String phone = req.getParameter("receiverPhone");
        String address = req.getParameter("receiverAddress");

        // 2. Gọi Controller
        controller.execute(user.getId(), name, phone, address);

        // 3. Kiểm tra kết quả
        var viewModel = presenter.getViewModel();

        if ("SUCCESS".equals(viewModel.getStatus())) {
            // Đặt hàng thành công
            session.setAttribute("orderSuccessMsg", viewModel.getMessage() + " Mã đơn: #" + viewModel.getOrderId());
            session.removeAttribute("cartCount"); // Reset icon giỏ hàng về 0
            resp.sendRedirect("home");
        } else {
            // Thất bại -> Quay lại trang checkout báo lỗi
            req.setAttribute("errorMessage", viewModel.getMessage());
            // Load lại cart để hiển thị lại form
            Cart cart = cartRepo.findByUserId(user.getId());
            req.setAttribute("cart", cart);
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
        }
    }
}