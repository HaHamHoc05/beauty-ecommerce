<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<jsp:include page="common/header.jsp" />
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card shadow">
                <div class="card-header bg-primary text-white text-center">
                    <h4>ĐĂNG NHẬP</h4>
                </div>
                <div class="card-body">
                    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
                    <c:if test="${param.message == 'register_success'}"><div class="alert alert-success">Đăng ký thành công! Hãy đăng nhập.</div></c:if>
                    <c:if test="${param.message == 'access_denied'}"><div class="alert alert-warning">Bạn không có quyền truy cập!</div></c:if>

                    <form action="login" method="post">
                        <div class="mb-3">
                            <label>Tên đăng nhập</label>
                            <input type="text" name="username" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label>Mật khẩu</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Đăng Nhập</button>
                    </form>
                    <div class="text-center mt-3">
                        <a href="register">Chưa có tài khoản? Đăng ký ngay</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>