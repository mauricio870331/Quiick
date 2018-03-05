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
public class Cliente extends Persistencia implements Serializable {

    private int codCliente;
    private String tipoCliente;

    private persona p;

    public Cliente() {
        super();
        p = new persona();
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public persona getP() {
        if(p==null){
            p=new persona();
        }
        return p;
    }

    public void setP(persona p) {
        this.p = p;
    }
    
    

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into Cliente (idPersona,TipoCliente) "
                + "values (?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setInt(1, p.getIdPersona());
            preparedStatement.setString(2, tipoCliente);

            transaccion = Cliente.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update Cliente set idPersona=? where"
                + " TipoCliente=? where IdCliente=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setInt(1, p.getIdPersona());
            preparedStatement.setString(2, tipoCliente);
            preparedStatement.setInt(3, codCliente);

            transaccion = Cliente.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from Cliente where IdCliente=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, codCliente);


            transaccion = Cliente.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<Cliente> List = new ArrayList();
//        String prepareQuery = "select a.idAsistencia, a.FechaMarcacion, a.HoraMarcacion, p.NombreCompleto "
//                + "from Asistencia a, persona p, rolxuser r "
//                + "where a.idPersona = p.idPersona and p.Estado = 'A' "
//                + "and r.idPersona = p.idPersona and r.idRol = 2 order by a.FechaMarcacion, a.HoraMarcacion";
//
////        System.out.println("prepare mauricio " + prepareQuery);
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            ResultSet rs = Cliente.super.getConecion().query(prepareQuery);
//            while (rs.next()) {
//                Cliente tabla = new Cliente();
//                AsistenciaID tablaID = new AsistenciaID();
//                tablaID.setIdAsistencia(rs.getInt(1));
//                tabla.setObjAsistenciaID(tablaID);
//                tabla.setFechaMarcacion(rs.getDate(2));
//                tabla.setHoraMarcacion(rs.getTime(3));
//                tabla.setNombreCliente(rs.getString(4));
//                List.add(tabla);
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error Consulta : " + ex.toString());
//        } finally {
//            try {
//                this.getConecion().con.close();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
        return List;
    }

//    public List List(Date inicio, Date fin) {
//        ArrayList<Cliente> List = new ArrayList();
//        String prepareQuery = "select a.idAsistencia, a.FechaMarcacion, a.HoraMarcacion, p.NombreCompleto "
//                + "from Asistencia a, persona p, rolxuser r "
//                + "where a.idPersona = p.idPersona and p.Estado = 'A' and a.FechaMarcacion "
//                + "between '" + sa.format(inicio) + " 00:00:00' and '" + sa.format(fin) + " 23:59:59' "
//                + "and r.idPersona = p.idPersona and r.idRol = 2 order by a.FechaMarcacion ASC";
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            ResultSet rs = Cliente.super.getConecion().query(prepareQuery);
//            while (rs.next()) {
//                Cliente tabla = new Cliente();
//                AsistenciaID tablaID = new AsistenciaID();
//                tablaID.setIdAsistencia(rs.getInt(1));
//                tabla.setObjAsistenciaID(tablaID);
//                tabla.setFechaMarcacion(rs.getDate(2));
//                tabla.setHoraMarcacion(rs.getTime(3));
//                tabla.setNombreCliente(rs.getString(4));
//                List.add(tabla);
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error Consulta : " + ex.toString());
//        } finally {
//            try {
//                this.getConecion().con.close();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
//        return List;
//    }
//
//    public boolean ExistAsistencia(Date fachaMarcacion, int idUser, int idPersona) {
//        boolean existe = false;
//        String prepareQuery = "select idPersona from asistencia "
//                + "where FechaMarcacion = '" + sa.format(fachaMarcacion) + "' "
//                + "and idPersona = " + idPersona + " and idUsuario = " + idUser;
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            ResultSet rs = Cliente.super.getConecion().query(prepareQuery);
//            if (rs.absolute(1)) {
//                existe = true;
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error Consulta : " + ex.toString());
//        } finally {
//            try {
//                this.getConecion().con.close();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
//        return existe;
//    }
//
//    public Date getHoraMarcacion() {
//        return HoraMarcacion;
//    }
//
//    public void setHoraMarcacion(Date HoraMarcacion) {
//        this.HoraMarcacion = HoraMarcacion;
//    }
//
//    public String getNombreCliente() {
//        return nombreCliente;
//    }
//
//    public void setNombreCliente(String nombreCliente) {
//        this.nombreCliente = nombreCliente;
//    }

}
