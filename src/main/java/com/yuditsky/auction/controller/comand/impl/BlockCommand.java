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

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.AUCTION_ID;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.USER_ID;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.DENY;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.BLOCKED;

public class BlockCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(BlockCommand.class);

    private final UserService userService;

    public BlockCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int userId = Integer.parseInt(request.getParameter(USER_ID));
            int auctionId = Integer.parseInt(request.getParameter(AUCTION_ID));

            User user = userService.findById(userId);

            userService.block(user);

            redirect(request, response, DENY + "?" + AUCTION_ID + "=" + auctionId);
        } catch (ServiceException | NumberFormatException e) {
            logger.error("BlockCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
