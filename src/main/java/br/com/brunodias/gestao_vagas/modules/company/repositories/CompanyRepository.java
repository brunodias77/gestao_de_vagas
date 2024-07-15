package br.com.brunodias.gestao_vagas.modules.company.repositories;

import br.com.brunodias.gestao_vagas.modules.company.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByUsernameOrEmail(String username, String email);

    Optional<Company> findByUsername(String username);
}