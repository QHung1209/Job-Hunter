package vn.jobhunter.jobhunter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.jobhunter.jobhunter.domain.Job;
import vn.jobhunter.jobhunter.domain.Skill;
import vn.jobhunter.jobhunter.domain.Subscriber;
import vn.jobhunter.jobhunter.domain.response.email.ResEmailJob;
import vn.jobhunter.jobhunter.repository.JobRepository;
import vn.jobhunter.jobhunter.repository.SkillRepository;
import vn.jobhunter.jobhunter.repository.SubscriberRepository;
import java.util.Optional;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final SkillRepository skillRepository;
    private final JobRepository jobRepository;
    private final EmailService emailService;

    public SubscriberService(SubscriberRepository subscriberRepository, SkillRepository skillRepository,
            EmailService emailService, JobRepository jobRepository) {
        this.subscriberRepository = subscriberRepository;
        this.skillRepository = skillRepository;
        this.jobRepository = jobRepository;
        this.emailService = emailService;
    }

    public Subscriber handleGetSubscriber(long id) {
        Optional<Subscriber> subOptional = this.subscriberRepository.findById(id);
        return subOptional.isPresent() ? subOptional.get() : null;
    }

    public boolean isExistSubscriber(String name) {
        return this.subscriberRepository.existsByName(name);
    }

    public Subscriber handleCreateSubscriber(Subscriber subscriber) {
        if (subscriber.getSkills() != null) {
            List<Long> listSkillId = subscriber.getSkills().stream()
                    .map(s -> s.getId()).collect(Collectors.toList());

            List<Skill> listSkill = skillRepository.findByIdIn(listSkillId);

            subscriber.setSkills(listSkill);
        }
        return this.subscriberRepository.save(subscriber);
    }

    public Subscriber handleUpdateSubscriber(Subscriber subscriber) {
        Subscriber update = this.handleGetSubscriber(subscriber.getId());

        if (subscriber.getSkills() != null) {
            List<Long> listSkillId = subscriber.getSkills().stream()
                    .map(s -> s.getId()).collect(Collectors.toList());

            List<Skill> listSkill = skillRepository.findByIdIn(listSkillId);

            update.setSkills(listSkill);
        }

        return this.subscriberRepository.save(update);
    }

    public ResEmailJob convertJobToSendEmail(Job job) {
        ResEmailJob res = new ResEmailJob();
        res.setName(job.getName());
        res.setSalary(job.getSalary());
        res.setCompany(new ResEmailJob.CompanyEmail(job.getCompany().getName()));
        List<Skill> skills = job.getSkills();
        List<ResEmailJob.SkillEmail> s = skills.stream().map(skill -> new ResEmailJob.SkillEmail(skill.getName()))
                .collect(Collectors.toList());
        res.setSkills(s);
        return res;
    }

    public void sendSubscribersEmailJobs() {
        List<Subscriber> listSubs = this.subscriberRepository.findAll();
        if (listSubs != null && listSubs.size() > 0) {
            for (Subscriber sub : listSubs) {
                List<Skill> listSkills = sub.getSkills();
                if (listSkills != null && listSkills.size() > 0) {
                    List<Job> listJobs = this.jobRepository.findBySkillsIn(listSkills);
                    if (listJobs != null && listJobs.size() > 0) {

                        List<ResEmailJob> arr = listJobs.stream().map(
                                job -> this.convertJobToSendEmail(job)).collect(Collectors.toList());

                        this.emailService.sendEmailFromTemplateSync(
                                sub.getEmail(),
                                "Cơ hội việc làm hot đang chờ đón bạn, khám phá ngay",
                                "job",
                                sub.getName(),
                                arr);
                    }
                }
            }
        }
    }
}
