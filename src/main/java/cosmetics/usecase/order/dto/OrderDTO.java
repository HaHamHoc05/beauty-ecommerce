package cosmetics.usecase.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderDTO {
    private Integer id;
    private String createdAt; // Đã format sẵn sang String
    private String status;
    private BigDecimal totalPrice;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private List<OrderItemDTO> items;

    public OrderDTO(Integer id, LocalDateTime createdAt, String status, BigDecimal totalPrice,
                    String receiverName, String receiverPhone, String receiverAddress, List<OrderItemDTO> items) {
        this.id = id;
        this.createdAt = createdAt != null ? createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "";
        this.status = status;
        this.totalPrice = totalPrice;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverAddress = receiverAddress;
        this.items = items;
    }
    // Getters
    public Integer getId() { return id; }
    public String getCreatedAt() { return createdAt; }
    public String getStatus() { return status; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public String getReceiverName() { return receiverName; }
    public String getReceiverPhone() { return receiverPhone; }
    public String getReceiverAddress() { return receiverAddress; }
    public List<OrderItemDTO> getItems() { return items; }
}