<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<jsp:include page="../common/header.jsp" />
<body>
<div class="container mt-4">
    <h2>Cập Nhật Sản Phẩm</h2>
    <form action="${pageContext.request.contextPath}/admin/product/edit" method="post" class="card p-4 shadow-sm">
        <input type="hidden" name="id" value="${p.id}">

        <div class="row">
            <div class="col-md-6 mb-3">
                <label>Tên sản phẩm</label>
                <input type="text" name="name" value="${p.name}" class="form-control" required>
            </div>
            <div class="col-md-6 mb-3">
                <label>Giá tiền</label>
                <input type="number" name="price" value="${p.price}" class="form-control" required>
            </div>
            <div class="col-md-6 mb-3">
                <label>Tồn kho</label>
                <input type="number" name="stock" value="${p.stockQuantity}" class="form-control" required>
            </div>
            <div class="col-md-6 mb-3">
                <label>Danh mục</label>
                <select name="categoryId" class="form-select">
                    <c:forEach var="cat" items="${categories}">
                        <option value="${cat.id}" ${cat.id == p.categoryId ? 'selected' : ''}>${cat.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-6 mb-3">
                <label>Trạng thái</label>
                <select name="status" class="form-select">
                    <option value="ACTIVE" ${p.status == 'ACTIVE' ? 'selected' : ''}>Đang bán</option>
                    <option value="INACTIVE" ${p.status == 'INACTIVE' ? 'selected' : ''}>Ngừng bán</option>
                </select>
            </div>
            <div class="col-12 mb-3">
                <label>Link hình ảnh</label>
                <input type="text" name="image" value="${p.image}" class="form-control">
            </div>
            <div class="col-12 mb-3">
                <label>Mô tả</label>
                <textarea name="description" class="form-control" rows="4">${p.description}</textarea>
            </div>
        </div>
        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-warning">Cập Nhật</button>
            <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>
</body>
</html>