import java.util.Arrays;
import java.util.Scanner;

public class World{
  private static final byte LIMIT_CAPSULE = 3;
  private int score;
  //private Menu subMenu; //atributo posible, o puede que sea un metodo!!!
  private Player player;
  private Piece[] capsule;
  private Board board;
  private byte dimensionBoard;

  Scanner scanner = new Scanner(System.in);

  public World(Player player, byte dimension, byte idBoard){
    this.player = player;
    this.score = 0; //validar que el juego sigue o si es una partida nueva
    this.capsule = new PieceSquareBoard[LIMIT_CAPSULE];
    //capsule contendra mis piezas
    this.dimensionBoard = dimension;
    if (idBoard == 1){
      this.board = new SquareBoard(dimensionBoard, idBoard); //instacio mi tablero cuadrado
      //se generan las piezas principales
      for (byte counterOfPieces = 0; counterOfPieces < LIMIT_CAPSULE; counterOfPieces ++) {
        capsule[counterOfPieces] = new PieceSquareBoard();
      }
    }
    // por ahora se tiene un solo tipo de tablero
  }

  public void worldControl(){
    board.shapeBoard(); //doy forma al tablero
    board.testDelete(); //llamo metodo que llenas 2 filas y 2 columnas para ver si las libera y si suma puntaje

    int positionPiece = 0; byte idBoard = board.getIDBoard();
    while (true){
      System.out.println("Jugador: " + player.getUser() + " Puntaje actual: " + score);
      for (byte counterOfPieces = 0; counterOfPieces < LIMIT_CAPSULE; counterOfPieces ++) {
        System.out.println("Pieza "+ "(" + counterOfPieces + "): " + capsule[counterOfPieces].getTypePiece() + " " + capsule[counterOfPieces].getSizePiece());
      }
      positionPiece = movePiece();
      if (positionPiece != dimensionBoard) //verifico si es necesario crear la pieza
        if (idBoard == 1) // evaluo que tablero uso para asi generar las piezas a medida que se usan
          capsule[positionPiece] = new PieceSquareBoard(); //utilizo una pieza y al final genero otra
      if (scannerAll(3) < dimensionBoard) clear();

    }
  }

  public int movePiece(){
    //byte deltaX = 2, deltaY = 2;
    int positionPiece =  scannerAll(1), positionBox =  scannerAll(2);
    //board.checkPositions(capsule[positionPiece], row, column);
    boolean answer = board.checkPositions(capsule[positionPiece], positionBox);
    String typePiece = capsule[positionPiece].getTypePiece();
    int sizePiece =  capsule[positionPiece].getSizePiece();
    if(answer){
      System.out.println("La pieza: " + typePiece + " " + sizePiece + " puede ser colocada");
      if (typePiece == "Square") score += sizePiece*sizePiece;
      else score += sizePiece;
      int addRelease = board.verifyLayer();
      score += addRelease;
      return positionPiece;
    }
    else{
      System.out.println("La pieza: " + typePiece + " " + sizePiece + " no puede ser colocada");
      return dimensionBoard; // retorno un numero mayor para asi no crear una pieza nueva al regresar a worldControl
    }
      /*Box box = board.getBox(positionBox);
      System.out.println("Estado de la casilla: " + box.getStatusBox() + " Color de la casilla: " + box.getColorBox());

      System.out.println("Jugador: " + player.getUser() + " Puntaje actual: " + score);*/
  }

  public int scannerAll(int option){
    int give_Back = 0; boolean flag = true; int maximun = dimensionBoard*dimensionBoard;
    while(flag == true){
      if (option == 1) System.out.print(" Que pieza desea mover? (0-2): ");
      else if (option == 2) System.out.print(" Posicion casilla referencia (0-99): ");
      else System.out.print(" Pulse 0 para limpiar pantalla: ");
      give_Back = scanner.nextInt(); System.out.print("\n");
      if (option == 1 && (give_Back < LIMIT_CAPSULE) && (give_Back >= 0)) flag = false;
      else if (option != 1 && (give_Back < maximun) && (give_Back >= 0)) flag = false;
      else System.out.print(" Valor incorrecto! \n");
    }
    return give_Back;
  }

  public void clear(){
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
