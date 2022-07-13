package com.showcatalog.pojos;

import lombok.*;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    private Integer numberOfSeats;
    private Long showId;

}
