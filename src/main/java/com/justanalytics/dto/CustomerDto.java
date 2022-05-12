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
@JsonPropertyOrder({"unique_key", "facility_id", "terminal_customer_id", "terminal_account_name", "terminal_customer_role", "account_number",
        "account_name", "account_type", "parent_account_name", "parent_account_number", "industry", "tax_id1", "tax_id2", "address", "update_ts"})
public class CustomerDto {

    @JsonProperty(value = "unique_key")
    private String uniqueKey;
    @JsonProperty(value = "facility_id")
    private String facilityId;
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
    @JsonProperty(value = "tax_id1")
    private String taxId1;
    @JsonProperty(value = "tax_id2")
    private String taxId2;
    @JsonProperty(value = "address")
    private String address;
    @JsonProperty(value = "update_ts")
    private String updateTs;


}
