<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<jsp:include page="common/header.jsp" />
<body>
<div class="container">
    <div class="row">
        <div class="col-md-7">
            <div class="card shadow mb-4">
                <div class="card-header bg-success text-white">
                    <h5>Thông Tin Giao Hàng</h5>
                </div>
                <div class="card-body">
                    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

                    <form action="checkout" method="post">
                        <div class="mb-3">
                            <label>Họ và tên người nhận</label>
                            <input type="text" name="name" class="form-control" required placeholder="Nguyễn Văn A">
                        </div>
                        <div class="mb-3">
                            <label>Số điện thoại</label>
                            <input type="text" name="phone" class="form-control" required placeholder="0909xxxxxx">
                        </div>
                        <div class="mb-3">
                            <label>Địa chỉ giao hàng</label>
                            <textarea name="address" class="form-control" rows="3" required placeholder="Số nhà, đường, phường..."></textarea>
                        </div>
                        <button type="submit" class="btn btn-success w-100 btn-lg mt-3">XÁC NHẬN ĐẶT HÀNG</button>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-5">
            <div class="card shadow">
                <div class="card-header bg-light">
                    <h5>Đơn Hàng Của Bạn</h5>
                </div>
                <div class="card-body">
                    <ul class="list-group list-group-flush">
                        <c:forEach var="item" items="${cart.items}">
                            <li class="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <h6 class="my-0">${item.productName}</h6>
                                    <small class="text-muted">SL: ${item.quantity}</small>
                                </div>
                                <span class="text-muted"><fmt:formatNumber value="${item.subTotal}" pattern="#,###"/> đ</span>
                            </li>
                        </c:forEach>
                        <li class="list-group-item d-flex justify-content-between bg-light">
                            <span class="fw-bold">TỔNG CỘNG</span>
                            <strong class="text-danger"><fmt:formatNumber value="${cart.totalPrice}" pattern="#,###"/> đ</strong>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>