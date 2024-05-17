import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Controller {
    private final List<Elevator> elevators;

    public Controller(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    public void callElevator(Request request){
        getElevator(request).getRequest(request);
    }

    public Elevator getElevator(Request request) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevators) {
            int distance = request.getFloor() - elevator.getFloor();
            if (checkDirection(request, elevator)) {
                if (distance < minDistance) {
                    bestElevator = elevator;
                    minDistance = distance;
                }
            }
        }

        if(bestElevator == null) {
            bestElevator = elevators.get(0);
        }

        return bestElevator;
    }

    private boolean checkDirection(Request request, Elevator elevator) {
        return request.getDirection() == elevator.getDirection() || elevator.getDirection() == 0;
    }
}
