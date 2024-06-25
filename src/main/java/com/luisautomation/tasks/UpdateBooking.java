package com.luisautomation.tasks;

import com.luisautomation.models.UserModel;
import com.luisautomation.utils.CreateInformationUser;
import io.restassured.http.ContentType;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.interactions.Put;
import org.json.simple.JSONObject;


import java.util.List;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class UpdateBooking implements Task {


    private String url = "/booking";

    private String bookingid = "/"+Serenity.sessionVariableCalled("bookingid");

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private String checkin;

    private String checkout;
    private String additionalneeds;

    public UpdateBooking (List<UserModel> user){
        this.firstname = user.get(0).getFirstname();
        this.lastname = user.get(0).getLastname();
        this.totalprice = (int) user.get(0).getTotalprice();
        this.depositpaid =  user.get(0).isDepositpaid();
        this.checkin = user.get(0).getCheckin();
        this.checkout = user.get(0).getCheckout();
        this.additionalneeds = user.get(0).getAdditionalneeds();
    }

    public JSONObject jsonUser(){
        JSONObject req = CreateInformationUser.Json(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);

        String firstname = (String) req.get("firstname");
        Serenity.setSessionVariable("firstname").to(firstname);

        String lastname = (String) req.get("lastname");
        Serenity.setSessionVariable("lastname").to(lastname);

        int totalprice = (int) req.get("totalprice");
        Serenity.setSessionVariable("totalprice").to(totalprice);

        boolean depositpaid = (boolean) req.get("depositpaid");
        Serenity.setSessionVariable("depositpaid").to(depositpaid);


        JSONObject bookingdates = (JSONObject) req.get("bookingdates");
        String checkin = (String) bookingdates.get("checkin");
        Serenity.setSessionVariable("checkin").to(checkin);

        String checkout = (String) bookingdates.get("checkout");
        Serenity.setSessionVariable("checkout").to(checkout);

        String additionalneeds = (String) req.get("additionalneeds");
        Serenity.setSessionVariable("additionalneeds").to(additionalneeds);

        return req;
    }
    public static UpdateBooking ofClient(List<UserModel> user){
        return Tasks.instrumented(UpdateBooking.class, user);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        String token =  Serenity.sessionVariableCalled("token");
        System.out.println(token);
        actor.attemptsTo(Put.to(url+bookingid).with(request -> request.contentType(ContentType.JSON).header("Accept", "application/json")
                .header("Content-Type", "application/json").header("Cookie", "token="+token).body(jsonUser())));

        lastResponse().prettyPrint();
    }
}
