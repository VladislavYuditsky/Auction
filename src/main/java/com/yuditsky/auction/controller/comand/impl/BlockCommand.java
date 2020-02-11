package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.DENY;
import static com.yuditsky.auction.controller.comand.ConstProv.ERROR_PAGE;

public class BlockCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(BlockCommand.class);

    private final UserService userService;

    public BlockCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userIdStr = request.getParameter("userId");
        String auctionIdStr = request.getParameter("auctionId");

        if (userIdStr != null && auctionIdStr != null) {
            int userId = Integer.parseInt(userIdStr);
            int auctionId = Integer.parseInt(auctionIdStr);

            try {
                User user = userService.findById(userId);

                userService.block(user);

                HttpSession session = request.getSession();
                session.setAttribute("blocked", true);

                redirect(request, response, DENY + "?auctionId=" + auctionId);
            } catch (ServiceException e) {
                logger.error("BlockCommand failed", e);
                forward(request, response, ERROR_PAGE);
            }
        }
    }
}
