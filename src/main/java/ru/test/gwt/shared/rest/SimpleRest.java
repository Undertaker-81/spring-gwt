package ru.test.gwt.shared.rest;

import org.fusesource.restygwt.client.DirectRestService;
import ru.test.gwt.shared.dto.PersonDto;

import javax.ws.rs.*;
import java.util.List;

/**
 * @author fil.
 */
@Path("rest/person")
public interface SimpleRest extends DirectRestService {


    @GET
    List<PersonDto> allPerson();

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") String id);

    @POST
    void create(PersonDto personDto);

    @PUT
    @Path("/{id}")
    void update(@PathParam("id") String id, PersonDto personDto);
}
