package rocket.app.view;

import java.awt.TextField;

import eNums.eAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import rocket.app.MainApp;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController {

	private MainApp mainApp;
	
	//	TODO - RocketClient.RocketMainController
	
	//	Create private instance variables for:
	//		TextBox  - 	txtIncome
	//		TextBox  - 	txtExpenses
	//		TextBox  - 	txtCreditScore
	//		TextBox  - 	txtHouseCost
	//		ComboBox -	loan term... 15 year or 30 year
	//		Labels   -  various labels for the controls
	//		Button   -  button to calculate the loan payment
	//		Label    -  to show error messages (exception throw, payment exception)
	@FXML private TextField txtIncome;
	@FXML private TextField txtExpenses;
	@FXML private TextField txtCreditScore;
	@FXML private TextField txtHouseCost;
	@FXML private ComboBox <Integer> cmbterm;
	@FXML private Label lblIncome;
	@FXML private Label lblExpenses;
	@FXML private Label lblHouseCost;
	@FXML private Label lblLoanTerm;
	@FXML private Label lblRate;
	@FXML private Label lblMortgagePayment;
	@FXML private Button btnPayment;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	
	//	TODO - RocketClient.RocketMainController
	//			Call this when btnPayment is pressed, calculate the payment
	@FXML
	public void btnCalculatePayment(ActionEvent event)
	{
		Object message = null;
		//	TODO - RocketClient.RocketMainController
		
		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		lq.setdAmount(Double.parseDouble(txtHouseCost.getText()));
		lq.setIncome(Double.parseDouble(txtIncome.getText()));
		lq.setExpenses(Double.parseDouble(txtExpenses.getText()));
		lq.setiCreditScore(Integer.valueOf(txtCreditScore.getText()));
		if(cmbterm.getValue()== 15 ){
			lq.setiTerm(15);
		}
		else
		{
			lq.setiTerm(30);
		}
		//	TODO - RocketClient.RocketMainController
		//			set the loan request details...  rate, term, amount, credit score, downpayment
		//			I've created you an instance of lq...  execute the setters in lq

		a.setLoanRequest(lq);
		
		//	send lq as a message to RocketHub		
		mainApp.messageSend(lq);
	}
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		//	TODO - RocketClient.HandleLoanRequestDetails
		//			lRequest is an instance of LoanRequest.
		//			after it's returned back from the server, the payment (dPayment)
		//			should be calculated.
		//			Display dPayment on the form, rounded to two decimal places
		double Income = lRequest.getIncome();
		double Expenses = lRequest.getExpenses();
		double Payment = lRequest.getdPayment();

		
		if ((Income*.36 - Expenses > Payment)& (Income * .28 < Payment)) {
			lblMortgagePayment.setText("$"+String.format("%.2f", lRequest.getdPayment()));
		} 
		else 
		{
			lblMortgagePayment.setText("This loan is too high");
			}

		double rte = lRequest.getdRate();
		if (rte == 0) {
			lblMortgagePayment.setText("Need better credit");

		} 
		else
		{
			lblRate.setText(Double.toString(rte)+"%");
			
		}		
	}
}
