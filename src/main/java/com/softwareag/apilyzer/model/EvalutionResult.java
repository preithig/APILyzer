package com.softwareag.apilyzer.model;

import java.util.List;

public class EvalutionResult {

    private String apiName;

    private String id;

    private List<Category> categories;

    public EvalutionResult() {
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
