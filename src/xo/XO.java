
package xo;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class XO extends Application{
    private ArrayList<String> tab = new ArrayList<String>();
    private Canvas canvas = new Canvas(630,630);
    private StackPane stack = new StackPane(canvas);
    private VBox vbox = new VBox();
    
    private Scene scene = new Scene(vbox,750,750);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    static int nbX ;
    static int nbO ;
    static char xORo = 'x';
    private Text text = new Text();
    private String description = " Each player must click with the mouse on the gray lines, one after the other. When a player completes all four\n"
                               + " sides of a square, an X or O is displayedinside that square. After completing all the squares, the winner (X or O)\n"
                               + " is determined based on who has the most (X or O) symbols. Player X starts, followed by player O.";
                               

    @Override
    public void start(Stage stage) {
        
        vbox.getChildren().addAll(stack, text);
        
        verticale();
        horizontale();
        text.setFont(new Font(15));
        text.setText(description);
        
        
        canvas.setOnMouseClicked(e -> fon(e));

        stage.setTitle("Launcher");
        stage.setScene(scene);
        stage.show();
    }
    
    private void fon(MouseEvent e){
        double mouseX = e.getX();
        double mouseY = e.getY();
        WritableImage snapshot = canvas.snapshot(null, null);

        PixelReader pixelReader = snapshot.getPixelReader();
        Color pixelColor = pixelReader.getColor((int) mouseX, (int) mouseY);
        //System.out.println("Couleur du pixel cliqué : " + pixelColor);
        if(pixelColor.toString().equals("0xd9d9d9ff")){
            gc.setFill(Color.BLACK);
            int a,b,x,y;
            
            if(vOUh(pixelColor,pixelReader,mouseX,mouseY)){
                //horizintale
                a = (((int)mouseX - 20) / 80) + 1;
                b = ((int)mouseY / 80) + 1;
                tab.add("h" + a + "." + b);
                x = 20 + (a - 1) * 80;
                y = (b - 1) * 80;
                gc.fillRect(x, y, 50, 10);
            }
            else{
                //verticale
                b = (((int)mouseY - 20) / 80) + 1;
                a = ((int)mouseX / 80) + 1;
                tab.add("v" + a + "." + b);
                x = (a - 1)* 80;
                y = 20 + (b - 1) * 80;
                gc.fillRect(x, y, 10, 50);
            }
            
            if(xORo == 'x'){
                ajoutX(x , y);
            }
            else{
                ajoutO(x , y);
            }
            xOUo();
        }
        
        if(nbX + nbO == 25){
            System.out.println(nbX + "VS" + nbO);
            if(nbX > nbO){
                description = description +"\n the winner is <<<<< X >>>>>";
            }
            else {
                description = description +"\n the winner is <<<<< O >>>>>";
            }
            text.setText(description);
        }
    }
    private void xOUo(){
        if(xORo == 'x'){
            xORo = 'o';
        }
        else{
            xORo = 'x';
        }
    }
    private boolean vOUh(Color mousePixel, PixelReader pixelReader, double mouseX, double mouseY){
        Color pixelG = pixelReader.getColor((int) mouseX + 15, (int) mouseY);
        Color pixelD = pixelReader.getColor((int) mouseX - 15, (int) mouseY);
        return (mousePixel.toString().equals(pixelG.toString()) || mousePixel.toString().equals(pixelD.toString()));
        //si vrai alors horyzontale
        //si faux alors verticale
    }
    private void ajoutX(int x, int y ){
        String ch = tab.get(tab.size() - 1);
        if(ch.charAt(0) == 'h'){
            String ch1,ch2,ch3;
            ch1 = ch.substring(0, 3) + (Integer.parseInt(ch.substring(3)) - 1 );
            ch2 = "v" + ch.substring(1, 3) + (Integer.parseInt(ch.substring(3)) - 1 );
            ch3 = "v" + (Integer.parseInt(ch.substring(1, 2)) +1) + "." + (Integer.parseInt(ch.substring(3)) - 1 );
            if(tab.contains(ch1) && tab.contains(ch2) && tab.contains(ch3)){
                gc.setFont(new Font("Impact" , 40));
                gc.setFill(Color.PINK);
                gc.fillText("X", x + 5, y - 10);
                nbX += 1 ;
                
            }
            ch1 = ch.substring(0, 3) + (Integer.parseInt(ch.substring(3)) + 1 );
            ch2 = "v" + ch.substring(1);
            ch3 = "v" + (Integer.parseInt(ch.substring(1, 2)) +1) + ch.substring(2);
            if(tab.contains(ch1) && tab.contains(ch2) && tab.contains(ch3)){
                gc.setFont(new Font("Impact" , 40));
                gc.setFill(Color.PINK);
                gc.fillText("X", x + 5, y + 70);
                nbX += 1 ;
                
            }
        }
        else{
            String ch1,ch2,ch3;
            ch1 = ch.replace('v', 'h');
            ch2 = "h" + ch.substring(1, 3) + (Integer.parseInt(ch.substring(3)) + 1);
            ch3 = "v" + (Integer.parseInt(ch.substring(1, 2)) + 1) + ch.substring(2);
            if(tab.contains(ch1) && tab.contains(ch2) && tab.contains(ch3)){
                gc.setFont(new Font("Impact" , 40));
                gc.setFill(Color.PINK);
                gc.fillText("X", x + 25, y + 50);
                nbX += 1 ;
                
            }
            ch1 = "h" + (Integer.parseInt(ch.substring(1, 2)) - 1) + ch.substring(2);
            ch2 = "h" + (Integer.parseInt(ch.substring(1, 2)) - 1) + "." + (Integer.parseInt(ch.substring(3)) + 1);
            ch3 = "v" + (Integer.parseInt(ch.substring(1, 2)) - 1) + ch.substring(2);
            if(tab.contains(ch1) && tab.contains(ch2) && tab.contains(ch3)){
                gc.setFont(new Font("Impact" , 40));
                gc.setFill(Color.PINK);
                gc.fillText("X", x - 55, y + 50);
                nbX += 1 ;
            }
            
        }
        
    }
    
    private void ajoutO(int x, int y ){
        String ch = tab.get(tab.size() - 1);
        if(ch.charAt(0) == 'h'){
            String ch1,ch2,ch3;
            ch1 = ch.substring(0, 3) + (Integer.parseInt(ch.substring(3)) - 1 );
            ch2 = "v" + ch.substring(1, 3) + (Integer.parseInt(ch.substring(3)) - 1 );
            ch3 = "v" + (Integer.parseInt(ch.substring(1, 2)) +1) + "." + (Integer.parseInt(ch.substring(3)) - 1 );
            if(tab.contains(ch1) && tab.contains(ch2) && tab.contains(ch3)){
                gc.setFont(new Font("Impact" , 40));
                gc.setFill(Color.PINK);
                gc.fillText("O", x + 5, y - 10);
                nbO += 1 ;
                
            }
            ch1 = ch.substring(0, 3) + (Integer.parseInt(ch.substring(3)) + 1 );
            ch2 = "v" + ch.substring(1);
            ch3 = "v" + (Integer.parseInt(ch.substring(1, 2)) +1) + ch.substring(2);
            if(tab.contains(ch1) && tab.contains(ch2) && tab.contains(ch3)){
                gc.setFont(new Font("Impact" , 40));
                gc.setFill(Color.PINK);
                gc.fillText("O", x + 5, y + 70);
                nbO += 1 ;
                
            }
        }
        else{
            String ch1,ch2,ch3;
            ch1 = ch.replace('v', 'h');
            ch2 = "h" + ch.substring(1, 3) + (Integer.parseInt(ch.substring(3)) + 1);
            ch3 = "v" + (Integer.parseInt(ch.substring(1, 2)) + 1) + ch.substring(2);
            if(tab.contains(ch1) && tab.contains(ch2) && tab.contains(ch3)){
                gc.setFont(new Font("Impact" , 40));
                gc.setFill(Color.PINK);
                gc.fillText("O", x + 25, y + 50);
                nbO += 1 ;
                
            }
            ch1 = "h" + (Integer.parseInt(ch.substring(1, 2)) - 1) + ch.substring(2);
            ch2 = "h" + (Integer.parseInt(ch.substring(1, 2)) - 1) + "." + (Integer.parseInt(ch.substring(3)) + 1);
            ch3 = "v" + (Integer.parseInt(ch.substring(1, 2)) - 1) + ch.substring(2);
            if(tab.contains(ch1) && tab.contains(ch2) && tab.contains(ch3)){
                gc.setFont(new Font("Impact" , 40));
                gc.setFill(Color.PINK);
                gc.fillText("O", x - 55, y + 50);
                nbO += 1 ;
            }
            
        }
        
    }
    
    
    
    private void verticale(){
        int j = 1;
        int y1 = 260;
        int x = 0;
        int y;
        int a,b;
        for(int i =1;i<5;i++){
            y = y1;
            for(int k = 1;k <= j ; k++){
                
                if(k == 1 || k == j){
                    gc.setFill(Color.BLACK);
                    a = (x / 80) +1;
                    b = ((y - 20)/80) +1;
                    tab.add("v" + a + "." + b);
                    
                }
                else{
                    gc.setFill(Color.web("0xD9D9D9"));
                }
                gc.fillRect(x, y, 10, 50);
                y += 80;
            }
            j += 2;
            x += 80;
            y1 -= 80;
        }
        
        j -= 2;
        y1 += 80;
        
        for(int i =1;i<5;i++){
            y = y1;
            for(int k = 1;k <= j ; k++){
                
                if(k == 1 || k == j){
                    gc.setFill(Color.BLACK);
                    a = (x / 80) +1;
                    b = ((y - 20)/80) +1;
                    tab.add("v" + a + "." + b);
                    
                }
                else{
                    gc.setFill(Color.web("0xD9D9D9"));
                }
                gc.fillRect(x, y, 10, 50);
                y += 80;
            }
            j -= 2;
            x += 80;
            y1 += 80;
        }
    }
    
    private void horizontale(){
        int j = 1;
        int x1 = 260;
        int y = 0;
        int x;
        int a,b;
        for(int i =1;i<5;i++){
            x = x1;
            for(int k = 1;k <= j ; k++){
                
                if(k == 1 || k == j){
                    gc.setFill(Color.BLACK);
                    b = (y / 80) +1;
                    a = ((x - 20)/80) +1;
                    tab.add("h" + a + "." + b);
                    
                }
                else{
                    gc.setFill(Color.web("0xD9D9D9"));
                }
                gc.fillRect(x, y, 50, 10);
                x += 80;
            }
            j += 2;
            y += 80;
            x1 -= 80;
        }
        j -= 2;
        x1 += 80;
        for(int i =1;i<5;i++){
            x = x1;
            for(int k = 1;k <= j ; k++){
                
                if(k == 1 || k == j){
                    gc.setFill(Color.BLACK);
                    b = (y / 80) +1;
                    a = ((x - 20)/80) +1;
                    tab.add("h" + a + "." + b);
                    
                }
                else{
                    gc.setFill(Color.web("0xD9D9D9"));
                }
                gc.fillRect(x, y, 50, 10);
                x += 80;
            }
            j -= 2;
            y += 80;
            x1 += 80;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
