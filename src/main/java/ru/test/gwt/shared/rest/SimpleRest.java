package ru.test.gwt.shared.rest;

import org.fusesource.restygwt.client.DirectRestService;
import org.springframework.web.bind.annotation.RequestBody;
import ru.test.gwt.shared.dto.PersonDto;
import ru.test.gwt.shared.dto.StringDto;

import javax.ws.rs.*;
import java.util.List;

/**
 * @author fil.
 */
@Path("rest/person")
public interface SimpleRest extends DirectRestService {

    @Path("hello")
    @POST
    StringDto sayHello(StringDto targetName);

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
