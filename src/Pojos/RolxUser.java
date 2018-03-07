/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class RolxUser extends Persistencia implements Serializable {

    private int idRolxUser;
    private Rol objRol;
    private Usuario objUsuario;
    private int idUsuarioOld;
    private String usuarioOld;
    private int idsedeOld;
    private int idEmpresaOld;
    private int idpersonaOld;
    private int idRolxUserOld;
    private int idRolOld;
    SimpleDateFormat sa = new SimpleDateFormat("yyyy-MM-dd");

    public int getIdRolxUser() {
        return idRolxUser;
    }

    public void setIdRolxUser(int idRolxUser) {
        this.idRolxUser = idRolxUser;
    }

    public Rol getObjRol() {
        if (objRol == null) {
            objRol = new Rol();
        }
        return objRol;
    }

    public void setObjRol(Rol objRol) {
        this.objRol = objRol;
    }

    public Usuario getObjUsuario() {
        if (objUsuario == null) {
            objUsuario = new Usuario();
        }
        return objUsuario;
    }

    public void setObjUsuario(Usuario objUsuario) {

        this.objUsuario = objUsuario;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String ruta = System.getProperty("java.io.tmpdir") + "\\default.png";
        FileInputStream fis = null;
        File file = null;
        File file2 = new File(ruta);
        ResultSet rs;
        String sql = "Select LAST_INSERT_ID()";

        String insertPersona = "Insert into persona (Documento,idTipoDocumento,Nombre,Apellidos,NombreCompleto,direccion,Telefono,"
                + "Sexo,FechaNacimiento,correo,Estado,foto) values (?,?,?,?,?,?,?,?,?,?,?,?)";

        String insertUsiario = "insert into usuario (usuario,idSede,idempresa,idPersona,clave,NickName,Estado) "
                + "values (?,?,?,?,?,?,?)";

        String prepareInsert = "insert into RolxUser (idRol,idUsuario,usuario,idsede,idempresa,idpersona)"
                + " values (?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            //persona
            if (!objUsuario.getObjPersona().getPathFoto().equals("")) { //si se adjunta la foto
                file = new File(objUsuario.getObjPersona().getPathFoto());
                fis = new FileInputStream(file);
            } else if (file2.exists()) { //si se captura la foto
                file = file2;
                fis = new FileInputStream(file);
            }
            PreparedStatement pstmpersona = this.getConecion().con.prepareStatement(insertPersona);
            pstmpersona.setString(1, objUsuario.getObjPersona().getDocumento());
            pstmpersona.setBigDecimal(2, objUsuario.getObjPersona().getIdtipoDocumento());
            pstmpersona.setString(3, objUsuario.getObjPersona().getNombre());
            pstmpersona.setString(4, objUsuario.getObjPersona().getApellido());
            pstmpersona.setString(5, objUsuario.getObjPersona().getNombreCompleto());
            pstmpersona.setString(6, objUsuario.getObjPersona().getDireccion());
            pstmpersona.setString(7, objUsuario.getObjPersona().getTelefono());
            pstmpersona.setString(8, objUsuario.getObjPersona().getSexo());
            pstmpersona.setString(9, sa.format(objUsuario.getObjPersona().getFechaNacimiento()));
            pstmpersona.setString(10, objUsuario.getObjPersona().getCorreo());
            pstmpersona.setString(11, objUsuario.getObjPersona().getEstado());
            if (file != null) {
                pstmpersona.setBinaryStream(12, fis, (int) file.length());
            } else {
                pstmpersona.setString(12, null);
            }
            RolxUser.this.getConecion().transaccion(pstmpersona);

            rs = RolxUser.this.getConecion().query(sql);
            if (rs.absolute(1)) {
                System.out.println("rs.getInt(1) " + rs.getInt(1));
                objUsuario.getObjUsuariosID().setIdPersona(rs.getInt(1));
            }
            //persona

            //usuario
            PreparedStatement pstmusuario = this.getConecion().con.prepareStatement(insertUsiario);
            pstmusuario.setString(1, objUsuario.getObjUsuariosID().getUsuario());
            pstmusuario.setInt(2, objUsuario.getObjUsuariosID().getIdSede());
            pstmusuario.setInt(3, objUsuario.getObjUsuariosID().getIdempresa());
            pstmusuario.setInt(4, objUsuario.getObjUsuariosID().getIdPersona());
            pstmusuario.setString(5, objUsuario.getClave());
            pstmusuario.setString(6, objUsuario.getNickName());
            pstmusuario.setString(7, objUsuario.getEstado());
            RolxUser.this.getConecion().transaccion(pstmusuario);

            rs = RolxUser.this.getConecion().query(sql);
            if (rs.absolute(1)) {
                System.out.println("rs2.getInt(1) " + rs.getInt(1));
                objUsuario.getObjUsuariosID().setIdUsuario(rs.getInt(1));
            }

            //usuario
            //rol*user
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setInt(1, objRol.getIdRol());
            preparedStatement.setInt(2, objUsuario.getObjUsuariosID().getIdUsuario());
            preparedStatement.setString(3, objUsuario.getObjUsuariosID().getUsuario());
            preparedStatement.setInt(4, objUsuario.getObjUsuariosID().getIdSede());
            preparedStatement.setInt(5, objUsuario.getObjUsuariosID().getIdempresa());
            preparedStatement.setInt(6, objUsuario.getObjUsuariosID().getIdPersona());
            //rol*user

            transaccion = RolxUser.this.getConecion().transaccion(preparedStatement);
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
        } catch (FileNotFoundException ex) {
            System.out.println("Error Con la foto : " + ex.toString());
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

//     @Override
//    public int create() {
//        int transaccion = -1;
//        String prepareInsert = "insert into RolxUser (idRol,idUsuario,usuario,idsede,idempresa,idpersona) values (?,?,?,?,?,?)";
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            this.getConecion().con.setAutoCommit(false);
//            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
//            preparedStatement.setInt(1, objRol.getIdRol());
//            preparedStatement.setInt(2, objUsuario.getObjUsuariosID().getIdUsuario());
//            preparedStatement.setString(3, objUsuario.getObjUsuariosID().getUsuario());
//            preparedStatement.setInt(4, objUsuario.getObjUsuariosID().getIdSede());
//            preparedStatement.setInt(5, objUsuario.getObjUsuariosID().getIdempresa());
//            preparedStatement.setInt(6, objUsuario.getObjUsuariosID().getIdPersona());
//
//            transaccion = RolxUser.this.getConecion().transaccion(preparedStatement);
//        } catch (SQLException ex) {
//            System.out.println("Error SQL : " + ex.toString());
//        } finally {
//            try {
//                this.getConecion().getconecion().setAutoCommit(true);
//                this.getConecion().con.close();
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        }
//        return transaccion;
//    }
    @Override
    public int edit() {
        int transaccion = -1;
        String ruta = System.getProperty("java.io.tmpdir") + "\\default.png";
        FileInputStream fis = null;
        File file = null;
        File file2 = new File(ruta);

        String updatePersona = "update persona set Documento = ?, idTipoDocumento = ?,Nombre = ?, "
                + "Apellidos = ?, NombreCompleto = ?, direccion = ?, Telefono = ?, "
                + "Sexo = ?, FechaNacimiento = ?, correo = ?, Estado = ?, foto = ? where idPersona = ?";

        String updateUsuario = "update usuario set usuario = ?,"
                + " idSede = ?,"
                + " idempresa = ?, "
                + "idPersona = ?,"
                + "clave = ?,"
                + "NickName = ?, "
                + "Estado = ? "
                + "where idUsuario  = ? and usuario = ? and idsede = ? and idempresa= ? and  idPersona = ?";

        String prepareUpdate = "update rolxuser set idRol = ?,"
                + " idUsuario = ?, "
                + "usuario = ?, "
                + "idsede = ?, "
                + "idempresa = ?, "
                + "idPersona = ?"
                + " where idRolxUser = ? ";

        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            //persona
            if (!objUsuario.getObjPersona().getPathFoto().equals("")) { //si se adjunta la foto
                file = new File(objUsuario.getObjPersona().getPathFoto());
                fis = new FileInputStream(file);
            } else if (file2.exists()) { //si se captura la foto
                file = file2;
                fis = new FileInputStream(file);
            }

            PreparedStatement pstmpersona = this.getConecion().con.prepareStatement(updatePersona);
            pstmpersona.setString(1, objUsuario.getObjPersona().getDocumento());
            pstmpersona.setBigDecimal(2, objUsuario.getObjPersona().getIdtipoDocumento());
            pstmpersona.setString(3, objUsuario.getObjPersona().getNombre());
            pstmpersona.setString(4, objUsuario.getObjPersona().getApellido());
            pstmpersona.setString(5, objUsuario.getObjPersona().getNombreCompleto());
            pstmpersona.setString(6, objUsuario.getObjPersona().getDireccion());
            pstmpersona.setString(7, objUsuario.getObjPersona().getTelefono());
            pstmpersona.setString(8, objUsuario.getObjPersona().getSexo());
            pstmpersona.setString(9, sa.format(objUsuario.getObjPersona().getFechaNacimiento()));
            pstmpersona.setString(10, objUsuario.getObjPersona().getCorreo());
            pstmpersona.setString(11, objUsuario.getObjPersona().getEstado());
            if (file != null) {
                pstmpersona.setBinaryStream(12, fis, (int) file.length());
            } else {
                pstmpersona.setString(12, null);
            }
            pstmpersona.setInt(13, getIdpersonaOld());
            RolxUser.this.getConecion().transaccion(pstmpersona);
            //persona

            //usuario
            PreparedStatement pstmusuario = this.getConecion().con.prepareStatement(updateUsuario);
            pstmusuario.setString(1, objUsuario.getObjUsuariosID().getUsuario());
            pstmusuario.setInt(2, objUsuario.getObjUsuariosID().getIdSede());
            pstmusuario.setInt(3, objUsuario.getObjUsuariosID().getIdempresa());
            pstmusuario.setInt(4, getIdpersonaOld());
            pstmusuario.setString(5, objUsuario.getClave());
            pstmusuario.setString(6, objUsuario.getNickName());
            pstmusuario.setString(7, objUsuario.getEstado());
            pstmusuario.setInt(8, getIdUsuarioOld());
            pstmusuario.setString(9, getUsuarioOld());
            pstmusuario.setInt(10, getIdsedeOld());
            pstmusuario.setInt(11, getIdEmpresaOld());
            pstmusuario.setInt(12, getIdpersonaOld());
            RolxUser.this.getConecion().transaccion(pstmusuario);
            //usuario

            //rol*user
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareUpdate);
            preparedStatement.setInt(1, objRol.getIdRol());
            preparedStatement.setInt(2, getIdUsuarioOld());
            preparedStatement.setString(3, objUsuario.getObjUsuariosID().getUsuario());
            preparedStatement.setInt(4, objUsuario.getObjUsuariosID().getIdSede());
            preparedStatement.setInt(5, objUsuario.getObjUsuariosID().getIdempresa());
            preparedStatement.setInt(6, getIdpersonaOld());
            preparedStatement.setInt(7, getIdRolxUserOld());
            transaccion = RolxUser.this.getConecion().transaccion(preparedStatement);
            System.out.println("transaccion " + transaccion);
            //rol*user

        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
        } catch (FileNotFoundException ex) {
            System.out.println("Error Con la foto : " + ex.toString());
        } finally {
            try {
                file = null;
                file2 = null;
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
    public int remove() {
        int transaccion = -1;
//        String PrepareDelete = "delete from RolxUser where idrolxuser=?";

//        ResultSet rs;
//        String sql = "Select idHuella from huellas "
//                 + "where idUsuario  = ? and usuario = ? and idsede = ? and idempresa= ? and  idPersona = ?";
        String deleteUsuario = "update usuario set Estado = ?"
                + "where idUsuario  = ? and usuario = ? and idsede = ? and idempresa= ? and  idPersona = ?";

        String deletePersona = "update persona set Estado = ? where idPersona = ?";

        String deleteHuella = "update huellas set Estado = ? where idUsuario  = ? and usuario = ? and idsede = ? and idempresa= ? and  idPersona = ? ";

        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);

//            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
//            preparedStatement.setInt(1, idRolxUser);
//            transaccion = RolxUser.this.getConecion().transaccion(preparedStatement);
            PreparedStatement pstmusuariodelete = this.getConecion().con.prepareStatement(deleteUsuario);
            pstmusuariodelete.setString(1, "I");
            pstmusuariodelete.setInt(2, objUsuario.getObjUsuariosID().getIdUsuario());
            pstmusuariodelete.setString(3, objUsuario.getObjUsuariosID().getUsuario());
            pstmusuariodelete.setInt(4, objUsuario.getObjUsuariosID().getIdSede());
            pstmusuariodelete.setInt(5, objUsuario.getObjUsuariosID().getIdempresa());
            pstmusuariodelete.setInt(6, objUsuario.getObjPersona().getIdPersona());
            transaccion = RolxUser.this.getConecion().transaccion(pstmusuariodelete);

            PreparedStatement pstmpersonadelete = this.getConecion().con.prepareStatement(deletePersona);
            pstmpersonadelete.setString(1, "I");
            pstmpersonadelete.setInt(2, objUsuario.getObjPersona().getIdPersona());
            transaccion = RolxUser.this.getConecion().transaccion(pstmpersonadelete);
            //******
//            rs = RolxUser.this.getConecion().query(sql);
//            if (rs.absolute(1)) {
//                System.out.println("rs2.getInt(1) " + rs.getInt(1));
//                objUsuario.getObjUsuariosID().setIdUsuario(rs.getInt(1));
//            }
            PreparedStatement psthuelladelete = this.getConecion().con.prepareStatement(deleteHuella);
            psthuelladelete.setString(1, "I");
            psthuelladelete.setInt(2, objUsuario.getObjUsuariosID().getIdUsuario());
            psthuelladelete.setString(3, objUsuario.getObjUsuariosID().getUsuario());
            psthuelladelete.setInt(4, objUsuario.getObjUsuariosID().getIdSede());
            psthuelladelete.setInt(5, objUsuario.getObjUsuariosID().getIdempresa());
            psthuelladelete.setInt(6, objUsuario.getObjPersona().getIdPersona());
            if (RolxUser.this.getConecion().transaccion(psthuelladelete) > 0) {
                transaccion = 1;
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
    public java.util.List List() {
        ArrayList<RolxUser> List = new ArrayList();
        String prepareQuery = "select * from RolxUser";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = RolxUser.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                RolxUser tabla = new RolxUser();
                tabla.setIdRolxUser(rs.getInt(1));
                tabla.getObjRol().setIdRol(rs.getInt(2));
                tabla.getObjUsuario().getObjUsuariosID().setIdUsuario(rs.getInt(3));
                tabla.getObjUsuario().getObjUsuariosID().setUsuario(rs.getString(4));
                tabla.getObjUsuario().getObjUsuariosID().setIdSede(rs.getInt(5));
                tabla.getObjUsuario().getObjUsuariosID().setIdempresa(rs.getInt(6));
                tabla.getObjUsuario().getObjUsuariosID().setIdPersona(rs.getInt(7));
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

    public int getidRolbyIpersona(int idPersona) {
        int r = 0;
        try {
            String sql = "select idRol from rolxuser where idPersona = " + idPersona;
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = RolxUser.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                r = rs.getInt(1);
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
        return r;
    }

    public String getRolbyIdRolXUser(int id_roluser) {
        String r = "";
        try {
            String sql = "SELECT r.Descripcion FROM rolxuser ru, rol r "
                    + "WHERE ru.idRol = r.idRol and ru.idRolxUser =" + id_roluser;
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = RolxUser.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                r = rs.getString(1);
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
        return r;
    }

    public List<RolxUser> List(int desde, int hasta, String filtro, String idRol) {
        ArrayList<RolxUser> List = new ArrayList();
        String and = "";
        if (!filtro.equals("")) {
            and = " and (p.Documento like '%" + filtro + "%' or p.Nombre like '%" + filtro + "%' or p.Apellidos like '%" + filtro + "%')";
        }
        String condicion = "";
        if (!idRol.equalsIgnoreCase("root")) {
            condicion = " and r.idRol <> 1";
        }
        String prepareQuery = "SELECT * FROM usuario u, persona p, rolxuser ru, rol r "
                + "where u.idPersona = p.idPersona "
                + "and ru.idUsuario = u.idUsuario "
                + "and ru.idRol = r.idRol and p.Estado = 'A' and u.Estado = 'A'" + condicion + and + " limit " + desde + ", " + hasta + "";
//        System.out.println("prepareQuery " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = RolxUser.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Usuario us = new Usuario();
                persona p = new persona();
                UsuarioID userId = new UsuarioID();
                Rol rol = new Rol();
                RolxUser roluser = new RolxUser();
                userId.setIdUsuario(rs.getInt(1));
                userId.setUsuario(rs.getString(2));
                userId.setIdSede(rs.getInt(3));
                userId.setIdempresa(rs.getInt(4));
                userId.setIdPersona(rs.getInt(5));
                p.setIdPersona(rs.getInt(9));
                p.setDocumento(rs.getString(10));
                p.setIdtipoDocumento(rs.getBigDecimal(11));
                p.setNombre(rs.getString(12));
                p.setApellido(rs.getString(13));
                p.setNombreCompleto(rs.getString(14));
                p.setDireccion(rs.getString(15));
                p.setTelefono(rs.getString(16));
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
                List.add(roluser);
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

    public int CountRs(String filtro, String idRol) {
        int totalRg = 0;
        String and = "";
        if (!filtro.equals("")) {
            and = " and (p.Documento like '%" + filtro + "%' or p.Nombre like '%" + filtro + "%' or p.Apellidos like '%" + filtro + "%')";
        }
        String condicion = "";
        if (!idRol.equalsIgnoreCase("root")) {
            condicion = " and r.idRol <> 1";
        }
        String prepareQuery = "SELECT count(*) FROM usuario u, persona p, rolxuser ru, rol r "
                + "where u.idPersona = p.idPersona "
                + "and ru.idUsuario = u.idUsuario "
                + "and ru.idRol = r.idRol and p.Estado = 'A' and u.Estado = 'A' " + condicion + and + "";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = RolxUser.super.getConecion().query(prepareQuery);
            if (rs.absolute(1)) {
                totalRg = rs.getInt(1);
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
        return totalRg;
    }

    public RolxUser getDatosPersonaById(int idPersona) {
        Usuario us = new Usuario();
        persona p = new persona();
        UsuarioID userId = new UsuarioID();
        RolxUser roluser = new RolxUser();
        Rol rol = new Rol();
        String prepareQuery = "SELECT * FROM usuario u, persona p, rolxuser ru, rol r "
                + "where u.idPersona = p.idPersona "
                + "and ru.idUsuario = u.idUsuario "
                + "and ru.idRol = r.idRol and p.idPersona = " + idPersona + "";
//        System.out.println("prepareQuery "+prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = RolxUser.super.getConecion().query(prepareQuery);
            if (rs.absolute(1)) {
                userId.setIdUsuario(rs.getInt(1));
                userId.setUsuario(rs.getString(2));
                userId.setIdSede(rs.getInt(3));
                userId.setIdempresa(rs.getInt(4));
                userId.setIdPersona(rs.getInt(5));
                p.setIdPersona(rs.getInt(9));
                p.setDocumento(rs.getString(10));
                p.setIdtipoDocumento(rs.getBigDecimal(11));
                p.setNombre(rs.getString(12));
                p.setApellido(rs.getString(13));
                p.setNombreCompleto(rs.getString(14));
                p.setDireccion(rs.getString(15));
                p.setTelefono(rs.getString(16));
                p.setSexo(rs.getString(17));
                p.setFechaNacimiento(rs.getDate(18));
                p.setCorreo(rs.getString(19));
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

    public RolxUser getDatosPersonaByDoc(String docPersona) {
        Usuario us = null;
        persona p = null;
        UsuarioID userId = null;
        RolxUser roluser = null;
        Rol rol = null;
        String prepareQuery = "SELECT * FROM usuario u, persona p, rolxuser ru, rol r "
                + "where u.idPersona = p.idPersona "
                + "and ru.idUsuario = u.idUsuario "
                + "and ru.idRol = r.idRol and p.Documento = '" + docPersona + "'";
//        System.out.println("prepareQuery "+prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = RolxUser.super.getConecion().query(prepareQuery);
            if (rs.absolute(1)) {
                us = new Usuario();
                p = new persona();
                userId = new UsuarioID();
                rol = new Rol();
                roluser = new RolxUser();
                userId.setIdUsuario(rs.getInt(1));
                userId.setUsuario(rs.getString(2));
                userId.setIdSede(rs.getInt(3));
                userId.setIdempresa(rs.getInt(4));
                userId.setIdPersona(rs.getInt(5));
                p.setIdPersona(rs.getInt(9));
                p.setDocumento(rs.getString(10));
                p.setIdtipoDocumento(rs.getBigDecimal(11));
                p.setNombre(rs.getString(12));
                p.setApellido(rs.getString(13));
                p.setNombreCompleto(rs.getString(14));
                p.setDireccion(rs.getString(15));
                p.setTelefono(rs.getString(16));
                p.setSexo(rs.getString(17));
                p.setFechaNacimiento(rs.getDate(18));
                p.setCorreo(rs.getString(19));
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

    public int getIdUsuarioOld() {
        return idUsuarioOld;
    }

    public void setIdUsuarioOld(int idUsuarioOld) {
        this.idUsuarioOld = idUsuarioOld;
    }

    public String getUsuarioOld() {
        return usuarioOld;
    }

    public void setUsuarioOld(String usuarioOld) {
        this.usuarioOld = usuarioOld;
    }

    public int getIdsedeOld() {
        return idsedeOld;
    }

    public void setIdsedeOld(int idsedeOld) {
        this.idsedeOld = idsedeOld;
    }

    public int getIdEmpresaOld() {
        return idEmpresaOld;
    }

    public void setIdEmpresaOld(int idEmpresaOld) {
        this.idEmpresaOld = idEmpresaOld;
    }

    public int getIdpersonaOld() {
        return idpersonaOld;
    }

    public void setIdpersonaOld(int idpersonaOld) {
        this.idpersonaOld = idpersonaOld;
    }

    public int getIdRolxUserOld() {
        return idRolxUserOld;
    }

    public void setIdRolxUserOld(int idRolxUserOld) {
        this.idRolxUserOld = idRolxUserOld;
    }

    public int getIdRolOld() {
        return idRolOld;
    }

    public void setIdRolOld(int idRolOld) {
        this.idRolOld = idRolOld;
    }

}
