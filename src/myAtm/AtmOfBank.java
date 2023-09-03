package myAtm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AtmOfBank {

	HashMap<String, List<Account>> accounts = new HashMap<>();

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

	public void start() {
		AtmOfBank atm = new AtmOfBank();
		atm.createAccount();
		atm.startAtm(atm);

	}

	public void startAtm(AtmOfBank atm) {

		Bank sbi = new Bank("SBI", atm);
		Bank canera = new Bank("canera", atm);
		Bank pnb = new Bank("PNB", atm);

		System.out.println("\nEnter the bank code in which you have your bank account\n");
		System.out.println("1) SBI");
		System.out.println("2) PNB");
		System.out.println("0 For terminating the application\n");
		Scanner sc = new Scanner(System.in);
		int bankCode = sc.nextInt();

		switch (bankCode) {
			case 1:
				sbi.start();
				break;
			case 2:
				pnb.start();
				break;
			case 0:
				break;
			default:
				System.out.println("\nWrong bank code!!!!");
				startAtm(atm);
		}

	}

	public static void main(String[] args) {

		new AtmOfBank().start();

		System.out.println("Program terminated");

	}

}
