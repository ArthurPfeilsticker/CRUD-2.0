package operacoes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import entidades.usuario.CrudUsuario;
import entidades.usuario.Movies;

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
    File file = new File("movie.db");
    if(file.exists())
      file.delete();
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
      //============================= FIM ===============================

      //System.out.println(movies[i].toString()); //print dos dados lidos no arquivo movies.csv (usado para teste)
      //apos essa carga de dados os objetos Movies devem ser salvos no arquivo de bytes
      byte[] teste = movies[i].toByteArray();
      movies[i].setSize(teste.length);
      save(movies[i].toString());
    }
    br.close();
  }

  public static String removerCaracter(String caracter, String str) throws Exception {
    str = str.replaceAll(caracter, "");
    return str;
  }

  // CRIAR NOVO DADO ----------------------------------------------------------------------------------------------------------
  public static boolean create ()throws Exception{
    int tombstone;
    int size, id, votes;
    String movieName, category, runTime, genre, grossTotal;
    Date releaseDate;
    float imdbRating;

    Utils.pulaLinha(2);
    System.out.println("**** CREATE ****");

    boolean cadastroConfimado;

    tombstone = 1;
    size = 0;
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
      id = seek()+1;
      newMovie = new Movies(tombstone, size, id, movieName, releaseDate, category, runTime, genre, imdbRating, votes, grossTotal);
      //String temp = id + " " + movieName + " " + releaseDate + " " + category + " " + runTime + " " + genre + " " + imdbRating + " " + votes + " " + grossTotal; // temp string
      //String str = temp.length() + " " + temp; //string with size on beginning
      //System.out.println(str);
      //toByte(str);
      byte[] teste = newMovie.toByteArray();
      newMovie.setSize(teste.length);
      save(newMovie.toString());
      //esse novo cadastro vai pro arquivo de bytes (o arquivo .csv serve apenas para fazer a carga inicial)
    } else
      System.out.println("Cadastro cancelado");

    return cadastroConfimado;
  }//fim create()

  // CONSULTAR DADOS ----------------------------------------------------------------------------------------------------------
  public static void read() throws Exception {
    
    // Lê o valor digitado pelo usuário
    System.out.print("Digite o ID do filme a ser buscado: ");
    String valorComparar = System.console().readLine();

    try {
        // Abre o arquivo para leitura
        BufferedReader leitor = new BufferedReader(new FileReader("movie.db"));

        //String temp = "";
        String linha;
        //SimpleDateFormat formato = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        // Lê o arquivo linha por linha
        while ((linha = leitor.readLine()) != null) {
            String[] partes = linha.split("#");
            // Verifica se a linha contém o valor a ser alterado
            if (linha.contains("*" + valorComparar + "*")) {
              Movies movie = new Movies();

              movie.setTombstone(Integer.parseInt(partes[0]));
              movie.setSize(Integer.parseInt(partes[1]));
              movie.setMovieName(partes[3]);
              //Date data = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(partes[4]);
              //movie.setReleaseDate(data);
              movie.setCategory(partes[5]);
              movie.setRunTime(partes[6]);
              movie.setGenre(partes[7]);
              movie.setImdbRating(Float.parseFloat(partes[8]));
              movie.setVotes(Integer.parseInt(partes[9]));
              movie.setGrossTotal(partes[10]);

              System.out.println("\n\n\n=== ID: " + valorComparar + " ===");
              System.out.println("Lápide: " + movie.getTombstone());
              System.out.println("Size: " + movie.getSize());
              System.out.println("Movie Name: " + movie.getMovieName());
              System.out.println("Movie Category: " + movie.getCategory());
              //System.out.println("Movie Release Date: " + movie.getReleaseDate());
              System.out.println("Movie Run Time: " + movie.getRunTime());
              System.out.println("Movie Genre: " + movie.getGenre());
              System.out.println("IMDB Rating: " + movie.getImdbRating());
              System.out.println("Votes: " + movie.getVotes());
              System.out.println("Total Money Received: " + movie.getGrossTotal());
              System.out.println("\n");

              //System.out.println("ARQUIVO: " + linha);

            }

          
        }

        // Fecha os arquivos
        leitor.close();

        // Remove o arquivo original

        System.exit(0);
        
    } catch (IOException e) {
        System.out.println("Erro ao ler ou escrever o arquivo.");
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

    // Lê o valor digitado pelo usuário
    System.out.print("Digite o valor a ser alterado: ");
    String valorAlterar = System.console().readLine();

    try {
        // Abre o arquivo para leitura
        BufferedReader leitor = new BufferedReader(new FileReader("movie.db"));

        // Abre o arquivo para escrita
        BufferedWriter escritor = new BufferedWriter(new FileWriter("arquivo_novo.txt"));

        String linha;

        // Lê o arquivo linha por linha
        while ((linha = leitor.readLine()) != null) {
            // Verifica se a linha contém o valor a ser alterado
            if (linha.contains("*" + valorAlterar + "*")) {
                // Encontra a posição do marcador inicial
                int posicaoInicial = linha.indexOf("*" + valorAlterar + "*") + 1;

                // Encontra a posição do marcador final
                int posicaoFinal = linha.indexOf("*", posicaoInicial + 1);

                // Extrai a substring entre os marcadores
                String valorAntigo = linha.substring(posicaoInicial, posicaoFinal);

                // Substitui o primeiro caractere pelo valor desejado
                String novaLinha = "0" + linha.substring(posicaoInicial + 1);

                // Escreve a nova linha no arquivo de saída
                escritor.write(novaLinha);
                escritor.newLine();

                System.out.println("Linha alterada com sucesso. Valor antigo: " + valorAntigo + ", Novo valor: 0" + valorAntigo.substring(1));
            } else {
                // Se a linha não contém o valor a ser alterado, escreve a linha original no arquivo de saída
                escritor.write(linha);
                escritor.newLine();
            }
        }

        // Fecha os arquivos
        leitor.close();
        escritor.close();

        // Remove o arquivo original
        File arquivoOriginal = new File("movie.db");
        arquivoOriginal.delete();

        // Renomeia o arquivo de saída para o nome do arquivo original
        File arquivoNovo = new File("arquivo_novo.txt");
        arquivoNovo.renameTo(arquivoOriginal);

    } catch (IOException e) {
        System.out.println("Erro ao ler ou escrever o arquivo.");
        e.printStackTrace();
    }
  } //fim deletarDados()


  public static Movies getUsuario(int ID)throws Exception{ //passa o ID e retorna o objeto Movie referente ao ID
    String valorComparar = System.console().readLine();
    Movies movie = new Movies();

    try {
        // Abre o arquivo para leitura
        BufferedReader leitor = new BufferedReader(new FileReader("movie.db"));

        String linha;
        
        //SimpleDateFormat formato = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        // Lê o arquivo linha por linha
        while ((linha = leitor.readLine()) != null) {
            String[] partes = linha.split("#");
            // Verifica se a linha contém o valor a ser alterado
            if (linha.contains("*" + valorComparar + "*")) {
              

              movie.setTombstone(Integer.parseInt(partes[0]));
              movie.setSize(Integer.parseInt(partes[1]));
              movie.setMovieName(partes[3]);
              //Date data = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(partes[4]);
              //movie.setReleaseDate(data);
              movie.setCategory(partes[5]);
              movie.setRunTime(partes[6]);
              movie.setGenre(partes[7]);
              movie.setImdbRating(Float.parseFloat(partes[8]));
              movie.setVotes(Integer.parseInt(partes[9]));
              movie.setGrossTotal(partes[10]);

              

              //System.out.println("ARQUIVO: " + linha);

            }

          
        }

        // Fecha os arquivos
        leitor.close();

        // Remove o arquivo original


    } catch (IOException e) {
        System.out.println("Erro ao ler ou escrever o arquivo.");
        e.printStackTrace();
    }
    return movie;
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

  public static int seek() {
    String previousID = "";
    String temp = "";

    File file = new File("movie.db");
    RandomAccessFile fileHandler = null;

    try {
      fileHandler = new RandomAccessFile( file, "r" );
      long fileLength = fileHandler.length() - 1;
      StringBuilder sb = new StringBuilder();

      for(long filePointer = fileLength; filePointer != -1; filePointer--){
          fileHandler.seek( filePointer );
          int readByte = fileHandler.readByte();

          if( readByte == 0xA ) {
              if( filePointer == fileLength ) {
                  continue;
              }
              break;
              
          } else if( readByte == 0xD ) {
              if( filePointer == fileLength - 1 ) {
                  continue;
              }
              break;
          }

          sb.append( ( char ) readByte );
      }

      String lastLine = sb.reverse().toString();
      for (int i = 0; i < lastLine.length(); i++) {
        if(lastLine.charAt(i) == '*'){
          for(int j = i+1; j < lastLine.length(); j++){
            temp += lastLine.charAt(j);
          }
        }
      }
      for (int i = 0; temp.charAt(i) != '*' && i < temp.length(); i++){
        previousID += temp.charAt(i);
      }
    // Exibir o valor da variável previousID
    
    
  } catch( java.io.FileNotFoundException e ) {
      e.printStackTrace();
  } catch( java.io.IOException e ) {
      e.printStackTrace();
  } finally {
      if (fileHandler != null )
          try {
              fileHandler.close();
          } catch (IOException e) {
              /* ignore */
          }
  }
  return Integer.parseInt(previousID);
  }

}//fim class AreaPessoal()