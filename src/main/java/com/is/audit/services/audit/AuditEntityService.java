package com.is.audit.services.audit;

import com.is.audit.model.Audit;
import com.is.audit.model.AuditParams;
import com.is.audit.model.AuditType;
import com.is.audit.model.EntityAudit;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;

@Service
public class AuditEntityService extends AuditService {

    private final EntityManagerFactory entityManagerFactory;

    public AuditEntityService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    Audit createAudit(Object obj) {
        AuditParams auditParams = (AuditParams)obj;
        Object primKey = entityManagerFactory.getPersistenceUnitUtil().getIdentifier(auditParams.getSource());
        String name = entityManagerFactory.getMetamodel().entity(auditParams.getSource().getClass()).getName();
        EntityAudit entityAudit = new EntityAudit();
        entityAudit.setEntity(name);
        entityAudit.setIdent_field(primKey);
        entityAudit.setEvent_data(auditParams.getSource());
        entityAudit.setAction(auditParams.getOperation());

        return entityAudit;
    }

    @Override
    AuditType getAuditType() {
        return AuditType.ENTITY;
    }
}
