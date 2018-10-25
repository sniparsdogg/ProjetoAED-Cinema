package pt.ulusofona.deisi.aedProj2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Genero {
    String nome;
    public Genero(String nome){
        this.nome=nome;
    }

    Genero(){
    }

    public String toString(){
        return nome;
    }

    public static void juntarGeneros(HashMap<Integer,Filme>localizaFilme){
        String inputFile = "deisi_genres.txt";

        try {
            File input = new File(inputFile);
            Scanner inputScanner = new Scanner(input);

            while (inputScanner.hasNextLine()) {
                String linha = inputScanner.nextLine();
                String coluna[] = linha.split(",");

                if (coluna.length == 2) {
                    int idFilmeGenero=Integer.parseInt(coluna[1]);

                    if(localizaFilme.containsKey(idFilmeGenero)){

                        Genero generos = new Genero();
                        generos.nome = coluna[0];
                        Filme filme=Main.pesquisaFilme.get(idFilmeGenero);
                        if(filme.generos.contains(generos)){
                            continue;
                        }
                        filme.generos.add(generos);
                    }
                }
            }
            inputScanner.close();
        } catch (FileNotFoundException exception) {
            String mensagem = "Erro: o ficheiro nao foi encontrado";
            System.out.println(mensagem);
        }
    }
}
