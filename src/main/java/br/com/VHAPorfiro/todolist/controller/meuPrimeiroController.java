package br.com.VHAPorfiro.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Criando uma rota

@RestController
@RequestMapping("/primeiraRota")

//http://localhost:8080/
public class meuPrimeiroController {

    /*
     * Metodos de acesso do HTTP
     * GET - Buscar Informação
     * Post - adicionar um dado
     * Put - alterar um dado/informação
     * Delete - remover um dado
     * Patch - alterar somente uma parte da informação
     */

     // METODO (FUNCIONALIDADE) DE UMA CLASSE

     @GetMapping("/primeiroMetodo")
    public String primeraMensagem() {
    
        return "Funcionou";

    }

}
