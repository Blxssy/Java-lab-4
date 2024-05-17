import java.util.Random;

public class Generator implements Runnable {
    private static final int High = 9;
    private static final int Low = 1;

    private final Random random;

    private Controller controller;

    public Generator(Controller c) {
        random = new Random();
        controller = c;
    }

    @Override
    public void run() {
        while(true) {
            Request request = makeRandomRequest();
            controller.callElevator(request);
            wait1S();
        }
    }

    private void wait1S() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Request makeRandomRequest() {
        int floor = random.nextInt(High - Low + 1) + 1;
        int toFloor = -1;
        toFloor = random.nextInt(High - Low) + 1;
        if(toFloor == floor) {
            while(true) {
                if (toFloor != floor) {
                    break;
                }
                toFloor = random.nextInt(High - Low);
            }
        }

        int direction = floor - toFloor > 0 ? -1 : 1;

        if (floor == 1) {
            return new Request(floor, 1, toFloor + 1);
        }
        if (floor == 9) {
            return new Request(floor, -1, toFloor - 1);
        }
//        int direction = random.nextInt(2) * 2 - 1;
        return new Request(floor, direction, toFloor);
    }
}
