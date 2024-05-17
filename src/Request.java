public class Request {
    private int floor;
    private int direction;
    private int toFloor;
    private boolean isPicked;

    public Request(int floor, int dir, int to) {
        this.floor = floor;
        this.direction = dir;
        this.toFloor = to;
        this.isPicked = false;
    }

    public void PrintRequest() {
        System.out.println("Floor: " + floor + " " + "direction: " + direction + " " + "toFloor: " + toFloor);
    }

    public int getFloor() {
        return floor;
    }

    public int getDirection() {
        return direction;
    }

    public int getToFloor() {
        return toFloor;
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked() {
        this.isPicked = true;
    }
}
