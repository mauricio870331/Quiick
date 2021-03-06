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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class Empresas extends Persistencia implements Serializable {

    private int idEmpresa;
    private String nit;
    private String nombre;
    private String direccion;
    private String telefonos;
    private String regimen = "Simplificado";
    private InputStream logo;
    private String pathLogo;
    private String estado;
    private Date create_at; //fecha creacion    
    private SimpleDateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sa = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat hh = new SimpleDateFormat("HH:mm:ss");

    public Empresas() {
        super();
    }

    public Empresas(int idEmpresa, String nombre) {
        this.idEmpresa = idEmpresa;
        this.nombre = nombre;
    }
    
    

    public SimpleDateFormat getSa() {
        return sa;
    }

    public void setSa(SimpleDateFormat sa) {
        this.sa = sa;
    }

    public SimpleDateFormat getHh() {
        return hh;
    }

    public void setHh(SimpleDateFormat hh) {
        this.hh = hh;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
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

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public InputStream getLogo() {
        return logo;
    }

    public void setLogo(InputStream logo) {
        this.logo = logo;
    }

    public String getPathLogo() {
        return pathLogo;
    }

    public void setPathLogo(String pathLogo) {
        this.pathLogo = pathLogo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into empresa (nit,Nombre,Direccion,Telefono,regimen,logo,Estado,create_at) "
                + "values (?,?,?,?,?,?,?,?)";
        try {
            FileInputStream fis = null;
            File file = null;
            if (!pathLogo.equals("")) {// cuando se adjunta la foto
                file = new File(pathLogo);
                fis = new FileInputStream(file);
            }
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, nit);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, direccion);
            preparedStatement.setString(4, telefonos);
            preparedStatement.setString(5, regimen);
            if (file != null) {
                preparedStatement.setBinaryStream(6, fis, (int) file.length());
            } else {
                preparedStatement.setString(6, null);
            }
            preparedStatement.setString(7, "A");
            preparedStatement.setString(8, fechaHora.format(create_at));
            transaccion = Empresas.this.getConecion().transaccion(preparedStatement);
        } catch (SQLException | FileNotFoundException ex) {
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
        try {
            FileInputStream fis = null;
            File file = null;
            if (!pathLogo.equals("")) {// cuando se adjunta la foto
                file = new File(pathLogo);
                fis = new FileInputStream(file);
            }
            String logoUpdate = "";
            if (file != null) {
                logoUpdate = ", logo=?";
            }
            String update = "update empresa set nit = ?,Nombre =?,Direccion=?,Telefono=?"
                    + logoUpdate + " where idempresa = ?";
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(update);
            preparedStatement.setString(1, nit);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, direccion);
            preparedStatement.setString(4, telefonos);
            if (file != null) {
                preparedStatement.setBinaryStream(5, fis, (int) file.length());
                preparedStatement.setInt(6, idEmpresa);
            } else {
                preparedStatement.setInt(5, idEmpresa);
            }
            transaccion = Empresas.this.getConecion().transaccion(preparedStatement);
        } catch (SQLException | FileNotFoundException ex) {
            System.out.println("Error SQL : " + ex.toString());
        } finally {
            try {
                this.getConecion().getconecion().setAutoCommit(true);
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        System.out.println("transaccion = " + transaccion);
        return transaccion;
    }

    @Override
    public int remove() {
        int transaccion = -1;
        String PrepareDelete = "delete from empresa where idempresa=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(PrepareDelete);
            preparedStatement.setInt(1, idEmpresa);
            transaccion = Empresas.this.getConecion().transaccion(preparedStatement);
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
        ArrayList<Empresas> List = new ArrayList();
        String prepareQuery = "select * from empresa where Estado = 'A'";
//        System.out.println("prepare mauricio " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Empresas.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Empresas tabla = new Empresas();
                tabla.setIdEmpresa(rs.getInt(1));
                tabla.setNit(rs.getString(2));
                tabla.setNombre(rs.getString(3));
                tabla.setDireccion(rs.getString(4));
                tabla.setTelefonos(rs.getString(5));
                tabla.setRegimen(rs.getString(6));
                tabla.setLogo(rs.getBinaryStream(7));
                tabla.setEstado(rs.getString(8));
                tabla.setCreate_at(rs.getDate(9));
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
        ArrayList<Empresas> List = new ArrayList();
//        String prepareQuery = "select a.idAsistencia, a.FechaMarcacion, a.HoraMarcacion, p.NombreCompleto "
//                + "from Asistencia a, persona p, rolxuser r "
//                + "where a.idPersona = p.idPersona and p.Estado = 'A' and a.FechaMarcacion "
//                + "between '" + sa.format(inicio) + " 00:00:00' and '" + sa.format(fin) + " 23:59:59' "
//                + "and r.idPersona = p.idPersona and r.idRol = 2 order by a.FechaMarcacion ASC";
//        try {
//            this.getConecion().con = this.getConecion().dataSource.getConnection();
//            ResultSet rs = Empresas.super.getConecion().query(prepareQuery);
//            while (rs.next()) {
//                Empresas tabla = new Empresas();
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

    public Empresas findById(int idEmpresa) {
        String prepareQuery = "select * from empresa where idempresa =" + idEmpresa;
        Empresas emp = null;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Empresas.super.getConecion().query(prepareQuery);
            if (rs.next()) {
                emp = new Empresas();
                emp.setIdEmpresa(rs.getInt(1));
                emp.setNit(rs.getString(2));
                emp.setNombre(rs.getString(3));
                emp.setDireccion(rs.getString(4));
                emp.setTelefonos(rs.getString(5));
                emp.setRegimen(rs.getString(6));
                emp.setLogo(rs.getBinaryStream(7));
                emp.setEstado(rs.getString(8));
                emp.setCreate_at(rs.getDate(9));
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
        return emp;
    }

    public boolean ExistAsistencia(Date fachaMarcacion, int idUser, int idPersona) {
        boolean existe = false;
        String prepareQuery = "select idPersona from asistencia "
                + "where FechaMarcacion = '" + sa.format(fachaMarcacion) + "' "
                + "and idPersona = " + idPersona + " and idUsuario = " + idUser;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Empresas.super.getConecion().query(prepareQuery);
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    @Override
    public String toString() {
        return  nombre ;
    }
    
    

}
