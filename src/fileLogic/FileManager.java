package fileLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import objects.User;

public class FileManager {
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
				String[] userFields = line.split(";");
				String username = checkNullity(userFields[0], 0);
				String password = checkNullity(userFields[1], 1);
				String email = checkNullity(userFields[2], 2);

				String userRole = checkRole(userFields[3]);
				boolean userState = checkState(Integer.parseInt(userFields[4]));
				// Ya si eso revisamos que el email tenga lo propio de una dirección

				User user = new User(username, password, email, userRole, userState);

				usersList.add(user);

				System.out.println(user.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkState(int givenState) {
		while (true) {
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
	}

	public static String checkRole(String givenRole) {
		while (true) {
			if (givenRole.toLowerCase().equals("admin")) {
				return "ADMIN";
			} else if (givenRole.toLowerCase().equals("user")) {
				return "USER";
			} else {
				System.err.println("Error de rol de usuario, estableciendo el rol como 'USUARIO'");
				return "USER";
			}
		}
	}

	public static String checkNullity(String field, int position) {
		if (field.equals("") || field == null) {

			switch (position) {
			case 0:
				System.err.println("Se ha detectado que el nombre de un usuario está vacío, restableciendo como 'user'");
				field = "user";
				break;
			case 1:
				System.err.println("Se ha detectado que la contraseña de un usuario está vacía, restableciendo como '0000'");
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