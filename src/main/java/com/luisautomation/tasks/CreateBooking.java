package com.luisautomation.tasks;

import com.google.gson.JsonObject;
import com.luisautomation.models.BookingFinal;
import com.luisautomation.models.UserModel;
import com.luisautomation.utils.CreateInformationUser;
import io.restassured.http.ContentType;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.json.simple.JSONObject;

import java.util.List;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class CreateBooking implements Task {


    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private String checkin;

    private String checkout;
    private String additionalneeds;

    public CreateBooking (List<UserModel> user){
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


    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(Post.to("/booking").with(requestSpecification -> requestSpecification.contentType(ContentType.JSON).body(jsonUser())));

        String bookingid = lastResponse().jsonPath().getString("bookingid");
        Serenity.setSessionVariable("bookingid").to(bookingid);


        lastResponse().prettyPrint();

    }

    public static CreateBooking forUser(List<UserModel> user){
        return Tasks.instrumented(CreateBooking.class, user);

    }
}

