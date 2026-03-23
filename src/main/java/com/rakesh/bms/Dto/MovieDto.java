package com.rakesh.bms.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MovieDto {
    private Long id;
    private String title;
    private String description;
    private String language;
    private String genre;
    private Integer durationMins;
    private String releaseDate;
    private String posterUrl;
    private String durationformatted;   // mean -> convert mins->hr+mins



}
