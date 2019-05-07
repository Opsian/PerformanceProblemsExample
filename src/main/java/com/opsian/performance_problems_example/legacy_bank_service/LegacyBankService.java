package com.opsian.performance_problems_example.legacy_bank_service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.ThreadPool.SizedThreadPool;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class LegacyBankService
{
    private static final int RANDOM_DELAY = -1;
    private static long delay;

    public static void main(String[] args)
    {
        if (args.length == 1)
        {
            delay = Integer.parseInt(args[0]);
        }
        else
        {
            delay = RANDOM_DELAY;
        }
        System.out.printf("Delay = %d%n", delay);

        LegacyBankService service = new LegacyBankService();
        service.start();
        service.join();
    }

    private final Server server;

    public LegacyBankService()
    {
        server = new Server(7082);
    }

    public void start()
    {
        final SizedThreadPool threadPool = (SizedThreadPool) server.getThreadPool();
        threadPool.setMinThreads(12);
        threadPool.setMaxThreads(12);

        final ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(LegacyBankServlet.class, "/");

        server.setHandler(servletHandler);

        try
        {
            server.start();
            server.dumpStdErr();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private void join()
    {
        try
        {
            server.join();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void stop()
    {
        try
        {
            server.setStopTimeout(500);
            server.stop();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static class LegacyBankServlet extends HttpServlet
    {
        @Override
        protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException
        {
            try
            {
                final long delay = LegacyBankService.delay == RANDOM_DELAY ?
                    ThreadLocalRandom.current().nextInt(300, 1000) :
                    LegacyBankService.delay;

                Thread.sleep(delay);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            final int bankBalance = 500;
            resp.getWriter().printf("Bank Balance: %d\n", bankBalance);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
