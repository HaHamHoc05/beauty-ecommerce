package cosmetics.usecase.order.create;

import cosmetics.RequestData;

public class PlaceOrderInputData implements RequestData {
    private final Integer userId;
    private final String receiverName;
    private final String receiverPhone;
    private final String receiverAddress;

    public PlaceOrderInputData(Integer userId, String receiverName, String receiverPhone, String receiverAddress) {
        this.userId = userId;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverAddress = receiverAddress;
    }

    public Integer getUserId() { return userId; }
    public String getReceiverName() { return receiverName; }
    public String getReceiverPhone() { return receiverPhone; }
    public String getReceiverAddress() { return receiverAddress; }
}