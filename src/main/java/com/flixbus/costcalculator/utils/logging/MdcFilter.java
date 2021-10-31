package com.flixbus.costcalculator.utils.logging;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MdcFilter implements Filter {

    private static final String X_CORRELATION_ID = "x-correlation-id";

    private final MdcContext mdcContext;
    private final RandomIdGenerator correlationIdGenerator;

    public MdcFilter(MdcContext mdcContext, RandomIdGenerator correlationIdGenerator) {
        this.mdcContext = mdcContext;
        this.correlationIdGenerator = correlationIdGenerator;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {

            var correlationId = ((HttpServletRequest) request).getHeader(X_CORRELATION_ID);

            if (!StringUtils.hasText(correlationId)) {
                correlationId = correlationIdGenerator.generateRandomId();
            }

            mdcContext.setLoggingContext(correlationId);
            ((HttpServletResponse) response).addHeader(X_CORRELATION_ID, correlationId);

            chain.doFilter(request, response);
        } finally {
            mdcContext.clearContext();
        }
    }
}
