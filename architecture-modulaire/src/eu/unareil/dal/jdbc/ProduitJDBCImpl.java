package eu.unareil.dal.jdbc;

import eu.unareil.bo.Produit;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitJDBCImpl implements DAO<Produit> {
    private static final String SQL_INSERT = "INSERT INTO produit (libelle, marque, prixUnitaire, qteStock, type) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE produit SET libelle = ?, marque = ?, prixUnitaire = ?, qteStock = ?, type = ? WHERE refProd = ?";
    private static final String SQL_DELETE = "DELETE FROM produit WHERE refProd = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM produit WHERE refProd = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM produit";
    private static final String SQL_ADD_RELATION_AUTEUR_CARTE_POSTALE = "INSERT INTO auteur_cartePostale (refAuteur, refCartePostale) VALUES (?, ?)";
    private static final String SQL_SELECT_ALL_BY_CARTE_POSTALE = "SELECT * FROM produit WHERE refProd IN (SELECT refProd FROM auteur_cartePostale WHERE refCartePostale = ?)";
    @Override
    public void insert(Produit objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, objet.getLibelle());
            preparedStatement.setString(2, objet.getMarque());
            preparedStatement.setDouble(3, objet.getPrixUnitaire());
            preparedStatement.setLong(4, objet.getQteStock());
            preparedStatement.setString(5, objet.getClass().getSimpleName());
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
    public void update(Produit objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, objet.getLibelle());
            preparedStatement.setString(2, objet.getMarque());
            preparedStatement.setDouble(3, objet.getPrixUnitaire());
            preparedStatement.setLong(4, objet.getQteStock());
            preparedStatement.setString(5, objet.getClass().getSimpleName());
            preparedStatement.setLong(6, objet.getRefProd());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la mise à jour d'un produit", e);
        }
    }

    @Override
    public void delete(Produit objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, objet.getRefProd());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la suppression d'un produit", e);
        }
    }

    @Override
    public Produit selectById(long id) throws DalException {
        try (Connection connection = JDBCTools.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Produit(resultSet.getLong("refProd"), resultSet.getString("libelle"), resultSet.getString("marque"), resultSet.getFloat("prixUnitaire"), resultSet.getLong("qteStock"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la sélection d'un produit", e);
        }
    }

    @Override
    public List<Produit> selectAll() throws DalException {
        List<Produit> produits = new ArrayList<>();
        try (Connection connection = JDBCTools.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                produits.add(new Produit(resultSet.getLong("refProd"), resultSet.getString("libelle"), resultSet.getString("marque"), resultSet.getFloat("prixUnitaire"), resultSet.getLong("qteStock")));
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la sélection de tous les produits", e);
        }
        return produits;
    }
}
