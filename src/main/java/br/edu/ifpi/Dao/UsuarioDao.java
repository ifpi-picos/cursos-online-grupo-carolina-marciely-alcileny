package br.edu.ifpi.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.entidades.Usuario;
import br.edu.ifpi.enums.PapeisUsuario;
import br.edu.ifpi.utilidades.Mensagem;

public class UsuarioDao implements Dao<Usuario> {
    private PapeisUsuario papel;
    private Mensagem mensagem = new Mensagem();

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
            mensagem.imprimirErroAoCarregarDados("usuario");
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

            mensagem.imprimirMenssagemDeCadastro(mensagem.SUCESSO, "usuario");
        } catch (SQLException e) {
            mensagem.imprimirMenssagemDeCadastro(mensagem.ERRO, "usuario");
        } catch (Exception e) {
            mensagem.imprimirMenssagemDeCadastro(mensagem.ERRO, "usuario");
        }
    }

    @Override
    public void consultar() {
        Mensagem.limparConsole();
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
            mensagem.imprimirMensagemNenhumDado("usuario");
        }
    }

    @Override
    public void remover(Usuario usuario) {
        String query = "DELETE FROM usuario WHERE ? = id";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setInt(1, usuario.getId());
            stm.executeUpdate();
            mensagem.imprimirMenssagemDeExclusao(mensagem.SUCESSO, "usuario");
        } catch (SQLException e) {
            mensagem.imprimirMenssagemDeExclusao(mensagem.ERRO, "usuario");
        } catch (Exception e) {
            mensagem.imprimirMenssagemDeExclusao(mensagem.ERRO, "usuario");
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
            //stm.setString(4, usuario.getPapel().toString());
            stm.setInt(4, usuario.getId());
            stm.executeUpdate();
            mensagem.imprimirMenssagemDeAtualizacao(mensagem.SUCESSO, "usuario");
        } catch (SQLException e) {
            e.printStackTrace();
            mensagem.imprimirMenssagemDeAtualizacao(mensagem.ERRO, "usuario");
        } catch (Exception e) {
            e.printStackTrace();
            mensagem.imprimirMenssagemDeAtualizacao(mensagem.ERRO, "usuario");
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
            mensagem.imprimirMenssagemDeAtualizacao(mensagem.SUCESSO, "Permissao");
        } catch (SQLException e) {
            mensagem.imprimirMenssagemDeAtualizacao(mensagem.ERRO, "Permissao");
        } catch (Exception e) {
            mensagem.imprimirMenssagemDeAtualizacao(mensagem.ERRO, "Permissao");
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
            mensagem.imprimirErroAoCarregarDados("usuario");
        }
        return null;
    }
}
