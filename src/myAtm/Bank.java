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
			System.out.println("Logged in the account " + userAccountNo);
			this.loggedInUserAcc = userAccount;
			return userAccount;
		} else {
			System.out.println("Wrong password ");
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

		if (foundTransation == null) {
			System.out.println("\nNo previous transations found");
			return;

		}

		System.out.println("Your previous transations are\n");
		for (String transation : foundTransation) {

			int i = 1;
			System.out.println(i++ + ") " + transation);
		}
	}

	public void start() {

		System.out.println("\nLogged in into " + this.bankname + " server");

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

			if (user == null) {
				return;
			}

			while (true) {

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

				if (task == 0) {
					break;
				}

				if (task == 6) {
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
						storeTransation(userAccountNo,
								"The amount " + user.deposit() + " is deposited in your account");
						break;

					case 4:
						showTransations();
						break;
					case 5:
						transferAmmount();
						break;
					case 6:
						System.out.println("Logged out of " + this.bankname);
						break;

					default:
						System.out.println("Enter only valid action number");

				}

			}

		} catch (InputMismatchException e) {
			System.out.println("Only numeric value is allowed in this application");
			return;
		}

	}

	public Account beneficiaryPresent(int beneficiaryAccountNo, int ammountToTransfer) {

		for (Map.Entry<String, List<Account>> a : atm.accounts.entrySet()) {

			List<Account> temp = a.getValue();

			for (Account ac : temp) {
				if (ac.accountNo == beneficiaryAccountNo) {

					loggedInUserAcc.balance = loggedInUserAcc.balance - ammountToTransfer;
					storeTransation(loggedInUserAcc.accountNo,
							"The amount " + ammountToTransfer + " has been deducted from your account");
					ac.balance = ac.balance + ammountToTransfer;
					storeTransation(ac.accountNo,
							"The amount " + ammountToTransfer + " has been credited in your account");
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
					"The amount " + ammountToTransfer + " has been trasfered to account no " + beneficiaryAccountNo);
		}

	}
}
