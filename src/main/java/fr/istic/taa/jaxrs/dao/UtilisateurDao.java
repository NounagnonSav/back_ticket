package fr.istic.taa.jaxrs.dao;


import fr.istic.taa.jaxrs.domaine.Utilisateur;

public class UtilisateurDao extends AbstractJpaDao<Long, Utilisateur> {
    public UtilisateurDao(){
        this.setClazz(Utilisateur.class);
    }
}

