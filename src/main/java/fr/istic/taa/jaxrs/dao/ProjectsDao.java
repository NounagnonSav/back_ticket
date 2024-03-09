package fr.istic.taa.jaxrs.dao;


import fr.istic.taa.jaxrs.domaine.Projects;

public class ProjectsDao extends AbstractJpaDao<Long, Projects> {
    public ProjectsDao(){
        this.setClazz(Projects.class);
    }
}

