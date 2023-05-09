package com.example.auth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.auth.clients.interceptors.MovieClientInterceptor;
import com.example.auth.responses.MoviePageResponse;

@FeignClient(value = "${the_movie_db_api.name}", url = "${the_movie_db_api.url}", configuration = MovieClientInterceptor.class)
public interface MovieClient {

    @GetMapping(value = "{version}/movie/popular")
    MoviePageResponse getPopularMovies(
            @PathVariable String version,
            @RequestParam Integer page,
            @RequestParam String language,
            @RequestParam(name = "include_adult", required = false) Boolean includeAdult,
            @RequestParam(name = "include_video", required = false) Boolean includeVideo,
            @RequestParam(name = "sort_by", required = false) String sortBy);

}
