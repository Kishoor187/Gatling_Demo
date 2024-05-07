package computerdatabase;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class OrangeHRM extends Simulation {

	private HttpProtocolBuilder httpProtocol = http.baseUrl("https://opensource-demo.orangehrmlive.com")
			.inferHtmlResources(AllowList(),
					DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff",
							".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*",
							".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff",
							".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
			.userAgentHeader(
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");

	private ChainBuilder Login = exec(http("request_0").post("/web/index.php/auth/validate")

			.formParam("_token",
					"e1b817ee68f5a4997949722a7e81c.HDDH7515Y3DiZ9AJZmwKOZDcLMkbCA6j3IYsabPTv-w.amWAhqwYUhuTNoM5UCdCCfKDeaxcb1nV6rVaHoPm0pVafqHZ8Dw7Q6USuw")
			.formParam("username", "Admin").formParam("password", "admin123")
			.resources(http("request_1").get("/web/dist/css/chunk-vendors.css?v=1711595107870"),
					http("request_2").get("/web/dist/css/app.css?v=1711595107870"),
					http("request_3").get("/web/dist/js/chunk-vendors.js?v=1711595107870"),
					http("request_4").get("/web/dist/js/app.js?v=1711595107870"),
					http("request_5").get("/web/index.php/core/i18n/messages"),
					http("request_6").get("/web/images/orange.png?v=1711595107870"),
					http("request_7").get("/web/images/orangehrm-logo.png?v=1711595107870"),
					http("request_8").get(
							"/web/index.php/api/v2/dashboard/employees/time-at-work?timezoneOffset=5.5&currentDate=2024-04-17&currentTime=17:47"),
					http("request_9").get("/web/index.php/api/v2/dashboard/employees/locations"),
					http("request_10").get("/web/index.php/api/v2/dashboard/employees/subunit"),
					http("request_11").get("/web/index.php/api/v2/dashboard/shortcuts"),
					http("request_12").get("/web/index.php/api/v2/dashboard/employees/leaves?date=2024-04-17"),
					http("request_13").post("/web/index.php/events/push"),
					http("request_14").get("/web/index.php/api/v2/dashboard/employees/action-summary"),
					http("request_15").get(
							"/web/index.php/api/v2/buzz/feed?limit=5&offset=0&sortOrder=DESC&sortField=share.createdAtUtc"),
					http("request_16").get("/web/index.php/pim/viewPhoto/empNumber/7"),
					http("request_17").get("/web/index.php/pim/viewPhoto/empNumber/11"),
					http("request_18").get("/web/index.php/buzz/photo/9"),
					http("request_19").get("/web/index.php/pim/viewPhoto/empNumber/22")

			), pause(7));

	private ChainBuilder Click_Admin = exec(

			http("Admin").get("/web/index.php/admin/viewAdminModule")

					.resources(http("request_21").get("/web/dist/css/chunk-vendors.css?v=1711595107870"),
							http("request_22").get("/web/dist/css/app.css?v=1711595107870"),
							http("request_23").get("/web/dist/js/chunk-vendors.js?v=1711595107870"),
							http("request_24").get("/web/dist/js/app.js?v=1711595107870"),
							http("request_25").get("/web/index.php/core/i18n/messages"),
							http("request_26").get("/web/images/orange.png?v=1711595107870"),
							http("request_27").get("/web/images/orangehrm-logo.png?v=1711595107870"),
							http("request_28").get("/web/index.php/pim/viewPhoto/empNumber/7"), http("request_29").get(
									"/web/index.php/api/v2/admin/users?limit=50&offset=0&sortField=u.userName&sortOrder=ASC")

					), pause(8));

	private ChainBuilder LogOut = exec(http("logout").get("/web/index.php/auth/logout")

			.resources(http("request_31").get("/web/index.php/core/i18n/messages"),
					http("request_32").get("/web/images/ohrm_branding.png?v=1711595107870")

			));

	private ScenarioBuilder login = scenario("User login to application").exec(Login);
	
	private ScenarioBuilder Admin = scenario("User clicks on the Admin link").exec(Click_Admin);
	
	private ScenarioBuilder logout = scenario("User logout of application").exec(LogOut);
	

	{
		setUp(
				login.injectOpen(atOnceUsers(20)),
				
				Admin.injectOpen(rampUsers(10).during(5)),
				
				logout.injectOpen(rampUsers(10).during(5))
				
				).protocols(httpProtocol);
	}}
