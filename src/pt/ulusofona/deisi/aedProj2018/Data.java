package pt.ulusofona.deisi.aedProj2018;

// dirty but does the job
class Data {
    String ano;
    String mes;
    String dia;
    Data(String ano, String mes, String dia){
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }
    public String toString() {
        return(dia + "-" + mes + "-" + ano);
    }
}
