package main;

import java.io.FileNotFoundException;

import fileLogic.FileManager;

public class Main {

	public static void main(String[] args) {
		try {
			FileManager.readUsers();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}