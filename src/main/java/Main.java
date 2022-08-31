import net.sf.saxon.expr.Component;

public class Main {
    public static void main(String[] args) throws Exception {
        Movements moves = new Movements(Movements.pathToMovementCSV);
        System.out.println(moves.getIncomeSum());
        System.out.println(moves.getExpenseSum());
    }

}