package com.techelevator.dao;

import com.techelevator.model.AddressFilter;
import org.junit.Assert;
import org.junit.Test;

public class AddressFilterTest {
    @Test
    public void filter_returns_proper_sql_clause() {
        AddressFilter filter = new AddressFilter(null, null, "Los Angeles", null, null);

        Assert.assertEquals("WHERE t.city = 'Los Angeles'", filter.getSqlFilterClause("t"));
        Assert.assertEquals("WHERE p.city = 'Los Angeles'", filter.getSqlFilterClause("p"));

        filter = new AddressFilter(1600, "Smith Street", "Los Angeles", "California", "US");

        Assert.assertEquals("WHERE t.building_number = 1600 AND t.street = 'Smith Street' AND t.city = 'Los Angeles' AND t.province = 'California' AND t.country = 'US'", filter.getSqlFilterClause("t"));
    }

    @Test
    public void empty_filter_returns_empty_string() {
        AddressFilter filter = new AddressFilter();

        Assert.assertEquals(" ", filter.getSqlFilterClause("t"));
    }
}
