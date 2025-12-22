<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<jsp:include page="../common/header.jsp" />
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h3 class="mb-3">Thêm Danh Mục Mới</h3>

            <c:if test="${not empty message}"><div class="alert alert-info">${message}</div></c:if>

            <form action="add-category" method="post" class="card p-4">
                <div class="mb-3">
                    <label>Tên danh mục</label>
                    <input type="text" name="name" class="form-control" required placeholder="Ví dụ: Son môi, Kem dưỡng...">
                </div>
                <button type="submit" class="btn btn-primary">Thêm Mới</button>
                <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-link mt-2">Quay lại quản lý SP</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>