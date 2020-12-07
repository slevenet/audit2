package com.is.audit.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.is.audit.model.EntityAudit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

public class LogAuditFileHandler implements LogAuditHandler{

    @Value("${audit.controller.file}")
    private String auditcontroller;
    @Value("${audit.entity.file}")
    private String auditEntity;


    public void logAudit(Object obj){
        String file = auditcontroller;
        if(obj instanceof EntityAudit)
            file = auditEntity;
        try(FileWriter writer = new FileWriter(file, true))
        {
            ObjectMapper objectMapper = new ObjectMapper();

            writer.write(objectMapper.writeValueAsString(obj));
            writer.append('\n');
             writer.flush();
        }
        catch(IOException ex){

        }
    }
}
