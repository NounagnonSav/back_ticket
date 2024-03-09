package fr.istic.taa.jaxrs.dao;


import fr.istic.taa.jaxrs.domaine.Tags;

public class TagsDao extends AbstractJpaDao<Long, Tags> {
    public TagsDao(){
        this.setClazz(Tags.class);
    }
}

