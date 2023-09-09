package myAtm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bank implements BankTransation {

	private String bankname;
	private AtmOfBank atm;
	private Account loggedInUserAcc;

	Bank(String bankName, AtmOfBank atm) {
		this.bankname = bankName;
		this.atm = atm;
	}

	public Account validate(int userAccountNo, int userPassWord) {

		List<Account> userAccounts = atm.accounts.get(this.bankname);
		Account userAccount = null;

		for (Account a : userAccounts) {
			if (a.accountNo == userAccountNo) {
				userAccount = a;
				break;
			}

		}

		if (userAccount == null) {
			System.out.println("This accoount doesn't exit in " + this.bankname);
			atm.startAtm(atm);
		}

		if (userPassWord == userAccount.password) {
			System.out.println("Logged in the Account no. " + userAccountNo);
			System.out.println("Welcome " + userAccount.accountHolderName + "!!!");

			this.loggedInUserAcc = userAccount;
			return userAccount;
		} else {
			System.out.println("!!! Wrong password !!!");
			return null;
		}

	}

	public void storeTransation(int accountNo, String message) {

		ArrayList<String> transaction = transations.get(accountNo);

		if (transaction == null) {
			ArrayList<String> temp = new ArrayList<>();

			temp.add(message);
			transations.put(accountNo, temp);
		} else {
			transaction.add(message);
			transations.put(accountNo, transaction);
		}

	}

	public void showTransations() {

		ArrayList<String> foundTransation = transations.get(loggedInUserAcc.accountNo);
//		System.out.println(transations);

		if (foundTransation == null) {
			System.out.println("\nNo previous transations found");
			return;
		}

		int count = 1;
		System.out.println("Your previous transations are:\n");
		for (String transation : foundTransation) {
			System.out.println(count++ + ") " + transation);
		}
	}

	public void showDetails() {
		System.out.println("*************Account Details*************\n");
		System.out.println("Accountholder Name: " + loggedInUserAcc.accountHolderName);
		System.out.println("Account Number: " + loggedInUserAcc.accountNo);
		System.out.println("Account Balance: Rs " + loggedInUserAcc.balance);
		System.out.println("\n*****************************************\n");
	}

	public void start() {

		System.out.println("\nEnter your " + this.bankname + " bank account number");

		Scanner sc = new Scanner(System.in);
		int userAccountNo = 000;
		int userPassWord = 000;

		String a = sc.nextLine();
		System.out.println("Enter your password");
		String b = sc.nextLine();

		System.out.println();

		try {
			userAccountNo = Integer.parseInt(a);
		} catch (Exception e) {
			System.out.println("This  account doesn't exist in " + this.bankname);
			atm.startAtm(atm);

		}

		try {
			userPassWord = Integer.parseInt(b);
		} catch (Exception e) {
			System.out.println("!!! WRONG PASSWORD !!! ");
			start();

		}

		Account user = validate(userAccountNo, userPassWord);

		if (user == null) {
			start();
		}

		while (true) {

			System.out.println("\n*****************************************\n");
			System.out.println("Press the action number you want to perform\n");
			System.out.println("1) CHECK ACCOUNT BALANCE");
			System.out.println("2) WITHDRAW MONEY");
			System.out.println("3) DEPOSIT MONEY IN YOUR ACCOUNT");
			System.out.println("4) ACCOUNT'S TRANSACTIONS");
			System.out.println("5) TRANSFER MONEY");
			System.out.println("6) ACCOUNT DETAILS");
			System.out.println("7) LOG OUT FROM " + this.bankname);
			System.out.println("0) QUIT");
			System.out.println();
			
			
			int task;
			try{
				task = sc.nextInt();
				
			}catch(Exception e) {
				task = 100;
				 sc.nextLine();
				
			}


			if (task == 0) {
				System.out.println("Program Ended");
				System.exit(0);
			}

			if (task == 7) {
				user = null;
				atm.startAtm(atm);
			}

			switch (task) {

			case 1:
				user.checkBalance();
				storeTransation(userAccountNo, "Checked the balance");
				break;

			case 2:
				storeTransation(userAccountNo, user.withdraw());
				break;

			case 3:
				storeTransation(userAccountNo, "The amount " + user.deposit() + " is deposited in your account");
				break;

			case 4:
				showTransations();
				break;
			case 5:
				transferAmmount();
				break;
			case 6:
				showDetails();
				break;
			case 7:
				System.out.println("Logged out of " + this.bankname);
				break;

			default:
				System.out.println("Enter only valid action number");
				break;

			}

		}

	}

	public Account beneficiaryPresent(int beneficiaryAccountNo, int ammountToTransfer) {

		for (Map.Entry<String, List<Account>> a : atm.accounts.entrySet()) {

			List<Account> temp = a.getValue();

			for (Account ac : temp) {

				if (ac.accountNo == beneficiaryAccountNo) {

					loggedInUserAcc.balance = loggedInUserAcc.balance - ammountToTransfer;
					storeTransation(loggedInUserAcc.accountNo,
							"You had transferred Rs " + ammountToTransfer + " to account no " + beneficiaryAccountNo);
					ac.balance = ac.balance + ammountToTransfer;
					storeTransation(ac.accountNo, "The amount Rs " + ammountToTransfer
							+ " has been transferred in your account by acount no. " + loggedInUserAcc.accountNo);
					return ac;
				}
			}
		}
		return null;
	}

	public void transferAmmount() {

		System.out.println("\nEnter the bank account number to transfer amount");

		Scanner sc = new Scanner(System.in);

		int beneficiaryAccountNo = sc.nextInt();

		if (loggedInUserAcc.accountNo == beneficiaryAccountNo) {
			System.out.println("You cannot transfer money in your account");
			return;
		}

		System.out.println("Enter ammount to transfer");
		int ammountToTransfer = sc.nextInt();

		if (ammountToTransfer > loggedInUserAcc.balance) {
			System.out.println("Insufficient balance in your account");
			return;
		}

		System.out.println("\nEnter your passsword");
		int password = sc.nextInt();

		if (loggedInUserAcc.password != password) {
			System.out.println("Incorrect password");
			return;
		}

		Account benificiaryAcc = beneficiaryPresent(beneficiaryAccountNo, ammountToTransfer);

		if (benificiaryAcc == null) {
			System.out.println("account not found");
		} else {
			System.out.println(
					"The amount RS" + ammountToTransfer + " has been trasfered to account no. " + beneficiaryAccountNo);
		}

	}
}
