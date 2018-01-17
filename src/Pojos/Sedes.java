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
public class Sedes extends Persistencia implements Serializable {

    private String Estado;
    private String nombre;
    private String direccion;
    private String telefono;

    private SedesID objSedesID;
    private Empresas objEmpresa;

    public Sedes() {
        super();
    }

    

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public SedesID getObjSedesID() {
        if (objSedesID == null) {
            objSedesID = new SedesID();
        }
        return objSedesID;
    }

    public void setObjSedesID(SedesID objSedesID) {
        this.objSedesID = objSedesID;
    }

    public Empresas getObjEmpresa() {
        if (objEmpresa == null) {
            objEmpresa = new Empresas();
        }
        return objEmpresa;
    }

    public void setObjEmpresa(Empresas objEmpresa) {
        this.objEmpresa = objEmpresa;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into Sedes (idempresa,Estado,nombre,direccion,telefono) values (?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setInt(1, objEmpresa.getIdEmpresa());
            preparedStatement.setString(2, Estado);
            preparedStatement.setString(3, nombre);
            preparedStatement.setString(4, direccion);
            preparedStatement.setString(5, telefono);

            transaccion = Sedes.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update Sedes set Estado=?,Nombre=?,Direccion=?,Telefono=? where idSede=? and idempresa=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, Estado);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, direccion);
            preparedStatement.setString(4, telefono);
            preparedStatement.setInt(5, objSedesID.getIdSede());
            preparedStatement.setInt(6, objSedesID.getIdEmpresa());

            transaccion = Sedes.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from Sedes where idSede=? and idempresa=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, objSedesID.getIdSede());
            preparedStatement.setInt(2, objSedesID.getIdEmpresa());

            transaccion = Sedes.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<Sedes> List = new ArrayList();
        String prepareQuery = "select * from Sedes where idempresa = " + objEmpresa.getIdEmpresa();
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Sedes.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Sedes tabla = new Sedes();
                tabla.getObjEmpresa().setIdEmpresa(rs.getInt(2));
                tabla.getObjSedesID().setIdSede(rs.getInt(1));
                tabla.setEstado(rs.getString(3));
                tabla.setNombre(rs.getString(4));
                tabla.setDireccion(rs.getString(4));
                tabla.setTelefono(rs.getString(5));
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
    
     public Sedes getSedeById(int id) {
        Sedes s = null;
        String prepareQuery = "select idSede,Nombre from sedes where idSede = " + id + "";        
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Sedes.super.getConecion().query(prepareQuery);
            if (rs.absolute(1)) {
                s = new Sedes();
                s.getObjSedesID().setIdSede(rs.getInt(1));
                s.setNombre(rs.getString(2));
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
        return s;
    }
    

    @Override
    public String toString() {
        return nombre;
    }

    
    
}
