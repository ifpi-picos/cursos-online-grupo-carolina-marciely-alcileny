package br.edu.ifpi.entidades;

public class Professor {

  private int id;
  private String nome;
  private String email;

  public Professor(int id, String nome, String email) {
    this.id = id;
    this.nome = nome;
    this.email = email;
  }

  public Professor() {
  }

  public int getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getEmail() {
    return email;
  }

}