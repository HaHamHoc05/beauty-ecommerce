<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<jsp:include page="common/header.jsp" />
<body>
<div class="container">
    <h3 class="mb-4">Lịch Sử Đơn Hàng</h3>

    <c:if test="${empty orders}">
        <div class="alert alert-info">Bạn chưa có đơn hàng nào.</div>
    </c:if>

    <div class="list-group">
        <c:forEach var="o" items="${orders}">
            <a href="order-detail?id=${o.id}" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                <div>
                    <h5 class="mb-1">Đơn hàng #${o.id}</h5>
                    <small class="text-muted">Ngày đặt: ${o.createdAt}</small>
                    <p class="mb-1">Tổng tiền: <strong class="text-danger"><fmt:formatNumber value="${o.totalPrice}" pattern="#,###"/> đ</strong></p>
                </div>
                <div>
                    <span class="badge rounded-pill
                        ${o.status == 'PENDING' ? 'bg-warning text-dark' :
                          o.status == 'SHIPPING' ? 'bg-primary' :
                          o.status == 'DELIVERED' ? 'bg-success' : 'bg-secondary'}">
                            ${o.status}
                    </span>
                    <i class="fas fa-chevron-right ms-2"></i>
                </div>
            </a>
        </c:forEach>
    </div>
</div>
</body>
</html>