/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
public class Ejercicios extends Persistencia implements Serializable {

    private String Descripcion;
    private InputStream imagen;
    private String pathImagen;
    private int dia;
//    private int filaSelected;
    private String opcion = "Agregar";
    private int series;
    private int repeticiones;
    private String observaciones;
    private String estado;

    @Override
    public String toString() {
        return Descripcion;
    }

    private EjerciciosID objEjerciciosID;

    public Ejercicios() {
        super();
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public EjerciciosID getObjEjerciciosID() {
        if (objEjerciciosID == null) {
            objEjerciciosID = new EjerciciosID();
        }
        return objEjerciciosID;
    }

    public void setObjEjerciciosID(EjerciciosID objEjerciciosID) {
        this.objEjerciciosID = objEjerciciosID;
    }

    @Override
    public int create() {
        int transaccion = -1;
        FileInputStream fis = null;
        File file = null;
        String prepareInsert = "insert into Ejercicios (idMusculo,descripcion,imagen) values (?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            if (!pathImagen.equals("")) { //si se adjunta la foto
                file = new File(pathImagen);
                fis = new FileInputStream(file);
            }
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setInt(1, objEjerciciosID.getIdMusculo());
            preparedStatement.setString(2, Descripcion);
            if (file != null) {
                preparedStatement.setBinaryStream(3, fis, (int) file.length());
            } else {
                preparedStatement.setString(3, null);
            }
            transaccion = Ejercicios.this.getConecion().transaccion(preparedStatement);
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
        } catch (FileNotFoundException ex) {
            System.out.println("Error archivo : " + ex.toString());
        } finally {
            try {
                file = null;
                fis = null;
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
        FileInputStream fis = null;
        File file = null;
        String img = "";
        try {
            if (!pathImagen.equals("")) { //si se adjunta la foto
                file = new File(pathImagen);
                fis = new FileInputStream(file);
                img = ",imagen=?";
            }
            String PrepareUpdate = "update Ejercicios set idMusculo=?,descripcion=?" + img + " where idEjercicios=? and "
                    + " idMusculo=?";
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);

            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setInt(1, objEjerciciosID.getIdMusculo());
            preparedStatement.setString(2, Descripcion);
            if (file != null) {
                preparedStatement.setBinaryStream(3, fis, (int) file.length());
                preparedStatement.setInt(4, objEjerciciosID.getIdEjercicio());
                preparedStatement.setInt(5, objEjerciciosID.getIdMusculo());
            } else {
                preparedStatement.setInt(3, objEjerciciosID.getIdEjercicio());
                preparedStatement.setInt(4, objEjerciciosID.getIdMusculo());
            }
            transaccion = Ejercicios.this.getConecion().transaccion(preparedStatement);
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
        } catch (FileNotFoundException ex) {
            System.out.println("Error img : " + ex.toString());
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
        String PrepareDelete = "delete from ejercicios where idEjercicios=? and "
                + " idMusculo=?";
        System.out.println("PrepareDelete " + "delete from ejercicios where idMusculo =" + objEjerciciosID.getIdMusculo() + " and "
                + " idEjercicios=" + objEjerciciosID.getIdEjercicio() + "");
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, objEjerciciosID.getIdEjercicio());
            preparedStatement.setInt(2, objEjerciciosID.getIdMusculo());

            transaccion = Ejercicios.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<Ejercicios> List = new ArrayList();
        String prepareQuery = "select * from Ejercicios";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Ejercicios.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Ejercicios tabla = new Ejercicios();
                EjerciciosID tablaId = new EjerciciosID();
                tablaId.setIdEjercicio(rs.getInt(1));
                tablaId.setIdMusculo(rs.getInt(2));
                tabla.setDescripcion(rs.getString(3));
                tabla.setImagen(rs.getBinaryStream(4));
                tabla.setObjEjerciciosID(tablaId);
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

    public InputStream getImagenByIdEjercicio(int idEjercicios) {
        InputStream imagen = null;
        String prepareQuery = "select imagen from Ejercicios where idEjercicios = " + idEjercicios;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Ejercicios.super.getConecion().query(prepareQuery);
            if (rs.absolute(1)) {
                imagen = rs.getBinaryStream(1);            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return imagen;
    }

    public ArrayList<Ejercicios> ListEjercicioByIdMusculo(int ismusculo) {
        ArrayList<Ejercicios> List = new ArrayList();
        String prepareQuery = "select * from Ejercicios where IdMusculo = " + ismusculo;
        System.out.println("prepareQuery " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Ejercicios.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Ejercicios ej = new Ejercicios();
                EjerciciosID ejid = new EjerciciosID();
                ejid.setIdEjercicio(rs.getInt(1));
                ejid.setIdMusculo(rs.getInt(2));
                ej.setDescripcion(rs.getString(3));
                ej.setImagen(rs.getBinaryStream(4));
                ej.setObjEjerciciosID(ejid);
                List.add(ej);
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

    public Ejercicios getEjercicioById(int idEjercicios) {
        Ejercicios ej = null;
        EjerciciosID ejid = null;
        String prepareQuery = "select * from Ejercicios where idEjercicios = " + idEjercicios;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Ejercicios.super.getConecion().query(prepareQuery);
            if (rs.absolute(1)) {
                ej = new Ejercicios();
                ejid = getObjEjerciciosID();
                ejid.setIdEjercicio(rs.getInt(1));
                ejid.setIdMusculo(rs.getInt(2));
                ej.setDescripcion(rs.getString(3));
                ej.setImagen(rs.getBinaryStream(4));
                ej.setObjEjerciciosID(ejid);
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
        return ej;

    }

    public InputStream getImagen() {
        return imagen;
    }

    public void setImagen(InputStream imagen) {
        this.imagen = imagen;
    }

    public ArrayList<Ejercicios> List(String filtro) {
        ArrayList<Ejercicios> e = new ArrayList();
        return e;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

//    public int getFilaSelected() {
//        return filaSelected;
//    }
//
//    public void setFilaSelected(int filaSelected) {
//        this.filaSelected = filaSelected;
//    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
