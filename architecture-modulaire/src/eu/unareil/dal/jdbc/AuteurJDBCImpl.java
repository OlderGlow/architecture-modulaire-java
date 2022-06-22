package eu.unareil.dal.jdbc;

import eu.unareil.bo.Auteur;
import eu.unareil.bo.CartePostale;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuteurJDBCImpl implements DAO<Auteur> {

    private final static String SQL_INSERT = "INSERT INTO auteur (nom, prenom) VALUES (?, ?)";
    private final static String SQL_UPDATE = "UPDATE auteur SET nom = ?, prenom = ? WHERE id = ?";
    private final static String SQL_DELETE = "DELETE FROM auteur WHERE id = ?";
    private final static String SQL_SELECT_BY_ID = "SELECT * FROM auteur WHERE id = ?";
    private final static String SQL_SELECT_ALL = "SELECT * FROM auteur a JOIN auteur_cartePostale ba ON a.id = ba.refAuteur JOIN produit b ON b.refProd = ba.refCartePostale WHERE b.type = 'CartePostale'";
    private static final String SQL_SELECT_ALL_BY_AUTEUR = "SELECT * FROM produit WHERE refProd IN (SELECT refProd FROM auteur_cartePostale WHERE refAuteur = ?)";
    private static final CartePostaleJDBCImpl cartePostaleJDBCImpl = new CartePostaleJDBCImpl();

    @Override
    public void insert(Auteur objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, objet.getNom());
            preparedStatement.setString(2, objet.getPrenom());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new DalException("Aucune ligne n'a été ajoutée");
            } else {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    objet.setRefAuteur(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de l'insertion d'un auteur", e);
        }
    }

    @Override
    public void update(Auteur objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, objet.getNom());
            preparedStatement.setString(2, objet.getPrenom());
            preparedStatement.setLong(3, objet.getRefAuteur());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la mise à jour d'un auteur", e);
        }
    }

    @Override
    public void delete(Auteur objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, objet.getRefAuteur());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la suppression d'un auteur", e);
        }
    }

    @Override
    public Auteur selectById(long id) throws DalException {
        Auteur auteur = new Auteur();
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID); PreparedStatement preparedStatement2 = connection.prepareStatement(SQL_SELECT_ALL_BY_AUTEUR)) {
            preparedStatement.setLong(1, id);
            preparedStatement2.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                auteur = new Auteur(resultSet.getLong("id"), resultSet.getString("nom"), resultSet.getString("prenom"));
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                List<CartePostale> cartePostales = new ArrayList<>();
                while (resultSet2.next()) {
                    CartePostale cartePostale = cartePostaleJDBCImpl.selectById(resultSet2.getLong("refProd"));
                    cartePostales.add(cartePostale);
                }
                auteur.setLesCartes(cartePostales);
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la sélection d'un auteur", e);
        }
        return auteur;
    }

    @Override
    public List<Auteur> selectAll() throws DalException {
        List<Auteur> auteurs = new ArrayList<>();
        List<CartePostale> cartePostales = new ArrayList<>();
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Auteur auteur = new Auteur(resultSet.getLong("id"), resultSet.getString("nom"), resultSet.getString("prenom"));
                auteurs.add(auteur);
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la sélection de tous les auteurs", e);
        }
        return auteurs;
    }
}
