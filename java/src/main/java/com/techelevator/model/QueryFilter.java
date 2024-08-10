package com.techelevator.model;

import java.util.*;

public class QueryFilter {
    private List<LinkedHashMap<String, Object>> filters;
    private String operator = "AND";
    private int limit;

    private String sql;
    private String clause = "WHERE ";
    private int filterCount = 0;

    private static final String TOURNAMENT_ALIAS = "t",
                                ADDRESS_ALIAS = "a",
                                TEAM_BRIDGE_ALIAS = "tt";

    private static final String SELECT_TOURNAMENT = String.format("SELECT " +
                                                                    "%1$s.tournament_id, " +
                                                                    "%1$s.game_id, " +
                                                                    "%1$s.bracket_id, " +
                                                                    "%1$s.creator_id, " +
                                                                    "%1$s.name, " +
                                                                    "%1$s.is_scrim, " +
                                                                    "%1$s.is_online, " +
                                                                    "%1$s.location, " +
                                                                    "%1$s.start_date, " +
                                                                    "%1$s.end_date " +
                                                                "FROM tournament %1$s ", TOURNAMENT_ALIAS);

    private static final String JOIN_ADDRESS = String.format("JOIN address %1$s ON " +
                                                             "%2$s.tournament_id = %1$s.tournament_id ", ADDRESS_ALIAS, TOURNAMENT_ALIAS);

    private static final String JOIN_TEAM = String.format("JOIN tournament_team %1$s ON " +
                                                          "%2$s.tournament_id = %1$s.tournament_id ", TEAM_BRIDGE_ALIAS, TOURNAMENT_ALIAS);

    public QueryFilter() {}
    public QueryFilter(Map<String, Object> queryMap) {
        if (!queryMap.containsKey("operator")) throw new IllegalArgumentException("Request query is malformed! Does not contain operator.");

        String op = queryMap.get("operator").toString();
        StringBuilder clauseBuilder = new StringBuilder(clause);
        int prevFilterCount = 0;

        filters = (List<LinkedHashMap<String, Object>>)queryMap.get("filters");
        operator = (op.equals("|") || op.equals("OR")) ? "OR" : operator;
        limit = (queryMap.containsKey("limit")) ? (int)queryMap.get("limit") : 0;
        sql = SELECT_TOURNAMENT;

        for (LinkedHashMap<String, Object> filter : filters) {
            boolean hasFilterProperty = filter.containsKey("filterProperty"),
                    hasValue = filter.containsKey("value");

            if (!hasFilterProperty || !hasValue) {
                System.out.println("Query filter is malformed!");
                continue;
            }

            String propertyName = filter.get("filterProperty").toString(),
                    condition = (filter.containsKey("condition")) ? filter.get("condition").toString() : "=",
                    tableProperty;

            switch(propertyName) {
                case "tournamentId":
                    tableProperty = "t.tournament_id";
                    break;
                case "gameId":
                    tableProperty = "t.game_id";
                    break;
                case "creatorId":
                    tableProperty = "t.creator_id";
                    break;
                case "name":
                    tableProperty = "t.name";
                    break;
                case "isScrim":
                    tableProperty = "t.is_scrim";
                    break;
                case "isOnline":
                    tableProperty = "t.is_online";
                    break;
                case "startDate":
                    tableProperty = "t.start_date";
                    break;
                case "location":
                    tableProperty = "";
                    break;
                case "endDate":
                    tableProperty = "t.end_date";
                    break;
                default:
                    continue;
            }

            /* Only runs when valid filter was added
                   in the previous iteration of the loop */
            if (prevFilterCount != filterCount) {
                    /* Adds the necessary boolean operator
                       between clause statements */
                clauseBuilder
                        .append(" ")
                        .append(operator)
                        .append(" ");

                prevFilterCount = filterCount;
            }

            /* If query property is not location */
            if (!tableProperty.isEmpty()) {
                Object valueObj = filter.get("value");
                String valueSyntax = (valueObj != null) ? (valueObj.getClass().getTypeName().equals(String.class.getTypeName())) ? "'" : "" : "";

                clauseBuilder
                        .append(tableProperty)
                        .append(" ")
                        .append(condition)
                        .append(" ")
                        .append(valueSyntax)
                        .append(valueObj)
                        .append(valueSyntax);
            } else { /* If query property is location */
                AddressFilter address;
                sql += JOIN_ADDRESS;

                try {
                    LinkedHashMap<String, Object> filterValue = (LinkedHashMap<String, Object>)filter.get("value");
                    address = new AddressFilter(filterValue);
                } catch (ClassCastException ex) {
                    clauseBuilder.delete(clauseBuilder.length() - (operator.length() + 2), clauseBuilder.length());

                    prevFilterCount--;
                    continue;
                }

                clauseBuilder.append(address.getSqlFilterClause(ADDRESS_ALIAS));
            }

            filterCount++;
        }
        String tempClause = clauseBuilder.toString();

        clause = (!tempClause.equals(clause)) ? tempClause : "";
        clause += (limit > 0) ? " LIMIT " + limit : "";
    }

    public void setFilters(List<LinkedHashMap<String, Object>> filters) { this.filters = filters; }
    public void setOperator(String operator) { this.operator = operator; }
    public void setLimit(int limit) { this.limit = limit; }

    public String getSqlQuery() { return sql + clause; }

    private void updateFilterCount(int currentFilterCount) { filterCount = currentFilterCount; }
}
