import java.awt.Color;

public class Box{
  protected Status status;
  private byte id;
  private Color color;
  private Layer layer;

  public Box(int id){
    this.id = (byte) id;
    this.status = new FreeBox(); //cambiara, puede tener otro estado al crearse (dependiendo del estilo de juego).
  }

  public void setStatus(Status status){
    this.status = status;
  }

  public void setLayer(Layer layer){
    this.layer = layer;
  }

  public String getStatus(){
      return status.getStatus();
  }

  public byte getIdBox(){
      return this.id;
  }

  public Color getColorBox(){
    return this.color;
  }

  public void setColorBox(Color color){
    this.color = color;
  }

  public String getLayer(){
    return this.layer.getLayer();
  }
}
