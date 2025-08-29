package infra.dao;

import java.util.*;

public interface DAO<T, K> {
    K insert(T t);
    Optional<T> findById(K id);
    List<T> findAll();
    boolean update(T t);
    boolean deleteById(K id);
}
