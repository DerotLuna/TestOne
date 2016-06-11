import java.awt.Color;

public class SquareBoard extends Board{


  public SquareBoard(byte dimension, byte id){
    super(dimension, id);
    //Aqui hago la creacion de mis capas, por cada fila y columna.
    layers = new Layer[dimension * 2];
    byte counterLayers = 0, counterRows = 0, counterColumns = 0, positionBoxLayer = 0;
    while(counterLayers < dimension){
      layers[counterLayers] = new Layer("Row " + counterLayers, dimension);
      layers[counterLayers + dimension] = new Layer("Column " + counterLayers, dimension);
      positionBoxLayer = 0;
      while(positionBoxLayer < dimension){
        //boxes[counterRows].setLayer(layers[counterLayers]);  // le doy a conocer a casilla a la capa que pertenece
        //boxes[counterColumns].setLayer(layers[counterLayers + dimension]);  // le doy a conocer a casilla a la capa que pertenece
        //verificar si es realmente necesario, que las casillas sepan a que capa pertenecen...
        layers[counterLayers].setBoxToLayer(boxes[counterRows], positionBoxLayer);
        layers[counterLayers + dimension].setBoxToLayer(boxes[counterColumns], positionBoxLayer);
        counterRows ++;
        counterColumns += dimension;
        positionBoxLayer ++;
      }
      counterLayers ++;
      counterColumns = counterLayers;
    }

    //super.printLayers();
  }

  public void shapeBoard(){
    byte rowAndColumn = 0, counterDown = dimension, counter = (byte)(counterDown - 1);
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
    if (typePiece == "SQUARE") checked = squareEvaluations(sizePiece, positionBox, checked);
    else if (typePiece == "VERTICAL LINE") checked = lineEvaluations(sizePiece, positionBox, 2, checked);
    else if (typePiece == "HORIZONTAL LINE") checked = lineEvaluations(sizePiece, positionBox, 1, checked);
    else if(typePiece == "L") checked = lEvaluation(sizePiece, positionBox, checked);
    else if(typePiece == "L LEFT") checked = leftLEvaluation(sizePiece, positionBox, checked);
    else if (typePiece == "L INVESTED") checked = investedLEvaluation(sizePiece, positionBox, checked);
    else checked = investedLEvaluation(sizePiece, positionBox, checked);
    if (checked == true){
      addPiece(typePiece, sizePiece, positionBox);
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

  public void addSquare(byte sizeSquare, int positionBox){
    Status fullBox = new FullBox();
    if(sizeSquare == 1){
      boxes[positionBox].setStatus(fullBox);
    }
    else{
      int positionBoxDown = positionBox + dimension;
      byte counterExit = 1;
      while(counterExit <= sizeSquare){
        byte counterJump = 1;
        while(counterJump <= sizeSquare){
          boxes[positionBox].setStatus(fullBox);
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

  public void addLine(byte sizeLine, int positionBox, int answer){
    Status fullBox = new FullBox();
    byte counterExit = 1;
    while(counterExit <= sizeLine){
      boxes[positionBox].setStatus(fullBox);
      counterExit ++;
      System.out.println("positionBox: " + positionBox);
      if(answer == 1)  positionBox++;
      else positionBox += dimension;
    }
  }

  public void addL(byte sizeL, int positionBox){
      Status fullBox = new FullBox();
      byte exitCounter = 0;

      while (exitCounter < sizeL){
        boxes[positionBox].setStatus(fullBox);
          if (exitCounter == 0) positionBox += dimension;
          else positionBox ++;
        exitCounter ++;
      }
    }


    public void addLeftL(byte sizeLeftL, int positionBox){
      Status fullBox = new FullBox();
      byte exitCounter = 0;
      byte counterJump = 1;

      while (exitCounter < sizeLeftL){
        boxes[positionBox].setStatus(fullBox);
          if (counterJump == sizeLeftL - 1) positionBox -= dimension;
          else positionBox ++;
        exitCounter ++;
        counterJump ++;
      }
    }

    public void addInvestedL(byte sizeInvestedL, int positionBox){
      Status fullBox = new FullBox();
      byte exitCounter = 0;

      while (exitCounter < sizeInvestedL){
        boxes[positionBox].setStatus(fullBox);
          if (exitCounter == 0) positionBox -= dimension;
          else positionBox ++;
        exitCounter ++;
      }
    }

    public void addLeftInvestedL(byte sizeInvestedLeftL, int positionBox){
      Status fullBox = new FullBox();
      byte exitCounter = 0;
      byte counterJump = 1;
      while (exitCounter < sizeInvestedLeftL){
        boxes[positionBox].setStatus(fullBox);
          if (counterJump == sizeInvestedLeftL - 1)positionBox += dimension;
          else positionBox ++;
        counterJump ++;
        exitCounter ++;
      }
    }

    public void addPiece(String typePiece, byte sizePiece,int positionBox){
      if (typePiece == "SQUARE") addSquare(sizePiece, positionBox);
      else if (typePiece == "VERTICAL LINE") addLine(sizePiece, positionBox, 2);
      else if (typePiece == "HORIZONTAL LINE") addLine(sizePiece, positionBox, 1);
      else if (typePiece == "L") addL(sizePiece, positionBox);
      else if (typePiece == "L LEFT") addLeftL(sizePiece, positionBox);
      else if (typePiece == "L INVESTED") addInvestedL(sizePiece, positionBox);
      else  addLeftInvestedL (sizePiece, positionBox);
    }
}
