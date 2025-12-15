package fileLogic;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import objects.User;
import programLogic.MessageUtils;

public class UserLogic {

	static public ArrayList<User> usersList = new ArrayList<>();

	public static void readUsers() throws FileNotFoundException {
		usersList.clear(); // antes me la lio repitiendo usuarios

		File users = new File("txtFiles/users.txt");

		if (!(users.exists())) {
			MessageUtils.showError(null, "No existe users.txt, por lo que no se pueden cargar los usuarios");
			return; // por si magicamente se borra users.txt
		}
		try (FileReader fr = new FileReader(users); BufferedReader br = new BufferedReader(fr);) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) {
					continue;
				}

				String[] userFields = line.split(";", -1);
				if (userFields.length < 4) {
					MessageUtils.showError(null, "Línea mal formada en users.txt (faltan campos): " + line);
					continue;
				}

				String username = checkNullity(userFields[0], 0);
				String password = checkNullity(userFields[1], 1);
				String email = checkNullity(userFields[2], 2);
				String userRole = checkRole(userFields[3]);

				// como la version 20 de leer si es nuevo o no
				boolean isNew = true;
				if (userFields.length > 4) {
					try {
						int stateInt = Integer.parseInt(userFields[4].trim());
						isNew = checkState(stateInt);
					} catch (NumberFormatException e) {
						MessageUtils.showError(null, "Error parseando estado para " + username + ", asumiendo nuevo.");
					}
				}

				User user = new User(username, password, email, userRole, isNew, new ArrayList<>());
				// le metemos un arraylist vacio y ya luego lo rellenamos
				usersList.add(user);
			}
		} catch (IOException e) {
			MessageUtils.showError(null, "Ha ocurrido este problema: " + e.getMessage(), e);
		}
	}

	public static void readUserPreferences() throws FileNotFoundException {
		File usersPreferences = new File("txtFiles/settings.txt");

		if (!(usersPreferences.exists())) {
			MessageUtils.showError(null,
					"No existe settings.txt, por lo que no se pueden cargar las preferencias de los usuarios");
			return; // de nuevo, por si magicamente se borra settings.txt
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
								if (u.getPreferencesList().size() > 0 && !u.getRole().equalsIgnoreCase("ADMIN")) {
									u.setNew(false);
								} else if (!u.getRole().equalsIgnoreCase("ADMIN")) {
									u.setNew(true);
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			MessageUtils.showError(null, "Ha ocurrido este problema: " + e.getMessage(), e);
		}
	}

	public static void writeUserPreferences(User user) {
		File usersPreferences = new File("txtFiles/settings.txt");
		ArrayList<String> linesToPreserve = new ArrayList<>();

		if (usersPreferences.exists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(usersPreferences))) {
				String line;
				while ((line = br.readLine()) != null) {
					String trimmed = line.trim();
					if (trimmed.startsWith("SECTION;USERS_PREFERENCES")) {
						break;
					}
					linesToPreserve.add(line);
				}
			} catch (IOException e) {
				MessageUtils.showError(null, "Error leyendo settings.txt para preservar configuración: " + e.getMessage(), e);
			}
		}

		// Eliminar lineas vacias al final para evitar crecimiento infinito de huecos
		while (!linesToPreserve.isEmpty() && linesToPreserve.get(linesToPreserve.size() - 1).trim().isEmpty()) {
			linesToPreserve.remove(linesToPreserve.size() - 1);
		}

		try (FileWriter fw = new FileWriter(usersPreferences); BufferedWriter bw = new BufferedWriter(fw)) {
			for (String line : linesToPreserve) {
				bw.write(line);
				bw.newLine();
			}

			// para reescribir la seccion de preferencias
			bw.newLine();
			bw.write("SECTION;USERS_PREFERENCES");
			bw.newLine();

			for (int i = 0; i < usersList.size(); i++) {
				User u = usersList.get(i);
				bw.write(u.getUsername());
				if (u.getPreferencesList() != null) {
					for (String category : u.getPreferencesList()) {
						bw.write(";" + category);
					}
				}
				if (i < usersList.size() - 1) {
					bw.newLine();
				}
			}
		} catch (IOException e) {
			MessageUtils.showError(null, "Ha ocurrido este problema: " + e.getMessage(), e);
		}
	}

	public static void createNewUser(String username, String password, String email, String role) {
		for (User u : usersList) {
			if (u.getUsername().equals(username)) {
				MessageUtils.showError(null, "El usuario ya existe.");
				return;
			}
		}

		User newUser = new User(username, password, email, role, true);
		usersList.add(newUser);

		appendUserToFile(newUser);
		writeUserPreferences(newUser);
	}

	public static void appendUserToFile(User u) {
		File usersFile = new File("txtFiles/users.txt");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersFile, true))) { // poner ejemplo ejercicio fran
			bw.newLine(); // para que no me escriba un usuario detras de otro sin saltar de linea

			int state;
			if (u.isNew()) {
				state = 0;
			} else {
				state = 1;
			}
			String line = u.getUsername() + ";" + u.getPassword() + ";" + u.getEmail() + ";" + u.getRole() + ";"
					+ state;
			bw.write(line);

			JOptionPane.showMessageDialog(null, "Usuario guardado correctamente: " + u.getUsername(), "Usuario Creado",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			MessageUtils.showError(null, "Ha ocurrido este problema: " + e.getMessage(), e);
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
			writeUserPreferences(null);
			JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito: " + username, "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "No se encontró el usuario: " + username, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void rewriteUsersFile() {
		File usersFile = new File("txtFiles/users.txt");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersFile, false))) {
			for (int i = 0; i < usersList.size(); i++) {
				User u = usersList.get(i);
				int state;
				if (u.isNew()) {
					state = 0;
				} else {
					state = 1;
				}
				String line = u.getUsername() + ";" + u.getPassword() + ";" + u.getEmail() + ";" + u.getRole() + ";"
						+ state;

				bw.write(line);

				// para que no me escriba un usuario detras de otro sin saltar de linea
				if (i < usersList.size() - 1) {
					bw.newLine();
				}
			}
		} catch (IOException e) {
			MessageUtils.showError(null, "Ha ocurrido este problema: " + e.getMessage(), e);
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
			MessageUtils.showError(null, "Error de estado de usuario, devolviendo TRUE...");
			return true;
		}
	}

	public static String checkRole(String givenRole) {
		if (givenRole.toLowerCase().equals("admin")) {
			return "ADMIN";
		} else if (givenRole.toLowerCase().equals("user")) {
			return "USER";
		} else {
			MessageUtils.showError(null, "Error de rol de usuario, estableciendo el rol como 'USUARIO'");
			return "USER";
		}
	}

	public static String checkNullity(String field, int position) {
		if (field.equals("") || field == null) {

			switch (position) {
			case 0:
				MessageUtils.showError(null,
						"Se ha detectado que el nombre de un usuario está vacío, restableciendo como 'user'");
				field = "user";
				break;
			case 1:
				MessageUtils.showError(null,
						"Se ha detectado que la contraseña de un usuario está vacía, restableciendo como '0000'");
				field = "0000";
				break;
			case 2:
				MessageUtils.showError(null,
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