package com.is.audit.model;


public class EntityAudit extends Audit {

    /*    event_ts - 'Дата время события'
    username - 'Кто совершил действие'
    user_role - 'Роль с которой выполнено действие'
    event_action - 'Действие'
    entity - 'Над какой сущьностью'
    ident_field - 'Значение первичного ключа'
    mnemonic_id - 'Наглядный идентификатор'
    event_data - 'Изменные поля'*/

    private String entity;
    private Object ident_field;
    private Object event_data;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Object getIdent_field() {
        return ident_field;
    }

    public void setIdent_field(Object ident_field) {
        this.ident_field = ident_field;
    }

    public Object getEvent_data() {
        return event_data;
    }

    public void setEvent_data(Object event_data) {
        this.event_data = event_data;
    }

}
