package pt.ulusofona.deisi.aedProj2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//classe do Filme como pedida
class Filme {
    int id;
    String titulo;
    Data data;
    ArrayList<Actor> actores = new ArrayList<>();
    ArrayList<Genero> generos = new ArrayList<Genero>();
    int orcamento;
    float mediaVotos;
    int nrVotos;


    // construtor normal
    Filme(int id, String titulo, Data data, ArrayList<Actor> actores, ArrayList<Genero> generos, int orcamento, int mediaVotos, int nrVotos) {
        this.id = id;
        this.titulo = titulo;
        this.data = data;
        this.actores = actores;
        this.generos = generos;
        this.orcamento = orcamento;
        this.mediaVotos = mediaVotos;
        this.nrVotos = nrVotos;
    }

    //construtor vazio
    Filme(){

    }

    // output em string da classe
    public String toString() {
        return(id + " | " + titulo + " | " + data + " | " + actores.size() + " | " + generos.size() + " | " + nrVotos);
    }

//função para criar filmes inserida nesta classe para simplificar o código

    static void criaFilmes(HashMap<Integer,Filme> localizarFilmes, ArrayList<Filme>filmes) {

        String inputFile = "deisi_movies.txt";

        try {
            //Ficheiro Filme
            File input = new File(inputFile);
            Scanner inputScanner = new Scanner(input);
            int i = 0;
            while (inputScanner.hasNextLine()) {
                long start = System.currentTimeMillis();
                String linha = inputScanner.nextLine();
                String coluna[] = linha.split(",");
                if(coluna.length == 7) { // verificação se a linha é válida, ou seja, não tem dados a mais/menos
                    int id = Integer.parseInt(coluna[0]);

                    //Se nao exite filme com o id:
                    if(!localizarFilmes.containsKey(id)) {
                        Filme movie = new Filme();
                        movie.id = Integer.parseInt(coluna[0]);
                        movie.titulo = coluna[1];
                        //solução suja para a data
                        String[] date = (coluna[2].split("-"));
                        movie.data = new Data(date[0], date[1], date[2]);
                        movie.orcamento = Integer.parseInt(coluna[3]);
                        movie.mediaVotos = Float.parseFloat(coluna[5]);
                        movie.nrVotos = Integer.parseInt(coluna[6]);
                        localizarFilmes.put(movie.id, movie);
                        filmes.add(movie);
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

