package Pojos;

import java.util.List;
import Coneccion.poolConecciones;
import org.apache.commons.dbcp.BasicDataSource;

public abstract class Persistencia {

    BasicDataSource DataSource = new BasicDataSource();
    poolConecciones pool;

    public Persistencia() {
//        InicConection(1);//bd liliana
        InicConection(5);//bd pruebas
    }

    private void InicConection(int opc) {
        String driver = "com.mysql.jdbc.Driver";
        String User , pass, url;
        switch (opc) {
            case 1:
                //bd liliana
                User = "root";
                pass = "ninguna";
                url = "jdbc:mysql://localhost:3306/appgym";
                break;
            case 2:
                //bd pruebas
                User = "root";
                pass = "PpY8lfp838Et3716";
                url = "jdbc:mysql://192.168.10.200:3306/appgym";
                break;
            default:
                //bd no programada
                User = "root";
                pass = "";
                url = "jdbc:mysql://localhost:3306/appgym";
                break;
        }
        DataSource.setDriverClassName(driver);
        DataSource.setUsername(User);
        DataSource.setPassword(pass);
        DataSource.setUrl(url);
        DataSource.setMaxActive(50);
        pool = new poolConecciones(DataSource);
    }

    public abstract int create();

    public abstract int edit();

    public abstract int remove();

    public abstract List List();

    public poolConecciones getConecion() {
        return pool;
    }

    public void setConecion(poolConecciones pool) {
        this.pool = pool;
    }

}
