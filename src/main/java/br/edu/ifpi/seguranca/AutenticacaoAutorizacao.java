package br.edu.ifpi.seguranca;

import br.edu.ifpi.Dao.UsuarioDao;
import br.edu.ifpi.entidades.Usuario;
import br.edu.ifpi.enums.PapeisUsuario;

public class AutenticacaoAutorizacao {

    public void gerenciarPermissoes(int idUsuario, PapeisUsuario papeisUsuario) {
        UsuarioDao usuarioDao = new UsuarioDao();
        Usuario usuario = new Usuario(idUsuario, papeisUsuario);
        usuarioDao.alterarTipo(usuario);
    }

    public Usuario autenticarUsuario(String emailUsuario, String senhaUsuario) {
        UsuarioDao usuarioDao = new UsuarioDao();
        Usuario usuario = new Usuario(0, null, emailUsuario, null,senhaUsuario);

        return usuarioDao.authenticar(usuario);
        
    }
}
