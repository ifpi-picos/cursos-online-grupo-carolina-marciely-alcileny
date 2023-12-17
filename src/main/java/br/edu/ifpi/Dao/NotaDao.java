package br.edu.ifpi.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.entidades.CursoAluno;
import br.edu.ifpi.entidades.Nota;
import br.edu.ifpi.utilidades.Mensagem;

public class NotaDao implements Dao<Nota> {
    Mensagem mensagem = new Mensagem();
   
    @Override
    public void cadastrar(Nota nota) {
        List<CursoAluno> matriculas = new ArrayList<>();
        CursoAlunoDao matriculaAlunoDao = new CursoAlunoDao();
        matriculas = matriculaAlunoDao.carregarDados();
        
        boolean cadastroEncontrado = false;
        for (int i = 0; i < matriculas.size(); i++) {
            if (matriculas.get(i).getIdAluno() == nota.getIdAluno()
                    && matriculas.get(i).getIdCurso() == nota.getIdCurso()) {
                cadastroEncontrado = true;
            }
        }
        if (cadastroEncontrado) {
            String query = "INSERT INTO nota (nota, aluno_id, curso_id) VALUES (?,?,?)";
            try (PreparedStatement stm = ConexaoDao.getConexao()
                    .prepareStatement(query)) {
                stm.setDouble(1, nota.getNota());
                stm.setInt(2, nota.getIdAluno());
                stm.setInt(3, nota.getIdCurso());
                stm.executeUpdate();

                mensagem.imprimirMenssagemDeCadastro(mensagem.SUCESSO, "nota");
            } catch (SQLException e) {
                mensagem.imprimirMenssagemDeCadastro(mensagem.ERRO, "nota");
            } catch (Exception e) {
                mensagem.imprimirMenssagemDeCadastro(mensagem.ERRO, "nota");
            }
        } else {
            System.out.println("Erro os registrar nota, o aluno nao esta cadastrados neste curso");
            Mensagem.pausar();
        }

    }

    @Override
    public void consultar() {
        Mensagem.limparConsole();
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
            mensagem.imprimirMensagemNenhumDado("nota");
        }
    }

    @Override
    public void remover(Nota nota) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remover'");
    }

    @Override
    public void alterar(Nota nota) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alterar'");
    }

    @Override
    public List<Nota> carregarDados() {
        List<Nota> notas = new ArrayList<>();
        String query = "SELECT no.nota, curs.id AS id_curso, al.id AS id_aluno, curs.nome AS nome_curso, al.nome AS nome_aluno "
                +
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
            mensagem.imprimirErroAoCarregarDados("nota");
            return null;
        }
    }

    public int getAproveitamento(int idAluno, int idCurso) {
        String query = "SELECT ( no.nota / COUNT(no.nota) * 100) AS porcentagem_nota " +
                "FROM nota no WHERE no.aluno_id = ? AND no.curso_id = ? GROUP BY no.nota";
        try (PreparedStatement stm = ConexaoDao.getConexao()
                .prepareStatement(query)) {
            stm.setInt(1, idAluno);
            stm.setInt(2, idCurso);
            ResultSet resultSet = stm.executeQuery();
            int porcentagem = 0;
            while (resultSet.next()) {
                porcentagem = resultSet.getInt("porcentagem_nota");
            }
            return porcentagem;
        } catch (SQLException e) {
            e.printStackTrace();
            mensagem.imprimirErroAoCarregarDados("aproveitamento");
            return 0;
        }
    }

}
