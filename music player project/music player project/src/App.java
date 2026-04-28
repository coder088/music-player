
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileNotFoundException;


public class App {
    static public void main(){
    String filepath = "src/music.wav";
    File file = new File(filepath);

    try(Scanner scanner = new Scanner(System.in);  AudioInputStream audioStream = AudioSystem.getAudioInputStream(file)){
     Clip clip = AudioSystem.getClip();
     clip.open(audioStream);
     String input = "";

     try {
        while(!input.equals("Q")){
           System.out.println("\n=== MUSIC PLAYER ===");
           System.out.println("P = play");
           System.out.println("S = stop");
           System.out.println("R = reset");
           System.out.println("V+ = volume up");
           System.out.println("V- = volume down");
           System.out.println("Q = quit");
           System.out.print("ENTER YOUR CHOICE: ");
           input = scanner.next().toUpperCase();

           switch(input){
              case "P" -> clip.start();
              case "S" -> clip.stop();
              case "R" -> clip.setMicrosecondPosition(0);
              case "V+" -> increaseVolume(clip);
              case "V-" -> decreaseVolume(clip);
              case "Q" -> input = "Q";
              default -> System.out.println("invalid choice");
           }
        }
     } finally {
        clip.close();
     }

    }
    catch(FileNotFoundException e){
     System.out.println("file not found! ");
    }
    catch(LineUnavailableException e){
     System.out.println("file not readable!");
    }
    catch(UnsupportedAudioFileException e){
     System.out.println("unsupported audio file!");
    }
    catch(IOException e){
     System.out.println("something went wrong");
    }
    finally{
     System.out.println("Bye!");
    }
}

    static private void increaseVolume(Clip clip) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float currentGain = gainControl.getValue();
        float newGain = Math.min(currentGain + 5.0f, gainControl.getMaximum());
        gainControl.setValue(newGain);
        System.out.println("Volume: " + String.format("%.1f", newGain) + " dB");
    }

    static private void decreaseVolume(Clip clip) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float currentGain = gainControl.getValue();
        float newGain = Math.max(currentGain - 5.0f, gainControl.getMinimum());
        gainControl.setValue(newGain);
        System.out.println("Volume: " + String.format("%.1f", newGain) + " dB");
    }
}
