package com.sociedadmedica.reserva.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        // 2. Extraer información vital del token
        // En microservicios, no cargamos el usuario, solo validamos la identidad
        final String username = jwtService.extractUsername(jwt);
        // Necesitamos extraer el rol. Esto requiere un nuevo método en JwtService (ver Solución 2)
        final String role = jwtService.extractRole(jwt);

        // 3. Verificar si hay un usuario y si el token es VÁLIDO (solo verifica expiración y firma)
        // La validación en la API de Reserva solo necesita el token.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // **IMPORTANTE: SOLO VERIFICAMOS LA FIRMA Y CADUCIDAD DEL TOKEN.**
            // El método isTokenValid DEBE ser modificado en JwtService (ver Solución 2)
            if (jwtService.isTokenValid(jwt)) {

                // 4. Creamos las autoridades (ROLE) a partir del token
                // Asumimos que el rol se obtiene del token. Si el rol es nulo, usamos un rol por defecto.
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + (role != null ? role : "CLIENT"));

                // 5. Creamos un objeto de autenticación en MEMORIA para el contexto de seguridad
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                username,       // El principal (email)
                                null,           // Credenciales (nulas para JWT)
                                List.of(authority) // Las autoridades/roles
                        );

                // 6. Colocar la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}