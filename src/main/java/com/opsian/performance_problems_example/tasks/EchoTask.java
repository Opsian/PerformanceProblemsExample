package com.opsian.performance_problems_example.tasks;

import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.PostBodyTask;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class EchoTask extends PostBodyTask
{
    public EchoTask()
    {
        super("echo");
    }

    @Override
    public void execute(final ImmutableMultimap<String, String> immutableMultimap, final String body, final PrintWriter output)
    {
        output.print(body);
        output.flush();
    }
}
