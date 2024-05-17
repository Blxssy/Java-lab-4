import java.util.concurrent.ConcurrentLinkedQueue;
public class Elevator implements Runnable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    
    private int direction;
    private int floor = 0;
    private final int place;

    private Request request;

    private final ConcurrentLinkedQueue<Request> requestsQueue;

    @Override
    public void run() {
        while (true) {
            checkState();
            takePassengers();
            updateCurrentRequest();
            changeDirection();
            move();
            printState();
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

    public Elevator(int i) {
        this.place = i;
        this.direction = 0;
        this.floor = 0;
        this.requestsQueue = new ConcurrentLinkedQueue<>();
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getFloor() {
        return floor;
    }

    public int getPlace() {
        return place;
    }

    private void updateCurrentRequest(){
        if( request == null || floor == request.getFloor()) {
            request = findBestRequest();
        }
    }

    public void getRequest(Request req){
        System.out.printf(ANSI_GREEN + "ЗАПРОС: Лифт: %d Этаж запроса: %s Этаж высадки: %s\n", place, req.getFloor(), req.getToFloor() + ANSI_RESET);
        requestsQueue.add(req);
    }

    private Request findBestRequest(){
        Request best = null;
        for(Request r : requestsQueue){
            int currentFloor = r.getFloor();
            if(best == null || Math.abs(currentFloor - floor) < best.getFloor()){
                best = r;
            }
        }

        if (best != null) {
            requestsQueue.remove(best);
        }

        return best;
    }

    private void move() {
        if (direction == 0) {
            return;
        }
        if(direction == 1) {
            floor++;
        } else {
            floor--;
        }
    }

    private void changeDirection() {
        if(request == null) {
            direction = 0;
            return;
        }

        if(request.getFloor() == floor) {
            direction = 0;
        } else if (request.getFloor() - floor > 0) {
            direction = 1;
        } else {
            direction = -1;
        }
    }

    private void printState() {
        if(direction == 1)
        {
            System.out.printf("ЛИФТ: %d Текущий этаж: %d Идет вверх\n", place, floor);
        } else if (direction == -1) {
            System.out.printf("ЛИФТ: %d Текущий этаж: %d Идет вниз\n", place, floor);
        } else {
            System.out.printf("ЛИФТ: %d Текущий этаж: %d Ожидает вызова\n", place, floor);
        }
    }

    public void takePassengers() {
        if(!requestsQueue.isEmpty()){
            for(Request r : requestsQueue) {
                if (floor == r.getFloor()) {
                    r.setPicked();
                }
            }
        }
    }

    private void checkState() {
        int count = 0;
        if(!requestsQueue.isEmpty()){
            for(Request r : requestsQueue) {
                if (floor == r.getToFloor() && r.isPicked()) {
                    count++;
                    requestsQueue.remove(r);
                }
            }
        }
        if(count != 0) {
            System.out.printf(ANSI_RED + "ЛИФТ: %d Текущий этаж: %d Высадил %d\n" + ANSI_RESET, place, floor, count);
        }
    }
}
