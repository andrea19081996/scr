package it.uniroma3.scr.utilities;

/**
 * Classe che modella la console
 * @author Jerin George Mathew
 *
 */
public class UserInterfaceConsole implements UserInterface {

	@Override
	public void print(String message) {
		System.out.print(message);
	}

	@Override
	public void println(String message) {
		System.out.println(message);
	}

	@Override
	public void println() {
		System.out.println();		
	}

}
