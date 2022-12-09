package com.finalproject.ironhack.models.Accounts.interfaces;

import com.finalproject.ironhack.models.Accounts.enums.Status;

public interface SecretKeyStatusAccount {




    String getSecretKey();

    Status getStatus();

    void setStatus(Status status);
}
