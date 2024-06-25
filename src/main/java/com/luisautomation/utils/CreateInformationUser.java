package com.luisautomation.utils;

import org.json.simple.JSONObject;

public class CreateInformationUser {


    public static JSONObject Json(String firstname, String lastname, int totalprice, boolean depositpaid, String checkin, String checkout, String additionalneeds) {
        JSONObject req = new JSONObject();
        req.put("firstname", firstname);
        req.put("lastname", lastname);
        req.put("totalprice", totalprice);
        req.put("depositpaid", depositpaid);

        JSONObject dates = new JSONObject();
        dates.put("checkin", checkin);
        dates.put("checkout", checkout);

        req.put("bookingdates", dates);
        req.put("additionalneeds", additionalneeds);

        return req;
    }

    public static JSONObject jsonUser(String user, String password){
        JSONObject req = new JSONObject();
        req.put("username", user);
        req.put("password", password);
        return req;
    }

}
