package employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
@Slf4j
public class EmployeesController {

    private EmployeesClient employeesClient;

    @GetMapping("/")
    public ModelAndView listEmployees() {
        Map<String, Object> model = new HashMap<>();
        model.put("employees", employeesClient.listEmployees());
        model.put("command", Employee.empty());

        return new ModelAndView("index", model);
    }

    @GetMapping("/create-employee")
    public ModelAndView createEmployee(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("JWT Token: {}", authHeader);
        var model = Map.of(
                "command", Employee.empty()
        );
        return new ModelAndView("create-employee", model);
    }

    @PostMapping("/create-employee")
    public ModelAndView createEmployeePost(@ModelAttribute Employee command) {
        employeesClient.createEmployee(command);
        return new ModelAndView("redirect:/");
    }

}