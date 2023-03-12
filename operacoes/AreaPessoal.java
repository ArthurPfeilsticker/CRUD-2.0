package operacoes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.instrument.Instrumentation;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import entidades.usuario.CrudUsuario;
import entidades.usuario.Movies;
import javafx.scene.shape.Path;

public class AreaPessoal {

  static int amountOfMovies = 20;
  public static CrudUsuario crudUsuario;
  public static Movies[] movies = new Movies[amountOfMovies]; //quantidade de dados de filmes 
  public static Movies newMovie;
	private static Scanner input = new Scanner(System.in);
  static SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");

  public static void menu() throws Exception{
    int escolha;
    do {
      System.out.println("\n===================\n");
      System.out.println("INÍCIO > C R U D\n");
      System.out.println("1) Criar novos dados (create).");
      System.out.println("2) Ler dados existentes (read).");
      System.out.println("3) Atualizar dados (update).");
      System.out.println("4) Deletar dados (delete).");
      System.out.println("\n0) Retornar ao menu anterior");

      escolha = Utils.leOpcoes(0, 4);

      if(escolha == 1) create(); 
      else if(escolha == 2) read(); 
      else if(escolha == 3) update();
      else if(escolha == 4) delete();
      else TelaInicial.telaInicial();
    } while(escolha != 0);

  }//fim void menu()

