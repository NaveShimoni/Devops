package hit.final_project;

import hit.final_project.exceptions.JobNotFoundException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CI_CDJobTests {

    private static final Logger logger = LoggerFactory.getLogger(CI_CDJobTests.class);

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
    class DeleteJobTests {

        @Test
        void testDeleteJob() {
            logger.info("Running testDeleteJob");
            doNothing().when(jobRepository).deleteById(anyLong());
            assertDoesNotThrow(() -> jobService.deleteJob(1L));
            verify(jobRepository, times(1)).deleteById(1L);
        }

        @Test
        void testDeleteNonExistentJob() {
            logger.info("Running testDeleteNonExistentJob");
            doThrow(new JobNotFoundException("Job not found")).when(jobRepository).deleteById(anyLong());
            assertThrows(JobNotFoundException.class, () -> jobService.deleteJob(999L));
        }
    }
}
