package pt.ulisboa.tecnico.softeng.bank.domain;

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
