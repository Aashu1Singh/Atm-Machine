package myAtm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import myAtm.Account;


public class Bank implements BankTransation{
	HashMap<Integer, Account> accounts = new HashMap<>();

	public void createAccount() {
		
		Account a1 = new AccountHolder(123, 20, 321);
		Account a2 = new AccountHolder(1234, 200, 4321);
		
		
		accounts.put(123, a1);
		accounts.put(1234, a2);
	
	}
	
	
	public Account validate(int userAccountNo, int userPassWord) {
		
		Account userAccount = accounts.get(userAccountNo);
		
		if(!accounts.containsKey(userAccountNo)) {
			System.out.println("This accoount doesn't exit ");
			return null;
		}
		
		if(userPassWord == userAccount.password) {
			System.out.println("Logged in the account " + userAccountNo);
			return userAccount;
		}else {
			System.out.println("Wrong password ");
			return null;
		}
		
	}
	
	public void storeTransation(int accountNo, String message) {
		
		ArrayList<String> transation = transations.get(accountNo);
		if(transation == null) {
			ArrayList<String> temp = new ArrayList<>();
			temp.add(message);
			
			transations.put(accountNo, temp);
		}else {
			transation.add(message);
			transations.put(accountNo , transation);
		}
		
	}
	
	
	public void showTransations(int accountNo) {
		
		if(transations.get(accountNo)== null) {
			System.out.println("\nNo previous transations found");
			return;
			
		}

		System.out.println("Your previous transations are\n");
		for(ArrayList<String> transation : transations.values()) {
			int i = 1;
			for(String s : transation) {
				System.out.println(i++ +") " + s);
			}
		}
	}
	
	public void start() {
		
		
		createAccount();

		
		System.out.println("Enter the bank account number");
		Scanner sc = new Scanner(System.in);
		int userAccountNo;
		int userPassWord;
		try {
			userAccountNo = sc.nextInt();
			System.out.println("Enter your password");
			
			userPassWord = sc.nextInt();
			System.out.println();
		
		
		
		
		Account user = validate(userAccountNo, userPassWord);
		
		if( user == null) {
			return;
		}
		
		while(true) {
			
			
			System.out.println();
			System.out.println("Press the action number you want to perform\n");			
			System.out.println("1=> For checking account balance");
			System.out.println("2=> For withdrawing money");
			System.out.println("3=> For depositing money in your account");
			System.out.println("4=> For previous transations");
			System.out.println("0=> For quit");
			System.out.println();
			
			int task = sc.nextInt();
			
			if(task == 0) {
				break;
			}
			
			
			switch(task) {
				
				case 1:
					user.checkBalance();
					storeTransation(userAccountNo, "Checked the balance");
					break;
					
				case 2:
					storeTransation(userAccountNo, user.withdraw());
					break;
					
				case 3:
					storeTransation(userAccountNo, "The amount "+ user.deposit() + " is deposited in your account" );
					break;
					
				case 4:
					showTransations(userAccountNo);
					break;
					
				default:
					System.out.println("Enter only valid task number");
			
			}
			
		}
		
		}catch(InputMismatchException e) {
			System.out.println("Only numeric value is allowed in this application");
			return;
		}

		
				
	}
	
	
	
}
