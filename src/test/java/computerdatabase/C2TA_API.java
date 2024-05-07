package computerdatabase;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class C2TA_API extends Simulation {

	private HttpProtocolBuilder httpProtocol = http.baseUrl("https://www.c2ta.co.in");

	// post Request

	private ChainBuilder Post = exec(

			http("Post_Request").post("/api/insert.php").asJson().body(RawFileBody("Datas/Post_Request.json")).check(

					status().is(200)

			)

	);

	private ChainBuilder Get = exec(

			http("Get_Request").get("/api/read.php").asJson()

	);

	private ChainBuilder Put = exec(

			http("Put_Request").put("/api/update.php").asJson()
					.body(StringBody("{\r\n" + "        \"id\": \"971\",\r\n"
							+ "        \"title\": \"Project Manager\",\r\n" + "        \"body\": \"API\",\r\n"
							+ "        \"author\": \"Sathish\"\r\n" + "    }\r\n" + ""))

	);

	ScenarioBuilder Creat = scenario("Post").exec(Post);

	ScenarioBuilder retrive = scenario("Get").exec(Get);

	ScenarioBuilder Update = scenario("Put").exec(Put);

	{

		setUp(Creat.injectOpen(rampUsers(10).during(5)),

				retrive.injectOpen(atOnceUsers(10)),

				Update.injectOpen(atOnceUsers(10))

		).protocols(httpProtocol);
	}

}
