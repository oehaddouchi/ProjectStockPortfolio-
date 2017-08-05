import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class StockPortfolioGUI {
    final String STOCK_DEFAULT="Stock";
    final String SHARES_DEFAULT="Shares";
    final String PERSHARE_DEFAULT="perShare";

    private PortfolioList stockList;
    private double cash;
    private JTextArea displayArea;
    private JButton sell;
    private JButton buy;
    private JTextField inputStock;
    private JTextField inputShares;
    private JTextField inputPerShare;
    private JLabel lblCash;
    private JTextArea displayError;
    private String Error;
    private int errorCount;

    public StockPortfolioGUI(){
        WidgetView wv = new WidgetView();
        stockList=new PortfolioList();

        cash=1000;
        Error="";
        errorCount=0;

        inputStock=new JTextField(STOCK_DEFAULT,5);
        inputShares=new JTextField(SHARES_DEFAULT,5);
        inputPerShare=new JTextField(PERSHARE_DEFAULT,5);
        sell=new JButton("Sell");
        buy=new JButton("Buy");
        lblCash=new JLabel("Cash: "+cash);
        displayArea=new JTextArea();
        displayError=new JTextArea();


        //Styling
        styleWV();

        //JButtons Click Action Listener
        addActionListenerWV();


        //JTextFields FocusListener
        addFocusListenerWV();

        //adding GUI elements to wv
        wv.add(inputStock);
        wv.add(inputShares);
        wv.add(inputPerShare);
        wv.add(lblCash);
        wv.add(sell);
        wv.add(buy);
        wv.add(displayArea);
        wv.add(displayError);
    }

    private void styleWV(){
        int hgap = 6;
        int vgap = 6;
        displayArea.setBorder(
                BorderFactory.createLineBorder(new Color(0,0,0),1));
        displayArea.setLayout(new BorderLayout(hgap, vgap));
        displayArea.setVisible(false);

        displayError.setForeground(new Color(255,0,0));
        displayError.setFont(new Font("Arial", Font.BOLD, 13));
        hgap = 5;
        vgap = 5;
        displayError.setBorder(
                BorderFactory.createLineBorder(new Color(255,0,0),1));
        displayError.setLayout(new BorderLayout(hgap, vgap));
        displayError.setVisible(false);

        sell.setBorder(
                BorderFactory.createLineBorder(new Color(18, 73, 249),1));
        sell.setPreferredSize(new Dimension(60,30));
        sell.setToolTipText("Click here to sell stocks");
        buy.setBorder(
                BorderFactory.createLineBorder(new Color(18, 73, 249),1));
        buy.setPreferredSize(new Dimension(60,30));
        buy.setToolTipText("Click here to buy stocks");
    }

    private void addActionListenerWV(){
        BuySellEvent event = new BuySellEvent();

        sell.addActionListener(event);
        buy.addActionListener(event);
        inputStock.addActionListener(event);
        inputShares.addActionListener(event);
        inputPerShare.addActionListener(event);
    }

    private void addFocusListenerWV(){
        inputStock.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(stockIsEmpty(inputStock.getText())){
                inputStock.setText("");
                }
                inputStock.setForeground(new Color(0,0,0));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(stockIsEmpty(inputStock.getText())){
                    inputStock.setText(STOCK_DEFAULT);
                }
            }
        });
        inputShares.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(sharesIsEmpty(inputShares.getText())) {
                    inputShares.setText("");
                }
                inputShares.setForeground(new Color(0,0,0));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(sharesIsEmpty(inputShares.getText())){
                    inputShares.setText(SHARES_DEFAULT);
                }
            }
        });
        inputPerShare.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(perShareIsEmpty(inputPerShare.getText())) {
                    inputPerShare.setText("");
                }
                inputPerShare.setForeground(new Color(0,0,0));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(perShareIsEmpty(inputPerShare.getText())){
                    inputPerShare.setText(PERSHARE_DEFAULT);
                }
            }
        });
    }

    //getters
    private int getInputShares(){
        int inputSharesValue=0;
        if (!sharesIsEmpty(inputShares.getText())) {
            inputSharesValue = Integer.parseInt(inputShares.getText());
        }
        return inputSharesValue;
    }
    private double getInputPerShare(){
        double inputPerShareValue=0;

        if (!perShareIsEmpty(inputPerShare.getText())) {
            inputPerShareValue =Double.parseDouble(inputPerShare.getText());
        }
        return inputPerShareValue;
    }

    private double getInputPerShare(String ticket){
        double inputPerShareValue=0;

        if (!perShareIsEmpty(inputPerShare.getText())) {
            inputPerShareValue =Double.parseDouble(inputPerShare.getText());
        }
        else if(stockList.find(ticket)!=null) {
                inputPerShareValue=stockList.find(ticket).getInitialSharePrice();
        }

        return inputPerShareValue;
    }

    //check if input is set or empty
    private Boolean stockIsEmpty(String stock){
        //if the user did enter something for the number of shares
        if (stock.length()==0 || stock.equals(STOCK_DEFAULT) ){
            return true;
        }
        else return false;
    }

    private Boolean sharesIsEmpty(String shares){
        //if the user did enter something for the number of shares
        if (shares.length()==0 || shares.equals(SHARES_DEFAULT) ){
            return true;
        }
        else return false;
    }

    private Boolean perShareIsEmpty(String perShare){
        //if the user did enter something for the current price
        if (perShare.length()==0 || perShare.equals(PERSHARE_DEFAULT) ){
            return true;
        }
        else return false;
    }

    //display
    private void defaultDisplay()
    {
        inputStock.setForeground(new Color(0,0,0));
        inputShares.setForeground(new Color(0,0,0));
        inputPerShare.setForeground(new Color(0,0,0));
        lblCash.setForeground(new Color(0,0,0));

        displayArea.setVisible(false);
        displayError.setVisible(false);
    }

    private void updatePortfolioDisplay(){
        lblCash.setText("Cash: "+cash);

        if(stockList.toString().trim().length()>0) displayArea.setVisible(true);
        displayArea.setText("Stock List:\n\n"+stockList.toString());


        //empty fields
        if(errorCount==0) {
            inputStock.setText(STOCK_DEFAULT);
            inputShares.setText(SHARES_DEFAULT);
            inputPerShare.setText(PERSHARE_DEFAULT);
        }

        if(Error.trim().length()>0) displayError.setVisible(true);
        displayError.setText("Error Log:\n\n"+Error);
        Error="";
        errorCount=0;
    }

    private void errorCounter(){
        errorCount++;
        if(errorCount==1) Error+="Error("+errorCount+"): ";
        else Error+="\nError("+errorCount+"): ";
    }

    //event handler
    class BuySellEvent extends WidgetViewActionEvent {

        @Override
        public void actionPerformed(ActionEvent e) {
            defaultDisplay();
            String ticker=inputStock.getText();
            int shares=getInputShares();
            double perSharePrice=getInputPerShare(ticker);

            if(e.getSource()==buy){
                   if(stockList.find(ticker)!=null){
                        //already existing stock, just add shares
                       if(cash>=shares*perSharePrice && !sharesIsEmpty(inputShares.getText())) {
                           //ignore any transaction that causes the cash position to go below $0
                           int originalShares = stockList.find(ticker).getShares();
                           int currentShares = originalShares + shares;
                           double originalPerShare = stockList.find(ticker).getInitialSharePrice();

                           StockHolding stock = new StockHolding(ticker, currentShares, originalPerShare);

                           stockList.remove(ticker);
                           stockList.add(stock);
                           cash-=shares*perSharePrice;
                       }
                       else{
                           //error
                           if(cash<shares*perSharePrice){
                               errorCounter();
                               Error+="This purchase costs more than your cash ";
                               lblCash.setForeground(new Color(255,0,0));
                           }

                           if(sharesIsEmpty(inputShares.getText())){
                               errorCounter();
                               Error+="Shares input is empty ";
                               inputShares.setForeground(new Color(255,0,0));
                           }
                       }
                    }
                    else{
                        //add new stock
                       if(cash>=shares*perSharePrice && !sharesIsEmpty(inputShares.getText())  && !perShareIsEmpty(inputPerShare.getText()) ) {
                           //ignore any transaction that causes the cash position to go below $0
                           StockHolding stock = new StockHolding(ticker, shares, perSharePrice);
                           stockList.add(stock);

                           cash-=shares*perSharePrice;
                       }
                       else{
                           //error
                           if(cash<shares*perSharePrice){
                               errorCounter();
                               Error+="This purchase costs more than your cash ";
                           }
                           if(stockIsEmpty(inputStock.getText())){
                               errorCounter();
                               Error+="Stock input is empty ";
                               inputStock.setForeground(new Color(255,0,0));
                           }
                           if(sharesIsEmpty(inputShares.getText())){
                               errorCounter();
                               Error+="Shares input is empty ";
                               inputShares.setForeground(new Color(255,0,0));
                           }
                           if(perShareIsEmpty(inputPerShare.getText())){
                               errorCounter();
                               Error+="PerShare input is empty ";
                               inputPerShare.setForeground(new Color(255,0,0));
                           }
                       }
                    }

            }

            else{ //e.getSource()==sell
                if(stockList.find(ticker)!=null) {
                    //ticker exists

                    int currentShares = stockList.find(ticker).getShares();

                    int leftShares = currentShares - shares;
                    StockHolding stock = new StockHolding(ticker, leftShares, perSharePrice);

                    stockList.remove(ticker);

                    if (!sharesIsEmpty(inputShares.getText())) {
                        //sold some, update stock
                        stockList.add(stock);
                        cash += shares * perSharePrice;
                    } else {
                        //sold all
                        cash += currentShares * perSharePrice;
                    }

                }
                else{
                    //error
                    if(stockIsEmpty(inputStock.getText())){
                        errorCounter();
                        Error+="Stock input is empty ";
                        inputStock.setForeground(new Color(255,0,0));
                    }
                    else{
                        errorCounter();
                        Error+="Stock does not exist ";
                        inputStock.setForeground(new Color(255,0,0));
                    }

                }
            }

            updatePortfolioDisplay();
        }
    }
}
