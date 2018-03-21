/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class persona extends Persistencia implements Serializable {

    private int idPersona;
    private int Current;
    private BigDecimal idtipoDocumento;
    private String documento;
    private String nombre;
    private String apellido;
    private String nombreCompleto;
    private String direccion;
    private String telefono;
    private String sexo;
    private Date fechaNacimiento;
    private String correo;
    private String estado;
    private InputStream foto;
    private String pathFoto;

    private TipoDocumento tipodocumento;

    public persona(int idPersona, BigDecimal idtipoDocumento, String documento, String nombre, String apellido, String direccion, String telefono, String sexo, Date fechaNacimiento, String correo, InputStream foto) {
        super();
        this.idPersona = idPersona;
        this.idtipoDocumento = idtipoDocumento;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.foto = foto;
    }

    public persona() {
        super();
        tipodocumento = new TipoDocumento();
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public InputStream getFoto() {
        return foto;
    }

    public void setFoto(InputStream foto) {
        this.foto = foto;
    }

    public BigDecimal getIdtipoDocumento() {
        return idtipoDocumento;
    }

    public void setIdtipoDocumento(BigDecimal idtipoDocumento) {
        this.idtipoDocumento = idtipoDocumento;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into persona (documento,idTipoDocumento,nombre,apellidos,nombrecompleto,direccion,Telefono,sexo,fechanacimiento,correo,estado,foto) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
//            
            FileInputStream fis = null;
            File file = null;//       
            if (!getPathFoto().equals("")) {// cuando se adjunta la foto
                file = new File(getPathFoto());
                fis = new FileInputStream(file);
            }
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, documento);
            preparedStatement.setBigDecimal(2, idtipoDocumento);
            preparedStatement.setString(3, nombre);
            preparedStatement.setString(4, apellido);
            preparedStatement.setString(5, nombreCompleto);
            preparedStatement.setString(6, direccion);
            preparedStatement.setString(7, telefono);
            preparedStatement.setString(8, sexo);
            preparedStatement.setDate(9, fechaNacimiento == null ? null : (java.sql.Date) fechaNacimiento);
            preparedStatement.setString(10, correo);
            preparedStatement.setString(11, estado);
            if (file != null) {
                preparedStatement.setBinaryStream(12, fis, (int) file.length());
            } else {
                preparedStatement.setString(12, null);
            }
            transaccion = persona.this.getConecion().transaccion(preparedStatement);
            String sql = "Select LAST_INSERT_ID()";
            ResultSet rs = persona.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                Current = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(persona.class.getName()).log(Level.SEVERE, null, ex);
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

    public boolean asocFinger(int idUsuario, String usuario, int idSede,
            int idempresa, int idPersona, ByteArrayInputStream huella, Integer tamañoHuella) throws SQLException {
        boolean result = false;
        int transaccion = -1;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        String prepareSql = "insert into huellas (idUsuario, usuario, idSede, idempresa, idPersona, huella, Estado) "
                + "values (?,?,?,?,?,?,?)";
        String prepareQuery = "Select * from huellas where Estado = 'A' "
                + "and idUsuario = " + idUsuario + " "
                + "and usuario = '" + usuario + "' "
                + "and idsede = " + idSede + " "
                + "and idempresa = " + idempresa + " "
                + "and idpersona = " + idPersona + "";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            rs = persona.super.getConecion().query(prepareQuery);
            if (rs.absolute(1)) {
                prepareSql = "update huellas set huella = ? where idUsuario = ? and usuario = ? and idsede = ? and idempresa = ? and idpersona = ?";
                pstm = this.getConecion().con.prepareStatement(prepareSql);
                pstm.setBinaryStream(1, huella, tamañoHuella);
                pstm.setInt(2, idUsuario);
                pstm.setString(3, usuario);
                pstm.setInt(4, idSede);
                pstm.setInt(5, idempresa);
                pstm.setInt(6, idPersona);
            } else {
                pstm = this.getConecion().con.prepareStatement(prepareSql);
                pstm.setInt(1, idUsuario);
                pstm.setString(2, usuario);
                pstm.setInt(3, idSede);
                pstm.setInt(4, idempresa);
                pstm.setInt(5, idPersona);
                pstm.setBinaryStream(6, huella, tamañoHuella);
                pstm.setString(7, "A");
            }

            transaccion = persona.this.getConecion().transaccion(pstm);
            if (transaccion > 0) {
                result = true;
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
            result = false;
        } finally {
            try {
                this.getConecion().getconecion().setAutoCommit(true);
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return result;

    }

    @Override
    public int edit() {
        int transaccion = -1;
        String PrepareUpdate = "update persona set documento=?,idTipoDocumento=?,nombre=?,apellidos=?,nombrecompleto=?,"
                + "direccion=?,telfono=?,sexo=?,fechanacimiento=?,correo=?,estado=?,foto=? where idpersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareUpdate);
            preparedStatement.setString(1, documento);
            preparedStatement.setBigDecimal(2, idtipoDocumento);
            preparedStatement.setString(3, nombre);
            preparedStatement.setString(4, apellido);
            preparedStatement.setString(5, nombreCompleto);
            preparedStatement.setString(6, direccion);
            preparedStatement.setString(7, telefono);
            preparedStatement.setString(8, sexo);
            preparedStatement.setDate(9, (java.sql.Date) fechaNacimiento);
            preparedStatement.setString(10, correo);
            preparedStatement.setString(11, estado);
            preparedStatement.setBinaryStream(12, foto);
            preparedStatement.setInt(13, idPersona);

            transaccion = persona.this.getConecion().transaccion(preparedStatement);
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
        String PrepareDelete = "delete from persona where idpersona";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, idPersona);

            transaccion = persona.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<persona> List = new ArrayList();
        String prepareQuery = "select * from persona";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = persona.super.getConecion().query(prepareQuery);

            while (rs.next()) {
                persona tabla = new persona();
                tabla.setIdPersona(rs.getInt(1));
                tabla.setDocumento(rs.getString(2));
                tabla.setIdtipoDocumento(rs.getBigDecimal(3));
                tabla.setNombre(rs.getString(4));
                tabla.setApellido(rs.getString(5));
                tabla.setNombreCompleto(rs.getString(6));
                tabla.setDireccion(rs.getString(7));
                tabla.setTelefono(rs.getString(8));
                tabla.setSexo(rs.getString(9));
                tabla.setFechaNacimiento(rs.getDate(10));
                tabla.setCorreo(rs.getString(11));
                tabla.setEstado(rs.getString(12));
                tabla.setFoto(rs.getBinaryStream(13));
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

    public persona getUsersById(int id) {
        persona p = null;
        try {
            String sql = "select idPersona, idTipoDocumento, Documento, "
                    + "Nombre, Apellidos, direccion, Telefono"
                    + ", Sexo, FechaNacimiento, correo, foto from persona where idPersona = " + id;
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = persona.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                p = new persona(rs.getInt(1), rs.getBigDecimal(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getDate(9), rs.getString(10), rs.getBinaryStream(11));
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
        return p;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public int ultimoid() {
        int id = -1;
        try {
            String sql = "Select LAST_INSERT_ID()";
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = persona.super.getConecion().query(sql);
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
        System.out.println("sistema Retorno : " + id);
        return id;
    }

    public String getNombreCompletoByIdPersona(int idPersona) {
        String nombre = "";
        try {
            String sql = "Select NombreCompleto from persona where idPersona = " + idPersona;
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = persona.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                nombre = rs.getString(1);
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
        return nombre;
    }

    public persona BuscarXDocumento(String documento) {
        persona tabla = new persona();
        String prepareQuery = "select * from persona where documento=" + documento;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = persona.super.getConecion().query(prepareQuery);

            while (rs.next()) {

                tabla.setIdPersona(rs.getInt(1));
                tabla.setDocumento(rs.getString(2));
                tabla.setIdtipoDocumento(rs.getBigDecimal(3));
                tabla.setNombre(rs.getString(4));
                tabla.setApellido(rs.getString(5));
                tabla.setNombreCompleto(rs.getString(6));
                tabla.setDireccion(rs.getString(7));
                tabla.setTelefono(rs.getString(8));
                tabla.setSexo(rs.getString(9));
                tabla.setFechaNacimiento(rs.getDate(10));
                tabla.setCorreo(rs.getString(11));
                tabla.setEstado(rs.getString(12));
                tabla.setFoto(rs.getBinaryStream(13));

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
        return tabla;
    }

    public Boolean BuscarXDocumentoTrue(String documento) {
        Boolean r = false;
        System.out.println("Entro a buscar documento : " + documento);
        String prepareQuery = "select Documento from persona where documento='" + documento.trim() + "'";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = persona.super.getConecion().query(prepareQuery);

            while (rs.next()) {
                r = true;
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
        System.out.println("Retorno " + r);
        return r;
    }

    public String ValidacionCampos(int condicion) { // Condicion depende de las vista en la cual es esta llamando el registro        
        String mns = "";
        // 1 : Registro de clientes
        // 2 : Registro de adm
        // 3 : Registro de proveedores
        if (documento.length() <= 0 && (condicion == 1 || condicion == 2 || condicion == 3)) {
            mns = "Debe Digitar el Documento";
            return mns;
        } else if (idtipoDocumento.intValue() <= 0 && (condicion == 1 || condicion == 2 || condicion == 3)) {
            mns = "Debe Seleccionar el tipo de Documento";
        } else if (nombre.length() <= 0 && (condicion == 1 || condicion == 2 || condicion == 3)) {
            mns = "Debe Digitar el Nombre";
        } else if (apellido.length() <= 0 && (condicion == 1 || condicion == 2 || condicion == 3)) {
            mns = "Debe Digitar el apellido";
        } else if (direccion.length() <= 0 && (condicion == 1 || condicion == 2)) {
            mns = "Debe Digitar la direccion";
        } else if (telefono.length() <= 0 && (condicion == 1 || condicion == 2 || condicion == 3)) {
            mns = "Debe Digitar el telefono";
        } else if (sexo.length() <= 0 && (condicion == 1 || condicion == 2)) {
            mns = "Debe Seleccionar el sexo";
        } else if (fechaNacimiento == null && (condicion == 1 || condicion == 2)) {
            mns = "Debe Digitar la fecha de nacimiento";
        } else if (correo.length() <= 0 && (condicion == 1 || condicion == 2)) {
            mns = "Debe Digitar el Correo";
        }

        return mns;
    }

    public int getCurrent() {
        return Current;
    }

    public void setCurrent(int Current) {
        this.Current = Current;
    }

    public TipoDocumento getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(TipoDocumento tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

}
