package com.kadirgurturk.emailSender.utilty;

public class EmailUtlity {

    public static String getEmailMessage(String firstName, String host, String token)
    {
        return "Hello" + firstName + ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n" +
                getVerificationUrl(host, token) + "\n\nThe support Team";
    }

    public static String getVerificationUrl(String host, String token) {
        return host + "/api/users?token=" + token;
    }
}
