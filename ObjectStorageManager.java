import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObjectStorageManager {
    private final static List<LaserBeam> laserBeams = new CopyOnWriteArrayList<>();
    private final static List<Asteroid> asteroids = new CopyOnWriteArrayList<>();
    private static boolean isPaused = false;
    private static Spaceship spaceship = GamePanel.spaceship;

    private ObjectStorageManager(){}

    public static List<LaserBeam> getLaserBeamList(){
        return laserBeams;
    }
    public static List<Asteroid> getAsteroidsList(){
        return asteroids;
    }
    public static void pause(){
        isPaused = !isPaused;
    }
    public static boolean getIsPaused(){
        return isPaused;
    }
    public static Spaceship getSpaceship(){
        return spaceship;
    }
}
