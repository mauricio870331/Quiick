/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Empresa extends Persistencia implements Serializable {

    private int idempresa;
    private String Nombre;
    private String direccion;
    private String Telefono;
    private InputStream logo;
    private String estado;
    private int codCurrent;

    public Empresa(int idempresa, String Nombre) {
        super();
        this.idempresa = idempresa;
        this.Nombre = Nombre;
    }

    public Empresa() {
        super();
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public InputStream getLogo() {
        return logo;
    }

    public void setLogo(InputStream logo) {
        this.logo = logo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCodCurval() {
        return codCurrent;
    }

    public void setCodCurval(int codCurrent) {
        this.codCurrent = codCurrent;
    }
    
    
    
     public Empresa getEmpresaById(int id) {
        Empresa e = null;
        String prepareQuery = "select idempresa,Nombre from empresa where idempresa = " + id + "";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Empresa.super.getConecion().query(prepareQuery);
            if (rs.absolute(1)) {
                e = new Empresa();
                e.setIdempresa(rs.getInt(1));
                e.setNombre(rs.getString(2));
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
        return e;
    }
    

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into Empresa (Nombre,direccion,telefono,logo,estado) values (?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, Nombre);
            preparedStatement.setString(2, direccion);
            preparedStatement.setString(3, Telefono);
            preparedStatement.setBinaryStream(4, logo);
            preparedStatement.setString(5, estado);
            transaccion = Empresa.this.getConecion().transaccion(preparedStatement);
//            codCurrent = Empresa.this.getConecion().Currval("select currval('sq_mproducto')");
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
        String PrepareUpdate = "update Empresa set Nombre=?,direccion=?,telefono=?,logo=?,estado=? where idEmpresa=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, Nombre);
            preparedStatement.setString(2, direccion);
            preparedStatement.setString(3, Telefono);
            preparedStatement.setBinaryStream(4, logo);
            preparedStatement.setString(5, estado);
            preparedStatement.setInt(6, idempresa);

            transaccion = Empresa.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from Empresa where idEmpresa=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, idempresa);

            transaccion = Empresa.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<Empresa> List = new ArrayList();
        String prepareQuery = "select * from Empresa";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Empresa.super.getConecion().query(prepareQuery);
            Empresa tabla = new Empresa();
            while (rs.next()) {
                tabla.setIdempresa(rs.getInt(1));
                tabla.setNombre(rs.getString(2));
                tabla.setDireccion(rs.getString(3));
                tabla.setTelefono(rs.getString(4));
                tabla.setLogo(rs.getBinaryStream(5));
                tabla.setEstado(rs.getString(6));
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

    @Override
    public String toString() {
        return Nombre ;
    }
    
    
   
    
    
}
