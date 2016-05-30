public class PieceSquareBoard extends Piece{

	public PieceSquareBoard(){
		super();
	}

	public void typePiece(int number){
    if (number == 1){
      this.size = 1;
      this.type = "Square";
    }
    else if (number == 2){
      this.size = 2;//tiene 4 casillas por decirlo de una manera, esto puede funcionar para el score (si es que lo usamos asi)
      this.type = "Square";
    }
    else if (number == 3){
      this.size = 3;
      this.type = "Square";
   }
   else if (number == 4){
      this.size = 2;
      this.type = "Horizontal Line";
    }
   else if (number == 5){
      this.size = 3;
      this.type = "Horizontal Line";
   }
   else if (number == 6){
      this.size = 4;
      this.type = "Horizontal Line";
   }
   else if (number == 7){
      this.size = 5;
      this.type = "Horizontal Line";
   }
   else if (number == 8){
      this.size = 2;
      this.type = "Vertical Line";
   }
   else if (number == 9){
     this.size = 3;
     this.type = "Vertical Line";
   }
  else if (number == 10){
     this.size = 4;
     this.type = "Vertical Line";
   }
   else if (number == 11){
     this.size = 5;
     this.type = "Vertical Line";
   }
   else if (number == 12){
     this.size = 3;
     this.type = "L";
   }
   else if (number == 13){
     this.size = 4;
     this.type = "L";
   }
   else if (number == 14){
     this.size = 3;
     this.type = "L Left";
   }
   else if (number == 15){
     this.size = 4;
     this.type = "L Left";
   }
   else if (number == 16){
     this.size = 3;
     this.type = "L Invested";//capaz y se pueda pensar unos mejores nombres para esto.
   }
   else if (number == 17){
     this.size = 4;
     this.type = "L Invested";
   }
   else if (number == 18){
     this.size = 3;
     this.type = "L Invested Left";
   }
   else{
     this.size = 4;
     this.type = "L Invested Left";
   }
  }
}
