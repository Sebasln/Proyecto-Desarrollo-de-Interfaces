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
		try (FileReader fr = new FileReader(users); BufferedReader br = new BufferedReader(fr);) {
			String line;
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

				User user = new User(username, password, email, userRole, userState, new ArrayList<>());

				usersList.add(user);

				// System.out.println(user.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readUserPreferences() throws FileNotFoundException {
		File usersPreferences = new File("txtFiles/settings.txt");

		if (!(usersPreferences.exists())) {
			System.err.println("No existe settings.txt, por lo que no se pueden cargar las preferencias de los usuarios");
			return;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(usersPreferences))) {
			String line;
			boolean inPreferences = false;

			while ((line = br.readLine()) != null) {
				String trimmedLine = line.trim();
				if (trimmedLine.isEmpty())
					continue;

				if (trimmedLine.startsWith("SECTION;")) {
					inPreferences = trimmedLine.contains("USERS_PREFERENCES");
					continue;
				}

				if (inPreferences) {
					String[] parts = trimmedLine.split(";");
					if (parts.length > 0) {
						String username = parts[0];
						ArrayList<String> userPreferences = new ArrayList<>();

						for (int i = 1; i < parts.length; i++) {
							if (!parts[i].trim().isEmpty()) {
								userPreferences.add(parts[i].trim());
							}
						}

						for (User u : usersList) {
							if (u.getUsername().equals(username)) {
								u.setPreferencesList(userPreferences);
								u.setNew(false);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeUserPreferences(User user) {
		File usersPreferences = new File("txtFiles/settings.txt");

		if (!(usersPreferences.exists())) {
			System.err.println("No existe settings.txt, por lo que no se pueden guardar las preferencias");
			return;
		}

		try (FileWriter fw = new FileWriter(usersPreferences); BufferedWriter bw = new BufferedWriter(fw)) {

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


	public static void createNewUser(String username, String password, String email, String role) {
		for (User u : usersList) {
			if (u.getUsername().equals(username)) {
				System.err.println("El usuario ya existe.");
				return;
			}
		}
		
		User newUser = new User(username, password, email, role, true);
		usersList.add(newUser); 
		
		File usersFile = new File("txtFiles/users.txt");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersFile, true))) {
			int state = 0;
			String line = username + ";" + password + ";" + email + ";" + role + ";" + state;
			bw.write(line);
			bw.newLine();
			System.out.println("Usuario guardado en users.txt: " + username);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteUser(String username) {
		User userToRemove = null;
		for (User u : usersList) {
			if (u.getUsername().equals(username)) {
				userToRemove = u;
				break;
			}
		}

		if (userToRemove != null) {
			usersList.remove(userToRemove);
			rewriteUsersFile();
			System.out.println("Usuario eliminado con éxito: " + username);
		} else {
			System.err.println("No se encontró el usuario: " + username);
		}
	}

	private static void rewriteUsersFile() {
		File usersFile = new File("txtFiles/users.txt");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersFile, false))) {
			for (User u : usersList) {
				int state = u.isNew() ? 0 : 1;
				String line = u.getUsername() + ";" + u.getPassword() + ";" + u.getEmail() + ";" + u.getRole() + ";" + state;
				bw.write(line);
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