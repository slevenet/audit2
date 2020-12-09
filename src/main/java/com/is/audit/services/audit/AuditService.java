package com.is.audit.services.audit;

import com.is.audit.model.Audit;
import com.is.audit.model.AuditType;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AuditService {

    Logger logger = LoggerFactory.getLogger(AuditService.class);

    public Audit audit(Object obj){
        Audit audit = createAudit(obj);
        audit.setEvent_ts(LocalDateTime.now().toString());
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            if(securityContext != null) {
                Authentication authentication = securityContext.getAuthentication();
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                List<String> roles = authorities.stream().map(x -> x.getAuthority()).collect(Collectors.toList());
                Optional<Object> principal = Optional.of(authentication.getPrincipal());
                audit.setUser_roles(roles);
                if (principal.get() instanceof String)
                    audit.setUsername((String) principal.get());
                else audit.setUsername(((Principal) principal.get()).getName());
            }
            return audit;
        }
        catch (Exception ex){
            logger.error("Error audit", ex);
            return audit;
        }
    }

    abstract Audit createAudit(Object obj);

    abstract AuditType getAuditType();
}
