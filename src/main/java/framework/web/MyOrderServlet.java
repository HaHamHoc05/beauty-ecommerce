package framework.web;

import adapters.order.OrderController;
import adapters.order.OrderPresenter;
import cosmetics.usecase.order.get.GetMyOrdersUseCase;
import repository.DB.MySQLOrderRepository;
import repository.DB.MySQLUserRepository;
import cosmetics.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/my-orders")
public class MyOrderServlet extends HttpServlet {

    private OrderController controller;
    private OrderPresenter presenter;
    private MySQLUserRepository userRepo;

    @Override
    public void init() {
        MySQLOrderRepository orderRepo = new MySQLOrderRepository();
        this.userRepo = new MySQLUserRepository();
        this.presenter = new OrderPresenter();

        // Khởi tạo UseCase lấy danh sách
        GetMyOrdersUseCase getListUC = new GetMyOrdersUseCase(presenter, orderRepo);

        // Controller nhận vào (Place=null, GetList=getListUC, GetDetail=null)
        this.controller = new OrderController(null, getListUC, null);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("user");
        if (username == null) {
            resp.sendRedirect("login");
            return;
        }
        User user = userRepo.findByUsername(username);

        // Gọi Controller
        controller.viewMyOrders(user.getId());

        // Lấy dữ liệu ra view
        req.setAttribute("orders", presenter.getViewModel().getOrders());
        req.getRequestDispatcher("/views/my-orders.jsp").forward(req, resp);
    }
}