package br.edu.ifpi.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.edu.ifpi.entidades.Aluno;
import br.edu.ifpi.entidades.CursoAluno;
import br.edu.ifpi.enums.StatusMatricula;

public class AlunoDao implements Dao<Aluno> {

    @Override
    public void cadastrar(Aluno aluno) {

        String query = "INSERT INTO aluno (nome, email) VALUES (?,?)";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setString(1, aluno.getNome());
            stm.setString(2, aluno.getEmail());
            stm.executeUpdate();

            System.out.println("Aluno cadastrado com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao cadastrar este aluno");
            
        }
    }

    @Override
    public void consultar() {
        List<Aluno> alunos = new ArrayList<>();
        alunos = carregarDados();
        if (!alunos.isEmpty()) {
            System.out.println("|=============PERFIL DE ALUNOS=================");
            for (int i = 0; i < alunos.size(); i++) {

                System.out.println("| ID -> " + alunos.get(i).getId());
                System.out.println("| Nome: " + alunos.get(i).getNome());
                System.out.println("| Email: " + alunos.get(i).getEmail());

                System.out.println("|==============================================");
            }
            
        } else {
            System.out.println("Nenhum aluno cadastrado foi encontrado!");
            

        }
    }

    @Override
    public void remover(Aluno aluno) {
        String query = "DELETE FROM aluno WHERE ? = id";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setInt(1, aluno.getId());
            stm.executeUpdate();
            System.out.println("Aluno removido com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao remover este aluno");
            
        }
    }

    @Override
    public void alterar(Aluno aluno) {
        String query = "UPDATE aluno SET nome = ?, email = ? WHERE id = ?";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setString(1, aluno.getNome());
            stm.setString(2, aluno.getEmail());
            stm.setInt(3, aluno.getId());
            stm.executeUpdate();

            System.out.println("Aluno atualizado com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao atualizar informacoes deste aluno");
            
        }
    }

    @Override
    public List<Aluno> carregarDados() {
        List<Aluno> alunos = new ArrayList<>();

        String query = "SELECT * FROM aluno";

        try (PreparedStatement stm = ConexaoDao.getConexao().prepareStatement(query)) {
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");

                Aluno aluno = new Aluno(id, nome, email);
                alunos.add(aluno);
            }
            return alunos;
        } catch (SQLException e) {
            System.out.println("Erro ao carregar dados dos alunos");
            
            return null;
        }
    }

    public Aluno carregarDadosPorId(int id) {
        Aluno aluno = null;
        String query = "SELECT * FROM aluno WHERE id = ?";

        try (PreparedStatement stm = ConexaoDao.getConexao().prepareStatement(query)) {
            stm.setInt(1, id);
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");

                aluno = new Aluno(id, nome, email);
            }
            return aluno;
        } catch (SQLException e) {
            System.out.println("Erro ao carregar dados do aluno!");
            
            return null;
        }
    }

    public void visualizarPerfil(int idAluno) {

        List<CursoAluno> matriculas = new ArrayList<>();
        CursoAlunoDao matriculaAlunoDao = new CursoAlunoDao();
        matriculas = matriculaAlunoDao.carregarDados();
        AlunoDao alunodDao = new AlunoDao();
        Aluno aluno = alunodDao.carregarDadosPorId(idAluno);

        System.out.println("|===============PERFIL DO ALUNO=================");
        System.out.println("| ID -> " + aluno.getId());
        System.out.println("| Nome " + aluno.getNome());
        System.out.println("| Email " + aluno.getEmail());
        System.out.println("|-------------Matriculado nos cursos------------");
        for (CursoAluno matricula : matriculas) {
            if (matricula.getIdAluno() == aluno.getId()) {
                System.out.println("| " + matricula.getStatus().toString() + ": " + matricula.getNomeCurso());
            }
        }
        System.out.println("|===============================================");
        
    }

    public void gerarRelatorioDesempenho() {
        List<Aluno> alunos = new ArrayList<>();
        alunos = carregarDados();
        CursoAlunoDao matriculaAlunoDao = new CursoAlunoDao();
        List<CursoAluno> matriculas = new ArrayList<>();
        matriculas = matriculaAlunoDao.carregarDados();

        if (!alunos.isEmpty()) {
            System.out.println("|======RELATORIO DE DESEMPENHO DE ALUNOS========");
            for (int i = 0; i < alunos.size(); i++) {

                System.out.println("| ID -> " + alunos.get(i).getId());
                System.out.println("| Nome: " + alunos.get(i).getNome());
                System.out.println("| Email: " + alunos.get(i).getEmail());

                System.out.println("|------------Cursos Matriculados----------------");
                for (CursoAluno matricula : matriculas) {
                    if (matricula.getIdAluno() == alunos.get(i).getId()) {
                        System.out.println("| Curso: " + matricula.getNomeCurso() + ", Aproveitamento: "
                                + matricula.getPorcentagemNota() + "%");
                    }
                }
                System.out.println("|-----------------------------------------------");

                System.out.println("|------------Cursos Concluidos------------------");

                for (CursoAluno matricula : matriculas) {
                    if (matricula.getIdAluno() == alunos.get(i).getId()
                            && matricula.getStatus() == StatusMatricula.CONCLUIDO) {
                        System.out.println("| Curso: " + matricula.getNomeCurso() + ", Aproveitamento: "
                                + matricula.getPorcentagemNota() + "%");
                    }
                }
                System.out.println("|-----------------------------------------------");
                System.out.println("|");
                System.out.println("|===============================================");
            }
            
        } else {
            System.out.println("Nenhum aluno cadastrado foi encontrado!");
            
        }
    }

    public void realizarMatricula(int idCurso, int idAluno) {
        CursoAlunoDao matriculaDao = new CursoAlunoDao();
        CursoAluno matricula = new CursoAluno(idCurso, idAluno, null, null, StatusMatricula.CURSANDO, 0);
        matriculaDao.cadastrar(matricula);
    }

    public void cancelarMatricula(int idCurso, int idAluno) {
        CursoAlunoDao matriculaDao = new CursoAlunoDao();
        CursoAluno matricula = new CursoAluno(idCurso, idAluno, null, null, StatusMatricula.CANCELADO, 0);
        matriculaDao.alterar(matricula);
    }
}