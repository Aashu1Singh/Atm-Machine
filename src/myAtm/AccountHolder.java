package myAtm;

import java.util.Scanner;


public class AccountHolder extends Account{
	
	AccountHolder(int a){
		
		accountNo = a;
		this.balance= 20000;
		this.password = 0000;
	}
	
	AccountHolder(int a, int ammount, int password){
		
		accountNo = a;
		this.balance= ammount;
		this.password = password;
	}


	public void checkBalance() {
		System.out.println("\nYour balance is " + this.balance);
	}
	
	
	public int deposit(){
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nEnter the ammount you want to deposit");
		
		int ammount = sc.nextInt();	
		
		this.balance = this.balance + ammount;
		
		System.out.println("\nyour new account balance is Rs " + this.balance);
		return ammount;
	}

	
	public String withdraw() {
		
		int attempts = 3;
		Scanner sc = new Scanner(System.in);
		
		while(attempts > 0) {
			System.out.println("\nEnter your account password");
			int enteredPassword = sc.nextInt();
			if(enteredPassword == this.password) {
				break;
			}
			System.out.println("Wrong password ");
			attempts--;
			System.out.println(attempts + " Attempts left");
		}
		
		if(attempts == 0) {
			return "You entered wrong password three times";
		}
		
		System.out.println("\nEnter the ammount you want to withdraw");
		
		int ammount = sc.nextInt();		
		if(ammount > this.balance) {
			System.out.println("\nInsufficient balance");
			return "Withdrawn failed due to Insufficient balance";
		}else {
			this.balance = this.balance - ammount;
			System.out.println("Rs "+ ammount + " withdrawn ");
			return "Rs " + ammount + " has been deducted from you account";
		}
		
	}
}
