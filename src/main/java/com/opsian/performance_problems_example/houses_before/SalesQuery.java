package com.opsian.performance_problems_example.houses_before;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SalesQuery
{
    public static void main(String[] args)
    {
        final List<HouseSale> sales = new SalesQuery().search("london");
        sales.forEach(System.out::println);
    }

    public List<HouseSale> search(final String queryStr)
    {
        return SalesData.getSalesData()
            .stream()
            .filter(sale ->
                containsIgnoreCase(sale.getCounty(), queryStr) ||
                containsIgnoreCase(sale.getDistrict(), queryStr) ||
                containsIgnoreCase(sale.getLocality(), queryStr) ||
                containsIgnoreCase(sale.getPrimaryAddressableObjectName(), queryStr) ||
                containsIgnoreCase(sale.getSecondaryAddressableObjectName(), queryStr))
            .sorted(Comparator.comparing(HouseSale::getPrice))
            .collect(toList());
    }

    private boolean containsIgnoreCase(final String field, final String queryStr)
    {
        return field.toUpperCase().contains(queryStr.toUpperCase());
    }
}
