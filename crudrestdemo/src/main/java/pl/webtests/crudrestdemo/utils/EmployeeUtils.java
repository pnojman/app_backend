package pl.webtests.crudrestdemo.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import pl.webtests.crudrestdemo.entity.Employee;

public class EmployeeUtils extends FileResourcesUtils {

	public Employee setEmployeeByJson(String pathFile) throws ParseException {

		JSONObject object = deserializeJsonFileToJsonObject(pathFile);

		Employee employee = new Employee();
		employee.setId(Integer.valueOf(object.get("id").toString()));
		employee.setFirstName(object.get("firstName").toString());
		employee.setLastName(object.get("lastName").toString());
		employee.setEmail(object.get("email").toString());

		return employee;
	}
}
