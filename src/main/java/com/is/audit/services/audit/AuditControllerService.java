package com.is.audit.services.audit;

import com.is.audit.model.Audit;
import com.is.audit.model.AuditType;
import com.is.audit.model.RestAudit;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class AuditControllerService extends AuditService{
    @Override
    Audit createAudit(Object joinPointArg) {
        JoinPoint joinPoint = (JoinPoint ) joinPointArg;
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        RestAudit restAudit = new RestAudit();
        restAudit.setAction(request.getMethod());
        try {
            restAudit.setBody(request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        restAudit.setUrl(request.getRequestURL().toString());
        restAudit.setIp(request.getRemoteAddr());
        restAudit.setJavaMethod(className + "." + methodName);
        return restAudit;
    }

    @Override
    AuditType getAuditType() {
        return AuditType.CONTROLLER;
    }
}
