package ru.hogwarts.school.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private FacultyService facultyService;

    @Test
    public void shouldGetFaculty() throws Exception {
    Long facultyId = 1L;
    Faculty faculty = new Faculty("Gryffindor", "brown");

    when(facultyService.getFacultyById(facultyId)).thenReturn(faculty);


    ResultActions perform = mockMvc.perform(
            get("/faculty/{id}", facultyId)
    );


        perform
                .andExpect(jsonPath("$.name").value(faculty.getName()))
            .andExpect(jsonPath("$.color").value(faculty.getColor()))
            .andDo(print());
    }

}
