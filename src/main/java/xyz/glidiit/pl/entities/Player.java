package xyz.glidiit.pl.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String shortName;
    private String Notionality;
    private int kitNumber;
    private Date dateOfBirth;
    @ManyToOne
    private Team team;
    private Integer goals;
    private Integer assists;
    private Integer yellowCards;
    private Integer redCards;

}
