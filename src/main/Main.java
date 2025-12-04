package main;

import java.io.FileNotFoundException;

import fileLogic.UserLogic;

public class Main {

	public static void main(String[] args) {
		
		try {
			UserLogic.readUsers();
			UserLogic.readUserPreferences();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}