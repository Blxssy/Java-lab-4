import java.util.Random;

public class Generator implements Runnable {
    private static final int High = 9;
    private static final int Low = 1;

    private final Random random;

    public Generator() {
        random = new Random();
    }

    @Override
    public void run() {
        while(true) {
            Request request = makeRandomRequest();
            request.PrintRequest();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Request makeRandomRequest() {
        int floor = random.nextInt(High - Low + 1) + 1;
        if (floor == 1) {
            return new Request(floor, 1);
        }
        if (floor == 9) {
            return new Request(floor, -1);
        }
        int direction = random.nextInt(2) * 2 - 1;
        return new Request(floor, direction);
    }
}
