package fr.istic.taa.jaxrs.domaine;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "Projects")
public class Projects {
    private Long id;
    private String name;

    private List<Tickets> ticketsList;

    public Projects() {

    }
    public Projects(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    @XmlElementWrapper(name = "tickets")
    @XmlElement(name = "ticket")
    public List<Tickets> getTicketsList() {
        return ticketsList;
    }

    public void setTicketsList(List<Tickets> ticketsList) {
        this.ticketsList = ticketsList;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
