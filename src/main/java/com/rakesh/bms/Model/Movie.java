package com.rakesh.bms.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String title;

    private String description;
    private String language;
    private String genre;               // genre means --> What kind of movie it is Like--> Comedy, Action
    private Integer durationMins;
    private String releaseDate;
    private String posterUrl;

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL)
    private List<Show> shows;
}
