package com.javastream.melles_crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {

    private long productId;
    private Product product;
    private long incomingBalance;
    private long arrival;
    private long reserve;
    private long plannedBalance;
}
