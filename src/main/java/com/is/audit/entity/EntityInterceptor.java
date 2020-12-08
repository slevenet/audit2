package com.is.audit.entity;

import com.is.audit.ApplicationContextHolder;
import com.is.audit.model.AuditParams;
import com.is.audit.model.AuditType;
import com.is.audit.services.audit.AuditHandler;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;

public class EntityInterceptor extends EmptyInterceptor {

    private final AuditHandler auditHandler;

    public EntityInterceptor() {
        this.auditHandler = (AuditHandler) ApplicationContextHolder
                .getApplicationContext().getBean("auditHandler");
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        audit(entity, "CREATE");
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        audit(entity, "UPDATE");
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        audit(entity, "DELETE");
        super.onDelete(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        audit(entity, "READ");
        return super.onLoad(entity, id, state, propertyNames, types);
    }

    private void audit(Object source, String operation){
        AuditParams auditParams = new AuditParams();
        auditParams.setSource(source);
        auditParams.setOperation(operation);
        auditHandler.audit(auditParams, AuditType.ENTITY);
    }
}
