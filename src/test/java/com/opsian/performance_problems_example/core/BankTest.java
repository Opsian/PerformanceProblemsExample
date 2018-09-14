package com.opsian.performance_problems_example.core;

import com.opsian.performance_problems_example.db.PersonDAO;
import io.dropwizard.testing.junit.DAOTestRule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest
{
    @Rule
    public DAOTestRule daoTestRule = DAOTestRule.newBuilder()
        .addEntityClass(Person.class)
        .build();

    private SessionFactory sessionFactory;
    private PersonDAO personDAO;
    private Bank bank;
    private Person fromPerson;
    private Person toPerson;

    @Before
    public void setUp()
    {
        sessionFactory = daoTestRule.getSessionFactory();
        personDAO = new PersonDAO(sessionFactory);
        bank = new Bank(personDAO, sessionFactory);
        fromPerson = personDAO.create(new Person("Richard Warburton", "", 100));
        toPerson = personDAO.create(new Person("Sadiq Jaffer", "", 0));
    }

    @Test
    public void shouldContendedTransferMoney()
    {
        genericTransferMoney(bank::contendedTransferMoney);
    }

    // Does not work atm.
    @Test
    public void shouldFastTransferMoney()
    {
        genericTransferMoney(bank::fastTransferMoney);
    }

    @Test
    public void shouldUserLockingTransferMoney()
    {
        genericTransferMoney(bank::userLockingTransferMoney);
    }

    private void genericTransferMoney(final TransferMoney transferMoney)
    {
        final long fromPersonId = fromPerson.getId();
        final long toPersonId = toPerson.getId();

        daoTestRule.inTransaction(() ->
            assertTrue(transferMoney.transferMoney(fromPersonId, toPersonId, 100)));

        Session session2 = sessionFactory.openSession();
        final Person person1 = session2.get(Person.class, fromPersonId);
        final Person person2 = session2.get(Person.class, toPersonId);

        assertEquals(0, person1.getBankBalance());
        assertEquals(100, person2.getBankBalance());
    }

    interface TransferMoney
    {
        boolean transferMoney(final long fromPersonId, final long toPersonId, final long amount);
    }
}
