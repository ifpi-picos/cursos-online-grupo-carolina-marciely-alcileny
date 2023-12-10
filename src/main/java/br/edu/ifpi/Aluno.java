package br.edu.ifpi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Aluno {

    private Connection conexao;

    public Aluno(Connection conexao) {
        this.conexao = conexao;
    }

    public void cadastrarAluno() {
        SistemaAcademico.limparConsole();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o nome do aluno");
        String nome = scanner.nextLine();
        System.out.println("Informe o email do aluno");
        String email = scanner.nextLine();
        SistemaAcademico.limparConsole();

        String query = "INSERT INTO aluno (nome, email) VALUES ('" + nome + "','" + email + "')";

        try {
            Statement stm = conexao.createStatement();
            stm.executeUpdate(query);
            System.out.println("|-------------------------------|");
            System.out.println("| Aluno cadastrado com sucesso! |");
            System.out.println("|-------------------------------|");
        } catch (SQLException e) {
            System.out.println("|-------------------------------|");
            System.out.println("| Erro ao realizar cadastrado!  |");
            System.out.println("|-------------------------------|");
        }
        SistemaAcademico.pausar();
    }

    private void matricular(int idCuso, int idAluno) {
        String query = "INSERT INTO curso_e_aluno (curso_id, aluno_id) VALUES ('" + idCuso + "','" + idAluno
                + "')";

        try {
            Statement stm = this.conexao.createStatement();
            stm.executeUpdate(query);
            System.out.println("|----------------------------------|");
            System.out.println("| Matricula realizado com sucesso! |");
            System.out.println("|----------------------------------|");
        } catch (SQLException e) {
            System.out.println("|----------------------------------|");
            System.out.println("| Erro ao realizar matricula!      |");
            System.out.println("|----------------------------------|");
        }
        SistemaAcademico.pausar();
    }

    public void realizarMatricula() {
        SistemaAcademico.limparConsole();
        Curso curso = new Curso(conexao);
        SistemaAcademico.limparConsole();
        int idAluno = carregarDadosDoAluno();
        SistemaAcademico.limparConsole();

        if (idAluno != 0) {
            int idCuso = curso.carregarDadosCursoNaoMatriculado(idAluno);
            SistemaAcademico.limparConsole();
            if (idCuso != 0) {
                matricular(idCuso,idAluno);
            } else {
                System.out.println("|-------------------------------------------|");
                System.out.println("| Nao foi encontrado nenhum curso ABERTO    |");
                System.out.println("| Que o aluno nao esteja matriculado!       |");
                System.out.println("|-------------------------------------------|");
                SistemaAcademico.pausar();
            }
        } else {
            System.out.println("|-------------------------------------------|");
            System.out.println("| Nenhum aluno encontrado!                  |");
            System.out.println("|-------------------------------------------|");
            SistemaAcademico.pausar();
        }

    }

    public void atualizarInformacoes() {
        SistemaAcademico.limparConsole();
        int idAluno = carregarDadosDoAluno();
        SistemaAcademico.limparConsole();
        if (idAluno != 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Informe novo nome do aluno");
            String novoNome = scanner.nextLine();
            System.out.println("Informe o novo email do aluno");
            String email = scanner.nextLine();
            SistemaAcademico.limparConsole();

            String query = "UPDATE aluno SET nome = '" + novoNome + "', email = '" + email + "' WHERE id = '" + idAluno
                    + "'";

            try {
                Statement stm = this.conexao.createStatement();
                stm.executeUpdate(query);
                System.out.println("|------------------------------------|");
                System.out.println("|Informacoes atualizado com sucesso! |");
                System.out.println("|------------------------------------|");
            } catch (SQLException e) {
                System.out.println("|------------------------------------|");
                System.out.println("| Erro ao atualizar informacoes!     |");
                System.out.println("|------------------------------------|");
            }
            SistemaAcademico.pausar();
        } else {
            System.out.println("|------------------------------------|");
            System.out.println("| Nenhum aluno encontrado!           |");
            System.out.println("|------------------------------------|");
            SistemaAcademico.pausar();
        }
    }

    public void visualizarPerfil() {
        SistemaAcademico.limparConsole();
        int idAluno = carregarDadosDoAluno();
        SistemaAcademico.limparConsole();

        if (idAluno != 0) {
            String query = "SELECT matr.*, al.nome AS alnome, al.email, cur.nome AS curso FROM aluno al LEFT JOIN curso_e_aluno matr ON al.id = matr.aluno_id LEFT JOIN curso cur ON cur.id = matr.curso_id WHERE al.id = "
                    + idAluno;
            try {
                Statement stm = this.conexao.createStatement();
                ResultSet result = stm.executeQuery(query);

                if (result.next()) {
                    String nome = result.getString("alnome");
                    String email = result.getString("email");
                    String curso = result.getString("curso");
                    System.out.println("|===============PERFIL DO ALUNO===============|");
                    System.out.println("| Nome: " + nome);
                    System.out.println("| E-mail: " + email);
                    System.out.println("|------------MATRICULADO NOS CURSOS-----------|");
                    if (curso == null) {
                        System.out.println("| Nao esta matriculado em nenhum curso |");
                    } else {
                        System.out.println("| " + curso);
                    }

                    while (result.next()) {
                        curso = result.getString("curso");
                        System.out.println("| " + curso);
                    }
                    System.out.println("|=============================================|");
                }

            } catch (SQLException e) {
                System.out.println("|------------------------------------|");
                System.out.println("| Erro ao carregar dados do aluno!   |");
                System.out.println("|------------------------------------|");

            }
            SistemaAcademico.pausar();
        } else {
            System.out.println("|------------------------------------|");
            System.out.println("| Nenhum aluno encontrado!           |");
            System.out.println("|------------------------------------|");
            SistemaAcademico.pausar();
        }
    }

    public void cancelarMatricula() {
        SistemaAcademico.limparConsole();
        Curso curso = new Curso(conexao);
        int aluno_id = carregarDadosDoAluno();
        SistemaAcademico.limparConsole();
        if (aluno_id != 0) {
            int curso_id = curso.carregarDadosCursoMatriculado(aluno_id);
            SistemaAcademico.limparConsole();
            if (curso_id != 0) {
                String query = "DELETE FROM curso_e_aluno WHERE aluno_id = " + aluno_id + " AND curso_id = " + curso_id;
                try {
                    Statement stm = this.conexao.createStatement();
                    stm.executeUpdate(query);
                    System.out.println("|------------------------------------|");
                    System.out.println("| Matricula cancelada com sucesso!   |");
                    System.out.println("|------------------------------------|");
                } catch (SQLException e) {
                    System.out.println("|------------------------------------|");
                    System.out.println("| Erro ao cancelar matricula!        |");
                    System.out.println("|------------------------------------|");
                }
                SistemaAcademico.pausar();

            } else {
                System.out.println("|--------------------------------------|");
                System.out.println("| Nenhuma curso matriculado encontrado!|");
                System.out.println("|--------------------------------------|");
                SistemaAcademico.pausar();
            }
        } else {
            System.out.println("|--------------------------------------|");
            System.out.println("| Nenhum aluno encontrado!             |");
            System.out.println("|--------------------------------------|");
            SistemaAcademico.pausar();
        }
    }

    public int carregarDadosDoAluno() {
        SistemaAcademico.limparConsole();
        String query = "SELECT id, nome FROM aluno";
        int idSelecionado = 0;

        try {
            Statement stm = this.conexao.createStatement();
            ResultSet result = stm.executeQuery(query);

            if (result.isBeforeFirst()) {

                System.out.println("----------------------------------");
                while (result.next()) {

                    int id = result.getInt("id");
                    String nome = result.getString("nome");

                    System.out.println(" ID -> " + id + " Nome: " + nome);
                }
                System.out.println("----------------------------------");

                System.out.println("Selecione o ID do aluno!");
                Scanner scanner = new Scanner(System.in);
                idSelecionado = scanner.nextInt();
            }

        } catch (SQLException e) {
            System.out.println("|--------------------------------------|");
            System.out.println("| Erro ao carregar dados do aluno!     |");
            System.out.println("|--------------------------------------|");
            SistemaAcademico.pausar();
        }

        return idSelecionado;
    }

    public void relatorioDesempenho() {
        SistemaAcademico.limparConsole();
        int idAluno = carregarDadosDoAluno();
        SistemaAcademico.limparConsole();

        if (idAluno != 0) {
            String query = "SELECT ca.*,(SELECT nome FROM aluno WHERE id = ca.aluno_id) AS nome, (SELECT nome FROM curso WHERE id = ca.curso_id AND ca.concluido = 'sim') AS concluidos, curs.nome AS matriculados FROM curso_e_aluno ca LEFT JOIN curso curs ON curs.id = ca.curso_id  WHERE ca.aluno_id = "
                    + idAluno;
            try {
                Statement stm = this.conexao.createStatement();
                ResultSet result = stm.executeQuery(query);
                List<String> cursosConcluidos = new ArrayList<>();
                List<String> cursosMatriculados = new ArrayList<>();

                if (result.next()) {
                    String nome = result.getString("nome");
                    String concluidos = result.getString("concluidos");
                    String matriculados = result.getString("matriculados");
                    System.out.println("|===============RELATORIO DO ALUNO===============|");
                    System.out.println("| Nome do aluno: " + nome);
                    cursosConcluidos.add(concluidos);
                    cursosMatriculados.add(matriculados);
                    matriculados = result.getString("matriculados");

                    while (result.next()) {
                        nome = result.getString("nome");
                        concluidos = result.getString("concluidos");
                        matriculados = result.getString("matriculados");
                        cursosConcluidos.add(concluidos);
                        cursosMatriculados.add(matriculados);
                    }
                    System.out.println("|--------------MATRICULADO NOS CURSOS------------|");
                    for (int i = 0; i < cursosMatriculados.size(); i++) {
                        if (cursosMatriculados.get(i) != null) {
                            System.out.println("| " + cursosMatriculados.get(i));
                        }
                    }

                    System.out.println("|--------------CURSOS CONCLUIDOS-----------------|");
                    for (int i = 0; i < cursosConcluidos.size(); i++) {
                        if (cursosConcluidos.get(i) != null) {
                            System.out.println("| " + cursosConcluidos.get(i));
                        }
                    }

                    System.out.println("|================================================|");
                } else {
                    System.out.println("|---------------------------------------|");
                    System.out.println("| Não está matriculado em nenhum curso! |");
                    System.out.println("|---------------------------------------|");
                }

            } catch (SQLException e) {
                System.out.println("|----------------------------------|");
                System.out.println("| Erro ao realizar consulta!       |");
                System.out.println("|----------------------------------|");
                e.printStackTrace();
            }
            SistemaAcademico.pausar();
        } else {
            System.out.println("|-------------------------------------------|");
            System.out.println("| Nenhum aluno encontrado!                  |");
            System.out.println("|-------------------------------------------|");
            SistemaAcademico.pausar();
        }
    }
}