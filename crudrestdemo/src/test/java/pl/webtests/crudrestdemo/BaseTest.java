package pl.webtests.crudrestdemo;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import pl.webtests.crudrestdemo.utils.EmployeeUtils;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseTest {


	
	protected static RequestSpecification requestSpecification;
	protected static EmployeeUtils employeeUtils;
	
	@BeforeAll
	public static void init() {
		
		requestSpecification = new RequestSpecBuilder()
				.setBaseUri("http://localhost:8080")
				.setContentType("application/json")
				.build()
				.log().all();
		
		employeeUtils = new EmployeeUtils();
	}
	
}
