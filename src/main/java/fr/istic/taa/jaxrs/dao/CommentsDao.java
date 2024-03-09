package fr.istic.taa.jaxrs.dao;


import fr.istic.taa.jaxrs.domaine.Comments;

public class CommentsDao extends AbstractJpaDao<Long, Comments> {
    public CommentsDao(){
        this.setClazz(Comments.class);
    }
}

