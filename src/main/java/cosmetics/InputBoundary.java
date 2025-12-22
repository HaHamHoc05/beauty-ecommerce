package cosmetics;

public interface InputBoundary<T extends RequestData> {
    void execute(T inputData);
}
