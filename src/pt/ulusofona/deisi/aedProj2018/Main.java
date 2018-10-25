package pt.ulusofona.deisi.aedProj2018;

import java.util.*;


public class Main {

    static ArrayList<Filme> movieList;
    static HashMap<Integer, Filme> pesquisaFilme;
    static HashMap<Integer, Actor> pesquisaActor;
    //função para analisar os ficheiros
    public static void parseMovieFiles() {

        movieList = new ArrayList<>();
        pesquisaFilme = new HashMap<>();
        pesquisaActor = new HashMap<>();

        //passamos à análise do ficheiro dos atores (as funções foram incluídas em cada classe
        //de modo a simplificar o main
        Filme.criaFilmes(pesquisaFilme, movieList);
        Actor.juntarActor(pesquisaActor);
        Genero.juntarGeneros(pesquisaFilme);
    }

    public static ArrayList<Filme> getMovies() {
        return movieList;
    }

    static String executeQuery(String input) {
        String[] dadosInput = input.split(" ");
        String query = dadosInput[0];

        //Selecionar a query que vai ser executada
        switch (query) {
            case "QUIT":
                return "Adeus.";

            case "COUNT_MOVIES_YEAR":
                String ano = dadosInput[1];
                int filmes = 0;
                for (Filme aMovieList1 : movieList) {
                    if (aMovieList1.data.ano.equals(ano)) {
                        boolean generoComedy = false;
                        for (int i = 0; i < aMovieList1.generos.size(); i++) {
                            if (aMovieList1.generos.get(i).nome.equals("Comedy")) {
                                generoComedy = true;
                            }
                        }
                        if (!generoComedy) {
                            filmes++;
                        }
                    }
                }
                    return (Integer.toString(filmes));

            case "COUNT_MOVIES_ACTOR":
                input = input.replace("COUNT_MOVIES_ACTOR ", "");
                StringBuilder actor = new StringBuilder(input);
                int totalFilmes = 0;
                for (Filme aMovieList1 : movieList) {
                    for (int j = 0; j < aMovieList1.actores.size(); j++) {
                        if (aMovieList1.actores.get(j).nome.equals(actor.toString())) {
                            totalFilmes++;
                        }
                    }
                }
                return (Integer.toString(totalFilmes));

            case "COUNT_MOVIES_ACTORS":
                input = input.replace("COUNT_MOVIES_ACTORS ", "");
                String[] nome = input.split(";");
                totalFilmes = 0;
                String actor1 = nome[0];
                String actor2 = nome[1];
                boolean pesquisaActor1;
                boolean pesquisaActor2;

                for (Filme compararFilme:movieList) {
                    pesquisaActor1=false;
                    pesquisaActor2=false;
                    for (Actor compararActores:compararFilme.actores) {
                        if(compararActores.nome.equals(actor1)){
                            pesquisaActor1=true;
                        }

                        if(compararActores.nome.equals(actor2)){
                            pesquisaActor2=true;
                        }
                    }
                    if(pesquisaActor1 && pesquisaActor2){
                        totalFilmes++;
                    }
                }
                return (Integer.toString(totalFilmes));

            case "GET_TITLES_YEAR":
                ano = dadosInput[1];
                StringBuilder resultado = new StringBuilder();
                boolean primeiroTitulo=true;

                for (Filme aMovieList : movieList) {
                    if (aMovieList.data.ano.equals(ano)) {
                        if (primeiroTitulo) {
                            resultado.append(aMovieList.titulo);
                            primeiroTitulo = false;
                        } else {
                            resultado.append("||").append(aMovieList.titulo);
                        }
                    }
                }
                return resultado.toString();

            case "HARD_MODE_ON_1":
                int id=Integer.parseInt(dadosInput[1]);
                resultado = new StringBuilder();
                boolean dataValida=false;

                Filme compararFilme=pesquisaFilme.get(id);

                primeiroTitulo=true;

                for (Filme aMovieList : movieList) {
                    if (aMovieList.id != id) {
                        int ano1 = Integer.parseInt(aMovieList.data.ano);
                        int ano2 = Integer.parseInt(compararFilme.data.ano);
                        int mes1 = Integer.parseInt(aMovieList.data.mes);
                        int mes2 = Integer.parseInt(compararFilme.data.mes);
                        int dia1 = Integer.parseInt(aMovieList.data.mes);
                        int dia2 = Integer.parseInt(compararFilme.data.mes);

                        if (ano1 > ano2) {
                            dataValida = true;
                        } else if (ano1 == ano2) {
                            if (mes1 > mes2) {
                                dataValida = true;
                            } else if (mes1 == mes2) {
                                if (dia1 > dia2) {
                                    dataValida = true;
                                }
                            }
                        }
                    }

                    if (dataValida) {
                        int countActor = 0;
                        for (int j = 0; j < aMovieList.actores.size(); j++) {
                            for (int k = 0; k < compararFilme.actores.size(); k++) {
                                if (aMovieList.actores.get(j).id == compararFilme.actores.get(k).id) {
                                    countActor++;
                                }
                            }
                        }
                        if (countActor == 1) {
                            if (aMovieList.mediaVotos == compararFilme.mediaVotos) {
                                if (primeiroTitulo) {
                                    resultado.append(aMovieList.titulo);
                                    primeiroTitulo = false;
                                } else {
                                    resultado.append("||").append(aMovieList.titulo);
                                }
                            }
                        }
                    }
                }
                return resultado.toString().trim();


            case "GET_TOP_VOTED_TITLES_YEAR":
                ano = dadosInput[1];
                int numFilmes = Integer.parseInt(dadosInput[2]);

                ArrayList<Filme>filmesAno=new ArrayList<>();

                for (Filme aMovieList : movieList) {
                    if (aMovieList.data.ano.equals(ano)) {
                        filmesAno.add(aMovieList);
                    }
                }

                filmesAno.sort(Comparator.comparingDouble(o -> o.mediaVotos));
                Collections.reverse(filmesAno);
                String valorAdicionar;

                StringBuilder resultadoCompilado=new StringBuilder();

                if(numFilmes == -1){
                    for(int i=0;i<filmesAno.size();i++){
                        if(i==filmesAno.size()-1){
                            valorAdicionar=filmesAno.get(i).titulo + ";" + filmesAno.get(i).mediaVotos;
                            resultadoCompilado.append(valorAdicionar);
                        }else{
                            valorAdicionar=filmesAno.get(i).titulo + ";" + filmesAno.get(i).mediaVotos + "\n";
                            resultadoCompilado.append(valorAdicionar);
                        }
                    }
                }else{
                    for(int i = 0; i < numFilmes; i++) {
                        if (i == numFilmes - 1) {
                            valorAdicionar = filmesAno.get(i).titulo + ";" + filmesAno.get(i).mediaVotos;
                            resultadoCompilado.append(valorAdicionar);
                        } else {
                            valorAdicionar = filmesAno.get(i).titulo + ";" + filmesAno.get(i).mediaVotos + "\n";
                            resultadoCompilado.append(valorAdicionar);
                        }
                    }
                }
                if(resultadoCompilado.toString().equals("")){
                    return "Erro: Nenhum filme foi encontrado de acordo com os dados fornecidos.";
                }else{
                    return resultadoCompilado.toString().trim();
                }


            case "COUNT_MOVIES_ACTOR_YEAR":
                ano = dadosInput[1];
                input = input.replace("COUNT_MOVIES_ACTOR_YEAR "+ ano + " ", "");
                nome = input.split(";");
                actor = new StringBuilder(nome[0]);
                totalFilmes = 0;

                for (int i = 1; i < nome.length; i++) {
                    if (i == nome.length - 1) {
                        actor.append(nome[i]);
                    } else {
                        actor.append(nome[i]).append(" ");
                    }
                }
                for (Filme aMovieList : movieList) {
                    for (int d = 0; d < aMovieList.actores.size(); d++) {
                        if (aMovieList.data.ano.equals(ano)) {
                            if (aMovieList.actores.get(d).nome.equals(actor.toString())) {
                                totalFilmes++;
                            }
                        }
                    }
                }
                return (Integer.toString(totalFilmes));

            case "GET_TOP_ACTOR_YEAR":
                ano=dadosInput[1];
                HashMap<Integer,Integer> mapa = new HashMap<>();

                for(Filme movies:movieList){
                    if(movies.data.ano.equals(ano)){
                        for (Actor actores:movies.actores){
                            if(!mapa.containsKey(actores.id)){
                                mapa.put(actores.id,1);
                            }else{
                                mapa.put(actores.id,mapa.containsKey(actores.id)?mapa.get(actores.id)+1:1);
                            }
                        }
                    }
                }
                int maior, idMaior;
                resultado = new StringBuilder();
                int chave;
                maior=0;
                for(HashMap.Entry<Integer,Integer> apagarDados:mapa.entrySet()){
                    chave=apagarDados.getKey();
                    totalFilmes=apagarDados.getValue();
                    if(totalFilmes>maior){
                        idMaior=chave;
                        maior=totalFilmes;
                        Actor actorMaior=pesquisaActor.get(idMaior);
                        resultado = new StringBuilder(actorMaior.nome + ";" + maior);
                    }
                }
                return resultado.toString();


            case "COUNT_MOVIES_YEAR_GENRE":
                input=input.replace("COUNT_MOVIES_YEAR_GENRE ","");
                String[] queryValor=input.split(" ");
                String ano1 = queryValor[0];
                String ano2 = queryValor[1];
                String tipo = input.replace(ano1 +" " + ano2 + " ","");
                totalFilmes = 0;

                for (Filme aMovieList : movieList) {
                    if (Integer.parseInt(aMovieList.data.ano) == Integer.parseInt(ano1) || Integer.parseInt(aMovieList.data.ano) == Integer.parseInt(ano2)) {
                        for (int d = 0; d < aMovieList.generos.size(); d++) {
                            if (aMovieList.generos.get(d).nome.equals(tipo)) {
                                totalFilmes++;
                                break;
                            }
                        }
                    }
                }

                return (Integer.toString(totalFilmes));


            case "GET_MALE_RATIO_YEAR":
                ano = dadosInput[1];
                int idActor, totalMasculino = 0;
                HashMap<Integer,Actor> actoresAComparar=new HashMap<>();

                for(Filme movie:movieList){
                    if(movie.data.ano.equals(ano)){
                        for(Actor actors:movie.actores){
                            idActor=actors.id;
                            if(!actoresAComparar.containsKey(idActor)){
                                actoresAComparar.put(idActor,actors);
                                if(actors.genero.equals("Masculino")){
                                    totalMasculino++;
                                }
                            }
                        }
                    }
                }

                return (int) Math.round((totalMasculino*100.0/actoresAComparar.size())) + "%";


            case "COUNT_MOVIES_WITH_MANY_ACTORS":
                int nActores = Integer.parseInt(dadosInput[1]);
                totalFilmes = 0;

                for (Filme filmeCompara : movieList) {
                    if (filmeCompara.actores.size() > nActores) {
                        totalFilmes++;
                    }
                }
                return (Integer.toString(totalFilmes));


            case "GET_TOP_ACTORS_BY_GENRE":
                String genero=dadosInput[1];
                ArrayList<Filme> generoArray = new ArrayList<>();
                HashMap<Integer,Integer> topActores=new HashMap<>();

                for(Filme movie:movieList){
                    for(Genero generos:movie.generos){
                        if(generos.nome.equals(genero)){
                            generoArray.add(movie);
                        }
                    }
                }

                for(Filme movie: generoArray){
                    for(Actor actores:movie.actores){
                        if(!topActores.containsKey(actores.id)){
                            topActores.put(actores.id,1);
                        } else {
                            topActores.put(actores.id,topActores.containsKey(actores.id)?topActores.get(actores.id)+1:1);
                        }
                    }
                }

                generoArray.clear();

                int comparador=0;
                idMaior=0;
                String adicionador=null;
                StringBuilder resultadoFinal=new StringBuilder();

                while(comparador<10){
                    int actorKey;
                    maior=0;

                    for(HashMap.Entry<Integer,Integer>receberDados:topActores.entrySet()){
                        actorKey=receberDados.getKey();
                        int informacao=receberDados.getValue();

                        if(informacao>maior){
                            idMaior=actorKey;
                            maior=informacao;
                            String nomeActorMaior=pesquisaActor.get(idMaior).nome;
                            adicionador=nomeActorMaior + ";" + maior;
                        }
                    }
                    topActores.remove(idMaior);
                    comparador++;
                    resultadoFinal.append(adicionador).append("\n");
                }

                return resultadoFinal.toString().trim();


            case "INSERT_ACTOR":
                input = input.replace("INSERT_ACTOR ", "");
                String[]valores = input.split(",");


                idActor=Integer.parseInt(valores[0]);
                String nomeActor=valores[1];
                String generoActor=valores[2];
                int idActorFilme=Integer.parseInt(valores[3]);

                Actor actorAInserir;

                if(!pesquisaFilme.containsKey(idActorFilme)){
                    return "Erro";
                }else {

                    if (pesquisaActor.containsKey(idActor)) {
                        return "Erro";
                    } else {
                        actorAInserir = new Actor(idActor, nomeActor, generoActor);

                        pesquisaActor.put(idActor, actorAInserir);

                        pesquisaFilme.get(idActorFilme).actores.add(actorAInserir);
                    }
                }

                return "OK";

            case "REMOVE_ACTOR":
                idActor=Integer.parseInt(dadosInput[1]);
                resultado = new StringBuilder("Erro");

                for (Filme aMovieList : movieList) {
                    for (int d = 0; d < aMovieList.actores.size(); d++) {
                        if (aMovieList.actores.get(d).id == idActor) {
                            aMovieList.actores.remove(d);
                            resultado = new StringBuilder("OK");
                        }
                    }
                }
                return resultado.toString();


            default:
                return "Query com formato inválido. Tente novamente.";
        }
    }


    public static void main(String[] args) {
        //testes

        parseMovieFiles();
        System.out.println("Bem-vindos à DEISI Movies Database.");
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        while(line != null && !line.equals("QUIT")) {
            long start = System.currentTimeMillis();
            String result = executeQuery(line);
            long end = System.currentTimeMillis();
            System.out.println(result);
            System.out.println("(demorou " + (end - start) + " ms)");
            line = input.nextLine();
        }
        System.out.println("Cheers mate");
    }                                                       // the swiftness of the ranger is still talked about today
}
