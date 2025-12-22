package framework.web;

import adapters.product.ProductController;
import adapters.product.ProductPresenter;
import cosmetics.usecase.product.get.GetProductListUseCase;
import repository.DB.MySQLProductRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/home", ""}) // Chạy khi vào trang chủ hoặc /home
public class HomeServlet extends HttpServlet {

    private ProductController controller;
    private ProductPresenter presenter;

    @Override
    public void init() {
        MySQLProductRepository productRepo = new MySQLProductRepository();
        this.presenter = new ProductPresenter();

        // 1. Khởi tạo UseCase Lấy danh sách
        GetProductListUseCase getListUC = new GetProductListUseCase(presenter, productRepo);

        // 2. Khởi tạo Controller (Chỉ truyền UseCase cần thiết, các cái khác null)
        // ProductController(Add, Edit, Delete, GetList)
        this.controller = new ProductController(null, null, null, getListUC);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Gọi Controller lấy danh sách
        controller.viewList();

        // Lấy dữ liệu từ ViewModel đẩy ra JSP
        req.setAttribute("products", presenter.getViewModel().getProducts());
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}