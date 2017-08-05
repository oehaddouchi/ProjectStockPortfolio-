import java.util.ArrayList;

public class PortfolioList {
    private ArrayList<StockHolding> list= new ArrayList<StockHolding>();

    public PortfolioList(){
    }
    public void add(StockHolding stock){
        list.add(stock);
    }
    public void remove(String ticker){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getTicker().equals(ticker)) {
                list.remove(i);
                i--;
            }
        }
    }
    public StockHolding find(String ticker){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getTicker().equals(ticker)) {
                return list.get(i);
            }
        }
        return null;
    }
    public String toString(){
        String str="";
        for(int i=0;i<list.size();i++){
                str+=list.get(i).toString()+"\n";
        }
        return str;
    }

}
