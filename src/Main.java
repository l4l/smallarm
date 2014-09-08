import java.io.File;

/**
 * Created by Kitsu on 13.08.2014.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length <= 1) {
            return;
        }

        String[] time = args[0].split(":");

        Sound sound = new Sound(new File(args[1]));
        try {
            int ms = (Integer.parseInt(time[0])* 3600 + Integer.parseInt(time[1])*60 + Integer.parseInt(time[2])) * 1000;
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true)
            sound.play();
    }
}
