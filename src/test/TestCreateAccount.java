//package test;
//
//import static org.junit.Assert.*;
//
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.util.LinkedList;
//
//import org.junit.*;
//
//import model.Account;
//import model.Database;
//import model.Transaction;
//import model.User;
//
//public class TestCreateAccount {
//	
//	Database db;
//	
//	@Test
//	public void testCreateAccountSuccess() throws Exception {
//		String message = "";
//		String cpr = "1234567891";
//		String name = "account1";
//		String interestInt = "0.01";
//		String status = "1";
//		
//		try {
//			db = new Database();
//			User user = new User(db, cpr);
//			BigDecimal interest = new BigDecimal(interestInt.replaceAll(",", ""));
//			BigDecimal balance = new BigDecimal("0".replaceAll(",", ""));
//			String accountID = generateAccountID(user);
//			Account account = new Account(user, accountID, name, balance, interest, status, new LinkedList<Transaction>());
//			message = user.addAccount(account);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
//		
//		assertEquals(message, "Success in Creating Account");
//	}
//	
//	@Test
//	public void testMissingInput() throws NullPointerException {
//		String message = "";
//		String cpr = "1234567891";
//		String name = "account1";
//		String interestInt = "";
//		String status = "1";
//		Account account = null;
//		
//		try {
//			db = new Database();
//			User user = new User(db, cpr);
//			BigDecimal interest = new BigDecimal(interestInt.replaceAll(",", ""));
//			BigDecimal balance = new BigDecimal("0".replaceAll(",", ""));
//			String accountID = generateAccountID(user);
//			account = new Account(user, accountID, name, balance, interest, status, new LinkedList<Transaction>());
//			message = user.addAccount(account);
//		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
//			
//		}
//		
//		assertEquals(account, null);
//	}
//	
//	@Test
//	public void testWrongInput() throws NullPointerException {
//		String message = "";
//		String cpr = "1234567891";
//		String name = "account1";
//		String interestInt = "hej";
//		String status = "1";
//		Account account = null;
//		
//		try {
//			db = new Database();
//			User user = db.getUser(cpr);
//			BigDecimal interest = new BigDecimal(interestInt.replaceAll(",", ""));
//			BigDecimal balance = new BigDecimal("0".replaceAll(",", ""));
//			String accountID = generateAccountID(user);
//			account = new Account(user, accountID, name, balance, interest, status, new LinkedList<Transaction>());
//			message = user.addAccount(account);
//		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
//			
//		}
//		
//		assertEquals(account, null);
//	}
//	
//	private String generateAccountID(User user) {
//		int min = 0;
//		int max = 9;
//		String ID = "";
//		Boolean generated = false;
//		ID += 0 + (int) (Math.random()*max);
//		while (!generated) {
//			for (int i = 0; i < 13; i++) {
//				ID += min + (int) (Math.random() * max);
//			}
//			generated = true;
//			for (Account existingAccount : user.getAccounts()) {
//				if (existingAccount.getAccountID().equals(ID)) {
//					generated = false;
//					break;
//				}
//			}
//		}
//		return ID;
//	}
//	
//}
