package Sem5;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoboMap {
    private final int n;
    private final int m;
    private final Map<Integer, Robot> robots;

    public RoboMap(int m, int n) {
        if (m < 0 || n < 0) {
            throw new IllegalArgumentException("Недопустимые значения размеров карты!");
        }
        this.n = n;
        this.m = m;
        this.robots = new HashMap<>();

    }

    public Robot createNewRobot(Point position) throws PositionException {

        checkPosition(position);

        Robot robot = new Robot(position);
        robots.put(robot.id, robot);
        System.out.println("New Robot: " + robot + "\n");
        return robot;
    }

    public void checkPosition(Point position) throws PositionException {
        if (!isFree(position)) {
            throw new PositionException("Точка " + position + " занята!");
        }

        if (position.getX() < 0 || position.getX() >= n ||
                position.getY() < 0 || position.getY() >= m) {
            throw new PositionException("Некорректное значение точки: " + position);
        }
    }

    private boolean isFree(Point position) {
        for (Robot value : robots.values()) {
            if (value.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    // return
    // robots.values().stream().map(Robot::getPosition).noneMatch(position::equals);

    public class Robot {
        // private final UUID id;
        private static Integer num = 0;
        private Integer id;
        private Point position;
        private Direction direction;

        public Robot(Point position) {
            // this.id = UUID.randomUUID();
            this.id = ++num;
            this.position = position;
            this.direction = Direction.UP;
        }

        public void changeDirection(Direction direction) {
            this.direction = direction;
            System.out.printf("Robot_#%s сменил направление движения на %s\n", id, direction.name());
        }

        public boolean noRobot() {
            return robots.isEmpty();
        }

        public void move() throws PositionException {
            if (!robots.isEmpty()) {
                Point newPosition = switch (direction) {
                    case UP -> new Point(position.getX(), position.getY() - 1);
                    case RIGHT -> new Point(position.getX() + 1, position.getY());
                    case LEFT -> new Point(position.getX() - 1, position.getY());
                    case DOWN -> new Point(position.getX(), position.getY() + 1);
                };

                checkPosition(newPosition);

                position = newPosition;
                System.out.println(this.toString());
            } else {
                System.out.println("Сперва создайте роботов!\n");
            }
        }

        public void checkRobotId(Integer id) throws RobotIdExeption {
            if (!robots.containsKey(id)) {
                throw new RobotIdExeption("Робот с таким id не найден!\n");
            }
        }

        @Override
        public String toString() {
            return String.format("[Robot_#%s %s]", id.toString(), position.toString());
        }

        public Integer getId() {
            return id;
        }

        public Point getPosition() {
            return position;
        }

    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public Robot getRobot(Integer id) {
        return robots.get(id);
    }

    @Override
    public String toString() {
        // String str;
        // for(Robot robot: robots.){

        // }
        return robots.entrySet().toString();
    }

}
