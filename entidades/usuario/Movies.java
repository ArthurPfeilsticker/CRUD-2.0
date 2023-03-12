package entidades.usuario;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import estruturas.Registro;

public class Movies implements Registro {
  protected int tombstone;
  protected int size;
  protected int id;
  protected String movieName;
  protected Date releaseDate;
  protected String category;    // tornar uma string de tamanho fixo ( maior tamanho possivel é 6)
  protected String runTime;
  protected String genre; // tornar uma lista e/ou array de string
  protected float imdbRating;
  protected int votes;
  protected String grossTotal;
  
  // Constructors ------------------

  public Movies(int tombstone, int size, int id, String movieName, Date releaseDate, String category, String runTime, String genre, Float imdbRating, int votes, String grossTotal) {
    this.tombstone = tombstone;
    this.size = size;
    this.id = id;
    this.movieName = movieName;
    this.releaseDate = releaseDate;
    this.category = category;
    this.runTime = runTime;
    this.genre = genre;
    this.imdbRating = imdbRating;
    this.votes = votes;
    this.grossTotal = grossTotal;
  }

  public Movies() {
    this.tombstone = 0;
    this.size = 0;
    this.id = -1;
    this.movieName = "";
    this.releaseDate = null;
    this.category = "";
    this.runTime = "";
    this.genre = "";
    this.imdbRating = 0;
    this.votes = 0;
    this.grossTotal = "";
  }

  // SET methods ------------------------

  public void setTombstone(int tombstone) {
    this.tombstone = tombstone;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public void setID(int id) {
    this.id = id;
  }
  public void setMovieName(String movieName) {
    this.movieName = movieName;
  }
  public void setReleaseDate(Date releasDate) {
    this.releaseDate = releasDate;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  public void setRunTime(String runTime) {
    this.runTime = runTime;
  }
  public void setGenre(String genre) {
    this.genre = genre;
  }
  public void setImdbRating(float imdbRating) {
    this.imdbRating = imdbRating;
  }
  public void setVotes(int votes) {
    this.votes = votes;
  }
  public void setGrossTotal(String grossTotal) {
    this.grossTotal = grossTotal;
  }

  // GET methods ------------------------

  public int getTombstone(){
    return this.tombstone;
  }

  public int getSize() {
    return this.size;
  }

  public int getID() {
    return this.id;
  }
  public String getMovieName() {
    return this.movieName;
  }
  public Date getReleaseDat() {
    return this.releaseDate;
  }
  public String getCategory() {
    return this.category;
  }
  public String getRunTime() {
    return this.runTime;
  }
  public String getGenre() {
    return this.genre;
  }
  public float getImdbRating() {
    return this.imdbRating;
  }
  public int getVotes() {
    return this.votes;
  }
  public String getGrossTotal() {
    return this.grossTotal;
  }

 // ------------------------

  public String toString() {
    return "\n" + this.tombstone + //tombstone
    "  " + this.size + //size
    "  " + this.id + //id
    "  " + this.movieName + //movieName
    "  " + this.releaseDate + //releaseDate
    "  " + this.category + //category
    "  " + this.runTime + //runTime
    "  " + this.genre + //genre
    "  " + this.imdbRating + //imdbRating
    "  " + this.votes + //votes
    "  " + this.grossTotal; //grossTotal
  }

  public byte[] toByteArray() throws IOException{
    // escreve para memoria
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(tombstone);
    dos.writeInt(size);
    dos.writeInt(id);
    dos.writeUTF(movieName);
    dos.writeUTF(releaseDate.toString()); //data em formato string
    dos.writeUTF(category);
    dos.writeUTF(runTime);
    dos.writeUTF(genre);
    dos.writeFloat(imdbRating);
    dos.writeInt(votes);
    dos.writeUTF(grossTotal);

    return baos.toByteArray(); // Representacao no vetor de bytes
  }
  
  public Movies fromByteArray(byte[] ba) throws IOException {
    Movies teste = new Movies();

    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    tombstone = dis.readInt();
    size = dis.readInt();
    id = dis.readInt();
    movieName = dis.readUTF();
    ((DataInput) releaseDate).readUTF(); //corrigir leitura de um date no arquivo
    category = dis.readUTF();
    runTime = dis.readUTF();
    genre = dis.readUTF();
    imdbRating = dis.readFloat();
    votes = dis.readInt();
    grossTotal = dis.readUTF();

    return teste;
  }
}
