package framework.web;

import adapters.category.add.AddCategoryController;
import adapters.category.add.AddCategoryPresenter;
import cosmetics.usecase.category.add.AddCategoryUseCase;
import repository.DB.MySQLCategoryRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/add-category")
public class AddCategoryServlet extends HttpServlet {

    private AddCategoryController controller;
    private AddCategoryPresenter presenter;

    @Override
    public void init() {
        MySQLCategoryRepository repo = new MySQLCategoryRepository();
        this.presenter = new AddCategoryPresenter();
        AddCategoryUseCase useCase = new AddCategoryUseCase(presenter, repo);
        this.controller = new AddCategoryController(useCase);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/admin/add-category.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        controller.execute(name);

        var viewModel = presenter.getViewModel();

        if ("SUCCESS".equals(viewModel.getStatus())) {
            req.setAttribute("message", viewModel.getMessage());
        } else {
            req.setAttribute("error", viewModel.getMessage());
        }
        req.getRequestDispatcher("/views/admin/add-category.jsp").forward(req, resp);
    }
}