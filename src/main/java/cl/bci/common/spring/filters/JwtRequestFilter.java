package cl.bci.common.spring.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.bci.common.spring.utils.CommonConstants;
import cl.bci.common.spring.utils.ErrorResponse;
import cl.bci.common.spring.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        if (exemptUrls(request, response, chain)) return;

        final String requestTokenHeader = request.getHeader(CommonConstants.HEADER);

        String username;
        String jwtToken;

        try {
            if (requestTokenHeader != null && requestTokenHeader.startsWith(CommonConstants.TOKEN_PREFIX)) {
                jwtToken = requestTokenHeader.substring(CommonConstants.TOKEN_PREFIX.length());
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } else {
                throw new IllegalArgumentException(CommonConstants.BAD_TOKEN);
            }

            if (isValidUser(request, response, username, jwtToken)) return;
            chain.doFilter(request, response);
        } catch (IllegalArgumentException e) {
            setErrorResponse(response, e.getMessage());
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, CommonConstants.TOKEN_EXPIRED);
        } catch (MalformedJwtException e) {
            setErrorResponse(response, CommonConstants.BAD_TOKEN);
        } catch (SignatureException e){
            setErrorResponse(response, CommonConstants.TOKEN_ERROR);
        }
    }

    private boolean isValidUser(HttpServletRequest request, HttpServletResponse response, String username, String jwtToken) throws IOException {
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(jwtToken, userDetails))) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                if (!hasPermission(request,userDetails)) {
                    setErrorResponse(response, CommonConstants.USUARIO_SIN_PERMISOS);
                    return true;
                }

            } else {
                throw new IllegalArgumentException(CommonConstants.BAD_TOKEN);
            }
        }
        return false;
    }

    private static boolean exemptUrls(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();


        if (requestURI.equals("/login") || requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/swagger-ui") || requestURI.equals("/swagger-ui.html")) {
            chain.doFilter(request, response);
            return true;
        }
        return false;
    }


    private boolean hasPermission(HttpServletRequest request, UserDetails userDetails) {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api/bci/user/createUser")) {
            return userDetails.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        } else if (requestURI.startsWith("/api/bci/user/getAllUsers")) {
            return userDetails.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        }

        return false;
    }

    private void setErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(CommonConstants.CONTENT_TYPE);
        ErrorResponse errorResponse = new ErrorResponse(message);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }


}