package hit.final_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class JobServiceTest {

    @Mock
    private JobRepository JobRepository;

    @InjectMocks
    private JobService JobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateJob() {
        Job job = new Job();
        job.setJobName("Test Job");
        when(JobRepository.save(any(Job.class))).thenReturn(job);

        Job createdJob = JobService.createJob(job);
        assertNotNull(createdJob);
        assertEquals("Test Job", createdJob.getJobName());
    }

    @Test
    void testGetJobById() {
        Job job = new Job();
        job.setId(1L);
        when(JobRepository.findById(1L)).thenReturn(Optional.of(job));

        Optional<Job> retrievedJob = JobService.getJobById(1L);
        assertTrue(retrievedJob.isPresent());
        assertEquals(1L, retrievedJob.get().getId());

    }
}
