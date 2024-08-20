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
//     void testCreateJob() throws Exception {
//         Job job = new Job();
//         job.setJobName("Integration Test Job");

//         logger.info("Running testCreateJob");

//         when(jobService.createJob(job)).thenReturn(job);

//         mockMvc.perform(post("/jobs")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{\"jobName\":\"Integration Test Job\"}"))
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("$.jobName").value("Integration Test Job"));
//     }

//     @Test
//     void testGetJobById() throws Exception {
//         Job job = new Job();
//         job.setId(1L);
//         job.setJobName("Existing Job");

//         logger.info("Running testGetJobById");

//         when(jobService.getJobById(1L)).thenReturn(Optional.of(job));

//         mockMvc.perform(get("/jobs/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.jobName").value("Existing Job"));
//     }

//     @Test
//     void testDeleteJob() throws Exception {
//         logger.info("Running testDeleteJob");

//         mockMvc.perform(delete("/jobs/1"))
//                 .andExpect(status().isNoContent());
//     }
// }
