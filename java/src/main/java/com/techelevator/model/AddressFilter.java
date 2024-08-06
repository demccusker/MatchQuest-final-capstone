package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class AddressFilter {
    private Integer buildingNumber = null;
    private String street = null;
    private String city = null;
    private String province = null;
    private String country = null;

    private List<Integer> activeFilters = new ArrayList<>();

    public AddressFilter() {}
    public AddressFilter(Integer buildingNumber, String street, String city, String province, String country) {
        setFilter(buildingNumber, street, city, province, country);
    }

    public void setFilter(Integer buildingNumber, String street, String city, String province, String country) {
        this.buildingNumber = buildingNumber;
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;

        activeFilters = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            switch(i) {
                case 0:
                    if (buildingNumber != null) activeFilters.add(i);
                    continue;
                case 1:
                    if (street != null) activeFilters.add(i);
                    continue;
                case 2:
                    if (city != null) activeFilters.add(i);
                    continue;
                case 3:
                    if (province != null) activeFilters.add(i);
                    continue;
                case 4:
                    if (country != null) activeFilters.add(i);
            }
        }
    }

    public String getSqlFilterClause(String sqlTournamentAlias) {
        if (isEmpty()) return " ";

        StringBuilder clauseBuilder = new StringBuilder("WHERE ");

        for(int i = 0; i < activeFilters.size(); i++) {
            Integer currentFilter = activeFilters.get(i);

            switch(currentFilter) {
                case 0:
                    clauseBuilder.append(addFilterToClause(sqlTournamentAlias, "%s.building_number = %d", buildingNumber));
                    break;
                case 1:
                    clauseBuilder.append(addFilterToClause(sqlTournamentAlias, "%s.street = '%s'", street));
                    break;
                case 2:
                    clauseBuilder.append(addFilterToClause(sqlTournamentAlias, "%s.city = '%s'", city));
                    break;
                case 3:
                    clauseBuilder.append(addFilterToClause(sqlTournamentAlias, "%s.province = '%s'", province));
                    break;
                case 4:
                    clauseBuilder.append(addFilterToClause(sqlTournamentAlias, "%s.country = '%s'", country));
                    break;
            }

            if (i != activeFilters.size() - 1) clauseBuilder.append(" AND ");
        }

        return clauseBuilder.toString();
    }

    public boolean isEmpty() {
        return (buildingNumber == null) &&
                (street == null) &&
                (city == null) &&
                (province == null) &&
                (country == null);
    }

    private String addFilterToClause(String tableAlias, String clauseFormat, Object compare) {
        return String.format(clauseFormat, tableAlias, compare);
    }
}
