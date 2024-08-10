package com.techelevator.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AddressFilter {
    private String city = null;
    private String province = null;
    private String country = null;

    private List<Integer> activeFilters = new ArrayList<>();

    public AddressFilter() {}
    public AddressFilter(String city, String province, String country) {
        setFilter(city, province, country);
    }
    public AddressFilter(LinkedHashMap<String, Object> value) {
        Object cityFilterObj =  value.getOrDefault("city", null),
                provinceFilterObj =  value.getOrDefault("province", null),
                countryFilterObj = value.getOrDefault("country", null);

        setFilter(
                (cityFilterObj != null) ? cityFilterObj.toString() : null,
                (provinceFilterObj != null) ? provinceFilterObj.toString() : null,
                (countryFilterObj != null) ? countryFilterObj.toString() : null
        );
    }

    public void setFilter(String city, String province, String country) {
        this.city = city;
        this.province = province;
        this.country = country;

        activeFilters = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            switch(i) {
                case 0:
                    if (city != null) activeFilters.add(i);
                    continue;
                case 1:
                    if (province != null) activeFilters.add(i);
                    continue;
                case 2:
                    if (country != null) activeFilters.add(i);
            }
        }
    }

    public String getSqlFilterClause(String sqlAddressAlias) {
        if (isEmpty()) return " ";

        StringBuilder clauseBuilder = new StringBuilder();

        for(int i = 0; i < activeFilters.size(); i++) {
            Integer currentFilter = activeFilters.get(i);

            switch(currentFilter) {
                case 0:
                    clauseBuilder.append(addFilterToClause(sqlAddressAlias, "%s.city = '%s'", city));
                    break;
                case 1:
                    clauseBuilder.append(addFilterToClause(sqlAddressAlias, "%s.province = '%s'", province));
                    break;
                case 2:
                    clauseBuilder.append(addFilterToClause(sqlAddressAlias, "%s.country = '%s'", country));
                    break;
            }

            if (i != activeFilters.size() - 1) clauseBuilder.append(" AND ");
        }

        return clauseBuilder.toString();
    }

    public String getSqlFilterClause() { return getSqlFilterClause(""); }

    public boolean isEmpty() {
        return (city == null) &&
                (province == null) &&
                (country == null);
    }

    private String addFilterToClause(String tableAlias, String clauseFormat, Object compare) {
        return String.format(clauseFormat, tableAlias, compare);
    }
}
