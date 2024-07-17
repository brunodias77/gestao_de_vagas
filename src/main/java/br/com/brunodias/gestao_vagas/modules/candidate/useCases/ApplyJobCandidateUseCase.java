package br.com.brunodias.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import br.com.brunodias.gestao_vagas.exceptions.JobNotFoundException;
import br.com.brunodias.gestao_vagas.exceptions.UserNotFoundException;
import br.com.brunodias.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.brunodias.gestao_vagas.modules.candidate.entities.ApplyJob;
import br.com.brunodias.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.brunodias.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    // ID do candidato
    // ID da vaga
    public ApplyJob execute(UUID idCandidate, UUID idJob){
        // Validar se o candidato existe
        this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        // Validar se a vaga existe
        this.jobRepository.findById(idJob)
                .orElseThrow(() -> {
                    throw new JobNotFoundException();
                });

        // Candidato se inscrever na vaga
        var applyJob = ApplyJob.builder()
                .candidateId(idCandidate)
                .jobId(idJob).build();

        applyJob = applyJobRepository.save(applyJob);
        return applyJob;
    }
}