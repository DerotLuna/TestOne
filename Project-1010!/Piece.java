import java.util.Random;
import java.awt.Color;

public abstract class Piece{
  private static final Color[] COLORS = {Color.red, Color.blue, Color.green, Color.yellow, Color.pink, Color.magenta, Color.cyan};
  private static final byte NUMBER_OF_PIECE = 19;
  protected String type; // hay tres tipos en 1010: cuadrado, linea, l (solo en tablero cuadrado)
  protected byte size;
  /*El tamaño varia en el tablero cuadrado, los cuadrados solo almacenan su dimension,
    las lineas y las l´s si toman como tamaño en numero de casillas que abarcarian.*/
  protected Color color;
  //private byte id;

  Random randomNumber = new Random();
  public Piece(){
    int randomPiece = (int)(randomNumber.nextDouble() * NUMBER_OF_PIECE + 1);
    color = COLORS[randomNumber.nextInt(COLORS.length)];
     /*Pido de mi arreglo PIECE_COLORS que me retorne un color random en una de las posiciones*/
    typePiece(randomPiece);
    //this.id = id;
  }

  public String getTypePiece(){
    return this.type;
  }

  public byte getSizePiece(){
    return this.size;
  }

  public Color getColorPiece(){
    return this.color;
  }

  public abstract void typePiece(int number);
}
