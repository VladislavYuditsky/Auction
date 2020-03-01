package com.yuditsky.auction.controller.comand;

import com.yuditsky.auction.controller.comand.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<CommandName, Command> repository = new HashMap<>();

    public CommandProvider() {
        repository.put(CommandName.SIGN_IN, new SignInCommand());
        repository.put(CommandName.SIGN_UP, new SignUpCommand());
        repository.put(CommandName.AUCTIONS, new AuctionsCommand());
        repository.put(CommandName.GREETING, new GreetingCommand());
        repository.put(CommandName.SIGN_OUT, new SignOutCommand());
        repository.put(CommandName.CREATE_LOT, new CreateLotCommand());
        repository.put(CommandName.USER_LOTS, new UserLotsCommand());
        repository.put(CommandName.USER_BIDS, new UserBidsCommand());
        repository.put(CommandName.AWAITING_PAYMENT_LOTS, new AwaitingPaymentLotsCommand());
        repository.put(CommandName.USER_PAYMENTS, new UserPaymentsCommand());
        repository.put(CommandName.USER_CREDITS, new UserCreditsCommand());
        repository.put(CommandName.SETTINGS, new SettingsCommand());
        repository.put(CommandName.REPLENISH_BALANCE, new ReplenishBalanceCommand());
        repository.put(CommandName.PROPOSED_AUCTIONS, new ProposedAuctionsCommand());
        repository.put(CommandName.USERS, new UsersCommand());
        repository.put(CommandName.AUCTION, new AuctionCommand());
        repository.put(CommandName.SHOW_PRICE, new ShowPriceCommand());
        repository.put(CommandName.CHANGE_AUCTION_STATUS, new ChangeAuctionStatusCommand());
        repository.put(CommandName.CREATE_AUCTION, new CreateAuctionCommand());
        repository.put(CommandName.CREATE_PAYMENT, new CreatePaymentCommand());
        repository.put(CommandName.EDIT_LOT, new EditLotCommand());
        repository.put(CommandName.DELETE_LOT, new DeleteLotCommand());
        repository.put(CommandName.DENY, new DenyCommand());
        repository.put(CommandName.CHANGE_BLOCK_STATUS, new ChangeUserBlockStatusCommand());
        repository.put(CommandName.CREATE_CREDIT, new CreateCreditCommand());
        repository.put(CommandName.BUY, new BuyCommand());
        repository.put(CommandName.CREATE_BID, new CreateBidCommand());
        repository.put(CommandName.USER_BALANCE, new UserBalanceCommand());
        repository.put(CommandName.UPDATE_SETTINGS, new UpdateSettingsCommand());
        repository.put(CommandName.REPAY_CREDIT, new RepayCreditCommand());
        repository.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());
        repository.put(CommandName.BAN, new BanCommand());
        repository.put(CommandName.DIRECT_AUCTION, new DirectAuctionCommand());
        repository.put(CommandName.REVERS_AUCTION, new ReversAuctionCommand());
        repository.put(CommandName.SELL, new SellCommand());
        repository.put(CommandName.UPDATE_LOT, new UpdateLotCommand());
        repository.put(CommandName.CREATE_USER, new CreateUserCommand());
        repository.put(CommandName.WRONG_REQUEST, new WrongRequestCommand());
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
