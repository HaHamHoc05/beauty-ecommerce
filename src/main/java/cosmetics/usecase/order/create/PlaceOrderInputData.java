package cosmetics.usecase.order.create;

import cosmetics.RequestData;

public class PlaceOrderInputData implements RequestData {
    private Integer userId;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;

    public PlaceOrderInputData(Integer userId, String receiverName, String receiverPhone, String receiverAddress) {
        this.userId = userId;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverAddress = receiverAddress;
    }
    // Getters...
    public Integer getUserId() { return userId; }
    public String getReceiverName() { return receiverName; }
    public String getReceiverPhone() { return receiverPhone; }
    public String getReceiverAddress() { return receiverAddress; }
}