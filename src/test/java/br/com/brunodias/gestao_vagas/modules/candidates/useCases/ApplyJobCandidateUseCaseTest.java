package br.com.brunodias.gestao_vagas.modules.candidates.useCases;

import br.com.brunodias.gestao_vagas.exceptions.UserNotFoundException;
import br.com.brunodias.gestao_vagas.modules.candidate.Candidate;
import br.com.brunodias.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.brunodias.gestao_vagas.modules.candidate.entities.ApplyJob;
import br.com.brunodias.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.brunodias.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.brunodias.gestao_vagas.modules.company.entities.Job;
import br.com.brunodias.gestao_vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class): Esta anotação é usada para registrar a extensão do Mockito com o JUnit 5.
// Ela permite que o Mockito gerencie a criação de mocks e a injeção de dependências automaticamente durante os testes.
@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    // @InjectMocks: Esta anotação instrui o Mockito a criar uma instância do objeto especificado e injetar
    // quaisquer mocks criados com @Mock ou @Spy nos campos desse objeto.
    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    // @Mock: Esta anotação é usada para criar e injetar um mock na classe de teste. Aqui, os repositórios de candidatos,
    // trabalhos e aplicações de trabalho são mockados para simular as operações de banco de dados sem interagir com uma base real.
    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    // @Test: Esta anotação marca o método como um método de teste que será executado pelo JUnit.
    @Test
    // @DisplayName: Esta anotação é usada para definir um nome amigável para o teste, que será mostrado nos relatórios de testes.
    @DisplayName("Não deverá ser possível se candidatar a uma vaga se o candidato não for encontrado")
    public void não_deve_ser_aplicado_com_candidato_não_encontrado(){
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            // assertThat: Método de AssertJ para validar as condições do teste. Aqui, verificamos se a exceção lançada é do tipo UserNotFoundException.
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    public void deve_ser_capaz_de_aplicar_a_um_trabalho() {
        // Cria IDs aleatórios para candidato e trabalho.
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        // Cria uma instância de ApplyJob com os IDs do candidato e do trabalho.
        var applyJob = ApplyJob.builder().candidateId(idCandidate)
                .jobId(idJob).build();

        // Cria uma instância de ApplyJob com um ID aleatório para simular o retorno do repositório.
        var applyJobCreated = ApplyJob.builder().id(UUID.randomUUID()).build();

        // Configura os mocks para retornar um candidato e um trabalho quando procurados pelos seus IDs.
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new Candidate()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new Job()));

        // Configura o mock do repositório de ApplyJob para retornar a instância criada de ApplyJob quando salvo.
        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        // Executa o caso de uso de aplicação de trabalho.
        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        // Verifica se o resultado possui a propriedade "id".
        assertThat(result).hasFieldOrProperty("id");
        // Verifica se o ID do resultado não é nulo.
        assertNotNull(result.getId());
    }
}
