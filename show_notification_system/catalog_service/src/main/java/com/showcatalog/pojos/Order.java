package com.showcatalog.pojos;

import lombok.*;

import java.sql.Date;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String code;
    private Date date;
    private Reservation reservation;

}
