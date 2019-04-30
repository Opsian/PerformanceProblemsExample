package com.opsian.performance_problems_example.houses_before;

import liquibase.util.csv.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesData
{
    public static void main(String[] args) throws IOException
    {
        final List<HouseSale> sales = readSales();
        sales.forEach(System.out::println);
    }

    private static List<HouseSale> sales = null;

    // TODO: break this
    public synchronized static List<HouseSale> getSales()
    {
        if (sales == null)
        {
            try
            {
                // sales = readSales();
                return readSales();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return sales;
    }

    private static List<HouseSale> readSales() throws IOException
    {
        final List<HouseSale> sales = new ArrayList<>();
        final File file = new File("pp-monthly-update-new-version.csv");
        try (final CSVReader reader = new CSVReader(new FileReader(file)))
        {
            String[] next;
            while ((next = reader.readNext()) != null)
            {
                // 0 - Transaction Id
                // 1 - Price
                // 2 - Date
                // 3 - Postcode
                // 4 - Property Type	D = Detached, S = Semi-Detached, T = Terraced, F = Flats/Maisonettes, O = Other
                // 5 - Y = New build, N = Established
                // 6 - Duration - F = Freehold, L = Leasehold
                // 7 - Primary Addressable Object Name. Typically the house number or name.
                // 8 - Secondary Addressable Object Name. Where a property has been divided into separate units
                // (for example, flats), the PAON (above) will identify the building and a SAON will be specified that
                // identifies the separate unit/flat.
                // 9 - Street
                // 10 - locality
                // 11 - Town/City
                // 12 - District
                // 13 - County
                // 14 - PPD, A = Standard Price Paid, Additional Price Paid.

                final long price = Long.parseLong(next[1]);
                final LocalDate date = LocalDate.parse(next[2].split(" ")[0]);
                final PropertyType propertyType = lookupPropertyType(next[4]);
                final boolean isNewBuild = "Y".equals(next[5]);
                final LandOwnership landOwnership = lookupLandOwnership(next[6]);
                final HouseSale sale = new HouseSale(
                    price,
                    date,
                    next[3],
                    propertyType,
                    isNewBuild,
                    landOwnership,
                    next[7],
                    next[8],
                    next[9],
                    next[10],
                    next[11],
                    next[12],
                    next[13]);
                sales.add(sale);
            }
        }
        return sales;
    }

    private static LandOwnership lookupLandOwnership(final String input)
    {
        switch (input)
        {
            case "F":
                return LandOwnership.FREEHOLD;
            case "L":
                return LandOwnership.LEASEHOLD;
        }

        throw new IllegalArgumentException("Unknown input: " + input);
    }

    private static PropertyType lookupPropertyType(final String input)
    {
        switch (input)
        {
            case "D":
                return PropertyType.DETACHED;
            case "S":
                return PropertyType.SEMI_DETACHED;
            case "T":
                return PropertyType.TERRACED;
            case "F":
                return PropertyType.FLAT;
            case "O":
            default:
                return PropertyType.OTHER;
        }
    }
}
