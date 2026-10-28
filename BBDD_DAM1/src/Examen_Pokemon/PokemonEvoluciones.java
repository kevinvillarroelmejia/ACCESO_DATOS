package Examen_Pokemon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PokemonEvoluciones {

    public static void main(String[] args) {
        String usuario = "admin";
        String password = "1234";
        String server = "jdbc:mysql://localhost:3306/pokemondb";

        try (Connection conexion = DriverManager.getConnection(server, usuario, password)) {
            evolucionesPosibles(conexion, "Bulbasaur");
            evolucionesPosibles(conexion, "Ivysaur");
            evolucionesPosibles(conexion, "Venusaur");
            evolucionesPosibles(conexion, "Mew");
            evolucionesPosibles(conexion, "Eevee");
            evolucionesPosibles(conexion, "Sylveon");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void evolucionesPosibles(Connection conexion, String pokemon) throws SQLException {

        // 1. Buscar el pokemon por nombre
        PreparedStatement query = conexion.prepareStatement(
                "SELECT numero_pokedex, nombre FROM pokemon WHERE nombre = ?",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        query.setString(1, pokemon);
        ResultSet resultado = query.executeQuery();
        resultado.last();

        if (resultado.getRow() == 0) {
            System.out.println("El pokemon " + pokemon + " no está en la pokedex o no es de la primera generación\n");
            return;
        }

        // Leer datos del pokemon
        resultado.beforeFirst();
        resultado.next();
        int idPokemon = resultado.getInt("numero_pokedex");
        String nombre = resultado.getString("nombre");

        System.out.println(nombre + " (#" + idPokemon + ")");

        // 2. Buscar en qué evoluciona (pokemon_origen = su id)
        query = conexion.prepareStatement(
                "SELECT pokemon_evolucionado FROM evoluciona_de WHERE pokemon_origen = ?",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        query.setInt(1, idPokemon);
        ResultSet evoluciones = query.executeQuery();
        evoluciones.last();

        if (evoluciones.getRow() == 0) {
            System.out.println("- no evoluciona en ningún otro");
        } else {
            evoluciones.beforeFirst();
            System.out.print("- evoluciona en ");
            while (evoluciones.next()) {
                int idEvolucion = evoluciones.getInt("pokemon_evolucionado");
                // Buscar nombre de la evolución
                query = conexion.prepareStatement("SELECT nombre FROM pokemon WHERE numero_pokedex = ?");
                query.setInt(1, idEvolucion);
                ResultSet datosEvo = query.executeQuery();
                datosEvo.next();
                System.out.print(datosEvo.getString("nombre") + " (#" + idEvolucion + ") ");
            }
            System.out.println();
        }

        // 3. Buscar de quién viene (pokemon_evolucionado = su id)
        query = conexion.prepareStatement(
                "SELECT pokemon_origen FROM evoluciona_de WHERE pokemon_evolucionado = ?",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        query.setInt(1, idPokemon);
        ResultSet origenes = query.executeQuery();
        origenes.last();

        if (origenes.getRow() == 0) {
            System.out.println("- no evoluciona de ningún otro");
        } else {
            origenes.beforeFirst();
            origenes.next();
            int idOrigen = origenes.getInt("pokemon_origen");
            query = conexion.prepareStatement("SELECT nombre FROM pokemon WHERE numero_pokedex = ?");
            query.setInt(1, idOrigen);
            ResultSet datosOrigen = query.executeQuery();
            datosOrigen.next();
            System.out.println("- evoluciona de " + datosOrigen.getString("nombre") + " (#" + idOrigen + ")");
        }

        System.out.println();
    }
}