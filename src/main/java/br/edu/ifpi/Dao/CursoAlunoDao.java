package br.edu.ifpi.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.entidades.Curso;
import br.edu.ifpi.entidades.CursoAluno;
import br.edu.ifpi.enums.StatusCurso;
import br.edu.ifpi.enums.StatusMatricula;

public class CursoAlunoDao implements Dao<CursoAluno> {

    @Override
    public void cadastrar(CursoAluno matricula) {

        List<Curso> cursos = new ArrayList<>();
        CursoDao cursoDao = new CursoDao();
        cursos = cursoDao.carregarDados();

        List<CursoAluno> cursosAlunos = new ArrayList<>();
        CursoAlunoDao cursoAlunoDao = new CursoAlunoDao();
        cursosAlunos = cursoAlunoDao.carregarDados();

        boolean isAberto = false;
        boolean isMatriculado = false;
        for (Curso curso : cursos) {
            if (matricula.getIdCurso() == curso.getId() && curso.getStatus() == StatusCurso.ABERTO) {
                isAberto = true;
            }
        }

        for (CursoAluno cursoAluno : cursosAlunos) {
            if (matricula.getIdAluno() == cursoAluno.getIdAluno()
                    && matricula.getIdCurso() == cursoAluno.getIdCurso()) {
                isMatriculado = true;
            }
        }

        if (isAberto && !isMatriculado) {

            String query = "INSERT INTO curso_e_aluno (curso_id, aluno_id, status) VALUES (?,?,?)";
            try (PreparedStatement stm = ConexaoDao.getConexao()
                    .prepareStatement(query)) {
                stm.setInt(1, matricula.getIdCurso());
                stm.setInt(2, matricula.getIdAluno());
                stm.setString(3, StatusMatricula.CURSANDO.toString());
                stm.executeUpdate();

                System.out.println("Aluno matricula realizada com sucesso!");
                
            } catch (SQLException e) {
                System.out.println("Ocorreu um erro ao realizar matricula");
                
            }

        } else {
            System.out.println("Erro ao matricular aluno, o curso esta (fechado), nao existe ou o aluno ja esta matriculado");
            
        }
    }

    @Override
    public void consultar() {
    }

    @Override
    public void remover(CursoAluno matricula) {
        String query = "DELETE FROM curso_e_aluno WHERE curso_id = ? AND aluno_id = ?";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setInt(1, matricula.getIdCurso());
            stm.setInt(2, matricula.getIdAluno());
            stm.executeUpdate();
            System.out.println("Aluno matricula removida com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao remover a matricula");
            
        }
    }

    @Override
    public void alterar(CursoAluno matricula) {

        List<CursoAluno> cursosAlunos = new ArrayList<>();
        CursoAlunoDao cursoAlunoDao = new CursoAlunoDao();
        cursosAlunos = cursoAlunoDao.carregarDados();

        boolean isMatriculado = false;

        for (CursoAluno cursoAluno : cursosAlunos) {
            if (matricula.getIdAluno() == cursoAluno.getIdAluno()) {
                isMatriculado = true;
            }
        }

        if (isMatriculado) {
            String query = "UPDATE curso_e_aluno SET aluno_id = ?, curso_id = ?, status = ? WHERE aluno_id = ? AND curso_id = ?";
            try (PreparedStatement stm = ConexaoDao.getConexao()
                    .prepareStatement(query)) {
                stm.setInt(1, matricula.getIdAluno());
                stm.setInt(2, matricula.getIdCurso());
                stm.setString(3, matricula.getStatus().toString());
                stm.setInt(4, matricula.getIdAluno());
                stm.setInt(5, matricula.getIdCurso());
                stm.executeUpdate();
                System.out.println("Matricula atualizada com sucesso!");
                
            } catch (SQLException e) {
                System.out.println("Ocorreu um erro ao atualizar a matricula");
                
            }
        } else {
            System.out.println("Erro ao atualizar matricula, este aluno nao esta matriculado neste curso!");
            
        }
    }

    @Override
    public List<CursoAluno> carregarDados() {
        List<CursoAluno> matriculas = new ArrayList<>();
        String query = "SELECT ca.status, curs.id AS id_curso, al.id AS id_aluno, curs.nome AS nome_curso, al.nome AS nome_aluno, "
                + " (SUM(no.nota) / COUNT(no.nota) * 10) AS porcentagem_nota " +
                "FROM curso_e_aluno ca " +
                "INNER JOIN curso curs ON curs.id = ca.curso_id " +
                "INNER JOIN aluno al ON al.id = ca.aluno_id " +
                "LEFT JOIN nota no ON no.aluno_id = al.id AND no.curso_id = curs.id " +
                "GROUP BY al.id, curs.id, ca.status;";

        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {

                StatusMatricula status = StatusMatricula.valueOf(resultSet.getString("status"));
                int idCurso = resultSet.getInt("id_curso");
                int idAluno = resultSet.getInt("id_aluno");
                String nomeCurso = resultSet.getString("nome_curso");
                String nomeAluno = resultSet.getString("nome_aluno");
                int porcentagemNota = resultSet.getInt("porcentagem_nota");

                CursoAluno matricula = new CursoAluno(idCurso, idAluno, nomeAluno, nomeCurso, status, porcentagemNota);
                matriculas.add(matricula);
            }
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao carregar dados da matricula");
            
        }
        return matriculas;

    }

}
