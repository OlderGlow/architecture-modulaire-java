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

public class CartePostaleJDBCImpl implements DAO<CartePostale> {

    private static final String SQL_INSERT = "INSERT INTO produit (libelle, marque, prixUnitaire, qteStock, type, typeCartePostale) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE produit SET libelle = ?, marque = ?, prixUnitaire = ?, qteStock = ?, type = ? WHERE refProd = ?";
    private static final String SQL_DELETE = "DELETE FROM produit WHERE refProd = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT p.refProd, p.libelle, p.marque, p.prixUnitaire, p.qteStock, p.typeCartePostale, a.nom, a.prenom FROM produit p, auteur a WHERE refProd IN (SELECT refCartePostale FROM auteur_cartePostale) AND p.refProd = ?";
    // select all produit where type = 'cartePostale' and with nom, prenom from auteur
    private static final String SQL_SELECT_ALL = "SELECT p.refProd, p.libelle, p.marque, p.prixUnitaire, p.qteStock, p.typeCartePostale, a.id, a.nom, a.prenom\n" + "FROM produit p\n" + "JOIN auteur_cartePostale ac ON p.refProd = ac.refCartePostale\n" + "JOIN auteur a ON a.id = ac.refAuteur";

    @Override
    public void insert(CartePostale objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, objet.getLibelle());
            preparedStatement.setString(2, objet.getMarque());
            preparedStatement.setDouble(3, objet.getPrixUnitaire());
            preparedStatement.setLong(4, objet.getQteStock());
            preparedStatement.setString(5, objet.getClass().getSimpleName());
            preparedStatement.setString(6, objet.getType());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new DalException("Aucune ligne n'a été ajoutée");
            } else {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    objet.setRefProd(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de l'insertion d'un produit", e);
        }

    }

    @Override
    public void update(CartePostale objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, objet.getLibelle());
            preparedStatement.setString(2, objet.getMarque());
            preparedStatement.setDouble(3, objet.getPrixUnitaire());
            preparedStatement.setLong(4, objet.getQteStock());
            preparedStatement.setString(5, objet.getClass().getSimpleName());
            preparedStatement.setLong(6, objet.getRefProd());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new DalException("Aucune ligne n'a été mise à jour");
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la mise à jour d'un produit", e);
        }

    }

    @Override
    public void delete(CartePostale objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, objet.getRefProd());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new DalException("Aucune ligne n'a été supprimée");
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la suppression d'un produit", e);
        }
    }

    @Override
    public CartePostale selectById(long id) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                CartePostale cartePostale = new CartePostale();
                List<Auteur> auteurs = new ArrayList<>();
                cartePostale.setRefProd(resultSet.getLong("refProd"));
                cartePostale.setLibelle(resultSet.getString("libelle"));
                cartePostale.setMarque(resultSet.getString("marque"));
                cartePostale.setPrixUnitaire(resultSet.getFloat("prixUnitaire"));
                cartePostale.setQteStock(resultSet.getLong("qteStock"));
                cartePostale.setType(resultSet.getString("typeCartePostale"));
                Auteur auteur = new Auteur(resultSet.getString("nom"), resultSet.getString("prenom"));
                auteurs.add(auteur);
                while (resultSet.next()) {
                    Auteur auteur2 = new Auteur(resultSet.getString("nom"), resultSet.getString("prenom"));
                    auteurs.add(auteur2);
                }
                cartePostale.setLesAuteurs(auteurs);
                return cartePostale;
            } else {
                throw new DalException("Aucun produit ne correspond à l'id " + id);
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la récupération d'un produit", e);
        }
    }

    @Override
    public List<CartePostale> selectAll() throws DalException {
        List<CartePostale> cartePostales = new ArrayList<>();
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
           do {
               resultSet.next();
                CartePostale cartePostale = new CartePostale();
                List<Auteur> auteurs = new ArrayList<>();
                cartePostale.setRefProd(resultSet.getLong("refProd"));
                cartePostale.setLibelle(resultSet.getString("libelle"));
                cartePostale.setMarque(resultSet.getString("marque"));
                cartePostale.setPrixUnitaire(resultSet.getFloat("prixUnitaire"));
                cartePostale.setQteStock(resultSet.getLong("qteStock"));
                cartePostale.setType(resultSet.getString("typeCartePostale"));
                Auteur auteur = new Auteur(resultSet.getString("nom"), resultSet.getString("prenom"));
                auteurs.add(auteur);
                cartePostale.setLesAuteurs(auteurs);
                cartePostales.add(cartePostale);
           } while (resultSet.next());
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la récupération de tous les produits", e);
        }
        return cartePostales;
    }

}