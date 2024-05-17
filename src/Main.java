import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Elevator> elevators = List.of(new Elevator(1), new Elevator(2));
        Controller controller = new Controller(elevators);
        Generator generator = new Generator(controller);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        elevators.forEach(executorService::execute);

        executorService.execute(generator);
    }
}