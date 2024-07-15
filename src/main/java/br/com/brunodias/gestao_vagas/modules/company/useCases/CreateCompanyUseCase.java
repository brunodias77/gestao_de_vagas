package br.com.brunodias.gestao_vagas.modules.company.useCases;

import br.com.brunodias.gestao_vagas.exceptions.UserFoundException;
import br.com.brunodias.gestao_vagas.modules.company.entities.Company;
import br.com.brunodias.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Company execute(Company company){
        this.companyRepository.findByUsernameOrEmail(company.getUsername(), company.getEmail()).ifPresent((user) -> {
            throw new UserFoundException();
        });
        var password = passwordEncoder.encode(company.getPassword());
        company.setPassword(password);

       return this.companyRepository.save(company);
    }
}
