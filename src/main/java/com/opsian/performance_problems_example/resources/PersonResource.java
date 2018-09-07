package com.opsian.performance_problems_example.resources;

import com.opsian.performance_problems_example.core.Person;
import com.opsian.performance_problems_example.db.PersonDAO;
import com.opsian.performance_problems_example.views.PersonView;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/people/{personId}")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource
{

    private final PersonDAO peopleDAO;

    public PersonResource(PersonDAO peopleDAO)
    {
        this.peopleDAO = peopleDAO;
    }

    @GET
    @UnitOfWork
    public Person getPerson(@PathParam("personId") LongParam personId)
    {
        return findSafely(personId.get());
    }

    @GET
    @Path("/view_freemarker")
    @UnitOfWork
    @Produces(MediaType.TEXT_HTML)
    public PersonView getPersonViewFreemarker(@PathParam("personId") LongParam personId)
    {
        return new PersonView(PersonView.Template.FREEMARKER, findSafely(personId.get()));
    }

    @GET
    @Path("/view_mustache")
    @UnitOfWork
    @Produces(MediaType.TEXT_HTML)
    public PersonView getPersonViewMustache(@PathParam("personId") LongParam personId)
    {
        return new PersonView(PersonView.Template.MUSTACHE, findSafely(personId.get()));
    }

    private Person findSafely(long personId)
    {
        return peopleDAO.findSafelyById(personId);
    }
}
