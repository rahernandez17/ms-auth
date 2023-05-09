package com.example.auth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.requests.PopularMovieRequest;
import com.example.auth.responses.MoviePageResponse;
import com.example.auth.responses.SimpleResponse;
import com.example.auth.services.MovieService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/movie")
@Api(tags = "Movies API")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @ApiOperation(value = "Método para consultar las películas populares")
    @PostMapping(value = "/popular", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SimpleResponse<MoviePageResponse>> getPopularMovies(PopularMovieRequest request) {
        return new ResponseEntity<>(
                SimpleResponse.<MoviePageResponse>builder()
                        .code(200)
                        .message("Las películas se obtuvieron exitosamente")
                        .value(movieService.getPopularMovies(request))
                        .build(),
                HttpStatus.OK);
    }
}
