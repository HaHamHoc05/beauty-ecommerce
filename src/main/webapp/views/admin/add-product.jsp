<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<jsp:include page="../common/header.jsp" />
<body>
<div class="container mt-4">
    <h2>Thêm Sản Phẩm Mới</h2>

    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

    <form action="${pageContext.request.contextPath}/admin/product/add" method="post" class="card p-4 shadow-sm">
        <div class="row">
            <div class="col-md-6 mb-3">
                <label>Tên sản phẩm</label>
                <input type="text" name="name" class="form-control" required>
            </div>
            <div class="col-md-6 mb-3">
                <label>Giá tiền (VNĐ)</label>
                <input type="number" name="price" class="form-control" required>
            </div>
            <div class="col-md-6 mb-3">
                <label>Số lượng kho</label>
                <input type="number" name="stock" class="form-control" required>
            </div>

            <div class="col-md-6 mb-3">
                <label>Danh mục</label>
                <select name="categoryId" class="form-select" required>
                    <option value="">-- Chọn danh mục --</option>
                    <c:forEach var="cat" items="${categories}">
                        <option value="${cat.id}">${cat.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-12 mb-3">
                <label>Link hình ảnh (URL)</label>
                <input type="text" name="image" class="form-control" placeholder="https://example.com/image.jpg">
            </div>
            <div class="col-12 mb-3">
                <label>Mô tả chi tiết</label>
                <textarea name="description" class="form-control" rows="4"></textarea>
            </div>
        </div>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary">Lưu Sản Phẩm</button>
            <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>
</body>
</html>