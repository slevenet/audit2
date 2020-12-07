package com.is.audit.model;

public class RestAudit extends Audit{

/*    События аудита веб-запросов
    audit_rest.action - 'HTTP метод'
    audit_rest.url - 'url запроса'
    audit_rest.body - 'Тело запроса'
    audit_rest.ip - 'Ip адрес пользователя в запросе'*/

    private String url;
    private String body;
    private String ip;
    private String javaMethod;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public String getJavaMethod() {
        return javaMethod;
    }

    public void setJavaMethod(String javaMethod) {
        this.javaMethod = javaMethod;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
