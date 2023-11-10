package br.edu.ifpi.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.entidades.CursoAluno;
import br.edu.ifpi.entidades.Nota;

public class NotaDao implements Dao<Nota> {
   
    @Override
    public void cadastrar(Nota nota) {
        List<CursoAluno> matriculas = new ArrayList<>();
        CursoAlunoDao matriculaAlunoDao = new CursoAlunoDao();
        matriculas = matriculaAlunoDao.carregarDados();
        
        boolean isCadastroEncontrado = false;
        for (int i = 0; i < matriculas.size(); i++) {
            if (matriculas.get(i).getIdAluno() == nota.getIdAluno()
                    && matriculas.get(i).getIdCurso() == nota.getIdCurso()) {
                isCadastroEncontrado = true;
            }
        }
        if (isCadastroEncontrado) {
            String query = "INSERT INTO nota (nota, aluno_id, curso_id) VALUES (?,?,?)";
            try (PreparedStatement stm = ConexaoDao.getConexao()
                    .prepareStatement(query)) {
                stm.setDouble(1, nota.getNota());
                stm.setInt(2, nota.getIdAluno());
                stm.setInt(3, nota.getIdCurso());
                stm.executeUpdate();

                System.out.println("Nota cadastrado com sucesso!");
               
            } catch (SQLException e) {
                System.out.println("Ocorreu um erro ao cadastrar este nota");
               
            }
        } else {
            System.out.println("Erro os registrar nota, o aluno nao esta cadastrado neste curso");
           
        }

    }

    @Override
    public void consultar() {
        List<Nota> notas = carregarDados();
        if (!notas.isEmpty()) {
            System.out.println("|=============LISTA DAS NOTAS=================");
            for (int i = 0; i < notas.size(); i++) {

                System.out.println("| Nota: " + notas.get(i).getNota());
                System.out.println("| Aluno: " + notas.get(i).getIdAluno());
                System.out.println("| Curso: " + notas.get(i).getIdCurso());
                System.out.println("|-----------------------------------------------");
            }
            System.out.println("|===============================================");
           
        } else {
            System.out.println("Nenhuma nota foi encontrada");
           
        }
    }

    @Override
    public void remover(Nota nota) {
    }

    @Override
    public void alterar(Nota nota) {
    }

    @Override
    public List<Nota> carregarDados() {
        List<Nota> notas = new ArrayList<>();
        String query = "SELECT no.nota, curs.id AS id_curso, al.id AS id_aluno, curs.nome AS nome_curso, al.nome AS nome_aluno "+
                "FROM nota no " + 
                "INNER JOIN curso curs ON curs.id = no.curso_id " +
                "INNER JOIN aluno al ON al.id = no.aluno_id";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {

                String nomeAluno = resultSet.getString("nome_aluno");
                String nomeCurso = resultSet.getString("nome_curso");
                int idAluno = resultSet.getInt("id_aluno");
                int idCurso = resultSet.getInt("id_curso");
                double notaAluno = resultSet.getInt("nota");

                Nota nota = new Nota(nomeAluno, nomeCurso, idAluno, idCurso, notaAluno);
                notas.add(nota);
            }
            return notas;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao carregar dados das notas!");
           
            return null;
        }
    }
}
