package vn.edu.crs.courseservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                String username = claims.getSubject();

                // Đọc linh hoạt cả claim "role" hoặc "roles"
                String role = claims.get("role", String.class);
                if (role == null) {
                    role = claims.get("roles", String.class);
                }

                if (role != null) {
                    String formattedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(new SimpleGrantedAuthority(formattedRole))
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println(">>> JWT Auth Success! User: " + username + " | Role: " + formattedRole);
                } else {
                    System.err.println(">>> JWT Auth Failed: Token khong chua claim 'role' hoac 'roles'!");
                }
            } catch (Exception e) {
                System.err.println(">>> JWT Verification Exception: " + e.getMessage());
                SecurityContextHolder.clearContext();
            }
        } else {
            System.err.println(">>> JWT Auth Warning: Request khong co Header Authorization hop le!");
        }

        filterChain.doFilter(request, response);
    }
}