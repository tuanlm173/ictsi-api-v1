package com.justanalytics.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(value = "dbo.container")
public class Container {

    @Column(value = "UniqueKey")
    private String uniqueKey;
    @Column(value = "TEU")
    private Integer teu;
    @Column(value = "arrive_pos_loctype")
    private String arrivePosLocType;
    @Column(value = "requires_power")
    private boolean requiresPower;


}
