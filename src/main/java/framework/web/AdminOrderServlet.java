package framework.web;

import adapters.order.OrderPresenter;
import adapters.order.admin.AdminOrderController;
import cosmetics.usecase.order.admin.GetAllOrdersUseCase;
import cosmetics.usecase.order.admin.status.UpdateOrderStatusUseCase;
import repository.DB.MySQLOrderRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/orders", "/admin/order/update"})
public class AdminOrderServlet extends HttpServlet {

    private AdminOrderController controller;
    private OrderPresenter presenter;

    @Override
    public void init() {
        MySQLOrderRepository orderRepo = new MySQLOrderRepository();
        this.presenter = new OrderPresenter();

        // Khởi tạo UseCase Admin
        GetAllOrdersUseCase getAllUC = new GetAllOrdersUseCase(presenter, orderRepo);
        UpdateOrderStatusUseCase updateUC = new UpdateOrderStatusUseCase(presenter, orderRepo);

        // Khởi tạo Admin Controller
        this.controller = new AdminOrderController(getAllUC, updateUC);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/orders".equals(path)) {
            controller.viewAllOrders();
            req.setAttribute("orders", presenter.getViewModel().getOrders());
            req.getRequestDispatcher("/views/admin/order-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/order/update".equals(path)) {
            try {
                Integer id = Integer.parseInt(req.getParameter("id"));
                String status = req.getParameter("status");

                controller.updateStatus(id, status);
                resp.sendRedirect(req.getContextPath() + "/admin/orders");
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendRedirect(req.getContextPath() + "/admin/orders?error=true");
            }
        }
    }
}