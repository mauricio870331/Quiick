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
import java.util.List;

/**
 *
 * @author admin
 */
public class Usuario extends Persistencia implements Serializable {

    private String clave;
    private String nickName;
    private String estado;

    private UsuarioID objUsuariosID;
    private persona objPersona;
    private Sedes objSede;

    public int totalregistros = 0;

    public Usuario() {
        super();
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public UsuarioID getObjUsuariosID() {
        if (objUsuariosID == null) {
            objUsuariosID = new UsuarioID();
        }
        return objUsuariosID;
    }

    public void setObjUsuariosID(UsuarioID objUsuariosID) {
        this.objUsuariosID = objUsuariosID;
    }

    public persona getObjPersona() {
        return objPersona;
    }

    public void setObjPersona(persona objPersona) {
        this.objPersona = objPersona;
    }

    public Sedes getObjSede() {
        return objSede;
    }

    public void setObjSede(Sedes objSede) {
        this.objSede = objSede;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into usuario (usuario,idsede,idempresa,idpersona,clave,nickname,estado)"
                + " values (?,?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, objUsuariosID.getUsuario());
            preparedStatement.setInt(2, objUsuariosID.getIdSede());
            preparedStatement.setInt(3, objUsuariosID.getIdempresa());
            preparedStatement.setInt(4, objPersona.getIdPersona());
            preparedStatement.setString(5, clave);
            preparedStatement.setString(6, nickName);
            preparedStatement.setString(7, estado);
            transaccion = Usuario.this.getConecion().transaccion(preparedStatement);
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
        String PrepareUpdate = "update usuario set clave=?,nickname=?,estado=? where"
                + " idUsuario=? and usuario=?,idsede=?,idempresa=?,idpersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, clave);
            preparedStatement.setString(2, nickName);
            preparedStatement.setString(3, estado);
            preparedStatement.setInt(4, objUsuariosID.getIdUsuario());
            preparedStatement.setString(5, objUsuariosID.getUsuario());
            preparedStatement.setInt(6, objUsuariosID.getIdSede());
            preparedStatement.setInt(7, objUsuariosID.getIdempresa());
            preparedStatement.setInt(8, objUsuariosID.getIdPersona());

            transaccion = Usuario.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from usuario where idUsuario=? and usuario=?,idsede=?,idempresa=?,idpersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, objUsuariosID.getIdUsuario());
            preparedStatement.setString(2, objUsuariosID.getUsuario());
            preparedStatement.setInt(3, objUsuariosID.getIdSede());
            preparedStatement.setInt(4, objUsuariosID.getIdempresa());
            preparedStatement.setInt(5, objUsuariosID.getIdPersona());

            transaccion = Usuario.this.getConecion().transaccion(preparedStatement);
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

    public RolxUser Login(String user, String pass) {
        Usuario us = null;
        persona p = null;
        UsuarioID userId = null;
        RolxUser roluser = null;
        Rol rol = null;
        String prepareQuery = "SELECT * FROM usuario u, persona p, rolxuser ru, rol r "
                + "where u.idPersona = p.idPersona "
                + "and ru.idUsuario = u.idUsuario "
                + "and ru.idRol = r.idRol "
                + "and u.usuario = '" + user + "' and u.clave = '" + pass + "'";

//        System.out.println("prepareQuery " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Usuario.super.getConecion().query(prepareQuery);
            if (rs.absolute(1)) {
                us = new Usuario();
                p = new persona();
                userId = new UsuarioID();
                roluser = new RolxUser();
                rol = new Rol();
                userId.setIdUsuario(rs.getInt(1));
                userId.setUsuario(rs.getString(2));
                userId.setIdSede(rs.getInt(3));
                userId.setIdempresa(rs.getInt(4));
                userId.setIdPersona(rs.getInt(5));
                p.setDocumento(rs.getString(10));
                p.setIdtipoDocumento(rs.getInt(11));
                p.setNombreCompleto(rs.getString(14));
                p.setFoto(rs.getBinaryStream(21));
                us.setObjUsuariosID(userId);
                us.setClave(rs.getString(6));
                us.setNickName(rs.getString(7));
                us.setEstado(rs.getString(8));
                us.setObjPersona(p);
                rol.setIdRol(rs.getInt(23));
                rol.setDescripcion(rs.getString(30));
                rol.setEstado(rs.getString(31));
                roluser.setObjUsuario(us);
                roluser.setIdRolxUser(rs.getInt(22));
                roluser.setObjRol(rol);
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
        return roluser;
    }

    public int ultimoid() {
        int id = -1;
        try {
            String sql = "Select LAST_INSERT_ID()";
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Usuario.super.getConecion().query(sql);
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

    @Override
    public List List() {
        return null;
    }

    public ArrayList ListaUsuarios() {
        ArrayList<Usuario> listUsuarios = new ArrayList();

        String prepareQuery = "SELECT * FROM usuario u, persona p "
                + "where u.idPersona = p.idPersona and p.estado='A' and p.Documento <> '1113626301'";
        try {
            System.out.println("-- : " + prepareQuery);
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Usuario.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Usuario us = new Usuario();
                persona p = new persona();
                UsuarioID userId = new UsuarioID();
                userId.setIdUsuario(rs.getInt(1));
                userId.setUsuario(rs.getString(2));
                userId.setIdSede(rs.getInt(3));
                userId.setIdempresa(rs.getInt(4));
                userId.setIdPersona(rs.getInt(5));
                p.setIdPersona(rs.getInt(5));
                p.setDocumento(rs.getString(10));
                p.setIdtipoDocumento(rs.getInt(11));
                p.setNombreCompleto(rs.getString(14));
                p.setFoto(rs.getBinaryStream(21));
                us.setObjUsuariosID(userId);
                us.setClave(rs.getString(6));
                us.setNickName(rs.getString(7));
                us.setEstado(rs.getString(8));
                us.setObjPersona(p);
                listUsuarios.add(us);

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
        return listUsuarios;
    }

    public List<RolxUser> seeMypass() {
        ArrayList<RolxUser> users = new ArrayList();
        Usuario us = null;
        persona p = null;
        UsuarioID userId = null;
        RolxUser roluser = null;
        Rol rol = new Rol();
        String prepareQuery = "SELECT * FROM usuario u, persona p, rolxuser ru, rol r "
                + "where u.idPersona = p.idPersona "
                + "and ru.idUsuario = u.idUsuario "
                + "and ru.idRol = r.idRol and r.idRol = 1";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Usuario.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                roluser = new RolxUser();
                userId = new UsuarioID();
                p = new persona();
                us = new Usuario();
                userId.setIdUsuario(rs.getInt(1));
                userId.setUsuario(rs.getString(2));
                userId.setIdSede(rs.getInt(3));
                userId.setIdempresa(rs.getInt(4));
                userId.setIdPersona(rs.getInt(5));
                p.setDocumento(rs.getString(10));
                p.setIdtipoDocumento(rs.getInt(11));
                p.setNombreCompleto(rs.getString(14));
                p.setFoto(rs.getBinaryStream(21));
                us.setObjUsuariosID(userId);
                us.setClave(rs.getString(6));
                us.setNickName(rs.getString(7));
                us.setEstado(rs.getString(8));
                us.setObjPersona(p);
                rol.setIdRol(rs.getInt(29));
                rol.setDescripcion(rs.getString(30));
                rol.setEstado(rs.getString(31));
                roluser.setObjUsuario(us);
                roluser.setIdRolxUser(rs.getInt(22));
                roluser.setObjRol(rol);
                users.add(roluser);
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
        return users;
    }

}
