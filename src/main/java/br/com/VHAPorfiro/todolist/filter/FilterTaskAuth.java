package br.com.VHAPorfiro.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
 
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
    throws ServletException, IOException {

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

        // Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }
}
