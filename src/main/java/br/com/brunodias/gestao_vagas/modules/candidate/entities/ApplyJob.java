package br.com.brunodias.gestao_vagas.modules.candidate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.brunodias.gestao_vagas.modules.candidate.Candidate;
import br.com.brunodias.gestao_vagas.modules.company.entities.Job;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "apply_jobs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJob {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    private Candidate candidateEntity;

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private Job jobEntity;

    @Column(name = "candidate_id")
    private UUID candidateId;

    @Column(name = "job_id")
    private UUID jobId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}