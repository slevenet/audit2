package com.is.audit.services.audit;

import com.is.audit.model.Audit;
import com.is.audit.model.AuditType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AuditService {

    public Audit audit(Object obj){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities =  authentication.getAuthorities();
        List<String> roles = authorities.stream().map(x -> x.getAuthority()).collect(Collectors.toList());
        Optional<Object> principal =  Optional.of(authentication.getPrincipal());
        Audit audit = createAudit(obj);
        audit.setEvent_ts(LocalDateTime.now().toString());
        audit.setUser_roles(roles);
        audit.setUsername(((Principal) principal.get()).getName());
        return audit;
    }

    abstract Audit createAudit(Object obj);

    abstract AuditType getAuditType();
}
