import java.io.StringWriter;
import java.util.*;

public class Game {
    private int item_num;
    private int size;
    private Scanner in = new Scanner(System.in);
    private int i_x;
    private int i_y;
    private int p_x,p_y;
    private LinkedList<Item> itemList = new LinkedList<>();
    private LinkedList<Item> removedItemList = new LinkedList<>();
    private Player player;
    private boolean itemShouldMove = true;
    private int stepCount = 0;

    public Game() {
        String item_name;
        Item item;
        System.out.println("Game Setup.");
        System.out.print("Please enter parameter k: ");
        if (in.hasNextInt()) {
            item_num = in.nextInt();
            if (item_num <= 0) {
                System.out.println("Negative Number or Zero! Please try again.");
                System.exit(0);
            }
        } else {
            System.out.println("Invalid Number! Please try again.");
            System.exit(0);
        }

        System.out.print("Please enter parameter n: ");
        if (in.hasNextInt()) {
            size = in.nextInt();
            if (size <= 0) {
                System.out.println("Negative Number or Zero! Please try again.");
                System.exit(0);
            }
        } else {
            System.out.println("Invalid Number! Please try again.");
            System.exit(0);
        }

        for(int i = 0; i < item_num; i++) {
            boolean ifNewItemValid = true;
            System.out.print("Please enter item name: ");
            item_name = in.next();
            System.out.print("Please enter " + item_name + "'s initial location (x,y): ");
            String input = in.next();
            String[] splitInput = input.split(",");
            if (splitInput.length == 2) {
                try {
                    i_x = Integer.parseInt(splitInput[0]);
                    i_y = Integer.parseInt(splitInput[1]);
                    if (i_x < 0 || i_x > size-1 || i_y < 0 || i_y > size-1) {
                        System.out.println("Make sure x and y are ranged in [0, n-1]! Please try again.");
                        System.exit(0);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Number(s)! Please try again.");
                    System.exit(0);
                }
            } else {
                System.out.println("Format Error (x,y)! Please try again.");
                System.exit(0);
            }

            for (Item nextItem: itemList) {
                if (nextItem.getX() == i_x && nextItem.getY() == i_y) {
                    System.out.println("The total number of items in each location cannot exceed 1! Please try again.");
                    ifNewItemValid = false;
                    i--;
                    break;
                }
            }

            if (ifNewItemValid) {
                item = new Item(item_name, i_x, i_y);
                itemList.add(item);
            }
        }

        System.out.print("Please enter the initial location of the player (x,y): ");
        String input = in.next();
        String[] splitInput = input.split(",");
        if (splitInput.length == 2) {
            try {
                p_x = Integer.parseInt(splitInput[0]);
                p_y = Integer.parseInt(splitInput[1]);
                if (p_x < 0 || p_x > size-1 || p_y < 0 || p_y > size-1) {
                    System.out.print("Make sure x and y are ranged in [0, n-1]! Please try again.");
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid Number(s)! Please try again.");
                System.exit(0);
            }
        } else {
            System.out.print("Format Error (x,y)! Please try again.");
            System.exit(0);
        }
        player = new Player(p_x, p_y);
        System.out.println();
    }

    @Override
    public String toString() {
        StringWriter outputWriter = new StringWriter();
        in.nextLine();
        for(Item item: itemList) {
            outputWriter.write(item.getName() + "(" + item.getX() + "," + item.getY() + ") ");
        }
        outputWriter.write("player(" + player.getX() + "," + player.getY() + ")" + " - items picked up: " + (item_num - itemList.size()));
        return outputWriter.toString();
    }

    public void playerTurn() {
        checkIfPickedUp(itemList, player);
        System.out.print("Your move (l/r/u/d/ul/ur/dl/dr): ");
        String command = in.next();
        switch(command) {
            case "l":
                if (player.getX()-1 < 0) {
                    System.out.println("Out of Boundary! Please try another direction!");
                    itemShouldMove = false;
                } else {
                    player.setX(player.getX()-1);
                    stepCount++;
                    itemShouldMove = true;
                }
                break;
            case "r":
                if (player.getX()+1 > size-1) {
                    System.out.println("Out of Boundary! Please try another direction!");
                    itemShouldMove = false;
                } else {
                    player.setX(player.getX()+1);
                    stepCount++;
                    itemShouldMove = true;
                }
                break;
            case "u":
                if (player.getY()+1 > size-1) {
                    System.out.println("Out of Boundary! Please try another direction!");
                    itemShouldMove = false;
                } else {
                    player.setY(player.getY()+1);
                    stepCount++;
                    itemShouldMove = true;
                }
                break;
            case "d":
                if (player.getY()-1 < 0) {
                    System.out.println("Out of Boundary! Please try another direction!");
                    itemShouldMove = false;
                } else {
                    player.setY(player.getY()-1);
                    stepCount++;
                    itemShouldMove = true;
                }
                break;
            case "ul":
                if (player.getX()-1 < 0 || player.getY()+1 > size-1) {
                    System.out.println("Out of Boundary! Please try another direction!");
                    itemShouldMove = false;
                } else {
                    player.setX(player.getX()-1);
                    player.setY(player.getY()+1);
                    stepCount += 2;
                    itemShouldMove = true;
                }
                break;
            case "ur":
                if (player.getY()+1 > size-1 || player.getY()+1 > size-1) {
                    System.out.println("Out of Boundary! Please try another direction!");
                    itemShouldMove = false;
                } else {
                    player.setX(player.getX()+1);
                    player.setY(player.getY()+1);
                    stepCount += 2;
                    itemShouldMove = true;
                }
                break;
            case "dl":
                if (player.getY()-1 < 0 || player.getX()-1 < 0) {
                    System.out.println("Out of Boundary! Please try another direction!");
                    itemShouldMove = false;
                } else {
                    player.setX(player.getX()-1);
                    player.setY(player.getY()-1);
                    stepCount += 2;
                    itemShouldMove = true;
                }
                break;
            case "dr":
                if (player.getX()+1 > size-1 || player.getY()-1 < 0) {
                    System.out.println("Out of Boundary! Please try another direction!");
                    itemShouldMove = false;
                } else {
                    player.setX(player.getX()+1);
                    player.setY(player.getY()-1);
                    stepCount += 2;
                    itemShouldMove = true;
                }
                break;
            default:
                System.out.println("Invalid Operation! Please try again.");
                itemShouldMove = false;
        }

        if (itemShouldMove) {
            checkIfPickedUp(itemList, player);
        }
    }

    public void itemTurn() {
        for(Item item: itemList) {

            LinkedList<Tuple> tupleList = new LinkedList<>();
            LinkedList<Tuple> sortedTupleList = new LinkedList<>();

            if (item.getX() - 1 >= 0) {
                tupleList.add(new Tuple("l", Math.pow((Math.pow((item.getX() - 1 - player.getX()), 2) + Math.pow((item.getY() - player.getY()), 2)), 0.5)));
            }

            if (item.getX() + 1 <= size - 1) {
                tupleList.add(new Tuple("r", Math.pow((Math.pow((item.getX() + 1 - player.getX()), 2) + Math.pow((item.getY() - player.getY()), 2)), 0.5)));
            }

            if (item.getY() + 1 <= size - 1) {
                tupleList.add(new Tuple("u", Math.pow((Math.pow((item.getX() - player.getX()), 2) + Math.pow((item.getY() + 1 - player.getY()), 2)), 0.5)));
            }

            if (item.getY() - 1 >= 0) {
                tupleList.add(new Tuple("d", Math.pow((Math.pow((item.getX() - player.getX()), 2) + Math.pow((item.getY() - 1 - player.getY()), 2)), 0.5)));
            }

            sortTupleList(tupleList, sortedTupleList);

            if (sortedTupleList.getFirst().getDistance() > sortedTupleList.get(1).getDistance()) {
                moveItemSingleMax(sortedTupleList, item);
            } else {
                moveItemMultiMax(sortedTupleList, item, itemList);
            }
        }
    }

    public boolean itemsCollected() {
        boolean itemsCollected = false;
        if (removedItemList.size() == item_num) {
            for (Item item: removedItemList) {
                if (!item.isIfCollected()) {
                    itemsCollected = false;
                    break;
                } else {
                    itemsCollected = true;
                }
            }
        }
        if (itemsCollected) {
            System.out.println("The game ends. You moved " + stepCount + " time(s).");
        }
        return itemsCollected;
    }

    private void checkIfPickedUp(LinkedList<Item> itemList, Player player) {
        for (Item item: itemList) {
            if (item.getX() == player.getX() && item.getY() == player.getY()) {
                item.setIfCollected(true, player);
                itemList.remove(item);
                removedItemList.add(item);
                System.out.println(item.getName() + " is picked up!");
            }
        }
    }

    private void moveItemSingleMax(LinkedList<Tuple> sortedTupleList, Item item) {
        moveItem(sortedTupleList.getFirst().getDirection(), item);
    }

    private void moveItemMultiMax(LinkedList<Tuple> sortedTupleList, Item item, LinkedList<Item> itemList) {
        LinkedList<Tuple> itemTupleList = new LinkedList<>();
        LinkedList<Tuple> itemSortedTupleList = new LinkedList<>();
        int maxCount = 0;
        double max = sortedTupleList.getFirst().getDistance();

        for (Tuple tuple: sortedTupleList) {
            if (tuple.getDistance() == max) {
                maxCount++;
            }
        }

        for (int i = 0; i < maxCount; i++) {
            itemTupleList.add(new Tuple(sortedTupleList.get(i).getDirection(), getItemDistance(item, itemList, sortedTupleList.get(i).getDirection())));
        }

        sortTupleList(itemTupleList, itemSortedTupleList);
        moveItem(itemSortedTupleList.getFirst().getDirection(), item);
    }

    private double getItemDistance(Item selectedItem, LinkedList<Item> itemList, String direction) {
        double itemDistance = 0.0;
        LinkedList<Item> otherItemList = new LinkedList<>();

        otherItemList.addAll(itemList);
        otherItemList.remove(selectedItem);

        switch (direction) {
            case "r":
                for (Item item: otherItemList) {
                    itemDistance += Math.pow((Math.pow((selectedItem.getX() + 1 - item.getX()), 2) + Math.pow((selectedItem.getY() - item.getY()), 2)), 0.5);
                }
                break;
            case "l":
                for (Item item: otherItemList) {
                    itemDistance += Math.pow((Math.pow((selectedItem.getX() - 1 - item.getX()), 2) + Math.pow((selectedItem.getY() - item.getY()), 2)), 0.5);
                }
                break;
            case "u":
                for (Item item: otherItemList) {
                    itemDistance += Math.pow((Math.pow((selectedItem.getX() - item.getX()), 2) + Math.pow((selectedItem.getY() + 1 - item.getY()), 2)), 0.5);
                }
                break;
            case "d":
                for (Item item: otherItemList) {
                    itemDistance += Math.pow((Math.pow((selectedItem.getX() - item.getX()), 2) + Math.pow((selectedItem.getY() - 1 - item.getY()), 2)), 0.5);
                }
                break;
            default:
                System.out.println("System Error!");
                System.exit(3);
        }
        return itemDistance;
    }

    private void moveItem(String direction, Item item) {
        switch (direction) {
            case "r":
                item.setX(item.getX() + 1);
                break;
            case "l":
                item.setX(item.getX() - 1);
                break;
            case "u":
                item.setY(item.getY() + 1);
                break;
            case "d":
                item.setY(item.getY() - 1);
                break;
            default:
                System.out.println("System Error!");
                System.exit(3);
        }
    }

    private void sortTupleList(LinkedList<Tuple> tupleList, LinkedList<Tuple> sortedTupleList) {
        Tuple maxTuple;

        while (!tupleList.isEmpty()) {
            maxTuple = tupleList.getFirst();
            for (int i = 1; i < tupleList.size(); i++) {
                if (tupleList.get(i).getDistance() > maxTuple.getDistance()) {
                    maxTuple = tupleList.get(i);
                }
            }
            tupleList.remove(maxTuple);
            sortedTupleList.add(maxTuple);
        }
    }

    public boolean isItemShouldMove() {
        return itemShouldMove;
    }
}

class Tuple {

    private String direction;
    private double distance;

    Tuple(String direction, Double distance) {
        this.direction = direction;
        this.distance = distance;
    }

    String getDirection() {
        return direction;
    }

    double getDistance() {
        return distance;
    }
}