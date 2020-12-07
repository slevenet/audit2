package com.is.audit.services.audit;

import com.is.audit.model.AuditType;
import com.is.audit.services.audit.AuditHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingControllerHandler {

    private final AuditHandler auditHandler;

    public LoggingControllerHandler(AuditHandler auditHandler) {
        this.auditHandler = auditHandler;
    }


    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {
    }

    @Pointcut("within(com.is..*)")
    public void onlyProject() {
    }

    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
    }

    //Around -> Any method within resource annotated with @Controller annotation
    @Before("controller() && allMethod() && onlyProject()")
    public Object logAround(JoinPoint joinPoint) {
        auditHandler.audit(joinPoint, AuditType.CONTROLLER);
        return null;
    }
}
