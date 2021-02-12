package pl.webtests.crudrestdemo;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import pl.webtests.crudrestdemo.entity.Employee;
import pl.webtests.crudrestdemo.service.EmployeeService;

public class TestWhenManagingEmployee extends BaseTest {

	@Autowired
	private EmployeeService employeeService;

	@Test
	@Tag("local")
	@Tag("test")
	@Sql({ "/sqls/manageEmployee/shouldBeAbleToCreateEmployeeWhenOnlyDataRequired.sql" })
	void shouldBeAbleToCreateEmployeeWhenOnlyRequiredData() throws IOException, ParseException {

		Employee employeeRequest = employeeUtils.setEmployeeByJson("jsons/manageEmployee/createEmployee.json");

		Employee employeeResponse = given().spec(requestSpecification).body(employeeRequest).when()
				.post("api/employees").then().extract().as(Employee.class);

		assertThat(employeeRequest).isEqualToIgnoringGivenFields(employeeResponse, "id");

	}

	@Test
	@Tag("local")
	@Sql({ "/sqls/manageEmployee/shouldBeAbleToDeleteEmployeeByIdWhenEmployeeExists.sql" })
	void shouldBeAbleToDeleteEmployeeByIdWhenEmployeeExists() throws InterruptedException, ParseException {

		Employee employee = employeeUtils.setEmployeeByJson("jsons/manageEmployee/deleteEmployee.json");

		Employee employeeFound = employeeService.findByFirstAndLastNameAndEmail(employee);

		int employeeId = employeeFound.getId();

		String message = given().spec(requestSpecification).when().delete("api/employees/" + employeeId).then()
				.statusCode(200).extract().asString();

		assertTrue(message.equals("Delete employee id - " + employeeId));

	}

}
