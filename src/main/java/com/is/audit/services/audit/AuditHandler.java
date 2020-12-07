package com.is.audit.services.audit;

import com.is.audit.model.AuditType;
import com.is.audit.services.LogAuditFileHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuditHandler {


    private final Map<AuditType, AuditService> auditServices;

    public AuditHandler(List<AuditService> auditList) {
        this.auditServices = auditList.stream()
                .collect(Collectors.toMap(AuditService::getAuditType, auditService -> auditService));
    }

    public void audit(Object obj, AuditType auditType){
        LogAuditFileHandler logAuditFileHandler = new LogAuditFileHandler();
        logAuditFileHandler.logAudit(auditServices.get(auditType).audit(obj));
    }
}
