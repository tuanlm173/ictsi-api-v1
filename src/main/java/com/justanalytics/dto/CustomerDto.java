package com.justanalytics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"unique_key", "terminal_customer_id", "terminal_account_name", "terminal_customer_role", "account_number",
        "account_name", "account_type", "parent_account_name", "parent_account_number", "industry"})
public class CustomerDto {

    @JsonProperty(value = "unique_key")
    private String uniqueKey;
    @JsonProperty(value = "terminal_customer_id")
    private String terminalCustomerId;
    @JsonProperty(value = "terminal_account_name")
    private String terminalAccountName;
    @JsonProperty(value = "terminal_customer_role")
    private String terminalCustomerRole;
    @JsonProperty(value = "account_number")
    private String accountNumber;
    @JsonProperty(value = "account_name")
    private String accountName;
    @JsonProperty(value = "account_type")
    private String accountType;
    @JsonProperty(value = "parent_account_name")
    private String parentAccountName;
    @JsonProperty(value = "parent_account_number")
    private String parentAccountNumber;
    @JsonProperty(value = "industry")
    private String industry;


}
