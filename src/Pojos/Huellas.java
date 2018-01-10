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
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Huellas extends Persistencia implements Serializable {

    private BigDecimal huella;
    private String estado;

    private HuellaID objHuellasID;

    public BigDecimal getHuella() {
        return huella;
    }

    public void setHuella(BigDecimal huella) {
        this.huella = huella;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public HuellaID getObjHuellasID() {
        return objHuellasID;
    }

    public void setObjHuellasID(HuellaID objHuellasID) {
        this.objHuellasID = objHuellasID;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into huellas (idUsuario,usuario,idsede,idempresa,idpersona,huella,estado) values (?,?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setBigDecimal(1, objHuellasID.getIdUsuario());
            preparedStatement.setString(1, objHuellasID.getUsuario());
            preparedStatement.setBigDecimal(2, objHuellasID.getIdSede());
            preparedStatement.setBigDecimal(3, objHuellasID.getIdempresa());
            preparedStatement.setBigDecimal(4, objHuellasID.getIdPersona());
            preparedStatement.setBigDecimal(5, huella);
            preparedStatement.setString(6, estado);

            transaccion = Huellas.this.getConecion().transaccion(preparedStatement);
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

    public ResultSet verificFinger() {
        ResultSet rs = null;
//        String prepareQuery = "Select h.* from huellas h, rolxuser r where h.Estado = 'A' "
//                + "and h.idUsuario = r.idUsuario and r.idRol = 2";//para hora feliz haer join con tipo
        String prepareQuery = "Select h.*, t.Descripcion, horario.hora_desde, horario.hora_hasta "
                + "from huellas h left join rolxuser r on h.idUsuario = r.idUsuario and r.idRol = 2 "
                + "left join pagoservice p on h.idUsuario = p.idUsuarioCliente "
                + "and p.idPago = (select max(idPago) from pagoservice where idUsuarioCliente = p.idUsuarioCliente) "
                + "left join tiposervice t on t.idTipoService = p.idTipoService "
                + "left join horarioservice horario on t.idTipoService = horario.idTipoService "
                + "and horario.estado = 'A' "
                + "where h.Estado = 'A'";
//        System.out.println("prepareQuery = " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            rs = Huellas.super.getConecion().query(prepareQuery);
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return rs;
    }

    @Override
    public int edit() {
        int transaccion = -1;
        String PrepareUpdate = "update huellas set huella=?,estado=? where idhuella=? and "
                + " usuario=? and idUsuario=?,idsede=?,idempresa=?,idpersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setBigDecimal(1, huella);
            preparedStatement.setString(2, estado);
            preparedStatement.setBigDecimal(3, objHuellasID.getIdhuella());
            preparedStatement.setString(4, objHuellasID.getUsuario());
            preparedStatement.setBigDecimal(5, objHuellasID.getIdUsuario());
            preparedStatement.setBigDecimal(6, objHuellasID.getIdSede());
            preparedStatement.setBigDecimal(7, objHuellasID.getIdempresa());
            preparedStatement.setBigDecimal(8, objHuellasID.getIdPersona());

            transaccion = Huellas.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from huellas where idhuella=? and "
                + " usuario=? and idUsuario=?,idsede=?,idempresa=?,idpersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setBigDecimal(1, objHuellasID.getIdhuella());
            preparedStatement.setString(2, objHuellasID.getUsuario());
            preparedStatement.setBigDecimal(3, objHuellasID.getIdUsuario());
            preparedStatement.setBigDecimal(4, objHuellasID.getIdSede());
            preparedStatement.setBigDecimal(5, objHuellasID.getIdempresa());
            preparedStatement.setBigDecimal(6, objHuellasID.getIdPersona());

            transaccion = Huellas.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<Huellas> List = new ArrayList();
        String prepareQuery = "select * from huellas";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Huellas.super.getConecion().query(prepareQuery);
            Huellas tabla = new Huellas();
            HuellaID tablaID = new HuellaID();
            while (rs.next()) {

                tablaID.setIdhuella(rs.getBigDecimal(1));
                tablaID.setIdUsuario(rs.getBigDecimal(2));
                tablaID.setUsuario(rs.getString(3));
                tablaID.setIdSede(rs.getBigDecimal(4));
                tablaID.setIdempresa(rs.getBigDecimal(5));
                tablaID.setIdPersona(rs.getBigDecimal(6));

                tabla.setHuella(rs.getBigDecimal(7));
                tabla.setEstado(rs.getString(8));

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

}
