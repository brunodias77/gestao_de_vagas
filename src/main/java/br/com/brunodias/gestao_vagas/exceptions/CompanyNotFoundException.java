package br.com.brunodias.gestao_vagas.exceptions;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException() {
        super("Empresa nao encontrada");
    }
}