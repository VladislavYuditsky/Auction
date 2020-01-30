package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserLots implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String userIdStr = request.getParameter("id");

        HttpSession session = request.getSession();

        if (userIdStr != null & session.getAttribute("id") != null) {
            int userId = Integer.parseInt(userIdStr);
            int currentUserId = (Integer) session.getAttribute("id");

            if (userId == currentUserId) {
                ServiceFactory factory = ServiceFactory.getInstance();
                LotService lotService = factory.getLotService();

                try {
                    List<Lot> lots = lotService.findByOwnerId(userId);

                    request.setAttribute("lots", lots);

                    return "userLots";
                } catch (ServiceException e) {
                    //
                }
            } else {
                //просмотр чужих лотов
                return "greeting";
            }
        }
        return "signIn";
    }
}
