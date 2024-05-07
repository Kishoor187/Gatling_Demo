package computerdatabase;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class Feeder_API extends Simulation {

	private HttpProtocolBuilder httpProtocol = http.baseUrl("https://www.c2ta.co.in");

	FeederBuilder<String> data = csv("Datas/Data.csv").circular();

	private ChainBuilder post = exec(

			feed(data).exec(

					http("Post_Request").post("/api/insert.php").asJson().body(StringBody("${post_body}"))

							.check(

									status().is(200)

							))

	);

	private ChainBuilder Get = exec(

			feed(data).exec(

					http("Get_Request").get("${Get_Resourse}").asJson()

			));

	ChainBuilder Put = exec(

			feed(data).exec(

					http("Put_Request").put("/api/update.php").asJson().body(StringBody("${Put_body}"))

			)

	);

	ScenarioBuilder POST = scenario("Post").exec(post);

	ScenarioBuilder GET = scenario("Get").exec(Get);

	ScenarioBuilder UPDATE = scenario("Put").exec(Put);

	{

		setUp(

				POST.injectOpen(atOnceUsers(100)),

				GET.injectOpen(atOnceUsers(100)),

				UPDATE.injectOpen(atOnceUsers(100))

		).protocols(httpProtocol);

	}

}
