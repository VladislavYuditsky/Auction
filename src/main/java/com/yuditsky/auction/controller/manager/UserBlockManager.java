package com.yuditsky.auction.controller.manager;

import com.yuditsky.auction.service.CreditService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Manages users blocking.
 */
public class UserBlockManager {
    private final static Logger logger = LogManager.getLogger(UserBlockManager.class);

    private static final int DELAY = 5000;
    private static final int PERIOD = 1000 * 60 * 60 * 24;

    public UserBlockManager() {
        ServiceFactory factory = ServiceFactory.getInstance();
        CreditService creditService = factory.getCreditService();

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<Integer> debtorsId = null;
                try {
                    debtorsId = creditService.findDebtors();
                    creditService.blockDebtors();
                } catch (ServiceException e) {
                    logger.error("UserBlockManager failed", e);
                }

                if (debtorsId != null) {
                    logger.debug("Debtors with id: " + debtorsId + " blocked");
                } else {
                    logger.debug("No debtors");
                }
            }
        }, DELAY, PERIOD);
    }
}
