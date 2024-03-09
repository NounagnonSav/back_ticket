package fr.istic.taa.jaxrs.domaine;

import fr.istic.taa.jaxrs.utils.Role;

import java.util.List;

public class Utilisateur   {
    private Long id;
    private String username;
    private String name;
    private Role role;
    private List<Comments> comments;
    private List<Tickets> tickets;

    public Utilisateur(){

    }

    public Utilisateur(String username, String name, Role role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public List<Tickets> getTickets() {
        return tickets;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTickets(List<Tickets> tickets) {
        this.tickets = tickets;
    }

    public Role getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
