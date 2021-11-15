package com.justanalytics.constant;

public final class CheckTerminalConditionQuery {

    public static final String CHECK_TERMINAL_CONDITION_QUERY = "SELECT c.condition FROM api_config c\n" +
            "WHERE c.subscription_id = '%s' AND c.product_id = '%s' AND c.entity = '%s'";

    public static final String CHECK_TERMINAL_CONDITION_CONTAINER_NAME = "api_config";
}
