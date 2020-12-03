package com.example.moviesorshows.commons;

import java.util.ArrayList;
import java.util.List;

public class MovieOrShowList {

    List<MovieOrShow> Search;

    public MovieOrShowList(List<MovieOrShow> search) {
        Search = new ArrayList<MovieOrShow>();
    }

    public List<MovieOrShow> getSearch() {
        return Search;
    }

    public void setSearch(List<MovieOrShow> search) {
        Search = search;
    }
}
