package com.opsian.performance_problems_example.resources;

import com.opsian.performance_problems_example.core.Person;
import com.opsian.performance_problems_example.db.PersonDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.apache.commons.text.similarity.LevenshteinDistance;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    private final LevenshteinDistance distance = LevenshteinDistance.getDefaultInstance();

    private final PersonDAO peopleDAO;

    public SearchResource(PersonDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    // Contains ignore case?
    @GET
    @Path("/contains/{query}")
    @UnitOfWork
    public List<Person> contains(@PathParam("query") String query) {
        // All entirely bottlenecked on findAll() according to profiler, switch to SQL query?
        return peopleDAO.findAll()
            .stream()
            .filter(person -> person.getFullName().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }

    @GET
    @Path("/similarity/{query}")
    @UnitOfWork
    public List<Person> similarity(@PathParam("query") String query) {
        return peopleDAO.findAll()
            .stream()
            .sorted(comparing(person -> distance.apply(person.getFullName().toLowerCase(), query.toLowerCase())))
            .limit(10)
            .collect(Collectors.toList());
    }

    @GET
    @Path("/fast_similarity/{query}")
    @UnitOfWork
    public List<Person> fastSimilarity(@PathParam("query") String query) {
        final String lowerCaseQuery = query.toLowerCase();
        return peopleDAO.findAll()
            .stream()
            .map(person -> {
                final int distanceToPerson = this.distance.apply(person.getFullName().toLowerCase(), lowerCaseQuery);
                return new CachedDistance(person, distanceToPerson);
            })
            .sorted()
            .limit(10)
            .map(CachedDistance::getPerson)
            .collect(Collectors.toList());
    }

    private static final class CachedDistance implements Comparable<CachedDistance> {
        private final Person person;
        private final int distance;

        private CachedDistance(Person person, int distance) {
            this.person = person;
            this.distance = distance;
        }

        public Person getPerson() {
            return person;
        }

        @Override
        public int compareTo(CachedDistance other) {
            return Integer.compare(distance, other.distance);
        }
    }

}
