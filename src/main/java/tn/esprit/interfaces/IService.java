package tn.esprit.siyahidesktop.interfaces;

import tn.esprit.siyahidesktop.models.User;

import java.util.List;

public interface IService <T>{
    //CRUD
    void add(T t);
    void update(T t);
    void delete(T t);
    List<T> getAll();
    T getOneByID(int id);
    T getOneByEMAIL(String email);
}
