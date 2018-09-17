package com.opsian.performance_problems_example.legacy_bank_service;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.IOException;

public class LegacyBankProxy
{
    private static final int PREFIX_LENGTH = "Bank Balance: ".length();

    public int getBankBalance(final long personId)
    {
        try
        {
            final Response response = Request.Get("http://localhost:7082/").execute();
            final String body = response.returnContent().asString();
            return Integer.parseInt(body.substring(PREFIX_LENGTH).trim());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
