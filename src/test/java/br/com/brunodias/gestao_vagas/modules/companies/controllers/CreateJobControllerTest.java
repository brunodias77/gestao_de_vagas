package br.com.brunodias.gestao_vagas.modules.companies.controllers;

import java.util.UUID;

import br.com.brunodias.gestao_vagas.modules.company.dtos.CreateJobDTO;
import br.com.brunodias.gestao_vagas.modules.company.entities.Company;
import br.com.brunodias.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.brunodias.gestao_vagas.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


// @RunWith(SpringRunner.class): Esta anotação é usada para fornecer um ambiente de execução para testes JUnit.
// SpringRunner é uma subclasse do JUnit 4, que fornece suporte para testes de integração com o Spring Framework.
@RunWith(SpringRunner.class)
// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT): Esta anotação é usada para indicar que o contexto de teste deve ser um aplicativo Spring Boot completo.
// webEnvironment = WebEnvironment.RANDOM_PORT: Indica que o servidor web deve ser iniciado em uma porta aleatória.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @ActiveProfiles("test"): Esta anotação é usada para especificar o perfil ativo durante a execução do teste. Aqui, o perfil "test" é ativado.
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    // @Autowired: Esta anotação é usada para realizar a injeção de dependência do Spring. Aqui, o contexto da aplicação web e o repositório de empresas são injetados.
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    // @Before: Esta anotação é usada para indicar que o método setup() deve ser executado antes de cada método de teste.
    @Before
    public void setup(){
        // MockMvcBuilders.webAppContextSetup(context): Configura o MockMvc com o contexto da aplicação web.
        // apply(SecurityMockMvcConfigurers.springSecurity()): Aplica a configuração de segurança do Spring Security ao MockMvc.
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    // @Test: Esta anotação marca o método como um método de teste que será executado pelo JUnit.
    @Test
    public void deveria_ser_capaz_de_criar_um_novo_emprego() throws Exception {
        // Cria uma nova entidade CompanyEntity para teste.
        var company = Company.builder()
                .description("COMPANY_DESCRIPTION")
                .email("email@company.com")
                .password("1234567890")
                .username("COMPANY_USERNAME")
                .name("COMPANY_NAME").build();

        // Salva e persiste a entidade company no banco de dados.
        company = companyRepository.saveAndFlush(company);

        // Cria um DTO CreateJobDTO para o teste.
        var createdJobDTO = CreateJobDTO.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();

        // Realiza uma requisição POST para criar um novo job e verifica se o status da resposta é 200 (OK).
        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJson(createdJobDTO))
                        .header("Authorization", TestUtils.generateToken(company.getId(), "JAVAGAS_@123#")))
                        .andExpect(MockMvcResultMatchers.status().isOk());

        System.out.println(result);
    }

//    @Test
//    public void deveria_não_ser_capaz_de_criar_um_novo_emprego_se_empresa_não_encontrada() throws Exception {
//        // Cria um DTO CreateJobDTO para o teste.
//        var createdJobDTO = CreateJobDTO.builder()
//                .benefits("BENEFITS_TEST")
//                .description("DESCRIPTION_TEST")
//                .level("LEVEL_TEST")
//                .build();
//
//        // Realiza uma requisição POST para criar um novo job com um ID de empresa não existente e verifica se o status da resposta é 400 (Bad Request).
//        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(TestUtils.objectToJson(createdJobDTO))
//                        .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "JAVAGAS_@123#")))
//                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }
}
