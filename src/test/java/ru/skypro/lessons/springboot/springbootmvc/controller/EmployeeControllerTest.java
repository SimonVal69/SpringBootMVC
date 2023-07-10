package ru.skypro.lessons.springboot.springbootmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.lessons.springboot.springbootmvc.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.springbootmvc.dto.PositionDTO;
import ru.skypro.lessons.springboot.springbootmvc.model.Department;
import ru.skypro.lessons.springboot.springbootmvc.model.Position;
import ru.skypro.lessons.springboot.springbootmvc.repository.DepartmentRepository;
import ru.skypro.lessons.springboot.springbootmvc.repository.PositionRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PositionRepository positionRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("Тест для статуса 404 при неправильном id")
    @Test
    @SneakyThrows
    void status404ByWrongIdTest() {

        //Получение статуса 404 при вводе неправильного id
        long id = 1L;
        mockMvc.perform(get("/employees/" + id + "/fullInfo")
                        .with(user("user_test").roles("USER")))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Тест для статуса 404 при отсутствующем position/department")
    @Test
    @SneakyThrows
    void status404NotFoundByWrongPositionOrDepartmentTest() {

        //Добавление employees при отсутствующем position/department
        mockMvc.perform(post("/employees/")
                        .with(user("user_admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addEmployeesForTest()))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Тест для статуса 403 для пользователей с недостаточными правами")
    @Test
    @SneakyThrows
    void status403ForbiddenTest() {

        long id = 1L;

        //Удаление employee пользователем с недостаточными правами
        mockMvc.perform(delete("/employees/" + id)
                        .with(user("user_test").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Тесты для rest-запросов по добавлению и получению данных из position")
    @Test
    @SneakyThrows
    void addAndGetPositionTest() {

        PositionDTO positionDTO = new PositionDTO("Manager");
        String jsonPosition = new ObjectMapper().writeValueAsString(positionDTO);

        //Добавление position
        mockMvc.perform(post("/employees/position")
                        .with(user("user_admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPosition))
                .andExpect(status().isOk());

        //Получение всех positions
        mockMvc.perform(get("/employees/positions/all")
                        .with(user("user_test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].positionName").value("Manager"));
    }

    @DisplayName("Тесты для основных rest-запросов для employee(s)")
    @Test
    @SneakyThrows
    void allMainRestQueryOfEmployeeTest() {

        addPositionAndDepartmentForTest();

        //Добавление employees
        mockMvc.perform(post("/employees/")
                        .with(user("user_admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addEmployeesForTest()))
                .andExpect(status().isOk());

        //Получение всех employees
        mockMvc.perform(get("/employees/all")
                        .with(user("user_test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$").isNotEmpty());

        //Получение полной информации об employee по id
        long id = 1L;
        mockMvc.perform(get("/employees/" + id + "/fullInfo")
                        .with(user("user_test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$.name").value("John Doe"));

        //Получение информации об employee с зарплатой выше чем compareSalary
        int compareSalary = 6000;
        mockMvc.perform(get("/employees/salary/higherThan")
                        .with(user("user_test").roles("USER"))
                        .param("compareSalary", String.valueOf(compareSalary)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("John Rick"))
                .andExpect(jsonPath("$[0].salary").value(7000));

        //Постраничный вывод информации об employees
        int page = 0;
        mockMvc.perform(get("/employees/page")
                        .with(user("user_test").roles("USER"))
                        .param("page", String.valueOf(page)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());

        //Создание отчёта об employee в разрезе department
        long reportId = 1L;
        mockMvc.perform(post("/employees/report")
                        .with(user("user_admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string("Идентификатор (id) сохраненного отчёта: " + reportId));

        //Создание json файла из сохранённого в БД отчёта
        mockMvc.perform(get("/employees/report/" + id)
                        .with(user("user_test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        //Удаление employee
        mockMvc.perform(delete("/employees/" + id)
                        .with(user("user_admin").roles("ADMIN")))
                .andExpect(status().isOk());

        //Редактирование employee
        id = 2L;
        mockMvc.perform(put("/employees/" + id)
                        .with(user("user_admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new EmployeeDTO("Anna Doe", 6000, "Manager", "Sales"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.name").value("Anna Doe"))
                .andExpect(jsonPath("$.salary").value(6000));

    }

    @DisplayName("Тест для rest-запроса по загрузке employees из json-файла")
    @Test
    @SneakyThrows
    void loadEmployeesFromFileAndSaveTest() {

        addPositionAndDepartmentForTest();

        MockMultipartFile file = new MockMultipartFile("file", "employees.json", "application/json",
                ("[{\"name\":\"John Doe\",\"salary\":5000,\"positionName\":\"Manager\",\"departmentName\":\"Sales\"}," +
                        "{\"name\":\"John Rick\",\"salary\":7000,\"positionName\":\"Manager\",\"departmentName\":\"Sales\"}]").getBytes());

        //Загрузка employees из json файла
        mockMvc.perform(multipart("/employees/upload")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("user_admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "text/plain;charset=UTF-8"));
    }

    @SneakyThrows
    private String addEmployeesForTest() {

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        EmployeeDTO employeeDTO1 = new EmployeeDTO("John Doe", 5000, "Manager", "Sales");
        EmployeeDTO employeeDTO2 = new EmployeeDTO("John Rick", 7000, "Manager", "Sales");
        employeeDTOList.add(employeeDTO1);
        employeeDTOList.add(employeeDTO2);

        return new ObjectMapper().writeValueAsString(employeeDTOList);
    }

    private void addPositionAndDepartmentForTest() {
        Position position = new Position("Manager");
        positionRepository.save(position);
        Department department = new Department("Sales");
        departmentRepository.save(department);
    }
}
