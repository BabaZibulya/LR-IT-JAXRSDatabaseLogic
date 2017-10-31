package com.univ.it.ws;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import java.io.FileNotFoundException;

public class RSWebServiceDataBase implements IWebServiceDataBase {

    private Client client;
    private WebTarget webTarget;

    public RSWebServiceDataBase() {
        client = ClientBuilder.newClient();
        webTarget = client.target("http://localhost:8080/JAXRSDatabaseLogic/services/database/");
    }

    @Override
    public boolean addTable(String tableName, String tableRepresentation) {
        webTarget.path("/tables/add/" + tableName).request().post(Entity.text(tableRepresentation));
        return true;
    }

    @Override
    public boolean dropTable(String tableName) {
        webTarget.path("tables/drop/" + tableName).request().post(Entity.text(""));
        return true;
    }

    @Override
    public WebServiceTable[] getTables() {
        String tableNames = webTarget.path("tables").request().get(String.class);
        String[] tableNamesSplit = tableNames.split("\n");
        WebServiceTable[] tables = new WebServiceTable[tableNamesSplit.length];
        int i = 0;
        for (String tableName : tableNamesSplit) {
            tables[i] = getTable(tableName);
            ++i;
        }
        return tables;
    }

    @Override
    public void addRow(String tableName, String row) throws Exception {
        webTarget.path("tables/" + tableName + "/add_row").request().post(Entity.text(row));
    }

    @Override
    public void save() throws FileNotFoundException {
        webTarget.path("save").request().post(Entity.text(""));
    }

    @Override
    public WebServiceTable getTable(String tableName) {
        return new WebServiceTable(
                tableName,
                webTarget.path("tables/" + tableName).request().get(String.class)
        );
    }

    @Override
    public WebServiceTable calculateDifferenceBetween(String table1, String table2) {
        WebServiceTable result =  new WebServiceTable(
                "Difference",
                webTarget.path("tables/difference/" + table1 + "," + table2).request().get(String.class)
        );
        addTable(result.getTableName(), result.getTableStringRepresentation());
        return result;
    }
}
