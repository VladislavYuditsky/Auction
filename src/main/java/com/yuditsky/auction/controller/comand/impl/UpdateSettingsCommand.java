package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;
import com.yuditsky.auction.service.util.Encoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.SETTINGS;

public class UpdateSettingsCommand extends AbstractCommand {
    private final Encoder encoder;

    public UpdateSettingsCommand() {
        encoder = new Encoder();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

            ServiceFactory factory = ServiceFactory.getInstance();
            UserService userService = factory.getUserService();

            String password = request.getParameter("password");
            String email = request.getParameter("email");

            int id = (Integer) session.getAttribute("id");

            try {
                User user = userService.findById(id);

                if (password != null && !password.equals("")) {
                    Encoder encoder = new Encoder(); //v service
                    user.setPassword(encoder.encode(password));
                }

                if (email != null && !email.equals("")) {
                    user.setEmail(email);
                }

                if (password != null | email != null) {
                    userService.update(user);
                }

                redirect(request, response, SETTINGS);
                //return "settings";
            } catch (ServiceException e) {
                /////
            }
        //}

        //return "signIn";
    }
}
