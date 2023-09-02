package myAtm;

import java.util.Scanner;

public class AtmOfBank {
	
	public static void startAtm() {
		Bank sbi = new Bank("SBI");
		Bank canera = new Bank("canera");
		Bank pnb = new Bank("PNB");
		
		System.out.println("\nEnter the bank code in which you have your bank account\n");
		System.out.println("1) SBI");
		System.out.println("2) PNB");
		System.out.println("0 For terminating the application\n");
		Scanner sc = new Scanner(System.in);
		int bankCode = sc.nextInt();
		
		switch(bankCode) {
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
				startAtm();
		}
		
		
	}
	
	public static void main(String[] args) {
		
		
		AtmOfBank.startAtm();
	
		System.out.println("Program terminated");
	

}
	

	
	


}
