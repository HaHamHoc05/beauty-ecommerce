package framework.web;

import cosmetics.entities.Product;
import repository.DB.MySQLProductRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product-detail")
public class ProductDetailServlet extends HttpServlet {

    private MySQLProductRepository productRepo;

    @Override
    public void init() {
        this.productRepo = new MySQLProductRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Product product = productRepo.findById(id);

            if (product != null) {
                req.setAttribute("product", product);
                req.getRequestDispatcher("/views/product-detail.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("home");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("home");
        }
    }
}