package operacoes;

import entidades.usuario.CrudUsuario;
import entidades.usuario.Movies;

public class TelaInicial{

  public static Movies cliente;
  public static CrudUsuario crudUsuario;

  public static void dataLoading() throws Exception {

    int escolha;
    System.out.println("\n===================\n");
    System.out.println("CARREGAMENTO DE DADOS\n");
    System.out.println("1) Quero continuar com os dados atuais.");
    System.out.println("2) Regarregar todos os dados");
    System.out.println("\n0) Sair");
    escolha = Utils.leOpcoes(0,2);
    
    if(escolha == 1){
      //verificar se o arquivo ja existe (se nao dar um erro)
      //carregar o arquivo ja existente
    } else if(escolha == 2){
      //verificar se o arquivo já existe
      //carregar um novo arquivo .cvs
      AreaPessoal.newData();
    } else {
      Logout();
    }
  }

  public static boolean telaInicial() throws Exception {
    boolean logado = true;
    int escolha;
    System.out.println("\n===================\n");
    System.out.println("INÍCIO\n");
    System.out.println("1) CRUD");
    System.out.println("2) Ordenacao Externa");
    System.out.println("\n0) Sair");
    escolha = Utils.leOpcoes(0,2);
    
    if(escolha == 1){
      AreaPessoal.menu();
    } else if(escolha == 2){
      //fazer ordenação externa 
      telaInicial();
    } else {
      Logout();
      logado = false;
    }
    return logado;
  }

  public static void Logout() {
    System.out.println("\nLogout feito com sucesso!");
  }

}