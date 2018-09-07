package com.opsian.performance_problems_example.auth;

import com.opsian.performance_problems_example.core.User;
import io.dropwizard.auth.Authorizer;

public class ExampleAuthorizer implements Authorizer<User>
{

    @Override
    public boolean authorize(User user, String role)
    {
        return user.getRoles() != null && user.getRoles().contains(role);
    }
}
