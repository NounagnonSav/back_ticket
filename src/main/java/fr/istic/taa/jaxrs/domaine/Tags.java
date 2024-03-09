package fr.istic.taa.jaxrs.domaine;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

import java.sql.Date;
import java.util.List;

public class Tags {
    private Long id;
    private String name;
    private String description;
    private Utilisateur created_by;
    private List<Tickets> tickets;
    private Date created_at;

    public Tags(){

    }

    public Tags(String name, String description, Utilisateur created_by) {
        this.name = name;
        this.description = description;
        this.created_by = created_by;
    }

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    @XmlElement(name = "created_by")
    public Utilisateur getCreated_by() {
        return created_by;
    }

    @XmlElementWrapper(name = "tickets")
    @XmlElement(name = "ticket")
    public List<Tickets> getTickets() {
        return tickets;
    }

    @XmlElement(name = "created_at")
    public Date getCreated_at() {
        return created_at;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreated_by(Utilisateur created_by) {
        this.created_by = created_by;
    }

    public void setTickets(List<Tickets> tickets) {
        this.tickets = tickets;
    }
}
