import java.util.Scanner;

import entidades.usuario.CrudUsuario;
import operacoes.*;//import todas as operações

class Main {
  public static Scanner input;
  public static CrudUsuario crudUsuario;
  public static void main(String[] args) throws Exception {
    boolean usuarioLogado;
    input = new Scanner(System.in);
    TelaInicial.dataLoading();
    try{
      do{
        usuarioLogado = TelaInicial.telaInicial();
      }while(usuarioLogado);
      System.out.println("\nObrigado por usar o nosso CRUD");
    } catch(Exception ex){
      ex.printStackTrace();
    }

  }//fim void main()
  
}//fim Class Main()


/*
 * o Arquivo CRUD esta em erro pois precisa ser adptado, porem usando o visualCode 
 * e cliclar no icone de Run (localizado no canto superior direito)
 * ele funciona perfeitamente
 */