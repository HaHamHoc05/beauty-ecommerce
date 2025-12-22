<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<jsp:include page="common/header.jsp" />
<body>
<div class="container">
    <div class="card shadow">
        <div class="card-header d-flex justify-content-between align-items-center">
            <h4>Chi Tiết Đơn Hàng #${order.id}</h4>
            <span class="badge bg-info">${order.status}</span>
        </div>
        <div class="card-body">
            <div class="row mb-3">
                <div class="col-md-6">
                    <h5>Thông tin người nhận</h5>
                    <p><strong>Tên:</strong> ${order.receiverName}</p>
                    <p><strong>SĐT:</strong> ${order.receiverPhone}</p>
                    <p><strong>Địa chỉ:</strong> ${order.receiverAddress}</p>
                </div>
                <div class="col-md-6 text-end">
                    <p>Ngày đặt: ${order.createdAt}</p>
                </div>
            </div>

            <table class="table table-bordered">
                <thead class="table-light">
                <tr>
                    <th>Sản phẩm</th>
                    <th>Giá</th>
                    <th>SL</th>
                    <th>Thành tiền</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${order.items}">
                    <tr>
                        <td>${item.productName}</td>
                        <td><fmt:formatNumber value="${item.price}" pattern="#,###"/></td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.totalPrice}" pattern="#,###"/></td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="3" class="text-end fw-bold">TỔNG CỘNG</td>
                    <td class="text-danger fw-bold"><fmt:formatNumber value="${order.totalPrice}" pattern="#,###"/> đ</td>
                </tr>
                </tfoot>
            </table>
            <a href="my-orders" class="btn btn-secondary">Quay lại</a>
        </div>
    </div>
</div>
</body>
</html>