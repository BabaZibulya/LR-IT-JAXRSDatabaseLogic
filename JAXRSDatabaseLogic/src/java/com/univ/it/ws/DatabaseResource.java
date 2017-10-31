package com.univ.it.ws;

import com.univ.it.table.LocalDataBase;
import com.univ.it.table.Table;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.StringJoiner;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("database")
public class DatabaseResource {

    private LocalDataBase db;
    private final String PATH_TO_DB = "/home/bondarenko";
    private final String DB_NAME = "test.db";
    
    public DatabaseResource() throws Exception {
        db = LocalDataBase.readFromFile(PATH_TO_DB + "/" + DB_NAME);
    }
    
    @Context
    private UriInfo context;
    
    @GET
    @Path("/tables")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTablesNames() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (String tableName : db.getTables().keySet()) {
            stringJoiner.add(tableName);
        }
        return stringJoiner.toString();
    }
    
    @GET
    @Path("/tables/{tableName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTable(@PathParam("tableName") String tableName) {
        return db.getTable(tableName).toString();
    }

    @GET
    @Path("/tables/difference/{tableName1},{tableName2}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTableDifference(@PathParam("tableName1") String tableName1,
            @PathParam("tableName2") String tableName2) {
        return Table.differenceBetween(
                db.getTable(tableName1),
                db.getTable(tableName2)
        ).toString();
    }
    
    @POST
    @Path("/tables/add/{tableName}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addTable(String message, @PathParam("tableName") String tableName) {
        try {
            db.addTable(new Table(
                new BufferedReader(new StringReader(message)),
                tableName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.noContent().build();
        }
        return Response.created(context.getAbsolutePath()).build();
    }
    
    @POST
    @Path("/tables/{tableName}/add_row")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addRow(String message, @PathParam("tableName") String tableName) {
        if (db.getTable(tableName) != null) {
            try {
                db.getTable(tableName).addNewRow(message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return Response.noContent().build();
            }
            return Response.created(context.getAbsolutePath()).build();
        } else {
            return Response.noContent().build();
        }  
    }
    
    @POST
    @Path("/tables/drop/{tableName}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response dropTable(@PathParam("tableName") String tableName) {
        if (db.getTable(tableName) != null) {
            db.dropTable(tableName);
            return Response.created(context.getAbsolutePath()).build();
        } else {
            return Response.noContent().build();
        }  
    }
    
    @POST
    @Path("/save")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response save() {
        try {
            db.writeToFile(PATH_TO_DB);
            return Response.created(context.getAbsolutePath()).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.noContent().build();
        }
    }
}
