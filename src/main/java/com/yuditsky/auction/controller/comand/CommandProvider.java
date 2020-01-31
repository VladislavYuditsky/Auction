package com.yuditsky.auction.controller.comand;

import com.yuditsky.auction.controller.comand.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<CommandName, Command> repository = new HashMap<>();

    public CommandProvider() {
        repository.put(CommandName.SIGN_IN, new SignIn());
        repository.put(CommandName.SIGN_UP, new SignUp());
        repository.put(CommandName.MAIN, new Main());
        repository.put(CommandName.GREETING, new Greeting());
        repository.put(CommandName.SIGN_OUT, new SignOut());
        repository.put(CommandName.ADD_NEW_LOT, new AddNewLot());
        repository.put(CommandName.USER_LOTS, new UserLots());
        repository.put(CommandName.USER_BIDS, new UserBids());
        repository.put(CommandName.AWAITING_PAYMENT, new AwaitingPayment());
        repository.put(CommandName.USER_PAYMENTS, new UserPayments());
        repository.put(CommandName.USER_CREDITS, new UserCredits());
        repository.put(CommandName.SETTINGS, new Settings());
        repository.put(CommandName.REPLENISH_BALANCE, new ReplenishBalance());
        repository.put(CommandName.PROPOSED_AUCTIONS, new ProposedAuctions());
        repository.put(CommandName.USERS, new Users());
        repository.put(CommandName.AUCTION, new AuctionCommand());
        //repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
    }

    public Command getCommand(String name) {
        CommandName commandName;
        Command command;

        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            command = repository.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }
}
