package cosmetics;

public interface OutputBoundary<T extends ResponseData> {
    void present(T inputData);
}
