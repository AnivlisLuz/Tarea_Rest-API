package basicTest;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ItemsTest {
    @Test
    public void items() {
        //Creation
        JSONObject body = new JSONObject();
        body.put("Content", "LuzItem");

        Response response = given()
                .auth()
                .preemptive()
                .basic("ucb@ucb2021.com", "12345")
                .body(body.toString())
                .log()
                .all()
                .when()
                .post("https://todo.ly/api/items.json");

        response.then()
                .statusCode(200)
                .body("Content", equalTo("LuzItem"))
                .log()
                .all();

        int id = response.then().extract().path("Id");

        //Update
        body.put("Content", "LuzItemUpdate");
        body.put("Checked", true);

        response = given()
                .auth()
                .preemptive()
                .basic("ucb@ucb2021.com", "12345")
                .body(body.toString())
                .log()
                .all()
                .when()
                .put("https://todo.ly/api/items/" + id + ".json");

        response.then()
                .statusCode(200)
                .body("Content", equalTo("LuzItemUpdate"))
                .body("Checked", equalTo(true))
                .log()
                .all();

        //Get
        response = given()
                .auth()
                .preemptive()
                .basic("ucb@ucb2021.com", "12345")
                .log()
                .all()
                .when()
                .get("https://todo.ly/api/items/" + id + ".json");

        response.then()
                .statusCode(200)
                .body("Content", equalTo("LuzItemUpdate"))
                .body("Checked", equalTo(true))
                .log()
                .all();

        //Delete
        response = given()
                .auth()
                .preemptive()
                .basic("ucb@ucb2021.com", "12345")
                .log()
                .all()
                .when()
                .delete("https://todo.ly/api/items/" + id + ".json");

        response.then()
                .statusCode(200)
                .body("Content", equalTo("LuzItemUpdate"))
                .body("Checked", equalTo(true))
                .body("Deleted", equalTo(true))
                .log()
                .all();

    }
}

