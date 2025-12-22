package framework.web;

import adapters.product.ProductController;
import adapters.product.ProductPresenter;
import cosmetics.usecase.product.add.AddProductUseCase;
import cosmetics.usecase.product.delete.DeleteProductUseCase;
import cosmetics.usecase.product.edit.EditProductUseCase;
import cosmetics.usecase.product.get.GetProductListUseCase;
import repository.DB.MySQLProductRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Servlet này xử lý nhiều URL
@WebServlet(urlPatterns = {
        "/admin/products",       // Xem danh sách
        "/admin/product/add",    // Thêm mới
        "/admin/product/edit",   // Sửa
        "/admin/product/delete"  // Xóa
})
public class AdminProductServlet extends HttpServlet {

    private ProductController controller;
    private ProductPresenter presenter;
    private MySQLProductRepository productRepo;

    @Override
    public void init() {
        this.productRepo = new MySQLProductRepository();
        this.presenter = new ProductPresenter();

        // 1. Khởi tạo tất cả UseCase liên quan đến Product
        AddProductUseCase addUC = new AddProductUseCase(presenter, productRepo);
        EditProductUseCase editUC = new EditProductUseCase(presenter, productRepo);
        DeleteProductUseCase deleteUC = new DeleteProductUseCase(presenter, productRepo);
        GetProductListUseCase getListUC = new GetProductListUseCase(presenter, productRepo);

        // 2. Gom vào Controller
        this.controller = new ProductController(addUC, editUC, deleteUC, getListUC);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/admin/products".equals(path)) {
            // Xem danh sách quản lý
            controller.viewList();
            req.setAttribute("products", presenter.getViewModel().getProducts());
            req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);
        }
        else if ("/admin/product/add".equals(path)) {
            // Form thêm mới
            req.getRequestDispatcher("/views/admin/add-product.jsp").forward(req, resp);
        }
        else if ("/admin/product/edit".equals(path)) {
            // Form sửa (cần load dữ liệu cũ lên)
            try {
                Integer id = Integer.parseInt(req.getParameter("id"));
                var product = productRepo.findById(id); // Dùng tạm repo để load detail cho form edit
                req.setAttribute("p", product);
                req.getRequestDispatcher("/views/admin/edit-product.jsp").forward(req, resp);
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/admin/products");
            }
        }
        else if ("/admin/product/delete".equals(path)) {
            // Xóa nhanh qua GET (hoặc dùng POST an toàn hơn)
            String id = req.getParameter("id");
            if (id != null) controller.deleteProduct(id);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        // Lấy dữ liệu form chung
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String image = req.getParameter("image"); // URL ảnh
        String desc = req.getParameter("description");
        String stock = req.getParameter("stock");
        String catId = req.getParameter("categoryId");

        try {
            if ("/admin/product/add".equals(path)) {
                controller.addProduct(name, price, image, desc, stock, catId);
            }
            else if ("/admin/product/edit".equals(path)) {
                String id = req.getParameter("id");
                String status = req.getParameter("status"); // ACTIVE/INACTIVE
                controller.editProduct(id, name, price, image, desc, stock, catId, status);
            }

            // Kiểm tra kết quả
            var vm = presenter.getViewModel();
            if ("SUCCESS".equals(vm.getStatus())) {
                resp.sendRedirect(req.getContextPath() + "/admin/products");
            } else {
                req.setAttribute("error", vm.getMessage());
                // Forward lại trang cũ để hiện lỗi
                String view = path.contains("add") ? "/views/admin/add-product.jsp" : "/views/admin/edit-product.jsp";
                req.getRequestDispatcher(view).forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/admin/products?error=true");
        }
    }
}