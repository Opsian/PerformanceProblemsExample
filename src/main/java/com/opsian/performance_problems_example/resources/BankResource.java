package com.opsian.performance_problems_example.resources;

import com.opsian.performance_problems_example.core.Bank;
import com.opsian.performance_problems_example.core.Person;
import com.opsian.performance_problems_example.db.PersonDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
public class BankResource
{
    private final Bank bank;

    public BankResource(final Bank bank)
    {
        this.bank = bank;
    }

    @Path("/contended_transfer/{fromPersonId}/{toPersonId}/{amount}")
    @POST
    @UnitOfWork
    public boolean contendedTransferMoney(
        @PathParam("fromPersonId") LongParam fromPersonId,
        @PathParam("toPersonId") LongParam toPersonId,
        @PathParam("amount") LongParam amount)
    {
        return bank.contendedTransferMoney(fromPersonId.get(), toPersonId.get(), amount.get());
    }

    @Path("/fast_transfer/{fromPersonId}/{toPersonId}/{amount}")
    @POST
    @UnitOfWork
    public boolean fastTransferMoney(
        @PathParam("fromPersonId") LongParam fromPersonId,
        @PathParam("toPersonId") LongParam toPersonId,
        @PathParam("amount") LongParam amount)
    {
        return bank.fastTransferMoney(fromPersonId.get(), toPersonId.get(), amount.get());
    }

}
