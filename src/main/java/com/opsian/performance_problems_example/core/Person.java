package com.opsian.performance_problems_example.core;

import io.dropwizard.jersey.params.LongParam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "people")
@NamedQueries(
    {
        @NamedQuery(
            name = "Person.findAll",
            query = "SELECT p FROM Person p"
        )
    })
public class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(name = "jobTitle", nullable = false)
    private String jobTitle;

    @Column(name = "bankBalance", nullable = false)
    private long bankBalance;


    public Person()
    {
    }

    public Person(String fullName, String jobTitle, final long bankBalance)
    {
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.bankBalance = bankBalance;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getJobTitle()
    {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    public long getBankBalance()
    {
        return bankBalance;
    }

    public void withdraw(final long amount)
    {
        bankBalance -= amount;
    }

    public void deposit(final long amount)
    {
        bankBalance += amount;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Person))
        {
            return false;
        }

        final Person that = (Person) o;

        return Objects.equals(this.id, that.id) &&
            Objects.equals(this.fullName, that.fullName) &&
            Objects.equals(this.jobTitle, that.jobTitle) &&
            this.bankBalance == that.bankBalance;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, fullName, jobTitle);
    }
}
