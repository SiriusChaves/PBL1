package main.dao;

public interface IDao<G> {
    public void save(G genericObject);

    public G searchById(String idObject);

    public void deleteObjectById(String idObject);
}
