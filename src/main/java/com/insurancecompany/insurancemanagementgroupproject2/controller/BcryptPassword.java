package com.insurancecompany.insurancemanagementgroupproject2.controller;
/**
 * @author team 5
 */
import at.favre.lib.crypto.bcrypt.BCrypt;

public class BcryptPassword {
    // Method to has a password from string to hashed
    public String hashBcryptPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
    // Method to evaluate if String input match string hashed
    public boolean verifyHashedPassword(String bcryptHashString, String password) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
        return result.verified;
    }
}
