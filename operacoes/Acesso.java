package operacoes;

import entidades.usuario.*;//todos as classes do pacote de usuarios

public class Acesso {
  public static CrudUsuario crudUsuario;
  public static Movies customerData;
  public static int iDUsuario = -1;
  
	// public static boolean cadastro()throws Exception{
  //   Utils.pulaLinha(2);
  //   System.out.println("**** CADASTRO ****");
  //   String name, email, userName, password, cpf, city;
  //   boolean cadastroConfimado;

  //   name = Utils.leString("nome","o");
  //   email = novoEmail();
  //   password = Utils.leString("senha", "a");
  //   userName = Utils.leString("Nome de Usuario (apelido)", "o");
  //   cpf = novoCPF();
  //   city = Utils.leString("Cidade", "a");
  //   Utils.pulaLinha(5);

  //   System.out.println("**** DADOS CADASTRAIS ***");
  //   System.out.println("-> Nome: " + name + "\n-> e-mail: " + email + "\n-> senha: " + password + "\n-> Nome de Usuario: " + userName + "\n-> CPF: " + cpf + "\n-> Cidade: " + city);
  //   System.out.print("Voce gostaria de confirmar o cadastro? (S/N)");
  //   cadastroConfimado = Utils.lerConfirmacao();
  //   Utils.pulaLinha(5);

  //   if (cadastroConfimado) {
  //     System.out.println("Cadastro confirmado!");
  //     setIDUsuario(crudUsuario.create(new Movies(name, email, userName, password, cpf, city)));
  //   } else
  //     System.out.println("Cadastro cancelado");
  //   return cadastroConfimado;
  // }
  
  // public static String novoEmail()throws Exception{
  //   String email;
  //   boolean emailValidado = false;
  //   do{
  //     email = Utils.leString("email","o");
  //     emailValidado = (buscaEmail(email) == false);
  //     if(!emailValidado){
  //       System.out.println("Email ja existente em nosso banco de dados!");
  //       System.out.println("Por favor, insira novamente");
  //       Utils.pulaLinha(5);
  //     }
  //   }while(!emailValidado);
  //   return email;
  // }

  // public static String novoCPF()throws Exception{
  //   String CPF;
  //   boolean invalido = false;
  //   do{
  //     CPF = Utils.leCPF("CPF","o");
  //     invalido = (buscaCPF(CPF) == false);
  //     if(!invalido){
  //       System.out.println("CPF ja existente em nosso banco de dados!");
  //       System.out.println("Por favor, insira novamente");
  //       Utils.pulaLinha(5);
  //     }
  //   }while(!invalido);
  //   return CPF;
  // }

  // public static boolean novaSenha()throws Exception{
  //   Utils.pulaLinha(2);
  //   System.out.println("**** TROCA DE SENHA ****");
  //   String email,senhaNova;
  //   boolean senhaTrocada = false;
  //   email = Utils.leString("email para efetuar a troca de senha", "o");
  //   if(buscaEmail(email)){
  //     System.out.println("Um email de redefinicao de senha foi enviado para " + email + " (Nao enviamos de verdade)...");
  //     senhaNova = Utils.leString("senha","a");
  //     Movies atualizado = crudUsuario.read(email);
  //     atualizado.setSenha(senhaNova);
  //     crudUsuario.update(atualizado);
      
  //     System.out.println(crudUsuario.read(atualizado.getEmail()));
  //     Utils.pulaLinha(2);
  //     System.out.println("Senha alterada com sucesso");
  //     Utils.pulaLinha(1);
  //     senhaTrocada = true;
    
  //   }
  //   else{
  //     Utils.pulaLinha(2);
  //     System.out.println("e-mail invalido");
  //   }
  //   return senhaTrocada;
  // }

	// public static boolean login()throws Exception{
  //   Utils.pulaLinha(2);
  //   System.out.println("**** Login ****");
  //   boolean logado = false;
  //   String email, senha;
  //   email = Utils.leString("email", "o");

  //   if(!buscaEmail(email)){
  //     System.out.println("O email " + email + " nao existe em nosso sistema");
  //     Utils.pulaLinha(2);
  //   }
  //   else{
  //     senha = Utils.leString("senha", "a");
  //     Movies user = crudUsuario.read(email);
  //     if(senha.equals(user.getSenha())){
  //       iDUsuario = user.getID();
  //       logado = true;
  //     }
  //     else 
  //       System.out.println("Senha ou o email incorretos");
  //   }
  //   return logado;
	// }

  // public static String mostrarDadosCliente(int ID)throws Exception {
  //   if (crudUsuario.read(ID) != null) {
  //     	return crudUsuario.read(ID).toString();
  //   } else {
  //     return "\nNumero da conta Invalida\n";
  //   }
  // }

  // public static Movies getUsuario(int ID)throws Exception{ //passa o ID e retorna o objeto Movie referente ao ID
  //   return crudUsuario.read(ID);
  // }

  // public static void updateMovie(Movies cd)throws Exception{ //recebe um objeto Movie com os dados modificados
  //   crudUsuario.update(cd);
  // }

  // public static void deleteMovie(Movies cd)throws Exception{ //recebe um objeto Movie e o apaga, tbm pode ser adptado para receber um ID.
  //   crudUsuario.delete(cd);
  // }
}
