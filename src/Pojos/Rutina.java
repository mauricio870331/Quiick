/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author admin
 */
public class Rutina extends Persistencia implements Serializable {

    private int idRutina;
    private String Descripcion;
    private Date FechaCreacion;
    private String estado;
    SimpleDateFormat sa = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private ArrayList<Ejercicios> newRutina;

    public Rutina() {
        super();
    }

    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public Date getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(Date FechaCreacion) {
        this.FechaCreacion = FechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int create() {
        int transaccion = -1;
        int currerntInsert;
        String prepareInsert = "insert into Rutina (Descripcion,FechaCreacion,Estado) values (?,?,?)";
        String insertRutdiaejercicio = "insert into rutdiaejercicio (idRutina,idEjercicios,IdMusculo,Series,repeticiones,Observaciones,Estado) "
                + "values (?,?,?,?,?,?,?)";

        try {
           
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, Descripcion);
            preparedStatement.setString(2, sa.format(FechaCreacion));
            preparedStatement.setString(3, estado);
            transaccion = Rutina.this.getConecion().transaccion(preparedStatement);
            currerntInsert = ultimoid();
            //rutinaejercicio
            for (Ejercicios ejercicios : newRutina) {
                PreparedStatement stm = this.getConecion().con.prepareStatement(insertRutdiaejercicio);
                stm.setInt(1, currerntInsert);
                stm.setInt(2, ejercicios.getObjEjerciciosID().getIdEjercicio());
                stm.setInt(3, ejercicios.getObjEjerciciosID().getIdMusculo());
                stm.setInt(4, ejercicios.getSeries());
                stm.setInt(5, ejercicios.getRepeticiones());
                stm.setString(6, ejercicios.getObservaciones());
                stm.setString(7, "A");
                transaccion = Rutina.this.getConecion().transaccion(preparedStatement);
            }

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
        String PrepareUpdate = "update Rutina set Descripcion=?,FechaCreacion=?,Estado=? where idRutina=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setInt(1, idRutina);
            preparedStatement.setString(2, Descripcion);
            preparedStatement.setString(3, sa.format(FechaCreacion));
            preparedStatement.setString(4, estado);

            transaccion = Rutina.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from Rutina where idrutina=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, idRutina);

            transaccion = Rutina.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<Rutina> List = new ArrayList();
        String prepareQuery = "select * from Rutina";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Rutina.super.getConecion().query(prepareQuery);

            while (rs.next()) {
                Rutina tabla = new Rutina();
                tabla.setIdRutina(rs.getInt(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setFechaCreacion(rs.getDate(3));
                tabla.setEstado(rs.getString(4));

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

    public int ultimoid() {
        int id = -1;
        try {
            String sql = "Select LAST_INSERT_ID()";
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Rutina.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                id = rs.getInt(1);
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
        return id;
    }

    public ArrayList<Ejercicios> getNewRutina() {
        return newRutina;
    }

    public void setNewRutina(ArrayList<Ejercicios> newRutina) {
        this.newRutina = newRutina;
    }

}
