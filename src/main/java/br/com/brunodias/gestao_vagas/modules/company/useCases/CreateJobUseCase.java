package br.com.brunodias.gestao_vagas.modules.company.useCases;

import br.com.brunodias.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.brunodias.gestao_vagas.modules.company.entities.Job;
import br.com.brunodias.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.brunodias.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Job execute(Job job){
        companyRepository.findById(job.getCompanyId()).orElseThrow(() -> {
            throw new CompanyNotFoundException();
        });
        return this.jobRepository.save(job);
    }
}
