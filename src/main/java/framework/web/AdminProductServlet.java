package framework.web;

import adapters.product.ProductController;
import adapters.product.ProductPresenter;
import cosmetics.usecase.category.get.GetCategoryListUseCase; // <--- 1. Import UseCase lấy danh mục
import cosmetics.usecase.product.add.AddProductUseCase;
import cosmetics.usecase.product.delete.DeleteProductUseCase;
import cosmetics.usecase.product.edit.EditProductUseCase;
import cosmetics.usecase.product.get.GetProductListUseCase;
import repository.DB.MySQLCategoryRepository; // <--- 2. Import Repo danh mục
import repository.DB.MySQLProductRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {
        "/admin/products",
        "/admin/product/add",
        "/admin/product/edit",
        "/admin/product/delete"
})
public class AdminProductServlet extends HttpServlet {

    private ProductController controller;
    private ProductPresenter presenter;
    private MySQLProductRepository productRepo;
    private GetCategoryListUseCase getCategoryListUC; // <--- 3. Khai báo biến UseCase

    @Override
    public void init() {
        this.productRepo = new MySQLProductRepository();
        this.presenter = new ProductPresenter();

        // --- 4. KHỞI TẠO USECASE LẤY DANH MỤC ---
        MySQLCategoryRepository categoryRepo = new MySQLCategoryRepository();
        this.getCategoryListUC = new GetCategoryListUseCase(categoryRepo);

        // Khởi tạo các UseCase Product như cũ
        AddProductUseCase addUC = new AddProductUseCase(presenter, productRepo);
        EditProductUseCase editUC = new EditProductUseCase(presenter, productRepo);
        DeleteProductUseCase deleteUC = new DeleteProductUseCase(presenter, productRepo);
        GetProductListUseCase getListUC = new GetProductListUseCase(presenter, productRepo);

        this.controller = new ProductController(addUC, getListUC, editUC, deleteUC);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/admin/products".equals(path)) {
            controller.viewList();
            req.setAttribute("products", presenter.getViewModel().getProducts());
            req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);
        }
        else if ("/admin/product/add".equals(path)) {
            // --- 5. LẤY DANH SÁCH CATEGORY VÀ GỬI SANG JSP ---
            req.setAttribute("categories", getCategoryListUC.execute());

            req.getRequestDispatcher("/views/admin/add-product.jsp").forward(req, resp);
        }
        else if ("/admin/product/edit".equals(path)) {
            try {
                Integer id = Integer.parseInt(req.getParameter("id"));
                var product = productRepo.findById(id);
                req.setAttribute("p", product);

                // --- 6. CŨNG PHẢI GỬI CATEGORY SANG TRANG EDIT ---
                req.setAttribute("categories", getCategoryListUC.execute());

                req.getRequestDispatcher("/views/admin/edit-product.jsp").forward(req, resp);
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/admin/products");
            }
        }
        else if ("/admin/product/delete".equals(path)) {
            String id = req.getParameter("id");
            if (id != null) controller.deleteProduct(id);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        // Lấy dữ liệu form
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String image = req.getParameter("image");
        String desc = req.getParameter("description");
        String stock = req.getParameter("stock");
        String catId = req.getParameter("categoryId");

        // ... (Phần logic doPost giữ nguyên) ...
        // Lưu ý: Nếu có lỗi (Exception), bạn nên load lại danh sách category
        // trước khi forward về trang lỗi để dropdown không bị mất.
        try {
            if ("/admin/product/add".equals(path)) {
                controller.addProduct(name, price, image, desc, stock, catId);
            }
            else if ("/admin/product/edit".equals(path)) {
                String id = req.getParameter("id");
                String status = req.getParameter("status");
                controller.editProduct(id, name, price, image, desc, stock, catId, status);
            }

            var vm = presenter.getViewModel();
            if (vm.isSuccess()) {
                // Thành công -> Quay về danh sách
                resp.sendRedirect(req.getContextPath() + "/admin/products");
            } else {
                // Thất bại -> Ở lại trang cũ và báo lỗi
                req.setAttribute("error", vm.getMessage());

                // Load lại danh mục để không bị mất dropdown
                req.setAttribute("categories", getCategoryListUC.execute());

                String view = path.contains("add") ? "/views/admin/add-product.jsp" : "/views/admin/edit-product.jsp";
                req.getRequestDispatcher(view).forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // NẾU LỖI NGOẠI LỆ: Cũng load lại category
            req.setAttribute("categories", getCategoryListUC.execute());
            req.setAttribute("error", "Lỗi nhập liệu: " + e.getMessage());
            String view = path.contains("add") ? "/views/admin/add-product.jsp" : "/views/admin/edit-product.jsp";
            req.getRequestDispatcher(view).forward(req, resp);
        }
    }
}