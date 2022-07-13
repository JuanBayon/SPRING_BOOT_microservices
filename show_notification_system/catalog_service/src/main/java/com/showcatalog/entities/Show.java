package com.showcatalog.entities;

import com.showcatalog.exceptions.ExceptionMessages;
import com.showcatalog.exceptions.ShowException;
import com.showcatalog.common.Status;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;

    @Column(name = "price")
    private Double price;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "sale_date")
    private Date onSaleDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.PENDING;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "show_categories",
            joinColumns = @JoinColumn(name = "id_show"),
            inverseJoinColumns = @JoinColumn(name = "id_category")
    )
    @ToString.Exclude
    private List<Category> categories;

    @ElementCollection
    @CollectionTable(name="performance", joinColumns = @JoinColumn(name = "show_id"))
    private List<Performance> performances = new LinkedList<>();

    @Embedded
    @Column(name = "score")
    private Score score = new Score(0, 0);

    public void addPerformance(Performance performance) throws ShowException {
        if (performances.stream().anyMatch(p -> p.equals(performance))) {
            throw new ShowException(ExceptionMessages.PERFORMANCE_ALREADY_SAVED);
        }
        performances.add(performance);
    }

    public void deletePerformance(Integer index) throws ShowException {
        if (index >= performances.size()) throw new ShowException(ExceptionMessages.NO_PERFORMANCE_FOUND);
        List<Performance> result = new LinkedList<>();
        for (int i = 0; i < performances.size(); i++) {
            if (i != index) {
                result.add(performances.get(i));
            }
        }
        performances = result;
    }

    public void cancelShow() throws ShowException {
        if (status != Status.OPEN) throw new ShowException(ExceptionMessages.WRONG_STATE);
        status = Status.CANCELLED;
    }

    public void openShow() throws ShowException {
        if (status != Status.PENDING) throw new ShowException(ExceptionMessages.WRONG_STATE);
        status = Status.OPEN;
    }

    public void updateScore(Integer newScore) {
        score.incrementNumberRates();
        score.setScore((score.getScore() + newScore) / score.getNumberRates());
    }

}
