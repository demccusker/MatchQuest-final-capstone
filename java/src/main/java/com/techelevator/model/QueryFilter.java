package com.techelevator.model;

import java.util.*;

public class QueryFilter {
    public QueryFilter() {}
    public QueryFilter(Map<String, Object> queryMap) {
        if (!queryMap.containsKey("operator")) throw new IllegalArgumentException("Request query is malformed! Does not contain operator.");

        String op = queryMap.get("operator").toString();
        StringBuilder clauseBuilder = new StringBuilder(clause);
        int prevFilterCount = 0;

        filters = (List<LinkedHashMap<String, Object>>)queryMap.get("filters");
        globalOperator = (op.equals("|") || op.equals("OR")) ? "OR" : globalOperator;
        limit = (queryMap.containsKey("limit")) ? (int)queryMap.get("limit") : 0;
        sql = SELECT_TOURNAMENT;

        for (LinkedHashMap<String, Object> filter : filters) {
            prevFilterCount = addFilter(filter, clauseBuilder, prevFilterCount, globalOperator);
        }

        assignClause(clauseBuilder);
    }

    public void setFilters(List<LinkedHashMap<String, Object>> filters) { this.filters = filters; }

    public void setGlobalOperator(String globalOperator) { this.globalOperator = globalOperator; }

    public void setLimit(int limit) { this.limit = limit; }

    public String getSqlQuery() { return sql + clause; }
    private void updateFilterCount(int currentFilterCount) { filterCount = currentFilterCount; }

    private void assignClause(StringBuilder clauseBuilder) {
        String tempClause = clauseBuilder.toString();

        clause = (!tempClause.equals(clause)) ? tempClause : "";
        clause += (limit > 0) ? " LIMIT " + limit : "";
    }

    private static String getValueSyntax(Object valueObj) {
        return (valueObj != null) ? (valueObj.getClass().getTypeName().equals(String.class.getTypeName())) ? "'" : "" : "";
    }

    private static String getTableProperty(String propertyName) {
        switch(propertyName) {
            case "tournamentId":
                return "t.tournament_id";
            case "gameId":
                return "t.game_id";
            case "creatorId":
                return "t.creator_id";
            case "name":
                return "t.name";
            case "isScrim":
                return "t.is_scrim";
            case "isOnline":
                return "t.is_online";
            case "startDate":
                return "t.start_date";
            case "location":
                return "";
            case "endDate":
                return "t.end_date";
            default:
                return "NULL";
        }
    }

    private int setFilterClause(LinkedHashMap<String, Object> filter, StringBuilder clauseBuilder, int prevFilterCount) {
        boolean hasFilterProperty = filter.containsKey("filterProperty"),
                hasValue = filter.containsKey("value");

        if (!hasFilterProperty || !hasValue) {
            System.out.println("Query filter is malformed!");
            return prevFilterCount;
        }

        String propertyName = filter.get("filterProperty").toString(),
                condition = (filter.containsKey("condition")) ? filter.get("condition").toString() : "=",
                tableProperty = getTableProperty(propertyName);

        /* If query property is not location */
        if (!tableProperty.isEmpty()) {
            Object valueObj = filter.get("value");
            String valueSyntax = getValueSyntax(valueObj);

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
                LinkedHashMap<String, Object> filterValue = (LinkedHashMap<String, Object>) filter.get("value");
                address = new AddressFilter(filterValue);
            } catch (ClassCastException ex) {
                clauseBuilder.delete(clauseBuilder.length() - (globalOperator.length() + 2), clauseBuilder.length());

                prevFilterCount--;
                return prevFilterCount;
            }

            clauseBuilder.append(address.getSqlFilterClause(ADDRESS_ALIAS));
        }

        filterCount++;

        return prevFilterCount;
    }

    private int getConcatStatement(LinkedHashMap<String, Object> filter, StringBuilder clauseBuilder, int prevFilterCount) {
        try {
            List<LinkedHashMap<String, Object>> statements = (List<LinkedHashMap<String, Object>>) filter.get("concat");
            for(LinkedHashMap<String, Object> statement : statements) {
                String statementOperator = (statement.containsKey("operator")) ? statement.get("operator").toString() : "AND";
                statementOperator = (statementOperator.equals("|") || statementOperator.equals("OR")) ? "OR" : "AND";

                prevFilterCount = addFilter(statement, clauseBuilder, prevFilterCount, statementOperator);
            }

        } catch (ClassCastException ex) {
            return prevFilterCount;
        }
        finally {
            clauseBuilder.append(")");
        }

        return prevFilterCount;
    }

    private int addFilter(LinkedHashMap<String, Object> filter, StringBuilder clauseBuilder, int prevFilterCount, String booleanOperator) {
        boolean hasConcat = filter.containsKey("concat");

        /* Only runs when valid filter was added in the previous iteration of the loop */
        if (prevFilterCount != filterCount) {
            /* Adds the necessary boolean operator between clause statements */
            clauseBuilder
                    .append(" ")
                    .append(booleanOperator)
                    .append(" ");

            prevFilterCount = filterCount;
        }

        if (hasConcat) clauseBuilder.append("(");

        prevFilterCount = setFilterClause(filter, clauseBuilder, prevFilterCount);
        prevFilterCount = (hasConcat) ? getConcatStatement(filter, clauseBuilder, prevFilterCount) : prevFilterCount; // RECURSIVE CALL //

        return prevFilterCount;
    }

    private List<LinkedHashMap<String, Object>> filters;
    private String globalOperator = "AND";
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
}
