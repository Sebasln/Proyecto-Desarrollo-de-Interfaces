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

	static public ArrayList<User> usersList = new ArrayList<>();

	public static void readUsers() throws FileNotFoundException {
		usersList.clear(); // Evitar duplicados si se llama mÃ¡s de una vez

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

				String[] userFields = line.split(";", -1);
				String username = checkNullity(userFields[0], 0);
				String password = checkNullity(userFields[1], 1);
				String email = checkNullity(userFields[2], 2);
				String userRole = checkRole(userFields[3]);
				
				// Leer estado (campo 4)
				boolean isNew = true;
				if (userFields.length > 4) {
					try {
						int stateInt = Integer.parseInt(userFields[4].trim());
						isNew = checkState(stateInt);
					} catch (NumberFormatException e) {
						System.err.println("Error parseando estado para " + username + ", asumiendo nuevo.");
					}
				}

				User user = new User(username, password, email, userRole, isNew, new ArrayList<>());

				usersList.add(user);
			}
		} catch (IOException e) {
			System.err.println("Ha ocurrido este problema: " + e.getMessage());
		}
	}

	public static void readUserPreferences() throws FileNotFoundException {
		File usersPreferences = new File("txtFiles/settings.txt");

		if (!(usersPreferences.exists())) {
			System.err
					.println("No existe settings.txt, por lo que no se pueden cargar las preferencias de los usuarios");
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
								
								// Si tiene preferencias (> 0), ya no es nuevo.
								// Excluyendo ADMIN si es necesario, aunque el ADMIN suele tener isNew=false por defecto o se ignora.
								if (u.getPreferencesList().size() > 0 && !u.getRole().equalsIgnoreCase("ADMIN")) {
									u.setNew(false);
								} else if (!u.getRole().equalsIgnoreCase("ADMIN")) {
									// Si no es admin y no tiene preferencias, es nuevo.
									u.setNew(true);
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Ha ocurrido este problema: " + e.getMessage());
		}
	}

	public static void writeUserPreferences(User user) {
		File usersPreferences = new File("txtFiles/settings.txt");

		// No check for existence, we will create/overwrite it to ensure structure.

		try (FileWriter fw = new FileWriter(usersPreferences); BufferedWriter bw = new BufferedWriter(fw)) {
			
			// 1. Escribir la Sección de URLs (Hardcoded para asegurar integridad)
			bw.write("SECTION;URL");
			bw.newLine();
			bw.write("NACIONAL;https://elpais.com/espana/;article h2 a;https://www.elmundo.es/espana.html;article header a;https://www.abc.es/espana/;article h2 a");
			bw.newLine();
			bw.write("INTERNACIONAL;https://elpais.com/internacional/;article h2 a;https://www.elmundo.es/internacional.html;article header a;https://www.abc.es/internacional/;article h2 a");
			bw.newLine();
			bw.write("ECONOMIA;https://elpais.com/economia/;article h2 a;https://www.elmundo.es/economia.html;article header a;https://www.abc.es/economia/;article h2 a");
			bw.newLine();
			bw.write("DEPORTES;https://as.com/;article h2 a;https://www.marca.com/;div.ue-c-cover-content__body h2 a;https://www.sport.es/;article h2 a");
			bw.newLine();
			bw.write("TECNOLOGIA;https://elpais.com/tecnologia/;article h2 a;https://www.elmundo.es/tecnologia.html;article header a;https://www.abc.es/tecnologia/;article h2 a");
			bw.newLine();
			bw.write("VIDEOJUEGOS;https://vandal.elespanol.com/;div.caja h2 a;https://www.3djuegos.com/;article h2 a;https://www.hobbyconsolas.com/;article h2 a");
			bw.newLine();

			// 2. Escribir la Sección de Preferencias
			bw.write("SECTION;USERS_PREFERENCES");
			bw.newLine();

			for (User u : usersList) {
				System.out.println("DEBUG: Guardando preferencias para " + u.getUsername() + " (Cats: " + (u.getPreferencesList() != null ? u.getPreferencesList().size() : 0) + ")");
				bw.write(u.getUsername());
				if (u.getPreferencesList() != null) {
					for (String category : u.getPreferencesList()) {
						bw.write(";" + category);
					}
				}
				bw.newLine();
			}
		} catch (IOException e) {
			System.err.println("Ha ocurrido este problema: " + e.getMessage());
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
			// Asegurar que empezamos en nueva línea
			// Una forma robusta es leer el archivo antes o simplemente escribir newLine() al principio si no está vacío
			// Pero append mode simple: escribir newLine() -> escribir dato.
			// Ojo: si el archivo termina con newLine, quedará una línea vacía.
			// La forma más segura dadas las circunstancias:
			bw.newLine(); 
			
			int state = 0;
			String line = username + ";" + password + ";" + email + ";" + role + ";" + state;
			bw.write(line);
			// No escribimos newLine AL FINAL para evitar lineas vacías extra problemáticas en la lectura simple
			
			System.out.println("Usuario guardado en users.txt: " + username);
		} catch (IOException e) {
			System.err.println("Ha ocurrido este problema: " + e.getMessage());
		}
		
		// Actualizar settings.txt para incluir al nuevo usuario (sin preferencias aun)
		// Simplemente guardamos la lista actual de usuarios en settings.txt
		// Como newUser ya está en usersList, aparecerá en el archivo.
		writeUserPreferences(newUser); 
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

	public static void rewriteUsersFile() {
		File usersFile = new File("txtFiles/users.txt");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersFile, false))) {
			for (User u : usersList) {
				int state = u.isNew() ? 0 : 1;
				String line = u.getUsername() + ";" + u.getPassword() + ";" + u.getEmail() + ";" + u.getRole() + ";"
						+ state;
				bw.write(line);
				bw.newLine();
			}
		} catch (IOException e) {
			System.err.println("Ha ocurrido este problema: " + e.getMessage());
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