package ma.fstt.atelier_2.dao;
import java.util.List;
public interface GenericDAO<T> {
    void save(T entity);
    T findById(Long id);
    List<T> findAll();
    void update(T entity);
    void delete(T entity);
}