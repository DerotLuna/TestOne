import java.awt.Color;

public class SquareBoard extends Board{


  public SquareBoard(byte dimension, byte id){
    super(dimension, id);
    layers = new Layer[dimension * 2];
    byte counterLayers = 0, counterRows = 0, counterColumns = 0, addColumns = (byte) (dimension / 2);
    while(counterLayers < dimension){
      layers[counterLayers] = new Layer("Row " + counterLayers, dimension);
      layers[counterLayers + addColumns] = new Layer("Column " + counterLayers, dimension);
      while(counterColumns < dimension){
        boxes[counterRows].setLayer(layers[counterLayers]);
        boxes[counterColumns].setLayer(layers[counterLayers + addColumns]);
        layers[counterLayers].setBoxToLayer(boxes[counterRows]);
        layers[counterLayers + addColumns].setBoxToLayer(boxes[counterColumns]);
        counterRows ++;
        counterColumns += dimension;
      }
      counterLayers ++;
      counterColumns = counterLayers;
    }

    super.printLayers();
  }

  public void shapeBoard(){
    byte rowAndColumn = 0;
    byte counterDown = dimension;
    byte counter = (byte)(counterDown - 1);
    while (rowAndColumn < (numberOfBoxes - 1)){
      if(rowAndColumn <= counter){
        if (counter < numberOfBoxes - 1){
          neighborhood[rowAndColumn][counterDown] = true;
          neighborhood[counterDown][rowAndColumn] = true;
        }
        if (rowAndColumn < counter){
          neighborhood[rowAndColumn + 1][rowAndColumn] = true;
          neighborhood[rowAndColumn][rowAndColumn + 1] = true;
        }
      }
      if (rowAndColumn == counter) counter = counterDown;
      if (counterDown < numberOfBoxes - 1) counterDown ++;
      rowAndColumn ++;
    }
  }

  public boolean checkPositions(Piece piece, int positionBox){
    boolean checked = true;
    //creo que el pivote que definiremos seria un pivote logico, no podemos tener una variable, o no la veo por lo menos.
    String typePiece = piece.getTypePiece();
    byte sizePiece = piece.getSizePiece();
    Color color = piece.getColorPiece();
    if (typePiece == "Square") checked = squareEvaluations(sizePiece, positionBox, checked);
    else if (typePiece == "Vertical Line") checked = lineEvaluations(sizePiece, positionBox, 2, checked);
    else if (typePiece == "Horizontal Line") checked = lineEvaluations(sizePiece, positionBox, 1, checked);
    else if(typePiece == "L") checked = lEvaluation(sizePiece, positionBox, checked);
    else if(typePiece == "L Left") checked = leftLEvaluation(sizePiece, positionBox, checked);
    else if (typePiece == "L Invested") checked = investedLEvaluation(sizePiece, positionBox, checked);
    else checked = investedLEvaluation(sizePiece, positionBox, checked);
    if (checked == true){
      addPiece(typePiece, sizePiece, positionBox , color);
    }
    return checked;
  }

  public boolean squareEvaluations(byte sizeSquare, int positionBox, boolean checked){
    String status = "FREE";

    if (sizeSquare == 1){
      if (boxes[positionBox].getStatus() != status) checked = false;
    }
    else{ //sera general para todos las dimensiones de los cuadrados
      int positionBoxDown = positionBox + dimension;
      int referenceBox = positionBoxDown;
      byte counterExit = 1;
      exit:
      while(counterExit < sizeSquare){
        byte counterJump = 1;
        while(counterJump < sizeSquare){ //estar pendiente de la 2da condicion
          if(positionBoxDown >= numberOfBoxes || !neighborhood[positionBox][positionBox + 1]){
            checked = false;
            break exit;
          }
          else{
            if(boxes[positionBox].getStatus() == status && boxes[positionBox + 1].getStatus() == status && boxes[positionBoxDown].getStatus() == status){
              positionBox ++;
              positionBoxDown ++;
            }
            else{
              checked = false;
              break exit;
            }
          }
          counterJump ++;
        }

        if (counterExit == sizeSquare - 1){
          positionBoxDown ++;
          if (boxes[positionBoxDown].getStatus() != status) checked = false;
        }
        counterExit ++;
        positionBox = referenceBox;
        positionBoxDown = referenceBox + dimension;
        referenceBox = positionBoxDown;
      }
    }
    return checked;
  }

