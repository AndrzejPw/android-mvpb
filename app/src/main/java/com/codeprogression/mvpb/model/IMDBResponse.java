package com.codeprogression.mvpb.model;

import java.util.List;

/**
 * Created by andrzej.biernacki on 2016-04-15.
 */
public class IMDBResponse {
    public List<ImdbRecord> search;
    public int totalResults;
    public boolean response;
}
