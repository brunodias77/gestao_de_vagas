package br.com.brunodias.gestao_vagas.modules.company.useCases;

import br.com.brunodias.gestao_vagas.modules.company.entities.Job;
import br.com.brunodias.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public Job execute(Job job){
        return this.jobRepository.save(job);
    }
}
