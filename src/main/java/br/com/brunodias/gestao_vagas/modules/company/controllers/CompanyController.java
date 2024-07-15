package br.com.brunodias.gestao_vagas.modules.company.controllers;

import br.com.brunodias.gestao_vagas.modules.company.entities.Company;
import br.com.brunodias.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody Company company){
        try{
            var result = this.createCompanyUseCase.execute(company);
            return ResponseEntity.ok().body(result);
        }catch (Exception err){
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }
}
