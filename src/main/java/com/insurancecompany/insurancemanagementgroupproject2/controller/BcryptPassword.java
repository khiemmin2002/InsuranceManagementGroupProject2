package com.insurancecompany.insurancemanagementgroupproject2.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.Base64;

public class BcryptPassword {
    public String hashBcryptPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public boolean verifyHashedPassword(String bcryptHashString, String password) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
        return result.verified;
    }
}
