package com.intern.wcc.model.helper;

public class SearchModel {
    private Integer offset;
    private Integer maxResult;
    private String order;
    private String orderFlow;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(Integer maxResult) {
        this.maxResult = maxResult;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderFlow() {
        return orderFlow;
    }

    public void setOrderFlow(String orderFlow) {
        this.orderFlow = orderFlow;
    }
}
