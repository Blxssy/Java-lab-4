public class Request {
    int floor;
    int direction;

    public Request(int floor, int dir) {
        this.floor = floor;
        this.direction = dir;
    }

    public void PrintRequest() {
        System.out.println("Floor: " + floor + " " + "direction: " + direction);
    }
}
