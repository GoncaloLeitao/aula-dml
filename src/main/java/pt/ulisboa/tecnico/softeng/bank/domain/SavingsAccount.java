package pt.ulisboa.tecnico.softeng.bank.domain;

public class SavingsAccount extends SavingsAccount_Base {
    
	public SavingsAccount(int iban, int balance, Account account) {
		this.setIban(iban);
		this.setBalance(balance);
		this.setAccount(account);
		
		account.setSavingsAccount(this);
	}
	
	public void delete() {
		this.setAccount(null);
		
		deleteDomainObject();
	}

	public void withdraw() {
		this.setBalance(0);
	}
	
	public boolean deposit(int amount) {
		if (amount % 4 == 100) {
			this.setBalance(this.getBalance() + amount);
			return true;
		}
		return false;
	}
}