  public boolean lineEvaluations(byte sizeLine, int positionBox, int option, boolean checked){
    String status = "FREE";
    byte counterExit = 0;

      if(option == 1){
        while(counterExit < sizeLine){

          if (counterExit == sizeLine - 1){
            if(boxes[positionBox].getStatus() == status) positionBox ++;
            else{
              checked = false;
              break;
            }
          }
          else{
            if ((positionBox > numberOfBoxes - sizeLine)|| !neighborhood[positionBox][positionBox + 1]){
              checked = false;
              break;
            }
            else{
              if(boxes[positionBox].getStatus() == status) positionBox ++;
              else{
                checked = false;
                break;
              }
            }
          }
        counterExit ++;
        }
      }
      else{
        while(counterExit < sizeLine && positionBox <= numberOfBoxes){ //estar pendiente de la 2da condicion

          if (counterExit == sizeLine - 1){
            if(boxes[positionBox].getStatus() == status) positionBox ++;
            else{
              checked = false;
              break;
            }
          }
          else{
            if((positionBox >= numberOfBoxes - dimension) || !neighborhood[positionBox + dimension][positionBox]){
              checked = false;
              break;
            }
            else{
              if(boxes[positionBox].getStatus() == status) positionBox += dimension;
              else{
                checked = false;
                break;
              }
            }
          }
        counterExit ++;
        }
      }
    return checked;
  }

  public boolean lEvaluation(byte lSize ,int positionBox ,boolean checked){

    int pivot = positionBox;
    byte exitCounter = 0;
    String status = "FREE";

    while(exitCounter < lSize){
      if ((positionBox > numberOfBoxes) || (positionBox >= numberOfBoxes - dimension) || !(neighborhood[pivot][pivot + 1])){ //falta un condicional
        checked = false;
        break;
      }
      else {
        if (pivot == positionBox){
          if ((boxes[pivot].getStatus() == status) && (boxes[pivot + 1].getStatus() == status)) pivot += dimension;
          else{
            checked = false;
            break;
          }
        }
        else {
          if (((boxes[pivot].getStatus()) == status)) pivot++;
          else{
            checked = false;
            break;
          }
        }
      }
      exitCounter ++;
     }
    return checked;
  }

  public boolean leftLEvaluation(byte sizeOfPiece ,int positionBox ,boolean checked){
      byte exitCounter = 0;
      int counterJump = positionBox - dimension;
      String status = "FREE";

      while(exitCounter < sizeOfPiece){

        if ((positionBox <= dimension) || (positionBox > numberOfBoxes) || !(neighborhood[counterJump][counterJump + 1])){ //or (neighborhood[][])
          checked = false;
          break;
        }
        else {
          if ((boxes[positionBox].getStatus() == status) && (boxes[counterJump + 1].getStatus() == status))positionBox++;
          else{
            checked = false;
            break;
          }

          if (exitCounter == (sizeOfPiece - 1)){
            positionBox -= dimension;
            if ((boxes[positionBox].getStatus() == status) && (boxes[positionBox + 1].getStatus() == status)) positionBox++;
            else{
              checked = false;
              break;
            }
          }
        }
        exitCounter++;
      }
      return checked;
  }

  public boolean investedLEvaluation(byte sizeOfPiece ,int positionBox ,boolean checked){

    byte exitCounter = 0;
    int upCounter = positionBox - dimension;
    String status = "FREE";

    while(exitCounter < sizeOfPiece){
      if (upCounter < 0){
        checked = false;
        break;
      }

      if ((positionBox > numberOfBoxes)){
        checked = false;
        break;
      }
      else {
        if (exitCounter == 0){
          if (!neighborhood[upCounter][positionBox]){
            checked = false;
            break;
          }
          else{
            if ((boxes[positionBox].getStatus() == status) && (boxes[upCounter].getStatus() == status)) positionBox -= dimension;
            else {
              checked = false;
              break;
            }
          }
        }
        else {
          if(!neighborhood[upCounter][positionBox + 1]){
            checked = false;
            break;
          }
          else{
            if (boxes[positionBox + 1].getStatus() == status){
              positionBox ++;
              upCounter++;
            }
            else{
              checked = false;
              break;
            }
          }
        }
      }
      exitCounter ++;
    }
    return checked;

  }

