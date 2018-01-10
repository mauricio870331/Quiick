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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class Asistencia extends Persistencia implements Serializable {

    private Date FechaMarcacion;
    private Date HoraMarcacion;
    private AsistenciaID objAsistenciaID;
    private String nombreCliente;
    SimpleDateFormat sa = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat hh = new SimpleDateFormat("HH:mm:ss");

    public Asistencia() {
        super();
    }

    public Date getFechaMarcacion() {
        return FechaMarcacion;
    }

    public void setFechaMarcacion(Date FechaMarcacion) {
        this.FechaMarcacion = FechaMarcacion;
    }

    public AsistenciaID getObjAsistenciaID() {
        if (objAsistenciaID == null) {
            objAsistenciaID = new AsistenciaID();
        }
        return objAsistenciaID;
    }

    public void setObjAsistenciaID(AsistenciaID objAsistenciaID) {
        this.objAsistenciaID = objAsistenciaID;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into Asistencia (idUsuario,usuario,idsede,idempresa,idPersona,fechaMarcacion,HoraMarcacion) "
                + "values (?,?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setInt(1, objAsistenciaID.getIdUsuario());
            preparedStatement.setString(2, objAsistenciaID.getUsuario());
            preparedStatement.setInt(3, objAsistenciaID.getIdSede());
            preparedStatement.setInt(4, objAsistenciaID.getIdempresa());
            preparedStatement.setInt(5, objAsistenciaID.getIdPersona());
            preparedStatement.setString(6, sa.format(FechaMarcacion));
            preparedStatement.setString(7, hh.format(HoraMarcacion));
            transaccion = Asistencia.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update Asistencia set fechaMarcacion=? where"
                + " idAsistencia=?,idUsuario=?,usuario=?,idsede=?,idempresa=?,idpersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setDate(1, (java.sql.Date) FechaMarcacion);
            preparedStatement.setInt(2, objAsistenciaID.getIdAsistencia());
            preparedStatement.setInt(3, objAsistenciaID.getIdUsuario());
            preparedStatement.setString(4, objAsistenciaID.getUsuario());
            preparedStatement.setInt(5, objAsistenciaID.getIdSede());
            preparedStatement.setInt(6, objAsistenciaID.getIdempresa());
            preparedStatement.setInt(7, objAsistenciaID.getIdPersona());

            transaccion = Asistencia.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from Asistencia where idAsistencia=?,idUsuario=?,usuario=?,idsede=?,idempresa=?,idpersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, objAsistenciaID.getIdAsistencia());
            preparedStatement.setInt(2, objAsistenciaID.getIdUsuario());
            preparedStatement.setString(3, objAsistenciaID.getUsuario());
            preparedStatement.setInt(4, objAsistenciaID.getIdSede());
            preparedStatement.setInt(5, objAsistenciaID.getIdempresa());
            preparedStatement.setInt(6, objAsistenciaID.getIdPersona());

            transaccion = Asistencia.this.getConecion().transaccion(preparedStatement);
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
    public List List() {
        ArrayList<Asistencia> List = new ArrayList();
        String prepareQuery = "select a.idAsistencia, a.FechaMarcacion, a.HoraMarcacion, p.NombreCompleto "
                + "from Asistencia a, persona p, rolxuser r "
                + "where a.idPersona = p.idPersona and p.Estado = 'A' "
                + "and r.idPersona = p.idPersona and r.idRol = 2 order by a.FechaMarcacion, a.HoraMarcacion";

//        System.out.println("prepare mauricio " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Asistencia.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Asistencia tabla = new Asistencia();
                AsistenciaID tablaID = new AsistenciaID();
                tablaID.setIdAsistencia(rs.getInt(1));
                tabla.setObjAsistenciaID(tablaID);
                tabla.setFechaMarcacion(rs.getDate(2));
                tabla.setHoraMarcacion(rs.getTime(3));
                tabla.setNombreCliente(rs.getString(4));
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

    public List List(Date inicio, Date fin) {
        ArrayList<Asistencia> List = new ArrayList();
        String prepareQuery = "select a.idAsistencia, a.FechaMarcacion, a.HoraMarcacion, p.NombreCompleto "
                + "from Asistencia a, persona p, rolxuser r "
                + "where a.idPersona = p.idPersona and p.Estado = 'A' and a.FechaMarcacion "
                + "between '" + sa.format(inicio) + " 00:00:00' and '" + sa.format(fin) + " 23:59:59' "
                + "and r.idPersona = p.idPersona and r.idRol = 2 order by a.FechaMarcacion ASC";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Asistencia.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Asistencia tabla = new Asistencia();
                AsistenciaID tablaID = new AsistenciaID();
                tablaID.setIdAsistencia(rs.getInt(1));
                tabla.setObjAsistenciaID(tablaID);
                tabla.setFechaMarcacion(rs.getDate(2));
                tabla.setHoraMarcacion(rs.getTime(3));
                tabla.setNombreCliente(rs.getString(4));
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

    public boolean ExistAsistencia(Date fachaMarcacion, int idUser, int idPersona) {
        boolean existe = false;
        String prepareQuery = "select idPersona from asistencia "
                + "where FechaMarcacion = '" + sa.format(fachaMarcacion) + "' "
                + "and idPersona = " + idPersona + " and idUsuario = " + idUser;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Asistencia.super.getConecion().query(prepareQuery);
            if (rs.absolute(1)) {
                existe = true;
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
        return existe;
    }

    public Date getHoraMarcacion() {
        return HoraMarcacion;
    }

    public void setHoraMarcacion(Date HoraMarcacion) {
        this.HoraMarcacion = HoraMarcacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

}
