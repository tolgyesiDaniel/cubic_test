package hu.cubicfox.booking.service.impl;

import hu.cubicfox.booking.entity.CoreEntity;
import hu.cubicfox.booking.service.CoreCRUDService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class CoreCRUDServiceImpl <T extends CoreEntity> implements CoreCRUDService<T> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void add(T entity){
        entityManager.persist(entity);
    }

    @Override
    public void remove(T entity){
        entityManager.remove(findById(entity.getId()));
    }

    @Override
    public List<T> getAll(){
        return entityManager.createQuery("SELECT n FROM "+ getManagedClass().getSimpleName() +" n", getManagedClass()).getResultList();
    }

    @Override
    public void update(T entity){
        T persistedEntity = findById(entity.getId());
        updateCore(persistedEntity, entity);
        entityManager.merge(persistedEntity);
    }

    @Override
    public T findById(Long id){
        return entityManager.find(getManagedClass(), id);
    }

    @Override
    public List<T> findByName(String name, String fieldName){
        if (name.equals("")){
            return getAll();
        }
        else {
            return entityManager.createQuery("SELECT n FROM " + getManagedClass().getSimpleName() + " n WHERE " + fieldName + " like '" + name + "%'", getManagedClass()).getResultList();
        }
    }

    @Override
    public List<T> findByNumber(Integer name, String fieldName){
        if (name == null){
            return getAll();
        }
        else{
            return entityManager.createQuery("SELECT n FROM " + getManagedClass().getSimpleName() + " n WHERE " + fieldName + "=" + name, getManagedClass()).getResultList();
        }
    }

    protected abstract void updateCore(T persistedEntity, T entity);

    protected abstract Class<T> getManagedClass();

}
