package cosmetics.usecase.category.add;

import cosmetics.RequestData;

public class AddCategoryInputData implements RequestData {
    private String name;

    public AddCategoryInputData(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}