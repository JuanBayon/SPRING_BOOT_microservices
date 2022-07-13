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
public class Review {

    private Long id;
    private Date date;
    private Integer score;
    private Long showId;
    private Long userId;

}
