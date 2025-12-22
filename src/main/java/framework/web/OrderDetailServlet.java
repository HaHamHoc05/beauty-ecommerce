package framework.web;

import adapters.order.OrderController;
import adapters.order.OrderPresenter;
import cosmetics.usecase.order.detail.GetOrderDetailUseCase;
import repository.DB.MySQLOrderRepository;
import repository.DB.MySQLUserRepository;
import cosmetics.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order-detail")
public class OrderDetailServlet extends HttpServlet {

    private OrderController controller;
    private OrderPresenter presenter;
    private MySQLUserRepository userRepo;

    @Override
    public void init() {
        MySQLOrderRepository orderRepo = new MySQLOrderRepository();
        this.userRepo = new MySQLUserRepository();
        this.presenter = new OrderPresenter();

        GetOrderDetailUseCase getDetailUC = new GetOrderDetailUseCase(presenter, orderRepo);

        // Controller nhận vào (Place=null, GetList=null, GetDetail=getDetailUC)
        this.controller = new OrderController(null, null, getDetailUC);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("user");
        if (username == null) {
            resp.sendRedirect("login");
            return;
        }
        User user = userRepo.findByUsername(username);

        try {
            Integer orderId = Integer.parseInt(req.getParameter("id"));
            controller.viewOrderDetail(orderId, user.getId());

            var vm = presenter.getViewModel();
            if ("SUCCESS".equals(vm.getStatus())) {
                req.setAttribute("order", vm.getCurrentOrder());
                req.getRequestDispatcher("/views/order-detail.jsp").forward(req, resp);
            } else {
                // Không tìm thấy hoặc không có quyền
                resp.sendRedirect("my-orders");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("my-orders");
        }
    }
}