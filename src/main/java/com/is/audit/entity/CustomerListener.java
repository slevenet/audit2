package com.is.audit.entity;

import com.is.audit.ApplicationContextHolder;
import com.is.audit.services.audit.AuditHandler;
import com.is.audit.model.AuditParams;
import com.is.audit.model.AuditType;
import javax.persistence.*;

public class CustomerListener {

    private final AuditHandler auditHandler;

    public CustomerListener() {
        this.auditHandler = (AuditHandler) ApplicationContextHolder
                .getApplicationContext().getBean("auditHandler");
    }

    @PrePersist
    public void userPostPersist(Object source)  {
        audit(source, "CREATE");
    }

    @PostLoad
    public void userPostLoad(Object source) {
        audit(source, "READ");
    }

    @PreUpdate
    public void userPostUpdate(Object source) {
        audit(source, "UPDATE");
    }

    @PreRemove
    public void userPostRemove(Object source) {
        audit(source, "REMOVE");
    }

    private void audit(Object source, String operation){
        AuditParams auditParams = new AuditParams();
        auditParams.setSource(source);
        auditParams.setOperation(operation);
        auditHandler.audit(auditParams, AuditType.ENTITY);
    }
}
