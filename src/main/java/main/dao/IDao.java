package main.dao;

import java.io.IOException;

public interface IDao<T> {

    void save(T genericObject) throws IOException;

    T searchById(String objectId) throws IOException;

    void deleteObjectById(String objectId) throws IOException;
}
