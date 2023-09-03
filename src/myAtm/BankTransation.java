package myAtm;

import java.util.ArrayList;
import java.util.HashMap;

interface BankTransation {

	HashMap<Integer, ArrayList<String>> transations = new HashMap<>();

	void storeTransation(int accountNo, String tran);

	void showTransations();

	void transferAmmount();

}
