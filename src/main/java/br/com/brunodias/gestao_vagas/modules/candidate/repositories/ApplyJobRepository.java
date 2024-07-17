package br.com.brunodias.gestao_vagas.modules.candidate.repositories;

import br.com.brunodias.gestao_vagas.modules.candidate.entities.ApplyJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ApplyJobRepository extends JpaRepository<ApplyJob, UUID> {

}