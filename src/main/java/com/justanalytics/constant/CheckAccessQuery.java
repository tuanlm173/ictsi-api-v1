package com.justanalytics.constant;

public final class CheckAccessQuery {

    public static final String CHECK_ACCESS_QUERY = "SELECT " +
            "TOP 1 c.id \n" +
            "FROM api_config c " +
            "WHERE c.subscription_id = '%s' AND c.product_id = '%s' AND c.entity = '%s'";

    public static final String CHECK_ACCESS_CONTAINER_NAME = "api_config";
}
