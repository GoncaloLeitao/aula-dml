package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ist.fenixframework.FenixFramework;

public class Account extends Account_Base{
	
	public Account(int iban, int balance, Bank bank) {
		this.setIban(iban);
		this.setBalance(balance);
		this.setBank(bank);
		
		bank.addAccount(this);
	}
	
	public void delete() {
		this.setBank(null);
		
		deleteDomainObject();
	}

	public static Account getAccountByIban(int iban) {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			for (Account account : bank.getAccountSet()) {
				if (account.getIban() == iban) {
					return account;
				}
			}
		}
		return null;
	}
	
	public boolean withdraw(int amount) {
		if ((this.getBalance() - amount ) > 0) {
			this.setBalance(this.getBalance() - amount);
			return true;
		}
		return false;
	}
	
	public void deposit(int amount) {
		this.setBalance(this.getBalance() + amount);
	}
}
