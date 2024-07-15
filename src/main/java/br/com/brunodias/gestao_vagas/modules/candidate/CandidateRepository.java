package br.com.brunodias.gestao_vagas.modules.candidate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID > {
    Optional<Candidate> findByUsernameOrEmail(String username, String email );
    Optional<Candidate> findByUsername(String username);
}
