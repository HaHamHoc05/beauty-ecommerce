<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<jsp:include page="../common/header.jsp" />
<body>
<div class="container-fluid px-4 mt-4">
    <h2>Quản Lý Đơn Hàng</h2>
    <div class="card shadow mt-3">
        <div class="card-body">
            <table class="table table-hover table-bordered align-middle">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Ngày đặt</th>
                    <th>Khách hàng</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                    <th>Cập nhật</th>
                    <th>Chi tiết</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="o" items="${orders}">
                    <tr>
                        <td>#${o.id}</td>
                        <td>${o.createdAt}</td>
                        <td>
                            <strong>${o.receiverName}</strong><br>
                                ${o.receiverPhone}
                        </td>
                        <td class="text-danger fw-bold"><fmt:formatNumber value="${o.totalPrice}" pattern="#,###"/> đ</td>
                        <td>
                            <span class="badge ${o.status == 'PENDING' ? 'bg-warning' : 'bg-success'}">${o.status}</span>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/order/update" method="post" class="d-flex gap-1">
                                <input type="hidden" name="id" value="${o.id}">
                                <select name="status" class="form-select form-select-sm" style="width: 120px;">
                                    <option value="PENDING" ${o.status == 'PENDING' ? 'selected' : ''}>PENDING</option>
                                    <option value="SHIPPING" ${o.status == 'SHIPPING' ? 'selected' : ''}>SHIPPING</option>
                                    <option value="DELIVERED" ${o.status == 'DELIVERED' ? 'selected' : ''}>DELIVERED</option>
                                    <option value="CANCELLED" ${o.status == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
                                </select>
                                <button type="submit" class="btn btn-sm btn-primary"><i class="fas fa-save"></i></button>
                            </form>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/order-detail?id=${o.id}" class="btn btn-sm btn-outline-info">Xem</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>