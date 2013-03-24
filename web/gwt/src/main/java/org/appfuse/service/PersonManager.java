package org.appfuse.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.appfuse.model.Person;

@WebService
@Path("/people")
public interface PersonManager extends GenericManager<Person, Long> {

    @GET
    @Path("{id}")
    Person get(@PathParam("id") Long id);
    
    @GET
    @Path("count/{searchTerm}")
    long count(@PathParam("searchTerm") String searchTerm);
    
    @GET
    @Path("search/{searchTerm}")
    List<Person> search(@PathParam("searchTerm")String searchTerm,
    		@QueryParam("firstResult") Integer firstResult, 
    		@QueryParam("maxResults") Integer maxResults);
}