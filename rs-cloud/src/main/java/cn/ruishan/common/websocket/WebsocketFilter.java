package cn.ruishan.common.websocket;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author RS-LLG
 */
@Order(1)
@Component
@WebFilter(filterName = "WebsocketFilter", urlPatterns = "/socket/*")
public class WebsocketFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = ((HttpServletRequest) servletRequest).getHeader("Sec-WebSocket-Protocol");

        response.setHeader("Sec-WebSocket-Protocol", token);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
