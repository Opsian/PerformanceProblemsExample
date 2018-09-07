package com.opsian.performance_problems_example;

import com.opsian.performance_problems_example.auth.ExampleAuthenticator;
import com.opsian.performance_problems_example.auth.ExampleAuthorizer;
import com.opsian.performance_problems_example.cli.RenderCommand;
import com.opsian.performance_problems_example.core.Person;
import com.opsian.performance_problems_example.core.Template;
import com.opsian.performance_problems_example.core.User;
import com.opsian.performance_problems_example.db.PersonDAO;
import com.opsian.performance_problems_example.filter.DateRequiredFeature;
import com.opsian.performance_problems_example.health.TemplateHealthCheck;
import com.opsian.performance_problems_example.resources.*;
import com.opsian.performance_problems_example.tasks.EchoTask;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.util.Map;

public class HelloWorldApplication extends Application<HelloWorldConfiguration>
{
    public static void main(String[] args) throws Exception
    {
        new HelloWorldApplication().run(args);
    }

    private final HibernateBundle<HelloWorldConfiguration> hibernateBundle =
        new HibernateBundle<HelloWorldConfiguration>(Person.class)
        {
            @Override
            public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration)
            {
                return configuration.getDataSourceFactory();
            }
        };

    @Override
    public String getName()
    {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap)
    {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false)
            )
        );

        bootstrap.addCommand(new RenderCommand());
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<HelloWorldConfiguration>()
        {
            @Override
            public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration)
            {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new ViewBundle<HelloWorldConfiguration>()
        {
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(HelloWorldConfiguration configuration)
            {
                return configuration.getViewRendererConfiguration();
            }
        });
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment)
    {
        final PersonDAO dao = new PersonDAO(hibernateBundle.getSessionFactory());
        final Template template = configuration.buildTemplate();

        environment.healthChecks().register("template", new TemplateHealthCheck(template));
        environment.admin().addTask(new EchoTask());
        environment.jersey().register(DateRequiredFeature.class);
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
            .setAuthenticator(new ExampleAuthenticator())
            .setAuthorizer(new ExampleAuthorizer())
            .setRealm("SUPER SECRET STUFF")
            .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new HelloWorldResource(template));
        environment.jersey().register(new ViewResource());
        environment.jersey().register(new ProtectedResource());
        environment.jersey().register(new PeopleResource(dao));
        environment.jersey().register(new PersonResource(dao));
        environment.jersey().register(new FilteredResource());
        environment.jersey().register(new SearchResource(dao));
    }
}
