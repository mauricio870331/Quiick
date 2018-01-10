package Coneccion;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import org.apache.commons.dbcp.BasicDataSource;

public class poolConecciones {

    public Statement stat;
    public Connection con;
    public DataSource dataSource;
    public CallableStatement cstmt;

    public poolConecciones(BasicDataSource basicDataSource) {
        dataSource = basicDataSource;

    }

    public ResultSet query(String query) throws SQLException {
        stat = con.createStatement();
        ResultSet res = stat.executeQuery(query);
        return res;
    }

    public BigDecimal Currval(String query) throws SQLException {
        stat = con.createStatement();
        ResultSet res = stat.executeQuery(query);
        BigDecimal codigo = null;
        if (res.next()) {
            codigo = res.getBigDecimal(1);
        }
        return codigo;
    }

    public int transaccion(PreparedStatement preparedStatement) throws SQLException {
        int result = preparedStatement.executeUpdate();
        return result;
    }

    public boolean TransaccionDirecta(String query) {
        boolean r = true;
        try {
            stat.executeUpdate(query);
            r = true;
        } catch (SQLException e) {
            System.out.println("ERROR Al HACER UPDTAPE" + e.toString());
            r = false;
        }
        return r;
    }

    public int EliminarTransaciones(int proceso, int clave) {
        int transaccion = -1;
        String SQL = "select * from EliminarTransaciones(" + proceso + "," + clave + ")";
        try {
            System.out.println("QUERY : " + SQL);
            ResultSet rs = query(SQL);
            if (rs.next()) {
                transaccion = rs.getInt(1);
            }

        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
        }
        return transaccion;
    }

    public void probarConecion() {
        try {
            con = dataSource.getConnection();
            if (con != null) {
                JOptionPane.showMessageDialog(null, "Conectado");
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    public Connection getconecion() {
        return con;
    }

    public Statement getStat() {
        return stat;
    }

    public void setStat(Statement stat) {
        this.stat = stat;
    }

    public static void main(String[] args) {
//        BasicDataSource DataSource = new BasicDataSource();
//        DataSource.setDriverClassName("org.postgresql.Driver");
//        DataSource.setUsername("postgres");
//        DataSource.setPassword("Juan");
//        DataSource.setUrl("jdbc:postgresql://localhost:5432/prueba");
//        DataSource.setMaxActive(50);
//        poolConecciones pool = new poolConecciones(DataSource);
//        pool.probarConecion();

    }
}
