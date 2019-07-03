package Pojos;


import Coneccion.GetConecion;
import java.util.List;
import Coneccion.poolConecciones;
import org.apache.commons.dbcp.BasicDataSource;

public abstract class Persistencia {

    BasicDataSource DataSource = new BasicDataSource();
    poolConecciones pool;

    public Persistencia() {
//        InicConection(1);//bd liliana
        pool = GetConecion.getControllerpool(3);
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
