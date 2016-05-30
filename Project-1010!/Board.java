import java.util.Arrays;

public abstract class Board{
  protected byte id;
  protected Box[] boxes;
  protected int numberOfBoxes;
  protected boolean[][] neighborhood;
  protected Layer[] layers;
  protected byte dimension; //pueden ser filas y columnas o un atributo que defina las casillas
  /* Buscar la manera de no tener la dimension, porque en un tablero en forma de rombo ya la dimension como la definimos
     no nos serviria. O cualquier otro tipo de tablero que no sea ni cuadrado o rectangular*/

  public Board(byte dimension, byte id){
    this.dimension = dimension;
    this.numberOfBoxes = dimension*dimension;
    this.id = id;
    //this.numberOfBoxes = columns*rows;
    boxes = new Box[numberOfBoxes];
    for (byte counter = 0; counter < numberOfBoxes; counter++) { //no creo que se necesiten mas de 127 casillas, digo que el max sera 100.
      boxes[counter] = new Box(counter);
    }
    neighborhood = new boolean[numberOfBoxes][numberOfBoxes];
    for (int counterRows = 0; counterRows < numberOfBoxes; counterRows++) {
      for (int counterColumns = 0; counterColumns < numberOfBoxes; counterColumns++) {
          neighborhood[counterRows][counterColumns] = false;
      }
    }
  }

  public Box getBox(int positionBox){
    return this.boxes[positionBox];
  }
  public byte getIDBoard(){
    return this.id;
  }

  public abstract void shapeBoard();

  public abstract boolean checkPositions(Piece piece, int positionBox);

  public abstract int deleteRowColumn();

  public void printNeighborhood(){
    for (int counterRows = 0; counterRows < numberOfBoxes; counterRows++) {
      for (int counterColumns = 0; counterColumns < numberOfBoxes; counterColumns++)
          System.out.print("| " + neighborhood[counterRows][counterColumns] + " | ");
      System.out.println();
    }
  }

  public void testDelete(){ //Metodo solo de prueba, para verificar la liberacion de filas y columnas, tambien sumar puntaje.
    Status fullBox = new FullBox();
    byte counterJump = 0, positionBoxR = 10, positionBoxC = 7, positionBoxR2 = 20;
    while(counterJump < dimension){
      boxes[positionBoxR].setStatus(fullBox);
      boxes[positionBoxC].setStatus(fullBox);
      boxes[positionBoxR2].setStatus(fullBox);
      positionBoxR ++;
      positionBoxR2 ++;
      if ((positionBoxC + dimension) <= numberOfBoxes){
        positionBoxC += dimension;
      }
      counterJump ++;
    }
  }

  public void printLayers(){ // REVISAR ESTO!!!!!!!
    for (byte counter = 9; counter < numberOfBoxes; counter ++) {
      System.out.println("Casilla" + boxes[counter].getIdBox() + " pertenece a la capa: " + boxes[counter].getLayer());
    }
  }
}
