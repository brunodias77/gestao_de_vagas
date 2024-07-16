package br.com.brunodias.gestao_vagas.modules.company.useCases;

import java.util.List;
import java.util.UUID;

import br.com.brunodias.gestao_vagas.modules.company.entities.Job;
import br.com.brunodias.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ListAllJobsByCompanyUseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> execute(UUID companyId){
        return this.jobRepository.findByCompanyId(companyId);
    }

}