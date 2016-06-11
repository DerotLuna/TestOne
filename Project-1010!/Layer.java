public class Layer{
   private String name;
   private Box[] boxes;
   private boolean permitRelease;

   public Layer(String name, byte numberBoxes){
     this.name = name;
     this.permitRelease = false;
     boxes = new Box[numberBoxes];
   }

   public void setBoxToLayer(Box box, byte positionBox){
     boxes[positionBox] = box;
   }

   public String getLayer(){
     return name;
   }

   public void verifyLayer(byte dimension){
     byte counter = 0; Status status = Status.FULL;
     while(counter < dimension){
       if(boxes[counter].getStatus() == status) permitRelease = true;
       else{
         permitRelease = false;
         break;
       }
      counter ++;
     }
   }

   public byte boxesRelease(byte dimension){
     if(permitRelease){
       Status status = Status.FREE;
       for (byte counter = 0; counter < dimension; counter ++) {
         boxes[counter].setStatus(status);
       }
       permitRelease = false;
       return dimension;
     }
     return 0;
   }

   public void printLayers(byte dimension){
     System.out.println("La capa " + name + " tiene las siguientes casillas:");
     for (byte counter = 0; counter < dimension; counter ++) {
       System.out.print(boxes[counter].getIdBox() + "  ");
     }
      System.out.println();
   }
}
