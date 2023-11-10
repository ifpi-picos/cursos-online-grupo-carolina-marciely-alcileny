package br.edu.ifpi.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.entidades.Usuario;
import br.edu.ifpi.enums.PapeisUsuario;

public class UsuarioDao implements Dao<Usuario> {
    private PapeisUsuario papel;

    public UsuarioDao(PapeisUsuario papel) {
        this.papel = papel;
    }

    public UsuarioDao() {
    }

    @Override
    public List<Usuario> carregarDados() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuario";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                PapeisUsuario tipo = PapeisUsuario.valueOf(resultSet.getString("tipo"));
                Usuario usuario = new Usuario(id, nome, email, tipo, null);
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao carregar dados dos usuarios");
           
            return null;
        }
    }

    @Override
    public void cadastrar(Usuario usuario) {

        String query = "INSERT INTO usuario (nome, email, senha, tipo) VALUES (?,?,?,?)";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setString(1, usuario.getNome());
            stm.setString(2, usuario.getEmail());
            stm.setString(3, usuario.getSenha());
            stm.setString(4, usuario.getPapel().toString());
            stm.executeUpdate();

            System.out.println("Usuario cadastrado com sucesso!");
           
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao cadastrar este usuario");
           
        }
    }

    @Override
    public void consultar() {
        List<Usuario> usuarios = carregarDados();
        if (!usuarios.isEmpty()) {
            System.out.println("|=============LISTA DE USUARIOS=================");
            for (int i = 0; i < usuarios.size(); i++) {

                System.out.println("| ID -> " + usuarios.get(i).getId());
                System.out.println("| Nome " + usuarios.get(i).getNome());
                System.out.println("| Email " + usuarios.get(i).getEmail());
                System.out.println("| Tipo " + usuarios.get(i).getPapel());
                System.out.println("|-----------------------------------------------");
            }
            System.out.println("|===============================================");
           
        } else {
            System.out.println("Nenhum usuario cadastrado encontrado");
           
        }
    }

    @Override
    public void remover(Usuario usuario) {
        String query = "DELETE FROM usuario WHERE ? = id";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setInt(1, usuario.getId());
            stm.executeUpdate();

            System.out.println("Usuario removido com sucesso!");
           
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao remover este usuario");
           
        }
    }

    @Override
    public void alterar(Usuario usuario) {
        String query = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setString(1, usuario.getNome());
            stm.setString(2, usuario.getEmail());
            stm.setString(3, usuario.getSenha());
            stm.setInt(4, usuario.getId());
            stm.executeUpdate();

            System.out.println("Informacoes do usuario atualizado com sucesso!");
           
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao atualizar informacoes do usuario");
           
        }
    }

    public PapeisUsuario getPapel() {
        return papel;
    }

    public void alterarTipo(Usuario usuario) {
        String query = "UPDATE usuario SET tipo = ?  WHERE id = ?";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setString(1, usuario.getPapel().toString());
            stm.setInt(2, usuario.getId());
             stm.executeUpdate();

             System.out.println("Usuario atualizado com sucesso!");
            
            } catch (SQLException e) {
                System.out.println("Ocorreu um erro ao atualizar este usuario");
               
            }
    }

    public Usuario authenticar(Usuario usuario){

        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement("SELECT id, tipo FROM usuario WHERE email = ? AND senha = ?")) {

            stm.setString(1, usuario.getEmail());
            stm.setString(2, usuario.getSenha());
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                PapeisUsuario papel = PapeisUsuario.valueOf(resultSet.getString("tipo"));
                Usuario usuarioAuthenticado = new Usuario(id,papel);
                return usuarioAuthenticado;
            }
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao authenticar este usuario");
           
        }
        return null;
    }
}
