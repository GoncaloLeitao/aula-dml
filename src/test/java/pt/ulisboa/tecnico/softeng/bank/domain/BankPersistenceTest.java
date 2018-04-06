package pt.ulisboa.tecnico.softeng.bank.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

public class BankPersistenceTest {
	@Test
	public void success() {
		atomicProcess();
		atomicAssert();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() {
		new Bank("Money", "BK01");
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert() {
		Bank bank = Bank.getBankByCode("BK01");

		assertEquals("Money", bank.getName());
	}
	
	@Test
	public void successAccount() {
		atomicProcess();
		atomicAccountProcess();
		atomicAccountAssert();
	}
	
	@Atomic(mode = TxMode.WRITE)
	public void atomicAccountProcess() {
		new Account(123456789, 100, Bank.getBankByCode("BK01"));
	}
	
	@Atomic(mode = TxMode.READ)
	public void atomicAccountAssert() {
		for (Account account : Bank.getBankByCode("BK01").getAccountSet()) {
			if (account.getIban() == 123456789) {
				
				assertEquals(100, account.getBalance());
				return;
			}
		}
		fail();
	}
	
	@Test
	public void sucessDeposit() {
		atomicProcess();
		atomicAccountProcess();
		atomicDepositProcess();
		atomicDepositAssert();
		
	}
	
	@Test
	public void sucessWithdrawal() {
		atomicProcess();
		atomicAccountProcess();
		atomicWithdrawalProcess();
		atomicWithdrawalAssert();
	}
	
	@Atomic(mode = TxMode.WRITE)
	public void atomicDepositProcess() {
		for (Account account : Bank.getBankByCode("BK01").getAccountSet()) {
			if (account.getIban() == 123456789) {
				account.deposit(60);
				return;
			}
		}
	}
	
	@Atomic(mode = TxMode.WRITE)
	public void atomicWithdrawalProcess() {
		for (Account account : Bank.getBankByCode("BK01").getAccountSet()) {
			if (account.getIban() == 123456789) {
				account.withdraw(60);
			}
		}
	}
	
	@Atomic(mode = TxMode.READ)
	public void atomicDepositAssert() {
		for (Account account : Bank.getBankByCode("BK01").getAccountSet()) {
			if (account.getIban() == 123456789) {
				assertEquals(account.getBalance(), 160);
				return;
			}
		}
		fail();
	}
	
	@Atomic(mode = TxMode.READ)
	public void atomicWithdrawalAssert() {
		for (Account account : Bank.getBankByCode("BK01").getAccountSet()) {
			if (account.getIban() == 123456789) {
				assertEquals(account.getBalance(), 40);
				return;
			}
		}
		fail();
	}
	
	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			for (Account account : bank.getAccountSet()) {
				account.delete();
			}
			bank.delete();
		}
	}

}
