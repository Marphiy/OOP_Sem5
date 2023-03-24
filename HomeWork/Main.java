package Sem5;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    // Имеется карта nxm
    // На ней создаются роботы
    // Если начальная точка инекорректна или занята другим - исключение
    // Можно менять направление движения и перемещать на 1 шаг
    // create-robot x y
    // change-direction LEFT
    // move-robot
    
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RoboMap map = null;
        System.out.println("Введите команду создания карты (create-map m n): ");
        while (true) {
            String command = sc.nextLine();
            if (command.startsWith("create-map")) {
                String[] split = command.split(" ");
                String[] arguments = Arrays.copyOfRange(split, 1, split.length);
                try {
                    map = new RoboMap(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
                    System.out.printf("Создана новая карта с размерами [%d x %d]\n", map.getM(), map.getN());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("При создании карты возникло исключение: " + e.getMessage());
                }
            } else
                System.out.println("Комманда не найдена! Попробуйте еще раз!");
        }

        while (true) {
            System.out.println("Введите команду (create-robot x y)\n" +
                    "                (change-direction id dir (UP, DOWN, LEFT, RIGHT)\n" +
                    "                (move id)\n" +
                    "                (show-map)\n" +
                    "                (exit)");
            String command = sc.nextLine();
            if (command.toLowerCase().equals("exit"))
                break;
            else if (command.toLowerCase().startsWith("show-map"))
                System.out.println(map);
            else if (command.toLowerCase().startsWith("create-robot")) {
                String[] split = command.split(" ");
                String[] arguments = Arrays.copyOfRange(split, 1, split.length);
                if (arguments.length >= 2) {
                    try {
                        map.createNewRobot(new Point(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1])));
                    } catch (PositionException e) {
                        System.out.println("Во время создания робота возникла ошибка: " + e.getMessage());
                    }
                } else
                    System.out.println("Введено некорректное значение координат!\n");
            } else if (command.toLowerCase().startsWith("change-direction")) {
                String[] split = command.split(" ");
                String[] arguments = Arrays.copyOfRange(split, 1, split.length);
                try {
                    Integer id = Integer.parseInt(arguments[0]);
                    String direction = arguments[1].toUpperCase();
                    RoboMap.Robot robot = map.getRobot(id);
                    robot.checkRobotId(id);
                    switch (direction) {
                        case "UP" -> robot.changeDirection(Direction.UP);
                        case "DOWN" -> robot.changeDirection(Direction.DOWN);
                        case "LEFT" -> robot.changeDirection(Direction.LEFT);
                        case "RIGHT" -> robot.changeDirection(Direction.RIGHT);
                    };

                } catch (RobotIdExeption e) {
                    System.out.println(e.getMessage());
                }
            } else if (command.toLowerCase().startsWith("move")) {

                String[] split = command.split(" ");
                try {
                    Integer id = Integer.parseInt(split[1]);
                    RoboMap.Robot robot = map.getRobot(id);
                    if (robot != null) {
                        robot.checkRobotId(id);
                        try {
                            robot.move();
                        } catch (PositionException e) {
                            System.out.println("Во время создания робота возникла ошибка: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Сперва создайте роботов!\n");
                    }
                } catch (RobotIdExeption e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Команда не распознана!\n");
            }
        }
        // RoboMap map = new RoboMap(10, 10);
        // RoboMap.Robot robot1 = null;
        // RoboMap.Robot robot2 = null;
        // try {
        // robot1 = map.createNewRobot(new Point(6, 5));
        // robot2 = map.createNewRobot(new Point(3, 5));
        // System.out.println(robot1);
        // System.out.println(robot2);

        // } catch (PositionException e){
        // System.out.println("Во время создания робота возникла ошибка: " +
        // e.getMessage());
        // }

        // if(robot1 != null){
        // try {
        // robot1.changeDirection(Direction.RIGHT);
        // robot1.move();
        // } catch (PositionException e){
        // System.out.println("Не удалось переместить робота! " + e.getMessage());
        // }
        // }

        // robot.move();
        // System.out.println(robot);
        // robot.changeDirection(Direction.LEFT);
        // robot.move();
        // robot.move();
        // System.out.println(robot);

    }

}
