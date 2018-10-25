package pt.ulusofona.deisi.aedProj2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

// classe ator
class Actor {
    int id;
    String nome;
    String genero;

    // construtor normal
    Actor (int id, String nome, String genero) {
        this.id = id;
        this.nome = nome;
        this.genero= genero;
    }

    //construtor vazio
    Actor(){
    }

    //output em string da classe, embora nao pedido, usado para testes
    public String toString() {
        return (nome);
}
    public static void juntarActor(HashMap<Integer,Actor> pesquisaActor){

        String inputFile = "deisi_actors.txt";
        try{
            File input = new File(inputFile);
            Scanner inputScanner = new Scanner(input);
            while (inputScanner.hasNextLine()) {
                String linha = inputScanner.nextLine();
                String coluna[] = linha.split(",");
                if (coluna.length == 4) {
                    int idActor = Integer.parseInt(coluna[0]);
                    int filmeActor = Integer.parseInt(coluna[3]);
                    if (Main.pesquisaFilme.containsKey(filmeActor)) {
                        String nome = coluna[1];
                        String genero;
                        if (coluna[2].equals("true")) {
                            genero = "Masculino";
                        } else {
                            genero = "Feminino";
                        }

                        Actor actor = new Actor(idActor, nome, genero);
                        if (pesquisaActor.containsKey(idActor) == false) {
                            pesquisaActor.put(idActor, actor);
                        } else {
                            actor = pesquisaActor.get(idActor);
                        }
                        Filme actorFilme = Main.pesquisaFilme.get(filmeActor);

                        if (actorFilme.actores.contains(actor)) {
                            continue;
                        }
                        //por fim adicionamos o ator à lista dos filmes
                        Filme filme = Main.pesquisaFilme.get(filmeActor);
                        filme.actores.add(actor);
                    }
                }
            }
            inputScanner.close();
        } catch (FileNotFoundException exception) { //Se chegou aqui, o ficheiro não foi encontrado.
            String mensagemFalha = "Erro: o ficheiro " + inputFile + " não foi encontrado.";
            System.out.println(mensagemFalha);
        }
    }
}
