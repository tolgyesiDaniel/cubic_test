package hu.cubicfox.booking.service;

import hu.cubicfox.booking.entity.CoreEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CoreCRUDService<T extends CoreEntity> {

    @Transactional
    void add(T entity);

    @Transactional
    void remove(T entity);

    List<T> getAll();

    @Transactional
    void update(T entity);

    T findById(Long id);

    List<T> findByName(String name, String fieldName);

    List<T> findByKey(Long id, String fieldName);

    List<T> findByNumber(Integer name, String fieldName);

}
