package hit.final_project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {


    @Autowired
    private JobRepository JobRepository;

    public List<Job> getAllJobs() {
        return JobRepository.findAll();
    }

    public Optional<Job> getJobById(Long id) {
        return JobRepository.findById(id);
    }

    public Job createJob(Job job) {
        job.setCreatedAt(LocalDateTime.now());
        return JobRepository.save(job);
    }

    public Optional<Job> updateJob(Long id, Job jobDetails) {
        return JobRepository.findById(id).map(job -> {
            job.setJobName(jobDetails.getJobName());
            job.setStatus(jobDetails.getStatus());
            job.setUpdatedAt(LocalDateTime.now());
            job.setJobType(jobDetails.getJobType());
            return JobRepository.save(job);
        });
    }

    public void deleteJob(Long id) {
        JobRepository.deleteById(id);
    }

    public List<Job> getJobsByStatus(String status) {
        return JobRepository.findByStatus(status);
    }

    public List<Job> getJobsByJobType(String jobType) {
        return JobRepository.findByJobType(jobType);
    }

    public List<Job> getJobsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return JobRepository.findByCreatedAtBetween(startDate, endDate);
    }

}
