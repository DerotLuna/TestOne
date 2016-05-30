public class Player{
  private String user; //Puede usarse nickName
  //private int bestScore;
  // private CLASE_TROFEO nombre_Variable_Tofeo       atributo trofeo que determina los trofeos del usuario

  public Player(String user){
    this.user = user;
    //this.bestScore = 0;
    /*validar si no tiene un score guardado en un archivo.
      Se puede mandar el parametro desde menu, y en menu leer si en un archivo se tiene ese dato, para asi abrir
      o buscar la manera de abrir solo archivos o "bases de datos" en menu.
    */
  }

  public String getUser(){
    return this.user;
  }
}
