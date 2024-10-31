package br.com.compass.api.jwt;

import br.com.compass.api.config.SecurityConfig;
import br.com.compass.api.model.User;
import br.com.compass.api.repositories.UserRepository;
import br.com.compass.api.services.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request);
            if (token == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "O token está ausente.");
                return;
            }

            // Verifica a validade do token
            if (!jwtTokenService.isValidToken(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido.");
                return; // Finaliza o processamento se o token for inválido
            }

            String subject = jwtTokenService.getSubjectFromToken(token); // Obtém o assunto (neste caso, o nome de usuário) do token
            Optional<User> optionalUser = userRepository.findByUsername(subject);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                UserDetailsImpl userDetails = new UserDetailsImpl(user); // Cria um UserDetails com o usuário encontrado

                // Cria um objeto de autenticação do Spring Security
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Define o objeto de autenticação no contexto de segurança do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuário não encontrado.");
                return;
            }
        }

        // Continua o processamento da requisição
        filterChain.doFilter(request, response);
    }

    // Recupera o token do cabeçalho Authorization da requisição
    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !Arrays.asList(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
    }
}
