package tn.esprit.interfaces;

import java.util.List;

public interface CRService<T>
{
    // Create
    void Add(T t);

    // Update
    void Update(T t);

    // Delete
    void Delete(T t);

    // Read

    List<T> getAll();
    T getOne(int id);

    //recherche by creteria

}

