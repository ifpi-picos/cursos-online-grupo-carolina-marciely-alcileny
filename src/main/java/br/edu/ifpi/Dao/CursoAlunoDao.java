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
import br.edu.ifpi.utilidades.Mensagem;

public class CursoAlunoDao implements Dao<CursoAluno> {
    Mensagem mensagem = new Mensagem();

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
            if (matricula.getIdAluno() == cursoAluno.getIdAluno()) {
                isMatriculado = true;
            }
        }

        if (isAberto) {
            if (!isMatriculado) {
                String query = "INSERT INTO curso_e_aluno (curso_id, aluno_id, status) VALUES (?,?,?)";
                try (PreparedStatement stm = ConexaoDao.getConexao()
                        .prepareStatement(query)) {
                    stm.setInt(1, matricula.getIdCurso());
                    stm.setInt(2, matricula.getIdAluno());
                    stm.setString(3, StatusMatricula.CURSANDO.toString());
                    stm.executeUpdate();

                    mensagem.imprimirMenssagemDeCadastro(mensagem.SUCESSO, "matricula");
                } catch (SQLException e) {
                    mensagem.imprimirMenssagemDeCadastro(mensagem.ERRO, "matricula");
                } catch (Exception e) {
                    mensagem.imprimirMenssagemDeCadastro(mensagem.ERRO, "matricula");
                }
            } else {
                System.out.println("Este aluno ja esta matriculado");
                Mensagem.pausar();
            }
        } else {
            System.out.println("Erro ao matricular aluno, o curso esta (fechado) ou nao existe");
            Mensagem.pausar();
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
            mensagem.imprimirMenssagemDeExclusao(mensagem.SUCESSO, "aluno");
        } catch (SQLException e) {
            mensagem.imprimirMenssagemDeExclusao(mensagem.ERRO, "aluno");
        } catch (Exception e) {
            mensagem.imprimirMenssagemDeExclusao(mensagem.ERRO, "aluno");
        }
    }

    @Override
    public void alterar(CursoAluno matricula) {
        
        String query = "UPDATE curso_e_aluno SET aluno_id = ?, curso_id = ?, status = ? WHERE aluno_id = ? AND curso_id = ?";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setInt(1, matricula.getIdAluno());
            stm.setInt(2, matricula.getIdCurso());
            stm.setString(3, matricula.getStatus().toString());
            stm.setInt(4, matricula.getIdAluno());
            stm.setInt(5, matricula.getIdCurso());
            stm.executeUpdate();
            mensagem.imprimirMenssagemDeAtualizacao(mensagem.SUCESSO, "matricula");
        } catch (SQLException e) {
            e.printStackTrace();
            mensagem.imprimirMenssagemDeAtualizacao(mensagem.ERRO, "matricula");
        } catch (Exception e) {
            e.printStackTrace();
            mensagem.imprimirMenssagemDeAtualizacao(mensagem.ERRO, "matricula");
        }
    }

    @Override
    public List<CursoAluno> carregarDados() {
        List<CursoAluno> matriculas = new ArrayList<>();

        String query = "SELECT ca.status, curs.id AS id_curso, al.id AS id_aluno, curs.nome AS nome_curso, al.nome AS nome_aluno "
                +
                "FROM curso_e_aluno ca " +
                "INNER JOIN curso curs ON curs.id = ca.curso_id " +
                "INNER JOIN aluno al ON al.id = ca.aluno_id";

        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {

                StatusMatricula status = StatusMatricula.valueOf(resultSet.getString("status"));
                int idCurso = resultSet.getInt("id_curso");
                int idAluno = resultSet.getInt("id_aluno");
                String nomeCurso = resultSet.getString("nome_curso");
                String nomeAluno = resultSet.getString("nome_aluno");

                CursoAluno matricula = new CursoAluno(idCurso, idAluno, nomeAluno, nomeCurso, status);
                matriculas.add(matricula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mensagem.imprimirErroAoCarregarDados("matricula");
        } catch (Exception e) {
            e.printStackTrace();
            mensagem.imprimirErroAoCarregarDados("matricula");
        }

        return matriculas;

    }

}
