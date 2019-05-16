
public class Run {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Game game = new Game();
        System.out.println("Game Start.");
        while(!game.itemsCollected()) {
            System.out.println(game.toString());
            game.playerTurn();
            if (game.isItemShouldMove()) {
                game.itemTurn();
            }
        }
    }
}
