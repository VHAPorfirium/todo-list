package br.com.VHAPorfiro.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.VHAPorfiro.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.equals("/tasks/")) {

            var authorization = request.getHeader("Authorization");

            // Verifica se o header Authorization está presente e começa com "Basic "
            if (authorization == null || !authorization.startsWith("Basic ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
                return;
            }

            // Remove "Basic " e decodifica a string Base64
            String user_password = authorization.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(user_password);
            String authString = new String(authDecode);

            // Divide credenciais em username e password
            String[] credentials = authString.split(":", 2);

            // Garante que existem username e password
            if (credentials.length < 2) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid authentication format");
                return;
            }

            String username = credentials[0];
            String password = credentials[1];

            System.out.println("Authorization");
            System.out.println(username);
            System.out.println(password);

            var user = this.userRepository.findByUsername(username);

            if (user == null) {
                response.sendError(401);
            } else {

                // Validando senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if (passwordVerify.verified) {

                    // Continua a cadeia de filtros
                    filterChain.doFilter(request, response);

                } else {
                    response.sendError(401);
                }

            }

        }
        else {
            filterChain.doFilter(request, response);
        }

    }
}
