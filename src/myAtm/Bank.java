package myAtm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import myAtm.Account;


public class Bank implements BankTransation{
	
	HashMap<String, List<Account>> accounts = new HashMap<>();
	
	
	private String bankname;
	
	Bank(String bankName){
		this.bankname = bankName;
	}
	

	public void createAccount() {
		
		Account a1 = new AccountHolder(201, 208, 102);
		Account a2 = new AccountHolder(202, 200, 202);
		Account a3 = new AccountHolder(301, 20, 103);
		Account a4 = new AccountHolder(302, 200, 203);
		
		List<Account> sbiAcc = new ArrayList<>();
		sbiAcc.add(a1);
		sbiAcc.add(a2);
		
		List<Account> pnbAcc = new ArrayList<>();
		pnbAcc.add(a3);
		pnbAcc.add(a4);
		
		
		accounts.put("SBI", sbiAcc);
		accounts.put("PNB", pnbAcc);
	
	}
	
	
	public Account validate(int userAccountNo, int userPassWord) {
		
		List<Account> userAccounts = accounts.get(this.bankname);
		Account userAccount = null;
		
		for(Account a : userAccounts) {
			if(a.accountNo == userAccountNo) {
				userAccount = a;
				break;
			}
			
		}
		
		if(userAccount == null) {
			System.out.println("This accoount doesn't exit in "+ this.bankname);
			AtmOfBank.startAtm();
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
		
		
		System.out.println("\nLogged in into " + this.bankname + " server");
		
		createAccount();
		
		System.out.println("\nEnter the bank account number");
		
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
				System.out.println("1) Checking account balance");
				System.out.println("2) Withdrawing money");
				System.out.println("3) Depositing money in your account");
				System.out.println("4) Previous transations");
				System.out.println("5) Transfering money");
				System.out.println("6) Logging out of the " + this.bankname);
				System.out.println("0) Quit");
				System.out.println();
			
				int task = sc.nextInt();
			
				if(task == 0) {
					break;
				}
			
				if(task == 6) {
					user = null;
					AtmOfBank.startAtm();
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
					case 5:
//						validate(userAccountNo);
						break;
					case 6:
						System.out.println("Logged out of "+this.bankname);
						break;
					
					default:
						System.out.println("Enter only valid action number");
			
				}
			
		}
		
		}catch(InputMismatchException e) {
			System.out.println("Only numeric value is allowed in this application");
			return;
		}

		
				
	}
	
	public boolean transferAmmount(int accountNo, int ammount) {
		
		boolean accFound = false;
		
		for(Map.Entry<String, List<Account>> a: accounts.entrySet()) {
			
			List<Account> temp = a.getValue();
			
			for(Account ac : temp) {
				if(ac.accountNo == accountNo) {
					accFound = true;
					System.out.println("The amount " + ammount + " has been credited in account no "+ac.accountNo);
					ac.ammount += ammount;
				}
			}
			
			if(!accFound) {
				System.out.println("\nAccount not found");
			}
				
		}
		
		return accFound;
		
	}
}
