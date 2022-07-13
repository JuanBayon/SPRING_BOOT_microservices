package com.showcatalog.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Embeddable
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {

    private Date date;
    private Time time;
    private String streamingUrl;
    private Integer remainingSeats;

}
