package com.qmy.config.jwtSecurity;

import com.qmy.domain.user.UserInfo;
import com.qmy.services.user.impl.IUserServiceImpl;
import com.qmy.utils.SpringUntils;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 实现token的校验功能
 */

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);

    }
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");
        if (header == null||header.equals("null")) {
            chain.doFilter(req, res);
            return;
        }else if(header.startsWith("Bearer ")||header.contains("ccf_")){
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req,res);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        }else{
            chain.doFilter(req, res);
            return;
        }
    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request,HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            // parse the token.
            if(token.contains("ccf_")){
                UserInfo user=getUser(request,response);
                if(user==null){
                    return null;
                }else {
                    return new UsernamePasswordAuthenticationToken(user.getUsername(), null, new ArrayList<>());
                }
            }else{
                String user = Jwts.parser()
                        .setSigningKey("QmyJwtSecret")
                        .parseClaimsJws(token.replace("Bearer ", ""))
                        .getBody()
                        .getSubject();

                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            }
            return null;
        }
        return null;
    }

    private IUserServiceImpl userService=(IUserServiceImpl) SpringUntils.getBean("UserService");

    public UserInfo getUser(HttpServletRequest request, HttpServletResponse response){
        UserInfo user=this.userService.getUserByUUID(request.getHeader("Authorization"));
        if(user==null){
            PrintWriter writer = null;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            try {
                writer =  response.getWriter();
                String error = "no access";
                writer.print(error);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null)
                    writer.close();
            }
        }
        return user;
    }

}
