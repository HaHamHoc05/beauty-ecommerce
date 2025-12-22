<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<jsp:include page="common/header.jsp" />
<body>
<div class="container">
    <div class="row mb-4">
        <div class="col-md-6 mx-auto">
            <form action="home" method="get" class="d-flex">
                <input class="form-control me-2" type="search" name="search" placeholder="Tìm kiếm sản phẩm..." value="${param.search}">
                <button class="btn btn-outline-success" type="submit">Tìm</button>
            </form>
        </div>
    </div>

    <div class="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4">
        <c:forEach var="p" items="${products}">
            <div class="col">
                <div class="card h-100 shadow-sm">
                    <img src="${p.image}" class="card-img-top" alt="${p.name}" style="height: 200px; object-fit: cover;">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title text-truncate">${p.name}</h5>
                        <p class="card-text text-danger fw-bold"><fmt:formatNumber value="${p.price}" pattern="#,###"/> đ</p>

                        <div class="mt-auto d-flex justify-content-between">
                            <a href="product-detail?id=${p.id}" class="btn btn-sm btn-outline-secondary">Chi tiết</a>

                            <form action="cart" method="post">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="productId" value="${p.id}">
                                <input type="hidden" name="quantity" value="1">
                                <button type="submit" class="btn btn-sm btn-primary">
                                    <i class="fas fa-cart-plus"></i> Thêm
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>