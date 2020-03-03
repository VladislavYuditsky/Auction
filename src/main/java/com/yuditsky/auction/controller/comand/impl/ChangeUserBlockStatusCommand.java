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
import java.io.IOException;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.AUCTION_ID;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.USER_ID;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.DENY;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.USERS;

/**
 * Serves the change_user_block_status request.
 * This command available only for admin.
 */
public class ChangeUserBlockStatusCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ChangeUserBlockStatusCommand.class);

    private final UserService userService;

    public ChangeUserBlockStatusCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer auctionId;
        try{
            auctionId = Integer.parseInt(request.getParameter(AUCTION_ID));
        } catch (NumberFormatException e){
            auctionId = null;
        }

        try {
            int userId = Integer.parseInt(request.getParameter(USER_ID));

            User user = userService.findById(userId);

            userService.changeBlockStatus(user);

            if(auctionId != null){
                redirect(request, response, DENY + "?" + AUCTION_ID + "=" + auctionId);
            } else {
                redirect(request, response, USERS);
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.error("BlockCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
