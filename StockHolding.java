public class StockHolding {

    private String tickerSymbol;
    private int numberOfShares;
    private double stockInitialPrice;
    private double stockCurrentPrice;

    public StockHolding(){
    }
    public StockHolding(String ticker, int numberShares, double initialPrice){
        tickerSymbol=ticker;
        numberOfShares=numberShares;
        stockInitialPrice=initialPrice;
        stockCurrentPrice=initialPrice;
    }
    public String getTicker(){
        return tickerSymbol;
    }
    public int  getShares(){
        return numberOfShares;
    }
    public double getInitialSharePrice(){
        return stockInitialPrice;
    }
    public double getCurrentSharePrice(){
        return stockCurrentPrice;
    }
    public double getInitialCost(){
        return numberOfShares*stockInitialPrice;
    }
    public double getCurrentValue(){
        return numberOfShares*stockCurrentPrice;
    }
    public double getCurrentProfit(){
        return numberOfShares*(stockCurrentPrice-stockInitialPrice);
    }
    public String toString(){
        return "Stock: "+tickerSymbol+", Shares: "+numberOfShares+", per Share: "+stockInitialPrice;
    }
    public void setCurrentSharePrice(double sharePrice){
        stockCurrentPrice=sharePrice;
    }
}
