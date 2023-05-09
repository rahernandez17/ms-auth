package com.example.auth.requests;

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
public class PopularMovieRequest {

    private Boolean includeAdult;
    
    private Boolean includeVideo;
    
    private String language;
    
    private Integer page;
    
    private String sortBy;
}
