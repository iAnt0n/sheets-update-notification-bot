package ru.iAnt0n.dao;

import ru.iAnt0n.model.SheetValues;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class SheetsValuesDAOImpl implements SheetsValuesDAO {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("t");

    @Override
    public void addSheet(SheetValues s) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(s);
        try {
            em.getTransaction().commit();
        }
        catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<SheetValues> getValues() {
        EntityManager em = entityManagerFactory.createEntityManager();
        return (List<SheetValues>) em.createQuery("select p from SheetValues p").getResultList();
    }

    @Override
    public void merge(SheetValues s) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(s);
        try {
            em.getTransaction().commit();
        }
        catch (Exception e){
            em.getTransaction().rollback();
        }
    }

}
