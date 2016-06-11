import java.util.Random;
public enum PieceSquareBoard implements Piece{
	SQUARE3("SQUARE",3), SQUARE2("SQUARE",2),SQUARE1("SQUARE",1),
	HORIZONTALLINE2("HORIZONTAL LINE", 2), HORIZONTALLINE3("HORIZONTAL LINE", 3), HORIZONTALLINE4("HORIZONTAL LINE", 4), HORIZONTALLINE5("HORIZONTAL LINE", 5),
	VERTICALLINE2("VERTICAL LINE", 2), VERTICALLINE3("VERTICAL LINE", 3), VERTICALLINE4("VERTICAL LINE", 4), VERTICALLINE5("VERTICAL LINE", 5),
	L3("L",3), L4("L",4), LLEFT3("L LEFT", 3), LLEFT4("L LEFT", 4), LINVESTED3("L INVESTED", 3), LINVESTED4("L INVESTED", 4), LINVESTEDLEFT3("L INVESTED LEFT", 3), LINVESTEDLEFT4("L INVESTED LEFT", 4);

	private final String TYPE_PIECE;
	private final int SIZE_PIECE;
	private static final PieceSquareBoard[] VALUES = values();
	private static final int SIZE = VALUES.length;
  private static final Random RANDOM = new Random();

	PieceSquareBoard(String typePiece ,int size){
		this.TYPE_PIECE = typePiece;
		this.SIZE_PIECE = size;
	}

	public String getTypePiece(){
		return TYPE_PIECE;
	}

	public byte getSizePiece(){
		return (byte)SIZE_PIECE;
	}

  public static PieceSquareBoard getRandomPiece(){
    return VALUES[RANDOM.nextInt(SIZE)];
  }
}
