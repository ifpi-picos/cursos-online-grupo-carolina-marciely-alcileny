package br.edu.ifpi.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.entidades.Curso;
import br.edu.ifpi.entidades.CursoAluno;
import br.edu.ifpi.entidades.Nota;
import br.edu.ifpi.enums.StatusCurso;

public class CursoDao implements Dao<Curso> {

    @Override
    public void cadastrar(Curso curso) {
        String query = "INSERT INTO curso (nome, carga_horaria, status) VALUES (?,?,?)";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setString(1, curso.getNome());
            stm.setString(2, curso.getCargaHoraria());
            stm.setString(3, curso.getStatus().toString());
            stm.executeUpdate();

            System.out.println("Curso cadastrado com sucesso!");
           
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao cadastrar este curso");
           
        }
    }

    @Override
    public void consultar() {
        List<Curso> cursos = new ArrayList<>();
        cursos = carregarDados();
        if (!cursos.isEmpty()) {
            System.out.println("|==========LISTA DE CURSOS DISPONIVEIS==========");
            for (int i = 0; i < cursos.size(); i++) {

                System.out.println("| ID -> " + cursos.get(i).getId());
                System.out.println("| Nome: " + cursos.get(i).getNome());
                System.out.println("| Carga Horaria: " + cursos.get(i).getCargaHoraria());
                System.out.println("| Status: " + cursos.get(i).getStatus());
                System.out.println("|-----------------------------------------------");
            }
            System.out.println("|===============================================");
        } else {
            System.out.println("Nenhum curso cadastrado foi encontrado!");
           
        
        }
    }

    @Override
    public List<Curso> carregarDados() {
        List<Curso> cursos = new ArrayList<>();

        String query = "SELECT * FROM curso ORDER BY id";

        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String cargaHoraria = resultSet.getString("carga_horaria");
                StatusCurso status = StatusCurso.valueOf(resultSet.getString("status"));
                Curso curso = new Curso(id, nome, cargaHoraria, status);
                cursos.add(curso);
            }
            return cursos;
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao carregar dados dos cursos");
           
            return null;
        }
    }

    @Override
    public void remover(Curso curso) {
        String query = "DELETE FROM curso WHERE ? = id";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setInt(1, curso.getId());
            stm.executeUpdate();

            System.out.println("Curso removido com sucesso!");
           
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao temover este curso");
           
        }
    }

    @Override
    public void alterar(Curso curso) {
        String query = "UPDATE curso SET nome = ?, carga_horaria = ?, status = ? WHERE id = ?";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setString(1, curso.getNome());
            stm.setString(2, curso.getCargaHoraria());
            stm.setString(3, curso.getStatus().toString());
            stm.setInt(4, curso.getId());
            stm.executeUpdate();
            System.out.println("Curso atualizado com sucesso!");
           
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao atualizar este curso");
           
        }
    }

    public void gerarEstatisticasDesempenho() {
        List<CursoAluno> matriculas = new ArrayList<>();
        CursoAlunoDao matriculaAlunoDao = new CursoAlunoDao();
        matriculas = matriculaAlunoDao.carregarDados();
        List<Curso> cursos = new ArrayList<>();
        cursos = carregarDados();
        List<Nota> notas = new ArrayList<>();
        NotaDao notaDao = new NotaDao();
        notas = notaDao.carregarDados();

        System.out.println("|=====ESTATISTICAS DE DESEMPENHO DOS ALUNOS=====|");
        for (int i = 0; i < cursos.size(); i++) {
            System.out.println("| Nome do Curso: " + cursos.get(i).getNome());

            int totalNotas = 0;
            double somaNota = 0;
            double mediaNotas = 0;
            int totalMatriculados = 0;
            int notaAprovado = 0;
            int notaReprovado = 0;
            double porcentagemAprovado = 0;
            double porcentagemReprovado = 0;

            for (CursoAluno matricula : matriculas) {
                if (matricula.getIdCurso() == cursos.get(i).getId()) {
                    totalMatriculados++;
                }
            }

            for (Nota nota : notas) {
                if (nota.getIdCurso() == cursos.get(i).getId()) {
                    totalNotas++;
                    somaNota += nota.getNota();

                    if (nota.getNota() >= 7) {
                        notaAprovado++;
                    } else {
                        notaReprovado++;
                    }
                }
            }

                mediaNotas = totalNotas > 0 ? somaNota / totalNotas : 0;
                porcentagemAprovado = totalNotas > 0 ? (double) notaAprovado / totalNotas * 100 : 0;
                porcentagemReprovado = totalNotas > 0 ? (double) notaReprovado / totalNotas * 100 : 0;
    
                System.out.println("| Quantidade de alunos matriculados: " + totalMatriculados);
                System.out.println("| Nota media dos alunos: " + String.format("%.2f", mediaNotas));
                System.out.println("| Porcentagem dos alunos aprovados: " + porcentagemAprovado + "%");
                System.out.println("| Porcentagem dos alunos reprovados: " + porcentagemReprovado + "%");
                System.out.println("|-----------------------------------------------|");
            }
            System.out.println("|===============================================|");
           
    }

    public void listarCursos(StatusCurso statusCurso) {
        List<Curso> cursos = new ArrayList<>();
        cursos = carregarDados();
        if (!cursos.isEmpty()) {
            System.out.println("|================LISTA DE CURSOS================");
            for (int i = 0; i < cursos.size(); i++) {
                if (statusCurso != null) {
                    if (cursos.get(i).getStatus().equals(statusCurso)) {
                        // Exibe a lista filtrada do curso de acordo com os status
                        System.out.println("| ID -> " + cursos.get(i).getId());
                        System.out.println("| Nome: " + cursos.get(i).getNome());
                        System.out.println("| Carga Horaria: " + cursos.get(i).getCargaHoraria());
                        System.out.println("| Status: " + cursos.get(i).getStatus());
                        System.out.println("|-----------------------------------------------");
                    }
                } else {
                    // Exibe a lista completa de cursos
                    System.out.println("| ID -> " + cursos.get(i).getId());
                    System.out.println("| Nome: " + cursos.get(i).getNome());
                    System.out.println("| Carga Horaria: " + cursos.get(i).getCargaHoraria());
                    System.out.println("| Status: " + cursos.get(i).getStatus());
                    System.out.println("|-----------------------------------------------");
                }

            }
            System.out.println("|===============================================");
           
        } else {
            System.out.println("Nenhum curso cadastrado foi encontrado");
           
        }
    }

    public void registrarNotas(int idAluno, int idCurso, double notaDoAluno) {
        NotaDao notaDao = new NotaDao();
        Nota nota = new Nota(null, null, idAluno, idCurso, notaDoAluno);
        notaDao.cadastrar(nota);
    }
}
