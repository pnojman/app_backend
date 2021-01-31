package pl.webtests.crudrestdemo;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import pl.webtests.crudrestdemo.entity.Employee;

@Sql({ "/sqls/manageEmployee.sql" })
public class TestWhenManagingEmployee extends BaseTest {
	
	@Test
	@Tag("local")
	@Tag("test")
	//mvn clean test -Dgroups=test -Dspring.profiles.active=dev 
	void shouldBeAbleToCreateEmployeeWhenTestDataAcceptableByApplication() throws IOException, ParseException {
	
		Employee employeeRequest = employeeUtils.setEmployeeByJson("jsons/manageEmployee/createEmployee.json");
		
		Employee employeeResponse = given().spec(requestSpecification).body(employeeRequest).when().post("api/employees").then().extract().as(Employee.class);
		
		assertThat(employeeRequest).isEqualToIgnoringGivenFields(employeeResponse, "id");

	}
	
	@Test
	@Tag("local")
	//mvn clean test -Dgroups=local
	void shouldBePossibleToDeleteEmployeeByIdWhenEmployeeExists() throws InterruptedException {
		
		int employeeId = 1;
		
		String message = given().spec(requestSpecification).when().delete("api/employees/" + employeeId).then()
				.statusCode(200).extract().asString();

		assertTrue(message.equals("Delete employee id - " + employeeId));
		
		
	}
	
	@Test
	@Tag("local")
	void shouldNotBeAbleToDeleteEmployeeWhenEmployeeDoesNotExist() {
		
		int employeeId = 0;
		
		String message = given().spec(requestSpecification).when().delete("api/employees/" + employeeId).then()
				.extract().asString();
		
		assertTrue(message.contains("Employee id not found : " + employeeId));
		
	}
	
	@Test
	@Tag("local")
	void shouldBePossibleToGetEmployeeByIdWhenEmployeeExists() throws InterruptedException, ParseException {
		
		int employeeId = 2;
		
		Employee employeeToGet = employeeUtils.setEmployeeByJson("jsons/manageEmployee/getEmployee.json");
		
		Employee employeeResponse = given().spec(requestSpecification).when().get("api/employee/" + employeeId).then().extract().as(Employee.class);
			
		assertThat(employeeToGet).isEqualToComparingFieldByField(employeeResponse);
		
	}
	
	@Test
	@Tag("local")
	void shouldBePossibleToModifyEmployeeWhenFirstNameLastNameEmailAreChanged() throws InterruptedException, ParseException {
		
		//employeeId(3);
		
		Employee requestBody = employeeUtils.setEmployeeByJson("jsons/manageEmployee/modifyEmployee.json");
		
		Employee responseBody = given().spec(requestSpecification).body(requestBody).when().put("api/employees").then().extract().as(Employee.class);	

		assertThat(requestBody).isEqualToComparingFieldByField(responseBody);
	}
	
	@Test
	@Tag("local")
	void shouldBePossibleToGetAllEmployeesWhenWeHaveAtLeastSeven() throws InterruptedException {
		
		//employeeId(1,2,3,4,5,6,7)
		
		List<Employee> employees = given().spec(requestSpecification).when().get("api/employees")
				.then().extract().body().jsonPath().getList(".", Employee.class);
		
		for (Employee employee : employees) {
			if (employee.getId() == 1) {
				assertEquals("Przemek",employee.getFirstName());
				assertEquals("Nekon",employee.getLastName());
				assertEquals("nam@op.pl",employee.getEmail());
			}
		}
		System.out.print("Przemek : " + employees.size());
		assertTrue(employees.size() >=7 );
		
	}
}
