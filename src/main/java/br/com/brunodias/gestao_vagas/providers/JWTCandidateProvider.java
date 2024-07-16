package br.com.brunodias.gestao_vagas.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service // Indica que esta classe é um serviço do Spring.
public class JWTCandidateProvider {

    @Value("${security.token.secret.candidate}") // Injeta o valor da propriedade `security.token.secret.candidate` do
                                                 // arquivo de configuração.
    private String secretKey;

    public DecodedJWT validateToken(String token) {
        // Remove o prefixo "Bearer " do token.
        token = token.replace("Bearer ", "");

        // Cria um algoritmo HMAC256 usando a chave secreta.
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            // Verifica o token usando o algoritmo e retorna o token decodificado.
            var tokenDecoded = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return tokenDecoded;
        } catch (JWTVerificationException e) {
            // Em caso de falha na verificação, imprime a stack trace e retorna null.
            e.printStackTrace();
            return null;
        }
    }

}