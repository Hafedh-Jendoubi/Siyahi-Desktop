package Service;

import java.util.ArrayList;

public interface IService<T> {
    void insert(T o);
    void update(T o);
    void delete(int id);
    T readById(int id);
    ArrayList<T> readAll();
}
