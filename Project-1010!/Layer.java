public class Layer{
   String name;
   Box[] boxes;
   byte positionBox;

   public Layer(String name, byte numberBoxes){
     this.name = name;
     boxes = new Box[numberBoxes];
     this.positionBox = 0;
   }

   public void setBoxToLayer(Box box){
     boxes[positionBox] = box;
     this.positionBox ++;
   }

   public String getLayer(){
     return name;
   }
}
