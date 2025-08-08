import java.util.*;

class Stock {
    String symbol;
    String name;
    double price;

    Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    void updatePrice() {
        double change = (Math.random() - 0.5) * 10; // -5 to +5
        price = Math.max(1, price + change);
    }

    void displayInfo() {
        System.out.println(symbol + " (" + name + ") - $" + String.format("%.2f", price));
    }
}

class Transaction {
    String type;
    String stockSymbol;
    int quantity;
    double price;

    Transaction(String type, String stockSymbol, int quantity, double price) {
        this.type = type;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
    }

    void displayTransaction() {
        System.out.println(type + " " + quantity + " shares of " + stockSymbol + " @ $" + price);
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    List<Transaction> transactions = new ArrayList<>();
    double cashBalance;

    Portfolio(double initialCash) {
        this.cashBalance = initialCash;
    }

    void buyStock(Stock s, int qty) {
        double cost = s.price * qty;
        if (cashBalance >= cost) {
            cashBalance -= cost;
            holdings.put(s.symbol, holdings.getOrDefault(s.symbol, 0) + qty);
            transactions.add(new Transaction("BUY", s.symbol, qty, s.price));
            System.out.println("Bought " + qty + " shares of " + s.symbol);
        } else {
            System.out.println("Not enough cash!");
        }
    }

    void sellStock(Stock s, int qty) {
        if (holdings.getOrDefault(s.symbol, 0) >= qty) {
            cashBalance += s.price * qty;
            holdings.put(s.symbol, holdings.get(s.symbol) - qty);
            transactions.add(new Transaction("SELL", s.symbol, qty, s.price));
            System.out.println("Sold " + qty + " shares of " + s.symbol);
        } else {
            System.out.println("Not enough shares!");
        }
    }

    void showPortfolio(List<Stock> market) {
        System.out.println("\nPortfolio:");
        for (String symbol : holdings.keySet()) {
            int qty = holdings.get(symbol);
            double price = market.stream().filter(s -> s.symbol.equals(symbol)).findFirst().get().price;
            System.out.println(symbol + ": " + qty + " shares @ $" + String.format("%.2f", price));
        }
        System.out.println("Cash: $" + String.format("%.2f", cashBalance));
    }

    void showTransactions() {
        System.out.println("\nTransaction History:");
        for (Transaction t : transactions) {
            t.displayTransaction();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create market stocks
        List<Stock> market = Arrays.asList(
                new Stock("AAPL", "Apple Inc", 150),
                new Stock("TSLA", "Tesla Inc", 700),
                new Stock("GOOGL", "Alphabet Inc", 2800)
        );

        Portfolio portfolio = new Portfolio(10000); // Start with $10,000

        while (true) {
            System.out.println("\n===== STOCK TRADING PLATFORM =====");
            System.out.println("1. Show Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    for (Stock s : market) {
                        s.updatePrice();
                        s.displayInfo();
                    }
                    break;
                case 2:
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = sc.next().toUpperCase();
                    Stock buyStock = market.stream().filter(s -> s.symbol.equals(buySymbol)).findFirst().orElse(null);
                    if (buyStock != null) {
                        System.out.print("Enter quantity: ");
                        int qty = sc.nextInt();
                        portfolio.buyStock(buyStock, qty);
                    } else {
                        System.out.println("Stock not found!");
                    }
                    break;
                case 3:
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = sc.next().toUpperCase();
                    Stock sellStock = market.stream().filter(s -> s.symbol.equals(sellSymbol)).findFirst().orElse(null);
                    if (sellStock != null) {
                        System.out.print("Enter quantity: ");
                        int qtySell = sc.nextInt();
                        portfolio.sellStock(sellStock, qtySell);
                    } else {
                        System.out.println("Stock not found!");
                    }
                    break;
                case 4:
                    portfolio.showPortfolio(market);
                    break;
                case 5:
                    portfolio.showTransactions();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
