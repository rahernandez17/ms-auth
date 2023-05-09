package com.example.auth.responses;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MoviePageResponse {

    private Long page;

    private List<MovieResponse> results;

    @JsonAlias("total_pages")
    private Long totalPages;

    @JsonAlias("total_results")
    private Long totalResults;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class MovieResponse {

        private Boolean adult;

        @JsonAlias("backdrop_path")
        private String backdropPath;

        @JsonAlias("genre_ids")
        private List<Long> genreIDS;

        private Long id;

        @JsonAlias("original_language")
        private String originalLanguage;

        @JsonAlias("original_title")
        private String originalTitle;

        private String overview;

        private Double popularity;

        @JsonAlias("poster_path")
        private String posterPath;

        @JsonAlias("release_date")
        private LocalDate releaseDate;

        private String title;

        private Boolean video;

        @JsonAlias("vote_average")
        private Double voteAverage;

        @JsonAlias("vote_count")
        private Long voteCount;
    }
}
