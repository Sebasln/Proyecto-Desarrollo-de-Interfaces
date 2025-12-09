package fileLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import objects.User;

public class UserLogic {
	/* Aquí irán los métodos que traten con los archivos de texto */

	static public ArrayList<User> usersList = new ArrayList<>();

	public static void readUsers() throws FileNotFoundException {

		File users = new File("txtFiles/users.txt");

		if (!(users.exists())) {
			System.err.println("No existe users.txt, por lo que no se pueden cargar los usuarios");
			return;
		}

		FileReader fr = new FileReader(users);
		BufferedReader br = new BufferedReader(fr);
		String line;

		try {
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;

				String[] userFields = line.split(";", -1); // -1 para mantener campos vacíos al final
				String username = checkNullity(userFields[0], 0);
				String password = checkNullity(userFields[1], 1);
				String email = checkNullity(userFields[2], 2);

				String userRole = checkRole(userFields[3]);
				int stateInt = 0;
				try {
					if (userFields.length > 4 && !userFields[4].isEmpty()) {
						stateInt = Integer.parseInt(userFields[4]);
					}
				} catch (NumberFormatException e) {
					System.err.println("Error parsing state for user " + username + ", defaulting to 0/True");
				}
				boolean userState = checkState(stateInt);
				// Ya si eso revisamos que el email tenga lo propio de una dirección

				User user = new User(username, password, email, userRole, userState, null);

				usersList.add(user);

				// System.out.println(user.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readUserPreferences() throws FileNotFoundException {
		File usersPreferences = new File("txtFiles/userSettings.txt");

		if (!(usersPreferences.exists())) {
			System.err.println("No existe usersSettings.txt, por lo que no se pueden cargar los usuarios");
			return;
		}

		FileReader fr = new FileReader(usersPreferences);
		BufferedReader br = new BufferedReader(fr);
		String line;

		try {
			while ((line = br.readLine()) != null) {
				String[] categories = line.split(";");

				if (categories.length < 1)
					continue;

				String username = categories[0];

				ArrayList<String> userPreferences = new ArrayList<>();

				for (int i = 1; i < categories.length; i++) {
					userPreferences.add(categories[i]);
				}

				for (User u : usersList) {
					if (u.getUsername().equals(username)) {
						u.setPreferencesList(userPreferences);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeUserPreferences(User user) {
		File usersPreferences = new File("txtFiles/userSettings.txt");

		if (!(usersPreferences.exists())) {
			System.err.println("No existe usersSettings.txt, por lo que no se pueden guardar las preferencias");
			return;
		}

		try (FileWriter fw = new FileWriter(usersPreferences);
			BufferedWriter bw = new BufferedWriter(fw)) {
			
			for (User u : usersList) {
				bw.write(u.getUsername());
				for (String category : u.getPreferencesList()) {
					bw.write(";" + category);
				}
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public static boolean checkState(int givenState) {
		if (givenState == 0) {
			return true;
		} else if (givenState == 1) {
			return false;
		} else if (givenState == -1) {
			return false;
		} else {
			System.err.println("Error de estado de usuario, devolviendo TRUE...");
			return true;
		}
	}

	public static String checkRole(String givenRole) {
		if (givenRole.toLowerCase().equals("admin")) {
			return "ADMIN";
		} else if (givenRole.toLowerCase().equals("user")) {
			return "USER";
		} else {
			System.err.println("Error de rol de usuario, estableciendo el rol como 'USUARIO'");
			return "USER";
		}
	}

	public static String checkNullity(String field, int position) {
		if (field.equals("") || field == null) {

			switch (position) {
			case 0:
				System.err
						.println("Se ha detectado que el nombre de un usuario está vacío, restableciendo como 'user'");
				field = "user";
				break;
			case 1:
				System.err.println(
						"Se ha detectado que la contraseña de un usuario está vacía, restableciendo como '0000'");
				field = "0000";
				break;
			case 2:
				System.err.println(
						"Se ha detectado que la dirección de correo de un usuario está vacía, restableciendo como 'user@gmail.com'");
				field = "user@gmail.com";
				break;
			}
			return field;
		} else {
			return field;
		}
	}

}