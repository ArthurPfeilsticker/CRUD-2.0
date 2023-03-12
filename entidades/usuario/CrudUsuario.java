package entidades.usuario;

import java.io.File;
import estruturas.CRUD;

public class CrudUsuario extends CRUD<Movies> {

  public CrudUsuario(String nomeArquivo) throws Exception {
    super(nomeArquivo, Movies.class.getConstructor());

    File pasta = new File("dados");
    if (!pasta.exists()) {
      pasta.mkdir();
    }

    pasta = new File("dados/" + nomeArquivo);
    if (!pasta.exists()) 
      pasta.mkdir();
  }

  public int create(Movies user) throws Exception {
    int idUser = super.create(user);
    return idUser;
  }
  
  public Movies read(int ID) throws Exception {
    return super.read(ID);
  }

  public boolean update(Movies user) throws Exception {
    boolean userUpdated =  super.update(user);
    return userUpdated;
  }
  
  public void delete(Movies user) throws Exception {
    super.delete(user.getID());
    
  }
}
