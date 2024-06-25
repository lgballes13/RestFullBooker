package com.luisautomation.tasks;

import com.luisautomation.utils.CreateInformationUser;
import io.restassured.http.ContentType;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.json.simple.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class CreateToken implements Task {

    private String user;
    private String password;

    public CreateToken (){

        Properties prop = new Properties();
        try {
            FileInputStream file = new FileInputStream("user.properties");
            prop.load(file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.user = prop.getProperty("user");
        this.password = prop.getProperty("password");

    }

    public JSONObject jsonUser(){
        JSONObject req = CreateInformationUser.jsonUser(user, password);
        return req;
    }

    public static CreateToken forUpdate(){
        return Tasks.instrumented(CreateToken.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Post.to("/auth").with(requestSpecification -> requestSpecification.contentType(ContentType.JSON).body(jsonUser())));
        String token = lastResponse().jsonPath().getString("token");
        Serenity.setSessionVariable("token").to(token);
        lastResponse().prettyPrint();
    }
}