  public boolean leftInvestedLEvaluation(byte sizeOfPiece ,int positionBox ,boolean checked){
    byte exitCounter = 0;
    byte counterJump = 1;
    String status = "FREE";

    while (exitCounter < sizeOfPiece){

      if ((positionBox > numberOfBoxes) || (positionBox >= numberOfBoxes - dimension)){
        checked = false;
        break;
      }
      else {
        if (counterJump == sizeOfPiece - 1){
            if (!neighborhood[positionBox][positionBox + dimension]){
              checked = false;
              break;
            }
            else {
              if (boxes[positionBox].getStatus() == status) positionBox += dimension;
              else {
                checked = false;
                break;
              }
            }
        }
        else if (counterJump < sizeOfPiece -1){
          if (!neighborhood[positionBox][positionBox + 1]){
            checked = false;
            break;
          }
          else {
            if (boxes[positionBox].getStatus() == status) positionBox ++;
            else {
              checked = false;
              break;
            }
          }
        }
        else {
          if (boxes[positionBox].getStatus() == status) positionBox ++;
          else{
            checked = false;
            break;
          }
        }
        counterJump++;
        exitCounter++;
      }
    }
    return checked;
  }

  public void addSquare(byte sizeSquare, int positionBox, Color color){
    Status fullBox = new FullBox();
    if(sizeSquare == 1){
      boxes[positionBox].setStatus(fullBox);
      boxes[positionBox].setColorBox(color);
    }
    else{
      int positionBoxDown = positionBox + dimension;
      byte counterExit = 1;
      while(counterExit <= sizeSquare){
        byte counterJump = 1;
        while(counterJump <= sizeSquare){
          boxes[positionBox].setStatus(fullBox);
          boxes[positionBox].setColorBox(color);
          counterJump ++;
          positionBox++;
        }
        positionBox = positionBoxDown;
        positionBox += dimension;
        counterExit ++;
      }
    }
    System.out.println("positionBox: " + positionBox);
  }

  public void addLine(byte sizeLine, int positionBox, int answer, Color color){
    Status fullBox = new FullBox();
    byte counterExit = 1;
    while(counterExit <= sizeLine){
      boxes[positionBox].setStatus(fullBox);
      boxes[positionBox].setColorBox(color);
      counterExit ++;
      System.out.println("positionBox: " + positionBox);
      if(answer == 1)  positionBox++;
      else positionBox += dimension;
    }
  }

  public void addL(byte sizeL, int positionBox, Color color){
      Status fullBox = new FullBox();
      byte exitCounter = 0;

      while (exitCounter < sizeL){
        boxes[positionBox].setStatus(fullBox);
        boxes[positionBox].setColorBox(color);
          if (exitCounter == 0) positionBox += dimension;
          else positionBox ++;
        exitCounter ++;
      }
    }


    public void addLeftL(byte sizeLeftL, int positionBox, Color color){
      Status fullBox = new FullBox();
      byte exitCounter = 0;
      byte counterJump = 1;

      while (exitCounter < sizeLeftL){
        boxes[positionBox].setStatus(fullBox);
        boxes[positionBox].setColorBox(color);
          if (counterJump == sizeLeftL - 1) positionBox -= dimension;
          else positionBox ++;
        exitCounter ++;
        counterJump ++;
      }
    }

    public void addInvestedL(byte sizeInvestedL, int positionBox, Color color){
      Status fullBox = new FullBox();
      byte exitCounter = 0;

      while (exitCounter < sizeInvestedL){
        boxes[positionBox].setStatus(fullBox);
        boxes[positionBox].setColorBox(color);
          if (exitCounter == 0) positionBox -= dimension;
          else positionBox ++;
        exitCounter ++;
      }
    }

    public void addLeftInvestedL(byte sizeInvestedLeftL, int positionBox, Color color){
      Status fullBox = new FullBox();
      byte exitCounter = 0;
      byte counterJump = 1;
      while (exitCounter < sizeInvestedLeftL){
        boxes[positionBox].setStatus(fullBox);
        boxes[positionBox].setColorBox(color);
          if (counterJump == sizeInvestedLeftL - 1)positionBox += dimension;
          else positionBox ++;
        counterJump ++;
        exitCounter ++;
      }
    }

