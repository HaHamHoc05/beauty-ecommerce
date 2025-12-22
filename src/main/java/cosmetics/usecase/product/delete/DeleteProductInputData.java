package cosmetics.usecase.product.delete;

import cosmetics.RequestData;

public class DeleteProductInputData implements RequestData {
    private final Integer id;
    public DeleteProductInputData(Integer id) { this.id = id; }
    public Integer getId() { return id; }
}