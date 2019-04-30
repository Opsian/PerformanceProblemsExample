package com.opsian.performance_problems_example.resources;

import com.opsian.performance_problems_example.houses_before.HouseSale;
import com.opsian.performance_problems_example.houses_before.SalesData;
import com.opsian.performance_problems_example.houses_before.SalesQuery;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.Comparator.comparing;

@Path("/house")
@Produces(MediaType.APPLICATION_JSON)
public class HouseResource
{
    private final SalesQuery salesQuery = new SalesQuery();

    @GET
    @Path("/contains/{query}")
    @UnitOfWork
    public List<HouseSale> contains(@PathParam("query") String queryStr)
    {
        return salesQuery.query(queryStr);
    }
}
