package cosmetics.usecase.cart.add;

import cosmetics.InputBoundary;
import cosmetics.entities.Cart;
import cosmetics.entities.CartItem;
import cosmetics.entities.Product;
import repository.CartRepository;
import repository.ProductRepository;

public class AddToCartUseCase implements InputBoundary<AddToCartInputData> {

    private final AddToCartOutputBoundary outputBoundary;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public AddToCartUseCase(AddToCartOutputBoundary outputBoundary,
                            CartRepository cartRepository,
                            ProductRepository productRepository) {
        this.outputBoundary = outputBoundary;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void execute(AddToCartInputData input) {
        // 1. Kiểm tra đăng nhập (
        if (input.getUserId() == null) {
            outputBoundary.present(new AddToCartOutputData("Vui lòng đăng nhập để mua hàng!"));
            return;
        }

        // 2. Kiểm tra sản phẩm có tồn tại và hợp lệ không?
        Product product = productRepository.findById(input.getProductId());

        if (product == null) {
            outputBoundary.present(new AddToCartOutputData("Sản phẩm không tồn tại!"));
            return;
        }

        // Kiểm tra trạng thái ACTIVE và Tồn kho > 0
        if (!"ACTIVE".equals(product.getStatus()) || product.getStockQuantity() <= 0) {
            outputBoundary.present(new AddToCartOutputData("Sản phẩm này hiện đang hết hàng hoặc ngừng kinh doanh!"));
            return;
        }

        // 3. Lấy giỏ hàng của User
        Cart cart = cartRepository.findByUserId(input.getUserId());

        // Nếu User chưa có giỏ hàng - Tạo mới
        if (cart == null) {
            Integer newCartId = cartRepository.createCart(input.getUserId());

            // Khởi tạo object Cart tạm để dùng tiếp bên dưới
            cart = new Cart();
            cart.setId(newCartId);
            cart.setUserId(input.getUserId());
        }

        // Kiểm tra xem sản phẩm này đã có trong giỏ chưa?
        CartItem existingItem = cartRepository.findItemInCart(cart.getId(), product.getId());

        if (existingItem != null) {
            // SẢN PHẨM ĐÃ CÓ TRONG GIỎ ---
            // Cộng thêm số lượng mới vào số lượng cũ
            int newQuantity = existingItem.getQuantity() + input.getQuantity();

            // Kiểm tra xem tổng số lượng có vượt quá kho không?
            if (newQuantity > product.getStockQuantity()) {
                outputBoundary.present(new AddToCartOutputData(
                        "Kho chỉ còn " + product.getStockQuantity() + " sản phẩm. Giỏ hàng của bạn đã có " + existingItem.getQuantity() + "."
                ));
                return;
            }

            // Cập nhật số lượng mới vào DB
            cartRepository.updateItemQuantity(existingItem.getId(), newQuantity);

        } else {
            // SẢN PHẨM CHƯA CÓ TRONG GIỎ ---

            // Kiểm tra số lượng mua có lớn hơn kho không
            if (input.getQuantity() > product.getStockQuantity()) {
                outputBoundary.present(new AddToCartOutputData("Số lượng yêu cầu vượt quá tồn kho hiện có!"));
                return;
            }

            // Gọi Repository để insert item mới
            cartRepository.addItem(cart.getId(), product.getId(), input.getQuantity());
        }

        // 5. Tính toán tổng số item trong giỏ để cập nhật icon giỏ hàng
        int totalItems = cartRepository.countItemsInCart(cart.getId());

        // 6. Trả về kết quả
        outputBoundary.present(new AddToCartOutputData("Đã thêm " + product.getName() + " vào giỏ hàng!", totalItems));
    }
}