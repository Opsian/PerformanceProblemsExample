package com.opsian.performance_problems_example.houses_before;

import java.time.LocalDate;

public class HouseSale
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

    private final long price;
    private final LocalDate date;
    private final String postcode;
    private final PropertyType propertyType;
    private final boolean isNewBuild;
    private final LandOwnership landOwnership;
    private final String primaryAddressableObjectName;
    private final String secondaryAddressableObjectName;
    private final String street;
    private final String locality;
    private final String townOrCity;
    private final String district;
    private final String county;
    public HouseSale(
        final long price,
        final LocalDate date,
        final String postcode,
        final PropertyType propertyType,
        final boolean isNewBuild,
        final LandOwnership landOwnership,
        final String primaryAddressableObjectName,
        final String secondaryAddressableObjectName,
        final String street,
        final String locality,
        final String townOrCity,
        final String district,
        final String county)
    {
        this.price = price;
        this.date = date;
        this.postcode = postcode;
        this.propertyType = propertyType;
        this.isNewBuild = isNewBuild;
        this.landOwnership = landOwnership;
        this.primaryAddressableObjectName = primaryAddressableObjectName;
        this.secondaryAddressableObjectName = secondaryAddressableObjectName;
        this.street = street;
        this.locality = locality;
        this.townOrCity = townOrCity;
        this.district = district;
        this.county = county;
    }

    public long getPrice()
    {
        return price;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public PropertyType getPropertyType()
    {
        return propertyType;
    }

    public boolean isNewBuild()
    {
        return isNewBuild;
    }

    public LandOwnership getLandOwnership()
    {
        return landOwnership;
    }

    public String getPrimaryAddressableObjectName()
    {
        return primaryAddressableObjectName;
    }

    public String getSecondaryAddressableObjectName()
    {
        return secondaryAddressableObjectName;
    }

    public String getStreet()
    {
        return street;
    }

    public String getLocality()
    {
        return locality;
    }

    public String getTownOrCity()
    {
        return townOrCity;
    }

    public String getDistrict()
    {
        return district;
    }

    public String getCounty()
    {
        return county;
    }

    @Override
    public String toString()
    {
        return "Sale{" +
            "price=" + price +
            ", date=" + date +
            ", postcode='" + postcode + '\'' +
            ", propertyType=" + propertyType +
            ", isNewBuild=" + isNewBuild +
            ", landOwnership=" + landOwnership +
            ", primaryAddressableObjectName='" + primaryAddressableObjectName + '\'' +
            ", secondaryAddressableObjectName='" + secondaryAddressableObjectName + '\'' +
            ", street='" + street + '\'' +
            ", locality='" + locality + '\'' +
            ", townOrCity='" + townOrCity + '\'' +
            ", district='" + district + '\'' +
            ", county='" + county + '\'' +
            '}';
    }
}
