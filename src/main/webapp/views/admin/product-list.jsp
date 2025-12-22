<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<jsp:include page="../common/header.jsp" />
<body>
<div class="container-fluid px-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Quản Lý Sản Phẩm</h2>
        <a href="${pageContext.request.contextPath}/admin/product/add" class="btn btn-success">+ Thêm Mới</a>
    </div>

    <table class="table table-striped table-hover border">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Ảnh</th>
            <th>Tên sản phẩm</th>
            <th>Giá</th>
            <th>Kho</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${products}">
            <tr>
                <td>${p.id}</td>
                <td><img src="${p.image}" width="50" height="50" style="object-fit: cover;"></td>
                <td>${p.name}</td>
                <td><fmt:formatNumber value="${p.price}" pattern="#,###"/>đ</td>
                <td>${p.stockQuantity}</td>
                <td>
                    <a href="product/edit?id=${p.id}" class="btn btn-sm btn-primary">Sửa</a>
                    <a href="product/delete?id=${p.id}" class="btn btn-sm btn-danger" onclick="return confirm('Xóa sản phẩm này?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>