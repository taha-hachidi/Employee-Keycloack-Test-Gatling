package computerdatabase;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class EmployeeSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8081")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Gatling)");


    ChainBuilder fetchEmployees = exec(
            http("Get All Employees")
                    .get("/employe")
                    .check(status().is(200))
    ).pause(1);


    ChainBuilder createEmployee = exec(
            http("Create New Employee")
                    .post("/employe")
                    .body(StringBody("{\"name\": \"Alice\", \"age\": 28, \"email\": \"alice@example.com\", \"salary\": 5000}"))
                    .asJson()
                    .check(status().is(200))
    ).pause(1);

    ChainBuilder editEmployee = exec(
            http("Update Employee")
                    .put("/employe/1")
                    .body(StringBody("{\"name\": \"Alice Updated\", \"age\": 29, \"email\": \"alice.updated@example.com\", \"salary\": 5500}"))
                    .asJson()
                    .check(status().is(200))
    ).pause(1);


    ScenarioBuilder users = scenario("Users").exec(fetchEmployees);
    ScenarioBuilder admins = scenario("Admins").exec(fetchEmployees, createEmployee, editEmployee);

    {
        setUp(
                users.injectOpen(rampUsers(100).during(10)),
                admins.injectOpen(rampUsers(5).during(10))
        ).protocols(httpProtocol);
    }
}
