package framework.web;

import adapters.product.add.AddProductController;
import adapters.product.add.AddProductPresenter;
import cosmetics.usecase.product.add.AddProductUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import repository.DB.MySQLCategoryRepository;
import repository.DB.MySQLProductRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/admin/add-product")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class AddProductServlet extends HttpServlet {

    private AddProductController controller;
    private AddProductPresenter presenter;
    private MySQLCategoryRepository categoryRepo;

    @Override
    public void init() {
        MySQLProductRepository productRepo = new MySQLProductRepository();
        this.categoryRepo = new MySQLCategoryRepository();

        this.presenter = new AddProductPresenter();
        AddProductUseCase useCase = new AddProductUseCase(presenter, productRepo);
        this.controller = new AddProductController(useCase);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load danh sách Category để đổ vào Dropdown Select
        req.setAttribute("categories", categoryRepo.findAll());
        req.getRequestDispatcher("/views/admin/add-product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //  Xử lý upload ảnh
        Part filePart = req.getPart("image"); // là name của input file trong JSP
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Lưu ảnh vào thư mục 'uploads' trong server
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        // Chỉ lưu nếu có file
        String imagePathInDB = "";
        if (fileName != null && !fileName.isEmpty()) {
            filePart.write(uploadPath + File.separator + fileName);
            imagePathInDB = "uploads/" + fileName; // Đường dẫn sẽ lưu vào DB
        }

        //Lấy các tham số khác
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String desc = req.getParameter("description");
        String stock = req.getParameter("stock");
        String categoryId = req.getParameter("categoryId");


        controller.execute(name, price, desc, imagePathInDB, stock, categoryId);


        var viewModel = presenter.getViewModel();
        req.setAttribute("message", viewModel.getMessage());

        // Load lại categories để form không bị mất dropdown
        req.setAttribute("categories", categoryRepo.findAll());
        req.getRequestDispatcher("/views/admin/add-product.jsp").forward(req, resp);
    }
}