    public void addPiece(String typePiece, byte sizePiece,int positionBox ,Color color){
      if (typePiece == "Square") addSquare(sizePiece, positionBox, color);
      else if (typePiece == "Vertical Line") addLine(sizePiece, positionBox, 2, color);
      else if (typePiece == "Horizontal Line") addLine(sizePiece, positionBox, 1, color);
      else if (typePiece == "L") addL(sizePiece, positionBox, color);
      else if (typePiece == "L hacia la izquierda") addLeftL(sizePiece, positionBox, color);
      else if (typePiece == "L invertida") addInvestedL(sizePiece, positionBox, color);
      else  addLeftInvestedL (sizePiece, positionBox, color);
    }

  public void liberateBoxes(int[] delete, byte storedElements, byte option){
    Status freeBox = new FreeBox();
    int counterExit = 0, positionBox = 0;

    System.out.println("Liberando casillas!!");

    if(option == 1){ //aqui liberamos casillas en fila
      while(counterExit <= storedElements){
        byte counterJump = 0;
        positionBox = delete[counterExit];
        while(counterJump < dimension){
          boxes[positionBox].setStatus(freeBox);
          positionBox ++;
          counterJump ++;
        }
        counterExit ++;
      }
    }
    else{ //aqui liberamos casillas en columna
      int finalBox = ((numberOfBoxes) - dimension) + delete[0];
      while(counterExit <= storedElements){
        if (counterExit != 0) finalBox += delete[counterExit] - delete[counterExit - 1];
        positionBox = delete[counterExit];

        while(positionBox <= finalBox){
          boxes[positionBox].setStatus(freeBox);
          positionBox += dimension;
        }
        counterExit ++;
      }
    }
  }

  public int deleteRowColumn(){
    int[] deleteRows = new int[dimension]; //almacenador de la primera casilla de la fila a eliminar
    int[] deleteColumns = new int[dimension]; //almacenador de la primera casilla de la columna a eliminar
    byte positionDeleteRows = 0, positionBoxDown = dimension, positionBox = 0, rowColumn = 0, counterForScore = 0;
    String status = "FULL";
    while(positionBox < numberOfBoxes){ //evaluo todas las filas
      if(boxes[positionBox].getStatus() != status){
        //salto hacia la siguiente fila si en la fila que estoy hay una casilla libre
        positionBox = positionBoxDown;
        positionBoxDown += dimension;
        rowColumn = positionBox;
      }
      else{
        if(positionBox == positionBoxDown - 1){
          deleteRows[positionDeleteRows] = rowColumn;
          positionDeleteRows ++;
          rowColumn = positionBoxDown;
          positionBoxDown += dimension;
          counterForScore ++;
          System.out.println("Fila");
        }
        positionBox ++;
      }
    }

    positionBox = 0; rowColumn = 0;
    byte positionBoxNext = (byte) (positionBox + 1), positionDeleteColumns = 0; int finalBox = (numberOfBoxes) - dimension;
    while(rowColumn < dimension && positionBox < numberOfBoxes){ //evaluo todas las columnas
      if(boxes[positionBox].getStatus() != status){
        //salto hacia la siguiente fila si en la fila que estoy hay una casilla libre
        positionBox = positionBoxNext;
        positionBoxNext ++;
        rowColumn ++;
        finalBox ++;
      }
      else{
        if(positionBox == finalBox){
          deleteColumns[positionDeleteColumns] = rowColumn;
          positionDeleteColumns ++;
          rowColumn ++;
          positionBoxNext ++;
          finalBox ++;
          counterForScore ++;
          System.out.println("Columna");
        }
        positionBox += dimension;
      }
    }

    byte idRow = 1, idColumn = 2;
    if (counterForScore != 0) {
      liberateBoxes(deleteRows, positionDeleteRows, idRow); //1 es para decir que es fila
      liberateBoxes(deleteColumns, positionDeleteColumns, idColumn); //2 es para decir que es columna
    }
    return counterForScore;
  }
}
