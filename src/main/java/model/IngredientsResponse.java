package model;

import java.util.List;

public class IngredientsResponse {

    private boolean success;
    private List<Ingredients> data;

    public boolean isSuccess() {
        return success;
    }

    public List<Ingredients> getData() {
        return data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(List<Ingredients> data) {
        this.data = data;
    }
}