  public static void newData() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader("movies.csv")); //lendo arquivo de dados
		String linha = br.readLine(); //ignorando a primeira linha
    int x;
    String aux;

    for(int i = 0; i < amountOfMovies; i++){

      x = 1; // =1 para pular o primeiro caracter que é sempre ( " )
      aux = "";

			movies[i] = new Movies();
			linha = br.readLine();

      //============================== Tombstone ==============================

      movies[i].setTombstone(1);
			aux = "";
			
			//============================== ID ==============================
			while(linha.charAt(x) != ','){
				aux += linha.charAt(x);
				x++;
			}//fim while()

      movies[i].setID(Integer.parseInt(aux));
			aux = "";
			x++;

      //=========================== Movie Name ==========================
      while(linha.charAt(x) != ','){
				aux += linha.charAt(x);
				x++;
			}//fim while()

      movies[i].setMovieName(aux);
			aux = "";
			x++;
      //========================== Release Date =========================
      while(linha.charAt(x) != ','){
				aux += linha.charAt(x);
				x++;
			}//fim while()

      movies[i].setReleaseDate(formato.parse(aux));
			aux = "";
			x++;
      //============================ Category ===========================
      while(linha.charAt(x) != ','){
				aux += linha.charAt(x);
				x++;
			}//fim while()

      movies[i].setCategory(aux);
			aux = "";
			x++;
      //============================ Run Time ===========================
      while(linha.charAt(x) != ','){
				aux += linha.charAt(x);
				x++;
			}//fim while()

      movies[i].setRunTime(aux);
			aux = "";
			x++;
      //============================= Genre =============================
      while(linha.charAt(x) != '"' || linha.charAt(x+1) != ','){
				aux += linha.charAt(x);
				x++;
			}//fim while()

      aux = removerCaracter("\"", aux);
      movies[i].setGenre(aux);
			aux = "";
			x+=2;
      //========================== IMDB Rating ==========================
      while(linha.charAt(x) != ','){
				aux += linha.charAt(x);
				x++;
			}//fim while()

      movies[i].setImdbRating(Float.parseFloat(aux));
			aux = "";
			x++;
      //============================= Votes =============================
      while(linha.charAt(x) != '"' || linha.charAt(x+1) != ','){
				aux += linha.charAt(x);
				x++;
			}//fim while()

      aux = removerCaracter("\"", aux);
      movies[i].setVotes(Integer.parseInt(aux));
			aux = "";
			x+=2;
      //========================== Gross Total ==========================
      while(linha.charAt(x) != '"'){
				aux += linha.charAt(x);
				x++;
			}//fim while()

      movies[i].setGrossTotal(aux);
      //============================== Size ==============================
      movies[i].setSize(movies.length);
			aux = "";
      //============================= FIM ===============================

      //System.out.println(movies[i].toString()); //print dos dados lidos no arquivo movies.csv (usado para teste)
      //apos essa carga de dados os objetos Movies devem ser salvos no arquivo de bytes
      byte[] teste = movies[i].toByteArray();
      System.out.println(teste);
      save(movies[i].toString());
      //System.out.println("STRING: " + (movies[i].fromByteArray(teste).toString()));
    }
    br.close();
  }

  public static void loadData() throws Exception {
    //nao deve ler o arquivo .csv,  deve ler o arquivo de bytes 
  }

  public static String removerCaracter(String caracter, String str) throws Exception {
    str = str.replaceAll(caracter, "");
    return str;
  }

  // CRIAR NOVO DADO ----------------------------------------------------------------------------------------------------------
  public static boolean create ()throws Exception{
    int tombstone, size, id, votes;
    String movieName, category, runTime, genre, grossTotal;
    Date releaseDate;
    float imdbRating;

    Utils.pulaLinha(2);
    System.out.println("**** CREATE ****");

    boolean cadastroConfimado;

    tombstone = 1;
    size = 20;
    id = 0; //para aderir a um id preciso saber qual foi o ultimo id salvo.
    movieName = Utils.leString("nome do filme","o");
    releaseDate = (Date) Utils.leData("data no formato (yyyy/mm/dd)", "a");
    category = Utils.leString("categoria", "a");
    runTime = Utils.leString("tempo de duracao (em minutos)", "o");// + " min";
    genre = Utils.leString("genero(s)", "o(s)"); // criar um le generos[] separados por ( ,) e retornar do mesmo tipo []
    imdbRating = Utils.leFloat("nota IMDB", "a");
    votes = Utils.leInt("numero de votos", "o");
    grossTotal = Utils.leString("valor da arrecadacao total (em Milhoes)", "o");//"$" + + "M";
    //String temp = id + " " + movieName + " " + releaseDate + " " + category + " " + runTime + " " + genre + " " + imdbRating + " " + votes + " " + grossTotal; // temp string
    //String str = temp.length() + " " + temp; //string with size on beginning
    //System.out.println(str);
    Utils.pulaLinha(5);

    System.out.println("**** DADOS DO FILME ***");
    System.out.println("-> ID: " + id + "\n-> Nome do filme: " + movieName + "\n-> Data de lancamento: " + releaseDate + "\n-> Categoria: " + category + "\n-> Tempo de filme: " + runTime + "\n-> Genero: " + genre + "\n-> Nota IMDB: " + imdbRating + "\n-> votos: " + votes + "\n-> Arrecadacao total: " + grossTotal);
    System.out.print("Voce gostaria de confirmar o cadastro? (S/N)");
    cadastroConfimado = Utils.lerConfirmacao();
    Utils.pulaLinha(5);

    if (cadastroConfimado) {
      System.out.println("Cadastro confirmado!");
      newMovie = new Movies(tombstone, size, id, movieName, releaseDate, category, runTime, genre, imdbRating, votes, grossTotal);
      //String temp = id + " " + movieName + " " + releaseDate + " " + category + " " + runTime + " " + genre + " " + imdbRating + " " + votes + " " + grossTotal; // temp string
      //String str = temp.length() + " " + temp; //string with size on beginning
      //System.out.println(str);
      //toByte(str);
      save(newMovie.toString());
      //esse novo cadastro vai pro arquivo de bytes (o arquivo .csv serve apenas para fazer a carga inicial)
    } else
      System.out.println("Cadastro cancelado");

    return cadastroConfimado;
  }//fim create()

  // CONSULTAR DADOS ----------------------------------------------------------------------------------------------------------
  public static void read() throws Exception {
    File file = new File("movie.db");
    try (FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader)) {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
  } //fim consultarDados

  //ATUALIZAR DADOS ----------------------------------------------------------------------------------------------------------
  public static void update() throws Exception {

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> digitar o id do filme que deseja mudar
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> dar um get com o id que foi digitado (verificar se existe)

    int opcao, novoInt;
    float novoFloat;
    Date novaData;
    String novaString = "";
  
    System.out.println("\n\n\t*** ATUALIZAR DE DADOS ***");
    System.out.println("\nDigite o ID do filme que deseja atualizar: ");
    opcao = input.nextInt();

    Movies movie = getUsuario(opcao); //id >> passar o ID do filme aqui //essa funcao get vai procurar no arquivo de byte se o id existe

    System.out.println("\nQuais dados voce deseja atualizar?");
    System.out.println("1 -  Nome do Filme");
    System.out.println("2 -  Data de lancamento");
    System.out.println("3 -  Categoria");
    System.out.println("4 -  Tempo de filme");
    System.out.println("5 -  Genero");
    System.out.println("6 -  Nota IMDB");
    System.out.println("7 -  Numero de Votos");
    System.out.println("8 -  Arrecadacao total");
    System.out.println("\n0 -  Cancelar");

    opcao = Utils.leOpcoes(0, 8);

    switch (opcao) {
      case 1:
      System.out.println("\n\t= Atualizar Nome do filme =\n");
      novaString = Utils.leString("nome do filme","o");
      movie.setMovieName(novaString);
      break;
      case 2:
      System.out.println("\n\t= Atualizar Data de lancamento =\n");
      novaData = (Date) Utils.leData("data no formato (yyyy/mm/dd)", "a");
      movie.setReleaseDate(novaData);
      break;
      case 3:
      System.out.println("\n\t= Atualizar Categoria =\n");
      novaString = Utils.leString("categoria", "a");
      movie.setCategory(novaString);
      break;
      case 4:
      System.out.println("\n\t= Atualizar Tempo de filme =\n");
      novaString = Utils.leString("tempo de duracao (em minutos)", "o") + " min";
      movie.setRunTime(novaString);
      break;
      case 5:
      System.out.println("\n\t= Atualizar Genero =\n");
      novaString = Utils.leString("genero(s)", "o(s)"); // criar um le generos[] separados por ( ,) e retornar do mesmo tipo []
      movie.setGenre(novaString);
      break;
      case 6:
      System.out.println("\n\t= Atualizar Nota IMDB =\n");
      novoFloat = Utils.leFloat("nota IMDB", "a");
      movie.setImdbRating(novoFloat);
      break;
      case 7:
      System.out.println("\n\t= Atualizar Numero de Votos =\n");
      novoInt = Utils.leInt("numero de votos", "o");
      movie.setVotes(novoInt);
      break;
      case 8:
      System.out.println("\n\t= Arrecadacao total =\n");
      novaString = "$" + Utils.leString("valor da arrecadacao total (em Milhoes)", "o") + "M";
      movie.setGrossTotal(novaString);
      break;
      case 0:
      break;
    }

    updateMovie(movie); // recebe um objeto de movie(atualizado) e salva no arquivo no memso lugar(se tem tamanho igual, ou em outro lugar se o tamanho for diferente) (Obs: verificar os requisitos no TP, EX: se tem o mesmo tamanho de bytes, etc...)
    System.out.println("\n== Atualizacao feita com sucesso! ==\n");
  } //fim update()


  // DELETAR DADOS ----------------------------------------------------------------------------------------------------------
  public static void delete() throws Exception {

    String idToRemove = ("ID: " + Utils.leString("Digite O ID do filme a ser removido: ", "o(s)"));

    String caminhoDoArquivo = "movie.db";
        int idTombstone = 1; // ID da tombstone a ser atualizada
        String novaLinha = "Tombstone: 0"; // nova linha a ser escrita

        try {
            // cria um novo arquivo temporário para armazenar as linhas atualizadas
            File novoArquivo = new File("temp.db");
            BufferedWriter escritor = new BufferedWriter(new FileWriter(novoArquivo));

            // lê o arquivo original linha por linha e escreve as linhas atualizadas no novo arquivo
            File arquivo = new File(caminhoDoArquivo);
            BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
            String linha;
            boolean atualizou = false;
            while ((linha = leitor.readLine()) != null) {
                if (linha.equals("ID: " + idToRemove)) {
                    // encontrou a linha desejada, atualiza a tombstone
                    novaLinha = "Tombstone: 0";
                    atualizou = true;
                }
                escritor.write(linha + System.lineSeparator());
                if (atualizou) {
                    // escreve a nova linha atualizada após a linha original correspondente
                    escritor.write(novaLinha + System.lineSeparator());
                    atualizou = false;
                }
            }
            leitor.close();
            escritor.close();

            // substitui o arquivo original pelo novo arquivo atualizado
            arquivo.delete();
            novoArquivo.renameTo(new File(caminhoDoArquivo));

            System.out.println("Tombstone atualizada com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar tombstone: " + e.getMessage());
        }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> digitar o id do filme que deseja deletar
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> dar um delete no id que foi digitado (verificar se existe)

    // Movies clienteAtual = Acesso.getUsuario(Acesso.getIDUsuario());
    // boolean confirmacao;

    // System.out.println("\n\n\t*** DELETAR USUARIO ***");
    // System.out.println("\nTem certeza que deseja deletar seu usuario? Todos os seus dados serao apagados.");
    // System.out.println("\nDigite (s) para Sim ou (n) para Nao.");
    // confirmacao = Utils.lerConfirmacao();

    // if (confirmacao == true) {
    //   System.out.println("\nConta excluida com sucesso, todos os seus dados foram apagados.");
    //   TelaInicial.Logout();
    //   Acesso.deleteCustomer(clienteAtual);
    // } else {
    //   System.out.println("\nOperacao cancelada, voltando para menu.");
    // }
  } //fim deletarDados()


  public static Movies getUsuario(int ID)throws Exception{ //passa o ID e retorna o objeto Movie referente ao ID
    return crudUsuario.read(ID);
  }

  public static void updateMovie(Movies cd)throws Exception{ //recebe um objeto Movie com os dados modificados
    crudUsuario.update(cd);
  }

  public static void deleteMovie(Movies cd)throws Exception{ //recebe um objeto Movie e o apaga, tbm pode ser adptado para receber um ID.
    crudUsuario.delete(cd);
  }

  public static void toByte(String string) throws IOException{
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    dos.writeUTF(String.valueOf(string));
    byte[] byteArray = baos.toByteArray();
    System.out.println(byteArray);
    save(string);
  }

  public static void save(String movies) throws FileNotFoundException {
    File file = new File("movie.db");

        try (FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(movies.toString());
            bw.newLine();
            bw.flush();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
  }

public static String fromByte(byte ba[]) throws IOException{
    System.out.println(ba);
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    String str = dis.readUTF();
    return str;
  }

}//fim class AreaPessoal()