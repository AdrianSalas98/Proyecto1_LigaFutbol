package pkg;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main2 {

	public static void main(String[] args) {
		Main.obtenerRutas();
		procedureCrearTablas();
		procedureInsertarEquipos();
		procedureInsertarJugadores();
		procedureInsertarClasificacion();
		procedureInsertarPartidos();
	}

	public static Connection conectarDB() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/futbol_db", "root", "");
		} catch (Exception e) {
			System.out.print(e);
		}

		return conn;
	}

	public static void procedureCrearTablas() {
		Connection con = conectarDB();
		CallableStatement cs = null;
		try {
			String sql1 = "SELECT * FROM EQUIPOS";
			PreparedStatement ps = con.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				cs = con.prepareCall("{call CrearTablas}");
				cs.execute();
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		} finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
					System.err.println("SQLException: " + e.getMessage());
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println("SQLException: " + e.getMessage());
				}
			}
		}
	}

	public static void procedureInsertarEquipos() {
		Connection con = conectarDB();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(Main.rutaEquipos));

			String sql1 = "SELECT * FROM EQUIPOS";
			PreparedStatement ps = con.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == false) {
				CallableStatement callableStatement = con.prepareCall("{call InsertarEquipos(?,?)}");

				NodeList equipo = doc.getElementsByTagName("Equipo");
				for (int i = 0; i < equipo.getLength(); i++) {
					NodeList nl1 = doc.getElementsByTagName("ID_Equipo");
					callableStatement.setInt(1, Integer.parseInt(nl1.item(i).getTextContent()));
					NodeList nl2 = doc.getElementsByTagName("Nombre_Equipo");
					callableStatement.setString(2, nl2.item(i).getTextContent());
					callableStatement.executeUpdate();
				}
			}

		} catch (SQLException e) {
			System.err.println(e);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println("SQLException: " + e.getMessage());
				}
			}
		}
	}

	public static void procedureInsertarJugadores() {
		Connection con = conectarDB();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(Main.rutaJugadores));

			String sql1 = "SELECT * FROM JUGADORES";
			PreparedStatement ps = con.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == false) {
				CallableStatement callableStatement = con.prepareCall("{call InsertarJugadores(?,?,?,?,?)}");

				NodeList jugador = doc.getElementsByTagName("Jugador");

				for (int i = 0; i < jugador.getLength(); i++) {
					NodeList nl1 = doc.getElementsByTagName("ID_Jugador");
					callableStatement.setInt(1, Integer.parseInt(nl1.item(i).getTextContent()));
					NodeList nl2 = doc.getElementsByTagName("Nombre_Jugador");
					callableStatement.setString(2, nl2.item(i).getTextContent());
					NodeList nl3 = doc.getElementsByTagName("Posicion");
					callableStatement.setString(3, nl3.item(i).getTextContent());
					NodeList nl4 = doc.getElementsByTagName("ID_Equipo");
					callableStatement.setInt(4, Integer.parseInt(nl4.item(i).getTextContent()));
					NodeList nl5 = doc.getElementsByTagName("Nombre_Equipo");
					callableStatement.setString(5, nl5.item(i).getTextContent());

					callableStatement.executeUpdate();
				}
			}

		} catch (SQLException e) {
			System.err.println(e);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println("SQLException: " + e.getMessage());
				}
			}
		}
	}

	public static void procedureInsertarClasificacion() {
		Connection con = conectarDB();
		try {
			String sql1 = "SELECT * FROM CLASIFICACION";
			String sql2 = "SELECT COUNT(Nombre_Equipo) FROM EQUIPOS";
			String sql3 = "SELECT * FROM EQUIPOS";

			PreparedStatement ps = con.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == false) {
				CallableStatement callableStatement = con.prepareCall("{call InsertarClasificacion(?,?,?,?,?,?)}");

				ps = con.prepareStatement(sql2);
				rs = ps.executeQuery();
				int numEquipos = 0;
				if (rs.next()) {
					numEquipos = rs.getInt(1);
				}

				String[] equipos = new String[numEquipos];

				ps = con.prepareStatement(sql3);
				rs = ps.executeQuery();

				for (int i = 0; i < equipos.length; i++) {
					while (rs.next()) {
						equipos[i] = rs.getString(2);
						callableStatement.setInt(1, 1);
						callableStatement.setString(2, equipos[i].toString());
						callableStatement.setInt(3, 0);
						callableStatement.setInt(4, 0);
						callableStatement.setInt(5, 0);
						callableStatement.setInt(6, 0);

						callableStatement.executeUpdate();
					}
				}
			}

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println("SQLException: " + e.getMessage());
				}
			}
		}
	}

	public static void procedureInsertarPartidos() {
		Connection con = conectarDB();
		try {
			String sql1 = "SELECT * FROM PARTIDOS";

			PreparedStatement ps = con.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == false) {
				CallableStatement callableStatement = con.prepareCall("{call InsertarPartidos(?,?,?,?)}");

				ps = con.prepareStatement(sql1);
				rs = ps.executeQuery();

				if (rs.next() == false) {

					callableStatement.setString(1, "El Pozo Murcia");
					callableStatement.setInt(2, 0);
					callableStatement.setString(3, "Movistar Inter");
					callableStatement.setInt(4, 0);

					callableStatement.executeUpdate();

					callableStatement.setString(1, "Viña Albali Valdepeñas");
					callableStatement.setInt(2, 0);
					callableStatement.setString(3, "Barça");
					callableStatement.setInt(4, 0);

					callableStatement.executeUpdate();

					callableStatement.setString(1, "C.A Osasuna Magna");
					callableStatement.setInt(2, 0);
					callableStatement.setString(3, "Palma Futsal");
					callableStatement.setInt(4, 0);

					callableStatement.executeUpdate();
				}
			}

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println("SQLException: " + e.getMessage());
				}
			}
		}
	}

}
