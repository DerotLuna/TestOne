import java.util.Arrays;

public class Menu{
  //Atributo de opciones pero se necesita una clase que haga esas opciones que determine sus formas, etc
  private World world;
  private Player[] players; //aqui van todos los jugadores

  public Menu(){
    players = new Player[3]; //por los momentos pongamos un limite de solo tres
    /*supongamos que solo hay un limite de 10 usuario, se puede buscar la manera
      de que no sea un array si no una lista, para que asi no sea un numero limitado
      si no que se vayan agregando los usuarios a medida que se desee*/

    // Aqui se deberian cargar los usuarios en el array, los que se conozcan, por los momentos existen solo tres.
    players[0] = new Player("Ruben Luna");
    players[1] = new Player("Alejandro Martinez");
    players[2] = new Player("Carlos Alonzo");
    byte dimension = 10;
    byte idBoard = 1; 
    /*no se si idBoard se pueda ver como un atributo, y esta variable lo que hace es decirme que tipo
    de tablero utilizare*/
    world = new World(players[1], dimension, idBoard);
  }

  public void playGame(){
    world.worldControl();
  }
}
