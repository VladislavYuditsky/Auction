package com.yuditsky.auction.controller.comand.impl.util;

import com.yuditsky.auction.entity.Identified;

import java.util.List;

import static java.lang.Math.ceil;

public class PaginationHelper {
    public static final int NUMBER_OF_RECORDS_ON_PAGE = 5;

    public int calculatePagesNumber(List<? extends Identified> list, int limit) {
        double lotsNumber = list.size();
        return (int) ceil(lotsNumber / limit);
    }
}
