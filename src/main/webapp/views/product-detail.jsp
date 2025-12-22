<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<jsp:include page="common/header.jsp" />
<body>
<div class="container mt-5">
    <div class="row">
        <div class="col-md-5">
            <img src="${product.image}" class="img-fluid rounded shadow" alt="${product.name}">
        </div>

        <div class="col-md-7">
            <h2>${product.name}</h2>
            <h3 class="text-danger my-3"><fmt:formatNumber value="${product.price}" pattern="#,###"/> đ</h3>

            <p>${product.description}</p>
            <p>Trạng thái:
                <span class="badge ${product.stockQuantity > 0 ? 'bg-success' : 'bg-danger'}">
                    ${product.stockQuantity > 0 ? 'Còn hàng' : 'Hết hàng'}
                </span>
            </p>

            <form action="cart" method="post" class="mt-4">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productId" value="${product.id}">

                <div class="d-flex align-items-center mb-3">
                    <label class="me-3">Số lượng:</label>
                    <input type="number" name="quantity" value="1" min="1" max="${product.stockQuantity}" class="form-control" style="width: 80px;">
                </div>

                <button type="submit" class="btn btn-primary btn-lg">
                    <i class="fas fa-cart-plus"></i> Thêm vào giỏ
                </button>
                <a href="home" class="btn btn-outline-secondary btn-lg ms-2">Quay lại</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>