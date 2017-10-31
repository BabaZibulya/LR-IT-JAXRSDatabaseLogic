package com.univ.it.ws;

import java.io.FileNotFoundException;

public interface IWebServiceDataBase {
    boolean addTable(String tableName, String tableRepresentation);
    boolean dropTable(String tableName);
    WebServiceTable[] getTables();
    void addRow(String tableName, String row) throws Exception;
    void save() throws FileNotFoundException;
    WebServiceTable getTable(String tableName);
    WebServiceTable calculateDifferenceBetween(String table1, String table2);
}