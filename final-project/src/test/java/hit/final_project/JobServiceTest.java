package hit.final_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    @Test
    void testGetAllJobs() {
        List<Job> jobs = Arrays.asList(new Job(), new Job());
        when(JobRepository.findAll()).thenReturn(jobs);

        List<Job> retrievedJobs = JobService.getAllJobs();
        assertNotNull(retrievedJobs);
        assertEquals(2, retrievedJobs.size());
    }

    @Test
    void testUpdateJob() {
        Job job = new Job();
        job.setId(1L);
        job.setJobName("Original Name");

        Job updatedDetails = new Job();
        updatedDetails.setJobName("Updated Name");
        updatedDetails.setStatus("Success");
        updatedDetails.setJobType("Deploy");

        when(JobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(JobRepository.save(any(Job.class))).thenReturn(updatedDetails);

        Optional<Job> updatedJob = JobService.updateJob(1L, updatedDetails);
        assertTrue(updatedJob.isPresent());
        assertEquals("Updated Name", updatedJob.get().getJobName());
        assertEquals("Success", updatedJob.get().getStatus());
        assertEquals("Deploy", updatedJob.get().getJobType());
    }

    @Test
    void testDeleteJob() {
        Job job = new Job();
        job.setId(1L);

        when(JobRepository.findById(1L)).thenReturn(Optional.of(job));

        JobService.deleteJob(1L);

        verify(JobRepository).deleteById(1L);
    }

    @Test
    void testGetJobsByStatus() {
        List<Job> jobs = Arrays.asList(new Job(), new Job());
        when(JobRepository.findByStatus("Pending")).thenReturn(jobs);

        List<Job> retrievedJobs = JobService.getJobsByStatus("Pending");
        assertNotNull(retrievedJobs);
        assertEquals(2, retrievedJobs.size());
    }

    @Test
    void testGetJobsByJobType() {
        List<Job> jobs = Arrays.asList(new Job(), new Job());
        when(JobRepository.findByJobType("Build")).thenReturn(jobs);

        List<Job> retrievedJobs = JobService.getJobsByJobType("Build");
        assertNotNull(retrievedJobs);
        assertEquals(2, retrievedJobs.size());
    }

    @Test
    void testGetJobsByDateRange() {
        List<Job> jobs = Arrays.asList(new Job(), new Job());
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);

        when(JobRepository.findByCreatedAtBetween(start, end)).thenReturn(jobs);

        List<Job> retrievedJobs = JobService.getJobsByDateRange(start, end);
        assertNotNull(retrievedJobs);
        assertEquals(2, retrievedJobs.size());
}


}