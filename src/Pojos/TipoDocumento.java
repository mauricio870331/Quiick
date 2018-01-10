/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class TipoDocumento extends Persistencia implements Serializable {

    private int idTipoDocumento;
    private String Descripcion;
    private String Estado;

    public TipoDocumento() {
        super();
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into TipoDocumento (Descripcion,estado) values (?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, Descripcion);
            preparedStatement.setString(2, Estado);

            transaccion = TipoDocumento.this.getConecion().transaccion(preparedStatement);
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
        } finally {
            try {
                this.getConecion().getconecion().setAutoCommit(true);
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return transaccion;
    }

    @Override
    public int edit() {
        int transaccion = -1;
        String PrepareUpdate = "update TipoDocumento set Descripcion=?,Estado=? where idTipoDocumento=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, Descripcion);
            preparedStatement.setString(2, Estado);
            preparedStatement.setInt(3, idTipoDocumento);

            transaccion = TipoDocumento.this.getConecion().transaccion(preparedStatement);
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
        } finally {
            try {
                this.getConecion().getconecion().setAutoCommit(true);
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return transaccion;
    }

    @Override
    public int remove() {
        int transaccion = -1;
        String PrepareDelete = "delete from TipoDocumento where idTipoDocumento=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, idTipoDocumento);

            transaccion = TipoDocumento.this.getConecion().transaccion(preparedStatement);
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
        } finally {
            try {
                this.getConecion().getconecion().setAutoCommit(true);
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return transaccion;
    }

    @Override
    public java.util.List List() {
        ArrayList<TipoDocumento> List = new ArrayList();
        String prepareQuery = "select * from TipoDocumento";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = TipoDocumento.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                TipoDocumento tabla = new TipoDocumento();
                tabla.setIdTipoDocumento(rs.getInt(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setEstado(rs.getString(3));
                List.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return List;
    }

    public TipoDocumento getTipoDocById(int id) {
        TipoDocumento td = null;
        String prepareQuery = "select idTipoDocumento,Descripcion from TipoDocumento where idTipoDocumento = " + id + "";        
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = TipoDocumento.super.getConecion().query(prepareQuery);
            if (rs.absolute(1)) {
                td = new TipoDocumento();
                td.setIdTipoDocumento(rs.getInt(1));
                td.setDescripcion(rs.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return td;
    }

    @Override
    public String toString() {
        return Descripcion;
    }

}
