package com.justanalytics.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "api_config")
public class ApiRegistration {

    @Id
    @Column(value = "id")
    private Integer id;
    @Column(value = "subscription_id")
    private String subscriptionId;
    @Column(value = "product_id")
    private String productId;
    @Column(value = "entity")
    private String entity;
    @Column(value = "condition")
    private String condition;
}
