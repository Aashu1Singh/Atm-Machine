package myAtm;


abstract public class Account {
	public int accountNo;
	public int ammount;
	public int password;
	
	abstract public void checkBalance();
	
	abstract public int deposit();
	
	abstract public String withdraw();
		

}
