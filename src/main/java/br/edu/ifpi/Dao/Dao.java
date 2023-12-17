package br.edu.ifpi.Dao;

import java.util.List;

public interface Dao<T> {
    public void cadastrar(T entidade);
    
    public void consultar();

    public void remover(T entidade); 

    public void alterar(T entidade);

    public List<T> carregarDados();
    
}
