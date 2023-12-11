package br.edu.ifpi.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import br.edu.ifpi.conexaoBD.ConexaoBancoDeDados;
import br.edu.ifpi.enums.StatusCurso;

public class Curso extends ConexaoBancoDeDados {

    public void cadastrarCurso() {
        Scanner scanner = new Scanner(System.in);
        int papel = 0;
        limparConsole();
        System.out.println("Informe o nome do curso");
        String nome = scanner.nextLine();
        System.out.println("Informe a carga horaria do curso");
        String carga = scanner.nextLine();
        System.out.println();
        System.out.println("|-------STATUS DO CURSO------|");
        System.out.println("| 1 - Aberto                 |");
        System.out.println("| 2 - Fechado                |");
        System.out.println("|----------------------------|");
        System.out.println("Informe o status do curso");
        papel = scanner.nextInt();
        limparConsole();
        StatusCurso status = StatusCurso.ABERTO;

        if (papel == 2) {
            status = StatusCurso.FECHADO;
        }

        String query = "INSERT INTO curso (nome, carga_horaria, status) VALUES ('" + nome + "','" + carga
                + "', '" + status + "')";

        try {
            Statement stm = connectar().createStatement();
            stm.executeUpdate(query);
            imprimirMenssagemDeCadastro(SUCESSO, "Curso");
        } catch (SQLException e) {
            imprimirMenssagemDeCadastro(ERRO, "Curso");
        }
    }

    public void atualizarCurso() {

        Scanner scanner = new Scanner(System.in);

        limparConsole();
        int papel = 0;
        int idCurso = carregarDadosCurso();
        if (idCurso != 0) {

            System.out.println("Informe o novo nome do curso");
            String nome = scanner.nextLine();
            System.out.println("Informe a nova carga horaria do curso");
            String carga = scanner.nextLine();
            System.out.println();
            limparConsole();
            System.out.println("|-------STATUS DO CURSO------|");
            System.out.println("| 1 - Aberto                 |");
            System.out.println("| 2 - Fechado                |");
            System.out.println("|----------------------------|");
            System.out.println("Informe o status do curso");
            papel = scanner.nextInt();
            StatusCurso status = StatusCurso.ABERTO;
            limparConsole();
            if (papel == 2) {
                status = StatusCurso.FECHADO;
            }

            String query = "UPDATE curso SET nome = '" + nome + "', carga_horaria = '" + carga + "', status = '"
                    + status
                    + "' WHERE id = '" + idCurso + "'";

            try {
                Statement stm = connectar().createStatement();
                stm.executeUpdate(query);
                imprimirMenssagemDeAtualizacao(SUCESSO, "Curso");
            } catch (SQLException e) {
                imprimirMenssagemDeAtualizacao(ERRO, "Curso");
            }

        } else {
            imprimirMensagemNenhumDado("curso");
        }
    }

    public void registrarNotas() {
        Scanner scanner = new Scanner(System.in);
        Aluno aluno = new Aluno();
        Curso curso = new Curso();

        limparConsole();
        int idAluno = aluno.carregarDadosDoAluno();
        if (idAluno != 0) {
            limparConsole();
            int idCurso = curso.carregarDadosCursoMatriculado(idAluno);
            limparConsole();
            if (idCurso != 0) {
                System.out.println("Informe a nota do aluno");
                String nota = scanner.nextLine();
                String query = "INSERT INTO nota (nota, aluno_id, curso_id) VALUES ('" + nota + "','" + idAluno + "','"
                        + idCurso + "')";
                try {
                    Statement stm = connectar().createStatement();
                    stm.executeUpdate(query);
                    imprimirMenssagemDeCadastro(SUCESSO, "Nota");

                } catch (SQLException e) {
                    imprimirMenssagemDeCadastro(ERRO, "Nota");
                }

            } else {
                imprimirMensagemNenhumDado("curso");
            }
        } else {
            imprimirMensagemNenhumDado("aluno");
        }
    }

    public void gerarEstatisticasDesempenho() {
        limparConsole();
        int idCurso = carregarDadosCurso();
        limparConsole();
        if (idCurso != 0) {

            String query = "SELECT curs.nome AS nome_curso," +
                    "(SELECT COUNT(aluno_id) FROM curso_e_aluno WHERE curso_id = curs.id ) AS matriculados, " +
                    "(SELECT AVG(nota) FROM nota WHERE curso_id = curs.id ) AS media, " +
                    "(SELECT (SUM(CASE WHEN nota >= 7 THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) FROM nota WHERE curso_id = curs.id ) AS porcent_aprovados, "
                    +
                    "(SELECT (SUM(CASE WHEN nota < 7 THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) FROM nota WHERE curso_id = curs.id ) AS porcent_reprovados "
                    +
                    "FROM curso curs WHERE curs.id = " + idCurso;
            try {
                Statement stm = connectar().createStatement();
                ResultSet result = stm.executeQuery(query);

                if (result.next()) {
                    int matriculados = result.getInt("matriculados");
                    double media = result.getInt("media");
                    String nome = result.getString("nome_curso");
                    int porcentAprovados = result.getInt("porcent_aprovados");
                    int porcentReprovados = result.getInt("porcent_reprovados");
                    System.out.println("|=====ESTATISTICAS DE DESEMPENHO DOS ALUNOS=====|");
                    System.out.println("| Nome do Curso: " + nome);
                    System.out.println("| Quantidade de alunos matriculados: " + matriculados);
                    System.out.println("| Nota media dos alunos: " + media);
                    System.out.println("| Porcentagem dos alunos aprovados: " + porcentAprovados + "%");
                    System.out.println("| Porcentagem dos alunos reprovados: " + porcentReprovados + "%");
                    System.out.println("|===============================================|");
                    pausar();

                } else {
                    imprimirMensagemNenhumDado("registro");
                }

            } catch (SQLException e) {
                imprimirErroAoCarregarDados("aluno");
            }
        } else {
            imprimirMensagemNenhumDado("curso");
        }
    }

