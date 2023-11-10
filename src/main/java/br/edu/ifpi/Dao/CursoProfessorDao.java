package br.edu.ifpi.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.entidades.CursoProfessor;

public class CursoProfessorDao implements Dao<CursoProfessor> {

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
                stm.executeUpdate();

                System.out.println("Professor associado ao curso com sucesso!");
               
            } catch (SQLException e) {
                System.out.println("Ocorreu um erro ao associar professor e este curso");
               
            }

        } else {
            System.out.println("Este professor ja esta associado neste curso");
           
        }
    }

    @Override
    public void consultar() {
    }

    @Override
    public void remover(CursoProfessor associacaoProfessor) {
    }

    @Override
    public void alterar(CursoProfessor associacaoProfessor) {
        
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
            System.out.println("Ocorreu um erro ao carregar dados dos cursos e professores associados");
           
        }
        return associados;
    }
}
