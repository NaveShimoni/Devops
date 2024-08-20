// package hit.final_project;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import java.util.Optional;

// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @SpringBootTest
// public class CI_CDJobControllerTest {

//     private static final Logger logger = LoggerFactory.getLogger(CI_CDJobControllerTest.class);

//     @Autowired
//     private MockMvc mockMvc;

//     @Mock
//     private JobService jobService;

//     @InjectMocks
//     private JobController jobController;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();
//     }

//     @Test
//     void testCreateJob() {
//         Job job = new Job();
//         job.setJobName("Test Job");
//         when(JobRepository.save(any(Job.class))).thenReturn(job);

//         Job createdJob = JobService.createJob(job);
//         assertNotNull(createdJob);
//         assertEquals("Test Job", createdJob.getJobName());
//     }

//      @Test
//     void testGetJobById() {
//         Job job = new Job();
//         job.setId(1L);
//         when(JobRepository.findById(1L)).thenReturn(Optional.of(job));

//         Optional<Job> retrievedJob = JobService.getJobById(1L);
//         assertTrue(retrievedJob.isPresent());
//         assertEquals(1L, retrievedJob.get().getId());

//     }

//      @Test
//     void testGetAllJobs() {
//         List<Job> jobs = Arrays.asList(new Job(), new Job());
//         when(JobRepository.findAll()).thenReturn(jobs);

//         List<Job> retrievedJobs = JobService.getAllJobs();
//         assertNotNull(retrievedJobs);
//         assertEquals(2, retrievedJobs.size());
//     }

//     @Test
//     void testDeleteJob() throws Exception {
//         logger.info("Running testDeleteJob");

//         mockMvc.perform(delete("/jobs/1"))
//                 .andExpect(status().isNoContent());
//     }
// }
