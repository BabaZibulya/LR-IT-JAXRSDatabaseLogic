package com.univ.it.ws;

public class WebServiceTable {
    private String tableName;
    private String tableStringRepresentation;

    public WebServiceTable(String tableName, String tableStringRepresentation) {
        this.tableName = tableName;
        this.tableStringRepresentation = tableStringRepresentation;
    }

    public String getTableName() {
        return tableName;
    }
    public String getTableStringRepresentation() {
        return tableStringRepresentation;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public void setTableStringRepresentation(String tableStringRepresentation) {
        this.tableStringRepresentation = tableStringRepresentation;
    }
}