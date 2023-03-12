package estruturas;
import java.io.IOException;
import entidades.usuario.Movies;


public interface Registro {
  public void setID(int n);
  public int getID();
  public byte[] toByteArray() throws IOException;
  public Movies fromByteArray(byte[] ba) throws IOException;
}