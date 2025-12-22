<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<jsp:include page="common/header.jsp" />
<body>
<div class="container">
    <h2 class="mb-4">Giỏ Hàng Của Bạn</h2>

    <c:choose>
        <c:when test="${empty cart or empty cart.items}">
            <div class="alert alert-info text-center">Giỏ hàng đang trống. <a href="home">Mua sắm ngay!</a></div>
        </c:when>
        <c:otherwise>
            <table class="table table-bordered align-middle">
                <thead class="table-light">
                <tr>
                    <th>Sản phẩm</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Thành tiền</th>
                    <th>Xóa</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${cart.items}">
                    <tr>
                        <td>${item.productName}</td>
                        <td><fmt:formatNumber value="${item.price}" pattern="#,###"/> đ</td>
                        <td style="width: 150px;">
                            <form action="cart" method="post" class="d-flex gap-2">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="productId" value="${item.productId}">
                                <input type="number" name="quantity" value="${item.quantity}" min="1" class="form-control form-control-sm">
                                <button type="submit" class="btn btn-sm btn-outline-primary">Lưu</button>
                            </form>
                        </td>
                        <td class="fw-bold"><fmt:formatNumber value="${item.totalPrice}" pattern="#,###"/> đ</td>
                        <td>
                            <form action="cart" method="post">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="productId" value="${item.productId}">
                                <button type="submit" class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="3" class="text-end fw-bold">Tổng cộng:</td>
                    <td colspan="2" class="text-danger fw-bold fs-5">
                        <fmt:formatNumber value="${cart.totalPrice}" pattern="#,###"/>đ                    </td>
                </tr>
                </tfoot>
            </table>
            <div class="d-flex justify-content-end">
                <a href="checkout" class="btn btn-success btn-lg">Tiến Hành Thanh Toán</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>