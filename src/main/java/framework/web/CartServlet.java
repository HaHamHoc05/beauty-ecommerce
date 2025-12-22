package framework.web;

import adapters.cart.CartController;
import adapters.cart.CartPresenter;
import cosmetics.usecase.cart.add.AddToCartUseCase;
import cosmetics.usecase.cart.get.GetCartUseCase;
import cosmetics.usecase.cart.remove.RemoveCartUseCase;
import cosmetics.usecase.cart.update.UpdateCartUseCase;
import repository.DB.MySQLCartRepository;
import repository.DB.MySQLProductRepository;
import repository.DB.MySQLUserRepository;
import cosmetics.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private CartController controller;
    private CartPresenter presenter;
    private MySQLUserRepository userRepo;

    @Override
    public void init() {
        MySQLCartRepository cartRepo = new MySQLCartRepository();
        MySQLProductRepository productRepo = new MySQLProductRepository();
        this.userRepo = new MySQLUserRepository();
        this.presenter = new CartPresenter();

        // Khởi tạo 4 UseCase cho Giỏ hàng
        AddToCartUseCase addUC = new AddToCartUseCase(presenter, cartRepo, productRepo);
        GetCartUseCase getUC = new GetCartUseCase(presenter, cartRepo);
        RemoveCartUseCase removeUC = new RemoveCartUseCase(presenter, cartRepo);
        UpdateCartUseCase updateUC = new UpdateCartUseCase(presenter, cartRepo, productRepo);

        this.controller = new CartController(addUC, getUC, removeUC, updateUC);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUser(req);
        if (user == null) { resp.sendRedirect("login"); return; }

        // Xem giỏ hàng
        controller.viewCart(user.getId());
        req.setAttribute("cart", presenter.getViewModel().getCart());
        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUser(req);
        if (user == null) { resp.sendRedirect("login"); return; }

        String action = req.getParameter("action");
        try {
            if ("add".equals(action)) {
                Integer prodId = Integer.parseInt(req.getParameter("productId"));
                Integer quantity = Integer.parseInt(req.getParameter("quantity"));
                controller.addToCart(user.getId(), prodId, quantity);
            }
            else if ("remove".equals(action)) {
                Integer prodId = Integer.parseInt(req.getParameter("productId"));
                controller.removeFromCart(user.getId(), prodId);
            }
            else if ("update".equals(action)) {
                Integer prodId = Integer.parseInt(req.getParameter("productId"));
                Integer quantity = Integer.parseInt(req.getParameter("quantity"));
                controller.updateCart(user.getId(), prodId, quantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sau khi xử lý xong thì load lại trang giỏ hàng
        resp.sendRedirect("cart");
    }

    private User getUser(HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("user");
        return (username != null) ? userRepo.findByUsername(username) : null;
    }
}