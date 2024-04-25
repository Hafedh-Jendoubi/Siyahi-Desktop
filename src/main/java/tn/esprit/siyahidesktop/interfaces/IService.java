package tn.esprit.siyahidesktop.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IService<T>{
    //CRUD
    void add(T t) throws SQLException;
    void update(T t);
    void delete(T t);
    List<T> getAll();
    T getOne(int id);
}
