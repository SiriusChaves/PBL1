package main.dao;

public interface IDao<G> {
    /*
    * Dependendo da implementação pode ser interessante um metodo para editar os dados salvos
    * Um método para listar todos os objetos salvos
    * Vai ser muito interessante implementar esse Dao com Generics
    * */

    public void save(G genericObject);

    public G searchById(String idObject);

    public void deleteObjectById(String idObject);
}
