import java.awt.Color;

public class Box{
  protected Status status;
  private byte id;
  //private Layer[] layer;

  public Box(int id){
    this.id = (byte) id;
    this.status = Status.FREE; //cambiara, puede tener otro estado al crearse (dependiendo del estilo de juego).
  }

  public void setStatus(Status status){
    this.status = status;
  }

  /*public void setLayer(Layer layer){
    this.layer = layer;
  }*/

  public Status getStatus(){
      return status;
  }

  public byte getIdBox(){
      return this.id;
  }

  /*public String getLayer(){
    return this.layer.getLayer();
  }*/
}
