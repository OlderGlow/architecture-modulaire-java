package eu.unareil.dal.jdbc;

import eu.unareil.bo.*;
import eu.unareil.dal.DalException;

import java.time.LocalDate;
import java.util.List;

public class TestJDBCImpl {
    public static void main(String[] args) {
        AuteurJDBCImpl auteurJDBC = new AuteurJDBCImpl();
        CartePostaleJDBCImpl cartePostaleJDBC = new CartePostaleJDBCImpl();
        PainJDBCImpl painJDBC = new PainJDBCImpl();
        GlaceJDBCImpl glaceJDBC = new GlaceJDBCImpl();
        ProduitJDBCImpl produitJDBC = new ProduitJDBCImpl();
        try {
            System.out.println(cartePostaleJDBC.selectAll());
        } catch (DalException e) {
            e.printStackTrace();
        }
    }
}
