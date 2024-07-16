package br.com.brunodias.gestao_vagas.security;

import java.io.IOException;

import br.com.brunodias.gestao_vagas.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component  // Indica que essa classe é um componente Spring, permitindo que seja detectada automaticamente pelo Spring durante a varredura de componentes.
public class SecurityCompanyFilter extends OncePerRequestFilter {

    @Autowired  // Injeta uma instância do JWTProvider no filtro.
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Recupera o header "Authorization" do pedido HTTP.
        String header = request.getHeader("Authorization");

        // Verifica se a URI do pedido começa com "/company".
        if (request.getRequestURI().startsWith("/company")) {
            if (header != null) {
                // Valida o token JWT.
                var token = this.jwtProvider.validateToken(header);
                if (token == null) {
                    // Se o token não for válido, retorna o status 401 (Não Autorizado).
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                // Extrai as roles (papéis) do token.
                var roles = token.getClaim("roles").asList(Object.class);

                // Mapeia as roles para autoridades do Spring Security.
                var grants = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                        .toList();

                // Define o atributo "company_id" no request com o assunto do token.
                request.setAttribute("company_id", token.getSubject());
                // Cria um objeto de autenticação com as autoridades extraídas.
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null,
                        grants);

                // Define o contexto de segurança do Spring com a autenticação criada.
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // Continua a execução do filtro.
        filterChain.doFilter(request, response);
    }
}
