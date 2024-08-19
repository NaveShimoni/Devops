package hit.final_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CI_CDJobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private JobService jobService;

    @InjectMocks
    private JobController jobController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateJob() throws Exception {
        Job job = new Job();
        job.setJobName("Integration Test Job");

        // Mocking the service call
        when(jobService.createJob(any(Job.class))).thenReturn(job);

        // Performing the POST request
        mockMvc.perform(post("/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"jobName\":\"Integration Test Job\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.jobName").value("Integration Test Job"));
    }

    @Test
    void testGetJobById() throws Exception {
        Job job = new Job();
        job.setId(1L);
        job.setJobName("Existing Job");

        // Mocking the service call
        when(jobService.getJobById(1L)).thenReturn(Optional.of(job));

        // Performing the GET request
        mockMvc.perform(get("/jobs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobName").value("Existing Job"));
    }

    @Test
    void testUpdateJob() throws Exception {
        Job job = new Job();
        job.setId(1L);
        job.setJobName("Updated Job Name");

        // Mocking the service call
        when(jobService.updateJob(anyLong(), any(Job.class))).thenReturn(Optional.of(job));

        // Performing the PUT request
        mockMvc.perform(put("/jobs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"jobName\":\"Updated Job Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobName").value("Updated Job Name"));
    }

    @Test
    void testDeleteJob() throws Exception {
        // No need to mock service for delete if it's not returning a value
        mockMvc.perform(delete("/jobs/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetNonExistentJob() throws Exception {
        // Mocking the service call
        when(jobService.getJobById(999L)).thenReturn(Optional.empty());

        // Performing the GET request for a non-existent job
        mockMvc.perform(get("/jobs/999"))
                .andExpect(status().isNotFound());
    }
}
