import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Movements {
    public static final String pathToMovementCSV = "src/test/resources/movementList.csv";
    private String accountType;
    private String accountNumber;
    private String currency;
    private Date operationDate;
    private String reference;
    private String operation;
    private double income;
    private double waste;
    private double incomeSum;
    private double expenseSum;


    public Movements(String accountType, String accountNumber, String currency, Date operationDate,
                     String reference, String operation, Double income, Double waste) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.currency = currency;
        this.operationDate = operationDate;
        this.reference = reference;
        this.operation = operation;
        this.income = income;
        this.waste = waste;
    }

    public Movements(String path) throws Exception {
        List<Movements> movements = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (String line : lines) {
            try {
                String[] fragments = line.split(",");
                String dateFormat = "dd.MM.yy";
                if (fragments.length == 9) {
                    movements.add(new Movements(
                            fragments[0],
                            fragments[1],
                            fragments[2],
                            (new SimpleDateFormat(dateFormat)).parse(fragments[3]),
                            fragments[4],
                            fragments[5].substring(20,68),
                            (Double.parseDouble(fragments[6])),
                            (Double.parseDouble((fragments[7] + "." + fragments[8]).replaceAll("\"", "")))
                    ));
                }
                else if (fragments.length == 8) {
                    movements.add(new Movements(
                            fragments[0],
                            fragments[1],
                            fragments[2],
                            (new SimpleDateFormat(dateFormat)).parse(fragments[3]),
                            fragments[4],
                            fragments[5].substring(20,68),
                            (Double.parseDouble(fragments[6])),
                            (Double.parseDouble(fragments[7]))
                    ));
                } else {
                    System.out.println("Wrong line" + line);
                }
            } catch (ParseException e) {
                continue;
            }
        }
        HashMap<String, Double> map = new HashMap<>();
        for (Movements moves : movements) {
            incomeSum = incomeSum + moves.income;
            expenseSum = expenseSum + moves.waste;
            map.put(moves.operation, 0.0);
        }
        Set<String> setOfMoves = map.keySet();
        for (String operation : setOfMoves) {
            double x = 0.0;
            for (Movements movements1 : movements) {
                if(movements1.operation.equals(operation)) {
                    x = x + movements1.waste;
                }
            }
            System.out.println(operation + ": " + x);
        }
    }

    public double getExpenseSum() {
        return expenseSum;
    }

    public double getIncomeSum() {
        return incomeSum;
    }
}
