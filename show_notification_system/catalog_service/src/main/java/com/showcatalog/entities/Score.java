package com.showcatalog.entities;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Score {

    private Integer score;
    private Integer numberRates;

    public void incrementNumberRates() {
        numberRates++;
    }

}
