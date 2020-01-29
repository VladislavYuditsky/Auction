package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String strType = request.getParameter("auctionType");
        AuctionType type;

        if (strType == null) {
            type = AuctionType.DIRECT;
        } else {
            try {
                type = AuctionType.valueOf(strType.toUpperCase());
            } catch (IllegalArgumentException e){
                //logger
                type = AuctionType.DIRECT;
            }
        }

        ServiceFactory factory = ServiceFactory.getInstance();
        AuctionService auctionService = factory.getAuctionService();
        LotService lotService = factory.getLotService();

        try {
            List<Auction> auctions = auctionService.findByType(type);
            Iterator<Auction> iterator = auctions.iterator();

            List<Lot> lotsForSale = new ArrayList<>();

            while(iterator.hasNext()){
                Auction auction = iterator.next();
                Lot lot = lotService.findById(auction.getLotId());
                if(lot != null) {
                    lotsForSale.add(lot);
                }
            }
            request.setAttribute("lots", lotsForSale);
        } catch (ServiceException e) {
            //
        }

        return "main";
    }
}
