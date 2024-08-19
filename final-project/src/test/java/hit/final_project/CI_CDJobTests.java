package hit.final_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CI_CDJobTests {

    private static final Logger logger = LoggerFactory.getLogger(CI_CDJobTests.class);

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private JobService jobService;

    @InjectMocks
    private JobController jobController;

    @Mock
    private JobRepository jobRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Integration Tests
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

    @Nested
    class CreateJobTests {

        @Test
        void testAddJob() {
            logger.info("Running testAddJob");
            Job job = new Job();
            job.setJobName("Test Job");
            when(jobRepository.save(any(Job.class))).thenReturn(job);

            Job createdJob = jobService.createJob(job);
            assertNotNull(createdJob);
            assertEquals("Test Job", createdJob.getJobName());
        }

        @ParameterizedTest
        @ValueSource(strings = {"Pending", "In Progress", "Completed"})
        void testAddJobWithVariousStatuses(String status) {
            logger.info("Running testAddJobWithVariousStatuses for status: {}", status);
            Job job = new Job();
            job.setStatus(status);
            when(jobRepository.save(any(Job.class))).thenReturn(job);

            Job createdJob = jobService.createJob(job);
            assertNotNull(createdJob);
            assertEquals(status, createdJob.getStatus());
        }
    }

    @Nested
    class ReadJobTests {

        @Test
        void testGetJob() {
            logger.info("Running testGetJob");
            Job job = new Job();
            job.setId(1L);
            when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

            Optional<Job> retrievedJob = jobService.getJobById(1L);
            assertTrue(retrievedJob.isPresent());
            assertEquals(1L, retrievedJob.get().getId());
        }

        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void testGetJobByDifferentIds(Long jobId) {
            logger.info("Running testGetJobByDifferentIds for ID: {}", jobId);
            Job job = new Job();
            job.setId(jobId);
            when(jobRepository.findById(jobId)).thenReturn(Optional.of(job));

            Optional<Job> retrievedJob = jobService.getJobById(jobId);
            assertTrue(retrievedJob.isPresent());
            assertEquals(jobId, retrievedJob.get().getId());
        }
    }

    @Nested
    class UpdateJobTests {

        @Test
        void testUpdateJob() {
            logger.info("Running testUpdateJob");
            Job job = new Job();
            job.setId(1L);
            job.setJobName("Updated Job Name");
            when(jobRepository.save(any(Job.class))).thenReturn(job);
            when(jobRepository.findById(1L)).thenReturn(Optional.of(job));  // Ensure findById returns the job

            Optional<Job> updatedJob = jobService.updateJob(1L, job);
            assertTrue(updatedJob.isPresent());
            assertEquals("Updated Job Name", updatedJob.get().getJobName());
        }

        @Test
        void testUpdateJobWithInvalidData() {
            logger.info("Running testUpdateJobWithInvalidData");
            Job job = new Job();
            // Setting invalid data here
            when(jobRepository.save(any(Job.class))).thenThrow(new IllegalArgumentException("Invalid data"));

            assertThrows(IllegalArgumentException.class, () -> {
                jobService.updateJob(1L, job);
            });
        }
    }

    @Nested
    class DeleteJobTests {

        @Test
        void testDeleteJob() {
            logger.info("Running testDeleteJob");
            Job job = new Job();
            job.setId(1L);
            when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
            doNothing().when(jobRepository).deleteById(1L);

            jobService.deleteJob(1L);
            verify(jobRepository).deleteById(1L);
        }

        @Test
        void testDeleteNonExistentJob() {
            logger.info("Running testDeleteNonExistentJob");
            when(jobRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(JobNotFoundException.class, () -> {
                jobService.deleteJob(999L);
            });
        }
    }
}
