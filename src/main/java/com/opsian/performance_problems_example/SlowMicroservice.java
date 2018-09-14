package com.opsian.performance_problems_example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.ThreadPool.SizedThreadPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SlowMicroservice
{
    public static void main(String[] args) throws Exception
    {

        final Server server = new Server(7082);

        final SizedThreadPool threadPool = (SizedThreadPool) server.getThreadPool();
        threadPool.setMinThreads(12);
        threadPool.setMaxThreads(12);

        final ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(SlowServlet.class, "/");

        server.setHandler(servletHandler);

        server.start();
        server.dumpStdErr();
        server.join();
    }

    public static class SlowServlet extends HttpServlet
    {
        @Override
        protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            resp.getWriter().print("Hello World\n");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
