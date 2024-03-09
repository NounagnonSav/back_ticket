package fr.istic.taa.jaxrs.dao;


import fr.istic.taa.jaxrs.domaine.Tickets;

public class TicketsDao extends AbstractJpaDao<Long, Tickets> {
    public TicketsDao(){
        this.setClazz(Tickets.class);
    }
}