    public void visualizarListaDeCursos() {
        limparConsole();
        String query = "SELECT * FROM curso";

        try {
            Statement stm = connectar().createStatement();
            ResultSet result = stm.executeQuery(query);

            if (result.next()) {
                System.out.println("-------------LISTA DE CURSOS-----------------");
                int id = result.getInt("id");
                String nome = result.getString("nome");
                String carga = result.getString("carga_horaria");
                String status = result.getString("status");

                System.out.println("ID: " + id);
                System.out.println("Nome " + nome);
                System.out.println("Carga Horaria " + carga);
                System.out.println("Status " + status);
                System.out.println();

                while (result.next()) {

                    id = result.getInt("id");
                    nome = result.getString("nome");
                    carga = result.getString("carga_horaria");
                    status = result.getString("status");

                    System.out.println("ID: " + id);
                    System.out.println("Nome " + nome);
                    System.out.println("Carga Horaria " + carga);
                    System.out.println("Status " + status);
                    System.out.println();
                }
                System.out.println("---------------------------------------------");
                pausar();
            } else {
                imprimirMensagemNenhumDado("curso");
            }

        } catch (SQLException e) {
            imprimirErroAoCarregarDados("curso");
        }
    }

    public int carregarDadosCurso() {
        String query = "SELECT c.id, c.nome, c.status " +
                "FROM curso c LEFT JOIN curso_e_aluno ca ON c.id = ca.curso_id " +
                "GROUP BY c.id, c.nome, c.status";

        int idSelecionado = 0;
        try {
            Statement stm = connectar().createStatement();
            ResultSet result = stm.executeQuery(query);

            if (result.isBeforeFirst()) {
                System.out.println("----------------------------------");
                while (result.next()) {

                    int id = result.getInt("id");
                    String nome = result.getString("nome");
                    String status = result.getString("status");
                    System.out.println(" ID -> " + id + " Nome: " + nome + " Status: " + status);

                }
                System.out.println("----------------------------------");

                System.out.println("Selecione o ID do curso!");
                Scanner scanner = new Scanner(System.in);
                idSelecionado = scanner.nextInt();
            }

        } catch (SQLException e) {
            imprimirErroAoCarregarDados("curso");
        }

        return idSelecionado;
    }

    public int carregarDadosCursoMatriculado(int idAluno) {
        int idSelecionado = 0;
        String query = "SELECT c.id, c.nome, c.status, COUNT(ca.aluno_id) as quantidade_alunos " +
                "FROM curso c INNER JOIN curso_e_aluno ca ON c.id = ca.curso_id " +
                "WHERE ca.aluno_id = " + idAluno + " " +
                "GROUP BY c.id, c.nome, c.status";
        try {
            Statement stm = connectar().createStatement();
            ResultSet result = stm.executeQuery(query);

            if (result.isBeforeFirst()) {
                System.out.println("----------------------------------");
                while (result.next()) {

                    int id = result.getInt("id");
                    String nome = result.getString("nome");
                    String status = result.getString("status");
                    System.out.println(" ID -> " + id + " Nome: " + nome + " Status: " + status);
                }
                System.out.println("----------------------------------");

                System.out.println("Selecione o ID do curso!");
                Scanner scanner = new Scanner(System.in);
                idSelecionado = scanner.nextInt();
            }

        } catch (SQLException e) {
            imprimirErroAoCarregarDados("curso");
        }

        return idSelecionado;
    }

    public int carregarDadosCursoNaoMatriculado(int idAluno) {
        int idSelecionado = 0;
        String query = "SELECT cur.id, cur.nome, cur.status FROM curso cur WHERE status = 'ABERTO' AND cur.id NOT IN (SELECT curso_id FROM curso_e_aluno WHERE aluno_id = "
                + idAluno + ")";

        try {
            Statement stm = connectar().createStatement();
            ResultSet result = stm.executeQuery(query);

            if (result.isBeforeFirst()) {
                System.out.println("----------------------------------");
                while (result.next()) {

                    int id = result.getInt("id");
                    String nome = result.getString("nome");
                    String status = result.getString("status");
                    System.out.println(" ID -> " + id + " Nome: " + nome + " Status: " + status);

                }
                System.out.println("----------------------------------");

                System.out.println("Selecione o ID do curso!");
                Scanner scanner = new Scanner(System.in);
                idSelecionado = scanner.nextInt();
            }

        } catch (SQLException e) {
            imprimirErroAoCarregarDados("curso");
        }

        return idSelecionado;
    }

    public int carregarDadosCursoNaoAssociado(int idProfessor) {
        int idSelecionado = 0;
        String query = "SELECT cur.id, cur.nome, cur.status FROM curso cur WHERE cur.id NOT IN (SELECT curso_id FROM curso_e_professor WHERE professor_id  = "
                + idProfessor + ")";

        try {
            Statement stm = connectar().createStatement();
            ResultSet result = stm.executeQuery(query);

            if (result.isBeforeFirst()) {
                System.out.println("----------------------------------");
                while (result.next()) {

                    int id = result.getInt("id");
                    String nome = result.getString("nome");
                    String status = result.getString("status");
                    System.out.println(" ID -> " + id + " Nome: " + nome + " Status: " + status);

                }
                System.out.println("----------------------------------");

                System.out.println("Selecione o ID do curso!");
                Scanner scanner = new Scanner(System.in);
                idSelecionado = scanner.nextInt();
            }

        } catch (SQLException e) {
            imprimirErroAoCarregarDados("curso");
        }
        return idSelecionado;
    }
}
