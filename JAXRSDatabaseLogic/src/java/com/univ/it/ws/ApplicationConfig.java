package com.univ.it.test;

import com.univ.it.ws.DatabaseResource;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/services")
public class ApplicationConfig extends Application {

   private Set<Object> singletons = new HashSet<Object>();
   private Set<Class<?>> empty = new HashSet<Class<?>>();

    public ApplicationConfig() {
        try {
            singletons.add(new DatabaseResource());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
