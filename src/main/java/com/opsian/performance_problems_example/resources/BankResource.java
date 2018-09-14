package com.opsian.performance_problems_example.resources;

import com.opsian.performance_problems_example.core.Bank;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/bank/")
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
        return bank.userLockingTransferMoney(fromPersonId.get(), toPersonId.get(), amount.get());
    }

    @Path("/user_locking_transfer/{fromPersonId}/{toPersonId}/{amount}")
    @POST
    @UnitOfWork
    public boolean userLockingTransferMoney(
        @PathParam("fromPersonId") LongParam fromPersonId,
        @PathParam("toPersonId") LongParam toPersonId,
        @PathParam("amount") LongParam amount)
    {
        return bank.userLockingTransferMoney(fromPersonId.get(), toPersonId.get(), amount.get());
    }

}
