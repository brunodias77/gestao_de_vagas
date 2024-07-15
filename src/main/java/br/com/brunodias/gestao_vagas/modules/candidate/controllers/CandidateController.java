package br.com.brunodias.gestao_vagas.modules.candidate.controllers;

import br.com.brunodias.gestao_vagas.exceptions.UserFoundException;
import br.com.brunodias.gestao_vagas.modules.candidate.Candidate;
import br.com.brunodias.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.brunodias.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.brunodias.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;
    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody Candidate candidate){
        try{
            var result = this.createCandidateUseCase.execute(candidate);
            return ResponseEntity.ok().body(result);
        }catch (Exception err){
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Object> get(HttpServletRequest request){
        var idCandidate = request.getAttribute("candidate_id");
        try{
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        }catch (Exception err){
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }
}
