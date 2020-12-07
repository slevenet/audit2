package com.is.audit.services.audit;

import com.is.audit.model.AuditType;
import com.is.audit.services.LogAuditFileHandler;
import com.is.audit.services.LogAuditHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuditHandler {


    private final Map<AuditType, AuditService> auditServices;
    private final LogAuditHandler logAuditFileHandler;

    public AuditHandler(List<AuditService> auditList,
                        LogAuditFileHandler logAuditFileHandler) {
        this.auditServices = auditList.stream()
                .collect(Collectors.toMap(AuditService::getAuditType, auditService -> auditService));
        this.logAuditFileHandler = logAuditFileHandler;
    }

    public void audit(Object obj, AuditType auditType){
        logAuditFileHandler.logAudit(auditServices.get(auditType).audit(obj));
    }
}
