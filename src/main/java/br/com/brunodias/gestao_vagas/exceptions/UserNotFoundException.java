package br.com.brunodias.gestao_vagas.exceptions;


public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("Usuario nao encontrado !");
    }
}
