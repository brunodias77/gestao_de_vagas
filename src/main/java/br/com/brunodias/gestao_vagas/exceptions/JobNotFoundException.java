package br.com.brunodias.gestao_vagas.exceptions;

public class JobNotFoundException extends RuntimeException{
    public JobNotFoundException(){
        super("Vaga nao encontrada !");
    }
}
