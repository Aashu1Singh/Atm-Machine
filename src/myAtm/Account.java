package myAtm;

abstract public class Account {

	public int accountNo;
	public int balance;
	public int password;

	abstract public void checkBalance();

	abstract public int deposit();

	abstract public String withdraw();

}
