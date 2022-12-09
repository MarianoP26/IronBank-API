package com.finalproject.ironhack.consts;

public final class Consts {

    //----------------------------------------------ACCOUNTS RELATED------------------------------------------------------------------------------------------------------------

    public static final String CHECKING_ACCOUNT = "Checking";
    public static final String SAVINGS_ACCOUNT = "Savings";
    public static final String CREDITCARD_ACCOUNT = "CreditCard";

    public static final String ACCOUNT_ACCOUNT_HOLDER_NOT_FOUND_ERROR = "Account holder not found";
    public static final String ACCOUNT_ACCOUNT_NOT_FOUND_ERROR = "Account not found";

    public static final String ACCOUNT_GENERIC_UNEXPECTED_ERROR = "Error creating the account, read carefully the documentation and try again";

    //------------------------SAVINGS ACCOUNT-----------------
    //VALUES
    public static final String SAVINGS_MAX_INTEREST_RATE = "0.50";
    public static final String SAVINGS_DEFAULT_INTEREST_RATE = "0.0025";
    public static final String SAVINGS_DEFAULT_MINIMUM_BALANCE = "1000.00";
    public static final String SAVINGS_MIN_MINIMUM_BALANCE = "100.00";
    public static final String SAVINGS_MAX_MINIMUM_BALANCE = "1000.00";

    //ERROR MESSAGES
    public static final String SAVINGS_MAX_INTEREST_RATE_ERROR = "Sorry, you are not allowed to create a savings account with interest rate greater than 0.5%";
    public static final String SAVINGS_MAX_MINIMUM_BALANCE_ERROR = "Sorry, you are not allowed to create a savings account with a minimum balance greater than 1000";
    public static final String SAVINGS_MIN_MINIMUM_BALANCE_ERROR = "Sorry, you are not allowed to create a savings account with a minimum balance less than 100";



    //------------------------CREDIT CARDS--------------------
    //VALUES
    public static final String CREDITCARD_DEFAULT_CREDIT_LIMIT = "100.00";
    public static final String CREDITCARD_MIN_CREDIT_LIMIT = "100.00";
    public static final String CREDITCARD_MAX_CREDIT_LIMIT = "100000.00";
    public static final String CREDITCARD_DEFAULT_INTEREST_RATE = "0.20";
    public static final String CREDITCARD_MIN_INTEREST_RATE = "0.10";
    public static final String CREDITCARD_MAX_INTEREST_RATE = "0.20";

    //ERROR MESSAGES
    public static final String CREDITCARD_MIN_CREDIT_LIMIT_ERROR = "Sorry, you are not allowed to create a credit card with a credit limit less than 100";
    public static final String CREDITCARD_MAX_CREDIT_LIMIT_ERROR = "Sorry, you are not allowed to create a credit card with a credit limit higher than 100000";
    public static final String CREDITCARD_MIN_INTEREST_RATE_ERROR = "Sorry, you are not allowed to create a credit card with a interest rate lower than 0.10";
    public static final String CREDITCARD_MAX_INTEREST_RATE_ERROR = "Sorry, you are not allowed to create a credit card with a interest rate higher than 0.20";

    //------------------------CHECKING--------------------
    //VALUES
    public static final String CHECKING_MINIMUM_BALANCE = "250.00";
    public static final String CHECKING_MONTHLY_MAINTENANCE_FEE = "12.00";


    //---------------------------------------------- TRANSFER RELATED ------------------------------------------------------------
    public static final String TRANSFER_ACCOUNT_TO_ACCOUNT_ERROR = "Error while making the transaction. Make sure the id and name of the destination account are correct and you have enough balance. Also have in mind FROZEN accounts cannot send nor receive money";
    public static final String TRANSFER_TP_TO_ACCOUNT_ERROR = "The specified account id does not match with his secret key";
    public static final String TRANSFER_GENERAL_FROZEN_ERROR = "Account is frozen";
    public static final String TRANSFER_GENERAL_SECRET_KEY_ERROR = "Secret key is invalid";

    public static final String TRANSFER_DESTINY_ACCOUNT_NOT_FOUND_ERROR = "Destiny account was not found";
    public static final String TRANSFER_ORIGIN_ACCOUNT_NOT_FOUND_ERROR = "Origin account was not found";



    //---------------------------------------------- Agents RELATED ------------------------------------------------------------
    public static final String AGENT_NOT_FOUND_ERROR = "The agent was not found";
    public static final String AGENT_MUST_BE_LOGGED_IN_ERROR = "You must be logged in to access this area";

    public static final String ADMIN = "ADMIN";
    public static final String HOLDER = "HOLDER";
    public static final String THIRDPARTY = "THIRDPARTY";
}
