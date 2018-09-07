package com.opsian.performance_problems_example.db;

import com.opsian.performance_problems_example.core.Person;
import io.dropwizard.hibernate.AbstractDAO;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

public class PersonDAO extends AbstractDAO<Person>
{
    public PersonDAO(SessionFactory factory)
    {
        super(factory);
    }

    public Optional<Person> findById(Long id)
    {
        return Optional.ofNullable(get(id));
    }

    public Person findSafelyById(long personId)
    {
        return findById(personId).orElseThrow(() -> new NotFoundException("No such user: " + personId));
    }

    public Person create(Person person)
    {
        return persist(person);
    }

    @Override
    public Person persist(final Person entity) throws HibernateException
    {
        return super.persist(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Person> findAll()
    {
        return list((Query<Person>) namedQuery("Person.findAll"));
    }
}
