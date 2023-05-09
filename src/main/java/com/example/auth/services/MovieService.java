package com.example.auth.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.auth.clients.MovieClient;
import com.example.auth.requests.PopularMovieRequest;
import com.example.auth.responses.MoviePageResponse;

@Service
public class MovieService {

    @Value("${the_movie_db_api.version}")
    private String apiVersion;

    private final MovieClient movieClient;

    public MovieService(MovieClient movieClient) {
        this.movieClient = movieClient;
    }

    public MoviePageResponse getPopularMovies(PopularMovieRequest request) {
        return movieClient.getPopularMovies(
                apiVersion,
                request.getPage(),
                request.getLanguage(),
                request.getIncludeAdult(),
                request.getIncludeVideo(),
                request.getSortBy());
    }
}
