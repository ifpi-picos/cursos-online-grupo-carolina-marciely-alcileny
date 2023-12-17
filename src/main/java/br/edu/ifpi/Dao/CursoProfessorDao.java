package br.edu.ifpi.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.entidades.CursoAluno;
import br.edu.ifpi.entidades.CursoProfessor;
import br.edu.ifpi.utilidades.Mensagem;

public class CursoProfessorDao implements Dao<CursoProfessor> {

    private Mensagem mensagem = new Mensagem();

    @Override
    public void cadastrar(CursoProfessor associacaoProfessor) {

        List<CursoProfessor> cursosProfessores = new ArrayList<>();
        CursoProfessorDao cursoAlunoDao = new CursoProfessorDao();
        cursosProfessores = cursoAlunoDao.carregarDados();

        boolean isAssociado = false;

        for (CursoProfessor cursoProfessor : cursosProfessores) {
            if (cursoProfessor.getIdProfessor() == associacaoProfessor.getIdProfessor() && cursoProfessor.getIdCurso() == associacaoProfessor.getIdCurso()) {
                isAssociado = true;
            }
        }

        if (!isAssociado) {
            String query = "INSERT INTO curso_e_professor (curso_id , professor_id) VALUES (?,?)";
            try (PreparedStatement stm = ConexaoDao.getConexao()
                    .prepareStatement(query)) {
                stm.setInt(1, associacaoProfessor.getIdCurso());
                stm.setInt(2, associacaoProfessor.getIdProfessor());
                int linhasAfetadas = stm.executeUpdate();

                mensagem.imprimirMenssagemDeAssociacao(mensagem.SUCESSO, "curso");
            } catch (SQLException e) {
                e.printStackTrace();
                mensagem.imprimirMenssagemDeAssociacao(mensagem.ERRO, "curso");
            } catch (Exception e) {
                e.printStackTrace();
                mensagem.imprimirMenssagemDeAssociacao(mensagem.ERRO, "curso");
            }

        } else {
            System.out.println("Este professor ja esta associado");
            Mensagem.pausar();
        }
    }

    @Override
    public void consultar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultar'");
    }

    @Override
    public void remover(CursoProfessor associacaoProfessor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remover'");
    }

    @Override
    public void alterar(CursoProfessor associacaoProfessor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alterar'");
    }

    @Override
    public List<CursoProfessor> carregarDados() {
        List<CursoProfessor> associados = new ArrayList<>();
        String query = "SELECT curs.id AS id_curso, pr.id AS id_professor, curs.nome AS nome_curso, pr.nome AS nome_professor "
                +
                "FROM curso_e_professor cp " +
                "INNER JOIN curso curs ON curs.id = cp.curso_id " +
                "INNER JOIN professor pr ON pr.id = cp.professor_id";

        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {

                int idCurso = resultSet.getInt("id_curso");
                int idProfessor = resultSet.getInt("id_professor");
                String nomeCurso = resultSet.getString("nome_curso");
                String nomeProfessor = resultSet.getString("nome_professor");

                CursoProfessor associado = new CursoProfessor(idCurso, idProfessor, nomeCurso, nomeProfessor);
                associados.add(associado);
            }
        } catch (SQLException e) {
            mensagem.imprimirErroAoCarregarDados("matricula");
        } catch (Exception e) {
            mensagem.imprimirErroAoCarregarDados("matricula");
        }
        return associados;
    }
}
