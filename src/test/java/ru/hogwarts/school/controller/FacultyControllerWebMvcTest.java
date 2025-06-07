package ru.hogwarts.school.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FacultyService facultyService;

    @Test
    void shouldGetFaculty() throws Exception {
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

    @Test
    void shouldCreateFaculty() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty("Gryffindor", "brown");
        Faculty savedFaculty = new Faculty("Gryffindor", "brown");
        savedFaculty.setId(facultyId);
        when(facultyService.createFaculty(faculty)).thenReturn(savedFaculty);


        ResultActions perform = mockMvc.perform(
                post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)));


        perform
                .andExpect(jsonPath("$.id").value(savedFaculty.getId()))
                .andExpect(jsonPath("$.name").value(savedFaculty.getName()))
                .andExpect(jsonPath("$.color").value(savedFaculty.getColor()))
                .andDo(print());
    }

    @Test
    void shouldFaculty() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty("Gryffindor", "brown");
        Faculty savedFaculty = new Faculty("Slytherin", "green");
        savedFaculty.setId(facultyId);
        when(facultyService.editFaculty(facultyId,faculty)).thenReturn(savedFaculty);


        ResultActions perform = mockMvc.perform(
                put("/faculty/{id}",facultyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)));


        perform
                .andExpect(jsonPath("$.id").value(savedFaculty.getId()))
                .andExpect(jsonPath("$.name").value(savedFaculty.getName()))
                .andExpect(jsonPath("$.color").value(savedFaculty.getColor()))
                .andDo(print());
    }




}
