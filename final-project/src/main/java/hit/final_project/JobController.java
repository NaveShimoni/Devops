package hit.final_project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService JobService;

    @GetMapping
    public List<Job> getAllJobs() {
        return JobService.getAllJobs();
    }

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return JobService.createJob(job);
    }

    @GetMapping("/{id}")
    public Optional<Job> getJobById(@PathVariable Long id) {
        return JobService.getJobById(id);
    }

    @PutMapping("/{id}")
    public Optional<Job> updateJob(@PathVariable Long id, @RequestBody Job jobDetails) {
        return JobService.updateJob(id, jobDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        JobService.deleteJob(id);
    }

    @GetMapping("/status/{status}")
    public List<Job> getJobsByStatus(@PathVariable String status) {
        return JobService.getJobsByStatus(status);
    }

    @GetMapping("/jobType/{jobType}")
    public List<Job> getJobsByJobType(@PathVariable String jobType) {
        return JobService.getJobsByJobType(jobType);
    }

    @GetMapping("/date-range")
    public List<Job> getJobsByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return JobService.getJobsByDateRange(start, end);
    }

}
