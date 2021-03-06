-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-05-2018 a las 06:42:36
-- Versión del servidor: 10.1.29-MariaDB
-- Versión de PHP: 7.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `appgym`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `BuscarEmpresaProveedorXCodigo` (IN `codigo` INT)  NO SQL
SELECT idempresaproveedor, nombreEmpresa, nit, direccion, telefono, estado FROM appgym.empresaproveedor where idempresaproveedor=codigo$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `BuscarProductoXCodigo` (IN `serie` VARCHAR(20), IN `codigo` INT)  NO SQL
select A.*,B.descripcion,C.siglas,D.descripcion,B.Ganancia,C.Descripcion from producto A , categoria B  , unidad C , iva D 
where a.idCategoria=B.idCategoria and A.cod_unidad=C.cod_unidad and A.idIva=D.idIva and A.estado='A' 
and (a.serieProducto=serie and a.cod_producto=codigo)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cerrarCajasAuto` ()  BEGIN
  DECLARE idCajaUpdate int;
  DECLARE fin INT DEFAULT 0;

DECLARE runners_cursor CURSOR FOR  SELECT idCaja FROM cajaxuser where Estado = 'A' and DATE_FORMAT(FechaInicio, "%Y-%m-%d") < curdate();

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin=1;

  OPEN runners_cursor;
  get_runners: LOOP
    FETCH runners_cursor INTO idCajaUpdate;
    IF fin = 1 THEN
       LEAVE get_runners;
    END IF;

  update  cajaxuser set Estado = 'C', MontoVenta = (Select  case when sum(ValorTotal) is null then 0 else sum(ValorTotal) end as valorTotal from pagoservice where idCaja = IdCajaUpdate), Montofinal = (MontoInicial+MontoVenta) where idCaja =  IdCajaUpdate;

  END LOOP get_runners;

  CLOSE runners_cursor;
  
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ClienteBuscar_Name_Cedula` (IN `filtro` VARCHAR(1))  NO SQL
SELECT a.idcliente,a.tipocliente,b.idPersona,c.idTipoDocumento,c.siglas,b.Documento,b.Nombre,b.Apellidos,b.NombreCompleto,b.direccion,b.Telefono,b.Sexo FROM `cliente` a , persona b , tipodocumento c WHERE a.idpersona=b.idPersona and b.idTipoDocumento=c.idTipoDocumento and b.Estado='A' and (b.Documento like CONCAT('%', filtro , '%') or b.NombreCompleto like  CONCAT('%', filtro , '%')  )$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Compra_ajusteProductos` (IN `idCompra` INT)  NO SQL
BEGIN

declare idDetalle int;
declare serieProducto varchar(25);
declare cod_producto int;
declare costo decimal(14);
declare cantidad int ;

  DECLARE done INT DEFAULT FALSE;

declare cur_Compra CURSOR for 
SELECT `serie_producto`, `idDetalle`,cod_producto,costo,cantidad FROM `compra_detalle` WHERE `idCompra`=idCompra;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  open cur_Compra;
  read_loop: LOOP
  FETCH cur_Compra into 				   serieProducto,idDetalle,cod_producto,costo,cantidad;
     IF done THEN
      LEAVE read_loop;
    END IF;
	
    if(cod_producto>0) then
    
 	update producto set producto.costo=(producto.costo+costo)/2, 		producto.cantidad=200 where 		       	   producto.cod_producto=cod_producto;
 
    end if;

  END LOOP;
  
  CLOSE cur_Compra;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetNumerador` (IN `tipo` VARCHAR(12))  NO SQL
begin

START TRANSACTION;

UPDATE numeradores SET valor=(valor+Incremento) WHERE tipoNumerador=tipo;

insert into numeradorespendientes
SELECT tipoNumerador,valor,'P' FROM numeradores WHERE tipoNumerador=tipo;

SELECT valor FROM numeradores WHERE tipoNumerador=tipo;

COMMIT;

end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ListClientes` ()  NO SQL
SELECT a.idcliente,a.tipocliente,b.idPersona,c.idTipoDocumento,c.siglas,b.Documento,b.Nombre,b.Apellidos,b.NombreCompleto,b.direccion,b.Telefono,b.Sexo FROM `cliente` a , persona b , tipodocumento c WHERE a.idpersona=b.idPersona and b.idTipoDocumento=c.idTipoDocumento and b.Estado='A'$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ListProductos` ()  NO SQL
select A.*,B.descripcion,C.siglas,D.descripcion,B.Ganancia,D.porcentaje from producto A , categoria B  , unidad C , iva D 
where a.idCategoria=B.idCategoria and A.cod_unidad=C.cod_unidad and A.idIva=D.idIva and A.estado='A' limit 200$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ProductoBuscar` (IN `filtro` VARCHAR(20))  NO SQL
select A.*,B.descripcion,C.siglas,D.descripcion from producto A , categoria B  , unidad C , iva D 
where a.idCategoria=B.idCategoria and A.cod_unidad=C.cod_unidad and A.idIva=D.idIva and A.estado='A' 
and (a.serieProducto LIKE '%'||filtro||'%')$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Venta_AjusteCantidades` (IN `factura` INT)  NO SQL
UPDATE producto INNER JOIN ventaproducto A
ON A.cod_producto=producto.cod_producto AND
A.idCategoria=producto.idCategoria and 
A.cod_factura=factura
SET cantidad=(cantidad-A.cantidadVenta)$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE `asistencia` (
  `idAsistencia` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `idSede` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `FechaMarcacion` date NOT NULL,
  `HoraMarcacion` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `asistencia`
--

INSERT INTO `asistencia` (`idAsistencia`, `idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`, `FechaMarcacion`, `HoraMarcacion`) VALUES
(1, 1, 'mherrera', 2, 1, 1, '2017-07-27', '10:53:08'),
(2, 2, 'fdsaf', 2, 1, 2, '2017-07-27', '10:53:19'),
(3, 1, 'mherrera', 2, 1, 1, '2017-07-27', '13:16:26'),
(4, 1, 'mherrera', 2, 1, 1, '2017-07-27', '13:58:52'),
(5, 2, 'fdsaf', 2, 1, 2, '2017-09-16', '15:44:19'),
(6, 3, 'Gym', 1, 1, 3, '2017-10-16', '14:18:39'),
(7, 2, 'fdsaf', 2, 1, 2, '2017-10-16', '14:46:07'),
(8, 2, 'fdsaf', 2, 1, 2, '2017-10-22', '09:23:33');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bodega`
--

CREATE TABLE `bodega` (
  `idBodega` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `idSede` int(11) NOT NULL,
  `nombreBodega` varchar(30) NOT NULL,
  `estado` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `bodega`
--

INSERT INTO `bodega` (`idBodega`, `idempresa`, `idSede`, `nombreBodega`, `estado`) VALUES
(1, 1, 1, 'B1', 'A'),
(2, 1, 1, 'B2', 'A'),
(3, 1, 3, 'B1', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cajaxuser`
--

CREATE TABLE `cajaxuser` (
  `idCaja` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `idSede` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `FechaInicio` datetime NOT NULL,
  `FechaFinal` datetime NOT NULL,
  `MontoInicial` double NOT NULL,
  `MontoVenta` double NOT NULL,
  `Montofinal` double NOT NULL,
  `Estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cajaxuser`
--

INSERT INTO `cajaxuser` (`idCaja`, `idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`, `FechaInicio`, `FechaFinal`, `MontoInicial`, `MontoVenta`, `Montofinal`, `Estado`) VALUES
(1, 1, 'mherrera', 2, 1, 1, '2017-08-08 17:50:38', '0000-00-00 00:00:00', 0, 0, 0, 'C'),
(2, 2, 'fdsaf', 2, 1, 2, '2017-08-08 17:50:38', '0000-00-00 00:00:00', 0, 0, 0, 'C'),
(3, 1, 'mherrera', 2, 1, 1, '2017-09-09 00:00:00', '2017-09-09 00:00:00', 0, 0, 0, 'C'),
(4, 1, 'mherrera', 2, 1, 1, '2017-09-12 00:00:00', '2017-09-12 00:00:00', 0, 107250, 107250, 'C'),
(5, 1, 'mherrera', 2, 1, 1, '2017-09-16 00:00:00', '2017-09-16 00:00:00', 0, 0, 0, 'C'),
(7, 1, 'mherrera', 2, 1, 1, '2017-09-16 00:00:00', '2017-09-16 00:00:00', 0, 3000, 3000, 'C'),
(8, 1, 'mherrera', 2, 1, 1, '2017-09-17 00:00:00', '2017-09-17 00:00:00', 0, 0, 0, 'C'),
(9, 1, 'mherrera', 2, 1, 1, '2017-09-20 00:00:00', '2017-09-20 00:00:00', 0, 0, 0, 'C'),
(10, 1, 'mherrera', 2, 1, 1, '2017-09-24 00:00:00', '2017-09-24 00:00:00', 0, 0, 0, 'C'),
(11, 1, 'mherrera', 2, 1, 1, '2018-01-04 00:00:00', '2018-01-04 00:00:00', 0, 0, 0, 'C'),
(12, 1, 'mherrera', 2, 1, 1, '2018-01-05 00:00:00', '2018-01-05 00:00:00', 0, 0, 0, 'C'),
(13, 1, 'mherrera', 2, 1, 1, '2018-01-11 00:00:00', '2018-01-11 00:00:00', 0, 0, 0, 'C'),
(14, 1, 'mherrera', 2, 1, 1, '2018-03-20 00:00:00', '2018-03-20 00:00:00', 100, 0, 0, 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `idCategoria` int(11) NOT NULL,
  `descripcion` varchar(30) NOT NULL,
  `Estado` varchar(2) NOT NULL,
  `Ganancia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idCategoria`, `descripcion`, `Estado`, `Ganancia`) VALUES
(1, 'Gaseosas', 'A', 1),
(2, 'prueba2', 'A', 1),
(3, 'gfdgdfe4', 'A', 2),
(4, 'gfdgdfvg', 'A', 1),
(5, 'eee', 'A', 1),
(6, 'a23', 'A', 1),
(7, '', 'A', 1),
(8, '', 'A', 1),
(9, '', 'A', 1),
(10, '', 'A', 1),
(11, 'eee3', 'A', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `IdCliente` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `TipoCliente` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`IdCliente`, `idPersona`, `TipoCliente`) VALUES
(1, 3, 'Admon'),
(2, 1, 'Cliente'),
(3, 7, 'Cliente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra_detalle`
--

CREATE TABLE `compra_detalle` (
  `idCompra` int(11) NOT NULL,
  `serie_producto` varchar(40) NOT NULL,
  `nombre_producto` varchar(50) NOT NULL,
  `costo` decimal(14,2) NOT NULL,
  `idiva` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `valor_venta` int(11) NOT NULL,
  `cod_unidad` int(11) NOT NULL,
  `idDetalle` int(11) NOT NULL,
  `cod_producto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `compra_detalle`
--

INSERT INTO `compra_detalle` (`idCompra`, `serie_producto`, `nombre_producto`, `costo`, `idiva`, `cantidad`, `stock`, `valor_venta`, `cod_unidad`, `idDetalle`, `cod_producto`) VALUES
(8, '1', 'jajaj', '100.00', 1, 100, 10, 1000, 1, 1, 0),
(10, '2', 'pepsi', '100.00', 1, 100, 10, 1500, 1, 3, 0),
(11, '15', 'kiko', '100.00', 1, 150, 10, 1500, 1, 5, 0),
(12, '1', 'COCA COLA', '1000.00', 1, 100, 10, 1200, 1, 6, 1),
(12, '184', 'kiko', '100.00', 1, 100, 10, 1500, 1, 7, 0),
(13, '1', 'COCA COLA', '1000.00', 1, 100, 10, 1200, 1, 8, 1),
(14, '1', 'COCA COLA', '1000.00', 1, 100, 10, 1200, 1, 9, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra_productos`
--

CREATE TABLE `compra_productos` (
  `idCompra` int(11) NOT NULL,
  `cod_factura` varchar(20) NOT NULL,
  `cod_proveedor` int(11) NOT NULL,
  `fecha_compra` date NOT NULL,
  `estado_factura` varchar(20) NOT NULL,
  `costoCompra` decimal(14,2) NOT NULL,
  `CantidadProductos` int(5) NOT NULL,
  `idBodega` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `compra_productos`
--

INSERT INTO `compra_productos` (`idCompra`, `cod_factura`, `cod_proveedor`, `fecha_compra`, `estado_factura`, `costoCompra`, `CantidadProductos`, `idBodega`) VALUES
(6, '1', 7, '2018-02-20', 'Pendiente', '0.00', 1, 1),
(7, '1', 8, '2018-02-20', 'Pendiente', '0.00', 1, 1),
(8, '1', 8, '2018-02-20', 'Pendiente', '0.00', 1, 1),
(9, '12', 7, '2018-02-21', 'Pendiente', '0.00', 2, 1),
(10, '12', 8, '2018-02-21', 'Pendiente', '0.00', 2, 1),
(11, '123', 8, '2018-02-21', 'Pendiente', '0.00', 2, 1),
(12, '134', 8, '2018-02-21', 'Pendiente', '0.00', 2, 1),
(13, '144', 8, '2018-02-21', 'Pendiente', '0.00', 1, 1),
(14, '1345', 8, '2018-02-21', 'Pendiente', '0.00', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dias`
--

CREATE TABLE `dias` (
  `idDias` int(11) NOT NULL,
  `Descripcion` varchar(60) NOT NULL,
  `Estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `dias`
--

INSERT INTO `dias` (`idDias`, `Descripcion`, `Estado`) VALUES
(1, 'Lunes', 'A'),
(2, 'Martes', 'A'),
(3, 'Miercoles', 'A'),
(4, 'Jueves', 'A'),
(5, 'Viernes', 'A'),
(6, 'Sabado', 'A'),
(7, 'Domingo', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ejercicios`
--

CREATE TABLE `ejercicios` (
  `idEjercicios` int(11) NOT NULL,
  `IdMusculo` int(11) NOT NULL,
  `Descripcion` varchar(60) NOT NULL,
  `Imagen` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `ejercicios`
--

INSERT INTO `ejercicios` (`idEjercicios`, `IdMusculo`, `Descripcion`, `Imagen`) VALUES
(1, 1, 'prueba', NULL),
(2, 1, 'prueba2', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `idempresa` int(11) NOT NULL,
  `nit` varchar(20) DEFAULT NULL,
  `Nombre` varchar(60) NOT NULL,
  `Direccion` varchar(60) NOT NULL,
  `Telefono` varchar(60) NOT NULL,
  `regimen` varchar(20) NOT NULL,
  `Logo` longblob,
  `Estado` varchar(2) NOT NULL,
  `create_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`idempresa`, `nit`, `Nombre`, `Direccion`, `Telefono`, `regimen`, `Logo`, `Estado`, `create_at`) VALUES
(1, '', 'Gym1', 'prueba', '111111111', '', 0x89504e470d0a1a0a0000000d49484452000000e10000003f08060000001687af25000000017352474200aece1ce90000000467414d410000b18f0bfc6105000000097048597300000ec300000ec301c76fa8640000000f74455874417574686f72004c6f676173746572f45ab40a00000021744558744372656174696f6e2054696d6500323031383a30313a30392031383a30313a3236f8b9f22a0000218a49444154785eeddd05b47545d907f07d7951140b512c44c25696017621a0d82ebbeb3551ec2e14046359607761776077802d828509760b76a1f2dddf7cfe2ff36ef6d9679f73cfb9ef7d97e7bfd6ac33337be299679e67e699d8fb2cedbaebaea736eb1c1b366c68fef297bf34a79e7a6a73c94b5eb2b9ea55afdaecbefbeecd052e708166db6db72d69fefad7bf363ffff9cf9b6f7ef39bcd673ffbd9e6f8e38f6f9696969ab39ce52ccd7ffef39f92778105d623d6ad12529aadb6daaafcfee94f7f6af6dc73cf66e3c68dcd55ae7295e66c673b5bf3ef7fffbbf9d7bffe55140ca4dd7aebadcbef9ffffce7e60b5ff842f3aa57bdaaf9f297bf5cd2534869fd2eb0c07a425142821ee19c971f8487fa2913453be594539afdf7dfbf28e036db6c536644f1293bbfc9efd7cc6906fce73fffd9bcfad5af6e5ef4a21795384a5aa7abf34eea07e1bef879fa41781e7e10ee8b9fa71f84e7e107e1bef8d5faa11deec386edb6dbeea0fffad70d2820e5d390a73ef5a9cd9def7ce7a27c7ffffbdfcb332ed0504e5a2ecfa435f35deb5ad76a76d96597e6139ff8449939296266cf0516580f585a16d061eaba46a815f099cf7c66739deb5ca7f9ddef7e579407281c253ac319ced09ce94c672a331c981dfff18f7f94d94f19f203c53bd7b9ced57cf2939f6c1efad0879674f58cb8c0029b1beb4a09a38010053ce9a493561490f251bab39ef5accdaf7ffdebb2f9f2cb5ffeb23c3bef79cf5b366dfc5a1352b6cc8a51c40f7ff8c3cdc31ffef05286670b455c603da02861cc3958ad1f84fbe2bbfce314905299f9b2ce7bcf7bde531490822947baf39ffffccd4d6e7293e66e77bb5b593f324933532a7b871d76685ef18a57344f7bdad39ab39ffdec2b66699bd6d5f84178de7e105e4b3f08cfdb0fc26be907e159f8a11deec3d2ce3bef3c2ce51c410129133ce319cf68ae7bddeb9e4e01cf7ce63337279f7c723129ed7cdaf164926a2c683045b3937ac52b5eb179d6b39e5594ced1451491d29945ef7bdffb369ff9cc678a7fb13e5c6073e3b41d8ecd84cc8094a84f017ffffbdf37f7bffffdcb9103e5f29c0279cef18bf3ec98638e690e38e080e6b7bffd6dc95bcf78dc7dee739f32532e147081f580a284f5b4392f7fc2b5df2c1613749c02deef7ef76bbef18d6f34e73ce7394b1e0ad42e9363ae4af3ed6f7fbb79c0031e50664633a6676644bbac97bdec65cb79237f7b6d38d49ff0bcfd302e1e66e94f7873f9615efe84e7e9ef0af7b9cd361352404a8688a73ffde9cd7efbedd7a9807ff8c31fcaac4601ade3a2b47d90e61ce73847f3d5af7eb579ce739e53ca510ff855c7de7befbd9809175817d8b02cac6b7e4e480139470a871e7a68d94c19a58066c0af7ffdeb45a9b26e1c02caa68cef7ef7bbe5acf03ce7394f51ced47dc6339eb179fffbdf5fea5aec942eb039b1596642424fc1ec62dee216b7d8e41cb0ad805ffbdad7265640a054eaf9e31fffd87cee739f2b3babe228a0b2ac1dcf7dee734f5cee020bcc1a735d13265cfb29811dcb4b5ce212cdbdef7defa26cf5ee655b01b7db6ebb4e137454f950c7abef073ff8c18ae9299c7a98b794be465739d0f6d761cace293b0e92a64ebf1a3fac853fe1bef859fba1edc7c79aaf890fa6f127dc159f3adaf5c61fb4cb69fbbbc27d6ecd67428db179e22a9a192e262262988814d01a3033609e6304e5e1da4c1907e785354ac397cb30fbf20f45d21a3432709849fff6b7bf950b02b95a97d9551af52c301c358f0d9078cae169fa7d923eeb43bb3fc91679abfb93e337716432907696746c589e0dd66c4d88700ae81524375702f151aa073de841cd17bff8c5955d508cd71918e1b525618a4a81f8fb18e139a5b8fad5af5ed685fce94865bee52d6f296bd1ec9e8e433aca8eab4e3168984db5e7c217be70b92c204e593a4f3af5380e9965a74d839ac7e3e848bacd41b33af10ceff0f66217bb58e1abbeb36cd17ffa61167429475d962c144f3fb1bc2e74a10b35bbeeba6be9d71d77dcb1b9e0052f58962efa5ebfc77a1b2a37e3b0b4d34e3bad199711eeb8e196b7bc65b9b592c66084c6bff295af6c0e39e490d2600ae819a5c51ce6e935af79cdd2e8a38e3aaabc196163a76f36935f1d871d765873a31bdda8f875a23c0efe6f7ffbdb97dfac47fba02c9d8596bdf6daabb9def5aed75cfef2972f1b3eded8c87b8b19357ffce31f974b05ef7bdffbcab925e5b42e35a247c8d702da0bf8182b22971c846bd469c353748bd747f3069af4bba5824b1937bef18d0b7fd5efccf7a31ffd68d949377066b09b14f2a49de4c1607fed6b5fbbdcd0727445f65c04c91e02842ee97ff18b5f341ff9c8479ad7bdee75cd4f7ef2934d6e5e4d8ba2842a8b60ccd3aff194f0a0830e2ae6283fe1f64c43ee78c73b96f51b0688230c14e4b9cf7d6eb3efbefb16255016c6bded6d6f6b1ef18847940e13973afc82728d6e46b537bce10d45790892e714e64b5ffa5273cf7bdeb374668d9453fb4337e57bfce31f5f6ee404ca44bb5f69d52b7d3a5a1b28e2939ef4a4a2983a2d8a185aa7f183709f1f0d666470a7165f09937bb706b00890f4e83678084bcbea40fb6f7ef39b320b114ccfda74ccca1fa0cf607c831bdca0f034a63d3940a381ed36b7b9cd0a2d81b284536e7ee34f1a3c51aef6dfee76b72b17402e7ef18b97e7e059fab49d4ffd1cfcea57bf6a1ef9c84736471e79e4267c0c52ff106c95847ed7c26f1436c54768114f414e38e18422a494423c01d0e0c30f3fbc9892ee898ae3f81dec5fe63297298253330bc200691dd8677327f168f8fce73f5f941473435bf2d77ecf29e0c68d1b9b77bce31d4501e553af5fe5a62d4068d4eb1905d0be9bdffce64511bd986c20d1ae940fd3f8131ee5470f93ee7297bb5c19b5cd221ffad087caefbbdef5ae621998b5a5d346fe4b5dea52cd11471cd17cec631f6b3ef8c10f96b4eee8deec66372b6d09af8259fad14131987efa9b1f1f037cc6f32b5de94ae5a23efea63f39b409e37f9cb8d09c3af415bcf0852f6c9ef7bce71505546edd9f6d054f3d9e498357b9876c16c567f5d4907e2836cd3967208c02e4cdf84003981b1a68c4c37c71145023b36ec38c349620335530bbcd308ee23ce4210f696e78c31b162665048b29caa4a84d8e2ec8436998454c5a75e92c3478e6575d3a4d7bb874b667eae227c0d61704dc5a433bd38e7940d9ea602ebffded6f2fa63333cb46172be2ca57be72f3dad7beb62822daf0fbd297be741964f0ab4e7b852b5ca1cc4c8e926a3ece1ae907fc22ece16dc0afee2857dd6fc214037d2c23b4eb5b3250f35a19c29642b7bef5ad4bdb85959bfe84f4677e6bdaa4218bf2e95f66b3bcd24e8b3555c230c154cedcc988c56f26d490309902bad5521fe20386082be7fbdfff7e9939e58bf07398ffe0073fb8ecb252a23057d94c0726c477bef39dd2517dcc230cd23fe1094f58e9902e21447f5ccce31ae82524e73bdff99ac73dee719b8cf0f3001e182c6e7bdbdb16faad65c26b0e4fb4c59a18a4b54edf7efbed47a6b5548076db668df4e328b49fe95bbcf5dd21839cd9db00ebb5b597bce425656387b2a50fae76b5ab95b644f9221b81b8ba2ffd4ad39613e9c88799d4acc83f2d0a05356367e94f387ece28f2e217bfb8f9def7be57465c0d7029fb4d6f7a5379a6b114709f7df6399d0282e7cc4bdf8fa18451a4740e05b4c3ca11a8c44b83a13ffce10f4be7d4ca925f881fe309a7ced591cca3b6024a2b1dda5d0a37f332e5b4a32e13b483f2d900501e21405b4dc3247e18e507b4fae81568abf6c719b83cffca57be52c268fbd6b7be35362d65849a8e3e7f17ea34302add38e01dde53304b13b3388bc51e00ab63b7dd762b83d0bbdffdee32cbe7e8885510be473602617dce54f5a68d7d0b6b4679f145fbe3f0463f7ba7b5fdd279da14ff38b761d9345cb3230a1522dcc680918a227ee0031f28b6f9cf7ef6b3d2e12e72635497026abcd1fa652f7b597955293b9298c7450129037fe2a52148fc0f7bd8c3cae56e0a297e14d2c16663f75a632205f21a007ef4a31f15d3c60ea875a60145e765865627840e345b9b6500299db04c9bb2573aa50a77c133693c4f1dc29032d47fdc71c7157e5b8b12227e4e1eeb19ef6d4ac7b99b2b1ff3136f92569c19c6674684472134a08d3f74b45d684f9a1a94c4007b97bbdc65a56ee96ac8fffad7bfbeec4cda4b202736bdac67cdda1045a1504c6a4a44cec813c5a494edfe945e7f30df6dd8914dce061ed974e15f59e18bb23ef5a94f158b4bbda1771a6c3a17af012288b67a5ffad2971686b2e52db6ed82da15ab4796200a48789ef294a79432d2681d1505c4945a01d3f118cc14fcf4a73f7dba35e9b450be8e26c46833ab8bd3e97e87006dda6f9d0ada9d705b08d31633aa35b4d959ddc27826ec391e836776736dbd132c4ac744b75963a08a102a177f1c0f497bf7bbdfbd0c8636b5ac2bcd06a1c56f0d71948a02e13ba56081a021025b0b2ede4823ad34a16112288702a2057d1450fdda8b9e38e9d441e184f9f1aadd06c873e625fee84f4a675078cd6b5e53e22830d93bf0c003cb7a5a9882e29dbcd36269c71d775ca6e9b4ce9e85bfeedc38109f11ca886bc6b3516121cd7ca01c5d8bff5a015df8f632ae7a94ab4c1dfac0073eb008564c502e9d6c16a0806f7ef39b4b39ca0badd0e54703616166125e34d774295bb94cbeeb5ffffa259fceb6eeb32ed1810423e542f2dcea56b72a69b4db286af3c9b6bb113a3bc52e97bb4c80fed4cb6f76666eb9776b74b639e518c1d63d613143648022f0e831539b0d1d3d506eb79198d0ea31eb81b4061174e81bf9f0d5e57974b8082fad72398852e10d9aec1cdbf4d979e79dcb2ea7fe4c5ad07e3bdb8ea1bc54cd74b74ecb16bf418439295e5dfaa9e61f081bc033d069137988a2d508bfad01edf62aef5ef7ba571960b24eac8156fcc723835ce8e55838e8c23bf13e97127e08abbb6e6b3bdc87992821e1e0089d914663116684303a8599e2a4775bc6a680e79e713ac0af726a2853839df519d50958ea06821213b456c0d0a813e47be31bdf58ca5107f4b5877f5a2524143a6b9c127efce31f2f6d35eb3880ee02d3c82ca45e69ad232f72918b140522e86d18959d7dd974929e62502079dab03976873bdca1081561a480062916491b66d93bdde94ee52613c1d30e34e1f74e3bed54ee003b86e19f04ead637ac1303121e6b579f1242663cc0f3ae74e294619960ad881fe20c0cca3748a8af4b11c5b5e3816c1f7df4d1654f23bbebca8dccf80ddae13eacea9c107406e120b0e28c64466887eb84de488750a068071f7c70e9b49825663eccd0b14917609a3228901d4a0cc41c0d949f1050bea10a4829da6de8f3273c0e93a40dd0c2d43180c88b4ff8c8e10767c7d2cce259f86c0624a866d0a4e7849dbf1a34088b386b530ad895d660e17baee8302b505e0ad89516ef98b1d2025a0c7e2c196b7b5688baa50fed7519b5cb73ce39ef5bdffad6222b5d16d02890a394d7a580f8c9fc359b19c4f0431a71f62398d9ea37a06b13d90ba413d745af72ed1118acec4940ea6ff7ff24f230b941be0c1560186275868e767bc0d99351fae52f7f79d961320abbb7496174b4f706994646618453288ac7b5198931cc14e5b1c199093acaa84c3008a2177631749c0232412340936212660e05fab4cf481a7fcd0bbc4d5bcc10111271f288afd373c2e2b55b18326b8d4acb8c8be013c8beb4caca33fc37fb5bcf335bf52d41f74c792953be38b4d7cf39838b7e75830add047a08d0c9a5ec2e8857b6d9397def974cf906ad0b085efad66e75a34ffdd2d465b7db1485dcb8716391f169e5aa46e9adba2143fc3a83426880998da01b29ad69980a462acf8db68f79cc638ae0c863bd91ced2e851c0049d7eecb1c7960e0a23ec52aac76e2ae5a4d4ea4979f2f9c5548acb84cd1a10266de7a4189a37e9f23b8a17118c40fab4b10be181749cbc78d385765a7e69f9dba8d332ff0c0c764bf5a9d9c26f4d9374fa401fc675956d6632403bb2b121a2ac516daba13cb2e7573ded7295a1ed2c273356ee22a381d2a0df1acfe68a99d27f9788afcb8d6bf38f1c8a33f05064cb2a13917874c4411dee73139ba32ab370b52b451198471a2dce08112191ce8869e380d9c5a4f1a601051dc768f518c5983a94cce865d6638bbb2f6a14d68166c03049bdeac4380a68949ec6048dbffe9d0443f30c4d175e25fd38ded5cf27493b0ed246f86c24e9cb28600d74ea1be7672c23b76df4857e90366b388edfa06d606e0bf228a0c1a6959d75e5db38525f3b8f74e4d1da8fd96cd64d7bd1c20a009fcfbce94d6f5ace6f6dda505a339c4d40474931656b0873eab4436a9289dc077d6d68a37b981c014cb48e63c32352039987808188404c984cf9ec647166248a85b876a3ba203fc5d6d198e440968dafb32877944e79988a113ae3d18f7ef4ca0c287e5a4cc2c449d2d618924f9a69cb9f35f49b7eb1fce8a2499f502ae76b064aeb53474677bdeb5d9bc73ef6b1250fe1cf2c935d4dcb86134f3c71c5d4ee031accc2d6b3ac2272c162225beaaf11deb1d0dae56a07d84093d766168bce006ea0b7cf60a67cf2939fbcb2b95883922bd35f2c18ecb5635a0c56420a48e1349a39286ca4c94888484a41f14cf74c0107d9184669ac1d86dafde96c0a07367b3283aa230c90861f132cb875ba7528c60ea967966877f23c30b40ee9e6490f9eeb8b761dc29ef98b3a568a1d57b2e07d40379cdc4b253b0ece3937a3cc529e310387f619d9d2c7faddccacbe2e7ac47191d12ea8533e0340ca34807368728c03ca6943be5a1ea74551c2ba80b69f8b025a93310b81e623003484c9c8bea7048f7ad4a39a7bdce31e6513c62d12ca67a7af8b516da43eb39e9b2df2bab56034e2c08c6714c53474302998056e301804ea19b0dd9e60887f520ccd2bdd6aea992526a165683afd4736f403d988f9671d6603cf4eacb594fd02b77ac84eca1e528732e32840e47012d4f5f0c782436b9c38b236099455b765881bbb268c029a0129204522f81a0f0827f8ce4d6cbc98ca6de17af1961dae210eca63978f6398b28d9eec7ef73c8d4c6c73b6bad9507d14d4c79b9cafa99309c14cd6999ec3a8f64ce2afe3fa50a719923e189a76281d6b81217484de9a6e72c004b5494221cd32fce2f4599d7608927668bea4abd3d77ea8fdd07e3e0a4957a71d922fe83547a38016adae947529a069db02d98557b73528a4198ac940e98c7c16bc4cd2e40b10aa73320a7136728c98cf7ffef3573ac80cc874b11be580d90c6b8b9869c3e455a7114bda05d62ff4736698daffbf8e91e6a819cbec9235e028057455ca413a8561574799e47559da029abfad80982fce884861299299ccf522792830c54a27193d9d2fba3e65bd111bdea2baee4cf477b50726f54f02f926c93b346d9bb621f9926e92b4433069b9419d67947f12d4f987a02b5d1dc75f97598727c5a8b2fa5ca7394a01ed4cda05ed9b016b059446bc5f4a4201dd06b1b9d25640e932cb591358cb79fbdb4d1a33aaba2997e7802e7e4a698d88beae9134f443dd9e69fcf5efac3169b9356d9b0bd3d6dfce4716f41f17b9489a69da39347dbb0e725ad322dc2e6b68d990728349f26eaa1dcb4010c5b9c635ae516640847629e011471cb18902666663767adbb8fd36434071cc7aeef4599cdb50615e4aefb30b948cf9592b57a061e22769e0b4d852ebe82a73483df3a625f2c3c231c8b28e9c01836793d63f0dbdc943c6ed2f840ebfc2e2278532a7a1a5c6264a88198e1ddc5eb009634d384a016d86440111a10114d0fa8f126a5897023221ed983a4e300bba55e37d3087ba5eb7c9e6cb96026dd746ed4f27aeb6536a282bbb8d9308eb907449334b7a21037278827e72e5e0db9bedde177476e8d01f1c3344c626c13474cb6372f0f68ec1df057a17ea9df765d208afd1dd3519cc1aa5e5698c0a11e0bd32bb928e1cc629a03c7ea5759ec79cacaf92055140afdc64cde7d2afdb093918f51d1797817558ea455be81be28779f8bbe0b9995bdb6d60d9b5c5a75c4a1805cf86940dac02839b418db0e2fb28a4dc7165d798346dd277e54b7f5bbfa3154fd0ee552003bb8f46d9f576f3c9272b8f3cf2c81276fb6a5a459c14e4d501bcbf4fb79cb20148a6855d1a08af2da9f87344d1c7a7fa599b4743dcca9a1003546ccbdf271df82924102c0a641714a1042f0a08fc66b0bc1348e1462960dea010c7e4dd638f3dca61ab06330dd4ef60dfa8592374429f3fe159f8b5dfee2bd4ed01fcd266038f8184596dc7d62e2e21c4b33a4fcacbe1b28d25232fc5cdf31acac72367ae0629afe3e4ab03b57512c8af3ef4ced39250470ec747f1c4db117862a671bf579f5af3bbccefb941364eff3bac37c06b57bbcc2ea47fe21f85fa19dac8586ef118dc6a3ac8b4734b5699db3e6e694987fffa6114bf0d309456dfd6f5f5d1d5c64ac951144700ae8aa5521d6a0de79ea8f3be7a060cf84ddd5e2e554ee21082b1b502124202289eb2295f03a2f0eac590790ad210a8dfceadcbbdbebf929ddf1ae103057461c0db235192b630c9abe39d9fba57495129a1d9409bbbcac64baf173dfbd9cf2e42ec3d4cf9d459978fcff867e052deb899785a8427dec7734369d4da1d6dcc3c338c2b849c019ab06b6bfa9b13e74d07e6a1c1799ec013b32ebe72351dda46ee6d28fa5e0d7eb3cc5c7993b6ab3fb5c5eccef253c6b43c2f9aa630cae1cde88b5ef4a22b66280663ba4d149b3004113135e355cc7946b8defbdef716e5d241a6726b3ef999a0eaa0601aa57ce668dd38713aa53ed44ff943fd302bbf818545f0c4273eb1d099ce6a23a3695cbbc3b457bb8dc4de3af15c39ac063386b531e5a2bc75fdd2110c65e6b75dbfbe1087ff36d2dc2ed267495797370a49332eade7fad5f761cd18f8036d9aa40b2f42b7b6a54f03b42bcfe06d06cfe0d14747fd2cfe71e9eb745ec2ee5aebe135f9436f5ce8affb5319063bfde555281f0dd38fca4b5d757d435c51c208859771298e078167ec6604b59fd5108fa13ac7c8ede0de9bded970315a6436e1ccae943e0a2fbff29d05c6141e55d75a81f2b8bd63606112a28f2590360468ad5d208dcea1144c166fa7bb6c1c25210c061c9f5ff036808ec56ff5a6538595894729db33f9a523b8ca330b5bcbe7d690bcf2a46f6be439d4f5f4a5f52cf5e2897e35a0185c6a9ea4bcf02274cb0f9e499381180f0c444cdcccacf89c7a037e716d194c5d29b786f4a9577df86b1de8f31a5936b5eb41535ce84ed9d20bcb6be0d49ff422f54f8bb2265481cef45e572a124f69dc88f759030df02c95f96dfb11238d85afbba29cdbf3044df99e613a33cc464cfbd37f184c18b3484ff930d49ff0acfc3ac9806133c18d1d9ffe23745cd274419ba42158361f98a9f9ae0c3e80b2a5316059f3512471948a70cb1b3ec4e171045f3a79bd13e74c567ad09ffc2e3ec8433121f41222f431bbc4e967af8da16b545a9fda502ebf5fe97c44d73193d9253c419b3e96bfcb698f346807df0cb213af1e6dd31e263b9aea723c53be9d755f86f31c1d9e1924d14546411ca0519b988cf8a13cb3aeb5aa6b8f685026b983d4553bf4ca1b9a954716f4d74f7ffad3121f3a6ab4c37dd8b05cf041e9341b0bd66f04419c46f9a6c63bdff9ced2e8210587c9cc0f046b643a4e63996494cf264304ccf3e4b5b1a171e899a421f3043ab4c38572f762098241c57f2c6a57e80f84b5df8683c5be36990933fad6e9c37b16816f64ba276bb3469d113ccff9f1c7004550ad555ff082179441ce6507ca0df271e843af41cd7acb6eb73e4417a5f1ca97fff38810fa0423f34a5a773aa52507961836e3acebc8037a538fbc66763cb16eb6e98246e57946e8d15d3bed34139129eb2f7b0dca551ebea8d36025ce5a31ca69d3cea7306c96b0aa948d16ed34303291f35a117e3119c51b9c7ccf4779782f3d6bcb8b06797d0aadeae9a217bf6cbef81e8ef722ed8b58ffa35559e86df7ffa458da61871d4e8d201819f3da90383380c5b58ed6c97507a4e251fe1ae235d08c88b184533d66419da6219864edc83493369017943bc40fc2f3f0a3cbaf4edc679f7d8ab0e055d280e778c9bcb2094060f0517c1cc853fb012fb20e211804ca6b40fcd2e03f61543f6b8280101ef5d5caad5c7ee519f40c8a063e7d881e57fffc0a872602274e59d21a8cf557d29a55426f4db73ad42d2d68ab7d008aec9934757a7453188392baf4bbfe07e9529ec1868ce48356061eeb464a239fb49c32394b18ed413b7ea907edfa07ed78173ab4559d78a32c7b18de8d3578d4b4f2a3c3fbb0f8ae6f94cdd53443dd4e6887fbb0a284048cc96124ac95d06869f4d1c0543c2994ad2329a051441dc29801cac5286b476f63f04f5bd7bc81669de14f4b9841591304182f4cc8a431ab11aca11d222da7fd8491a265f00365139c0c54b57075417a69cc40ca1326c87edb3c4e5a83805f824481bbd2d6900ecda08ed0dd0565a19fc0ab836b23e591c31c55c963e691beabbda1513bfde28f7686975d904679ea483d6dc88f5669c39f2e9a5783a5e511eb548d468473c07cdd0b683cfbdbfb5f88192a4835c629a0cef2e6444cb78c5aeb1578a553ad0f7c30289f79083cd7f94c207fa292517b1a288b6b4379939619258171fc9d246d1ba1b78bee6028fd75fb87e69986f6ba9e2e0cad7b5a148a114e90bc454c69549838e754ce4ab2593214caa06814ce541f05640244018d984c2eb7152cd029fd3c1b3b0ba00f8fcc743e29686d266cc6e03cb711e0e6d06adb223f416abb69caadf38fc32469db40db28bae386d25f9733344f5dcf50cc8ade69516642cac5a6f60157674d392240803587b58fc3577e0471f508d5f6fb259866546781b6851dd65240f14001ad3b9c6b790b9f9267d6e020654deb07e179f8f18c29ca54f1e9789f72c02fc7323643b433e65320bfbcb3f083f03cfc20dc173f0b3f08f7c5cfda0fc27df1abf5433bdc87a5e599a898a394829279938179c84c144f889888de6cb7a369e61a373a4caa80d232e1fad61deb1114112f580cda04d919c49fa19db0c0ff36362c0b4cf95726c263138172f8ca70cc4f8a6881ecc63bc5b40eca62bd0d69956326658252406ba62e05f4f63c0564ae2a6f4b5340889299f1b4c140a2fdf5ecb7c002e3509430c244519c217999977965b326eb407ef194cf9f7918fda5f79c538634b68677df7df7f2f7657d0ac8bcad153034d4d3f86afd203c0f3f08c7ef3716421d3f2f3f08cfc30fc27df1f3f483f03cfc20dc17bf5a3fb4c37d58990981b298c51cdcba1d0204ab56c4bdf6daabfc6b0f65f3aa8a19533ce5b4034ab9dc9861d27a562ba0230f07a8d2504e669b7804075b8a1f121e150f5ba21f121e150f5ba21f121e150fabf1433bdc87a5edb7df7e1375a54c76febc4d611d68bd562ba2592b1b340e4fdd6ea1845e45b1f6f32b0fe54a9ecc801470e3c68d4501b75413748105668dd329215044370432abd905a448590766cd632d640d44eb2994b563942f238175a4cd1c26e842011758e0f4d8b0ac10654d18a5e1e71c463b03b3e5eea3bf14c76d0488925124b36094affdccafeb4bce01bd4b9835a0b49ec5669ea51f84e7ed07e1bef859fb4178de7e10ee8b9fb51f84e7ed07e1bef8d5faa11dee43e7e9bbcc948809e982adfb9cbed59f8bbd6642cf3323aa10122f6cfde7dccf6b5066d41c43445917586081ffc7d2b2a2f5aa2b13d40c661d689de85305763d73839ce2515a3360cc55a6acd751fc698c370e5cd2f52c4abbc0020b9c86b14a0814c8dace6c6676b43bea3fd029a3b01d504716b92de2fd4337d829666ec953d4cc980b2cb0c069284a582bc8283f5046e6a4a3078a45f96ccc5036f1d687c0ec3453ca9b991284e7ed07e179fb41782dfd203c6f3f08afa51f84e7ed07e179faa11deec3d2f2da6d58caff42e17150af0b13a7f285e9b9c00243d034ff07c40aa2f744cd89de0000000049454e44ae426082, 'A', '2017-08-01 00:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresaproveedor`
--

CREATE TABLE `empresaproveedor` (
  `idempresaproveedor` int(11) NOT NULL,
  `nombreEmpresa` varchar(50) NOT NULL,
  `nit` varchar(20) NOT NULL,
  `direccion` varchar(30) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `empresaproveedor`
--

INSERT INTO `empresaproveedor` (`idempresaproveedor`, `nombreEmpresa`, `nit`, `direccion`, `telefono`, `estado`) VALUES
(1, 'Pepsi', '15454', 'calle', '125488', 'A'),
(2, 'Pepsi3', '15454', 'calle', '125488', 'I'),
(3, 'Pepsi32', '15454', 'calle', '125488', 'A'),
(4, 'Pepsi323', '15454', 'calle', '125488', 'I'),
(5, 'Pepsi323', '15454', 'calle', '125488', 'A'),
(6, 'Pepsi', '15454', 'calle', '125488', 'A'),
(7, '123', '2222', '222', '222', 'I'),
(8, 'coca co', '19389489389', 'calle 101', '895782954', 'I'),
(9, 'coca34', '7897', 'calle', '1548465', 'I'),
(10, 'coca', '111454', 'calle', '45787', 'A'),
(11, 'eeee', '', '', '', 'I'),
(12, 'eee', '', '', '', 'I'),
(13, 'sss', '', '', '', 'I'),
(14, 'ee', '45', 'sss', '455', 'I');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horarioservice`
--

CREATE TABLE `horarioservice` (
  `idhorario` int(11) NOT NULL,
  `idTipoService` int(11) NOT NULL,
  `hora_desde` time NOT NULL,
  `hora_hasta` time NOT NULL,
  `estado` varchar(2) NOT NULL DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `horarioservice`
--

INSERT INTO `horarioservice` (`idhorario`, `idTipoService`, `hora_desde`, `hora_hasta`, `estado`) VALUES
(1, 4, '11:00:00', '15:00:00', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `huellas`
--

CREATE TABLE `huellas` (
  `idHuella` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `idSede` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `huella` longblob NOT NULL,
  `Estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `huellas`
--

INSERT INTO `huellas` (`idHuella`, `idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`, `huella`, `Estado`) VALUES
(1, 1, 'mherrera', 2, 1, 1, 0x00f88001c82ae3735cc0413709ab71f094145592659824d53c79251de046a680ed51793a2fceaab09937bd54dadcac4822856b8b5a8dc717afed60df1564a351d7ddbfada3c9dc39e455395ed60a0002dd683647e3996ff6e341cc7fe40a7e10a71934a7a8336cf4b655472b8465faed2184d58ea8983f1fba1211028d60304ff0be3167b4f4a05ccf4d2d2928c9940e3ac7d30146728d48b1be6e26f91ec73b9f6b38ae8061c970f480260cf21b946f9d3f00cd93108078acdbf9f1ea784723559fa2e6009d45dca7fa08c4b41c014932ed438f2cfe8f4494077879458e8561d91a99abfa52e99524c6e569fed8bad7c524b19d040a71aa9f9886185440775054a0d2f8520e96ad8de7f353665bb73d2dd7cb97bfa97041e9ae81dbd281a6b93ecee724bd16ffd6cddff5443e1f4ff27598c7b2f90789e9244db420c7470b01efc7d634813c52073572a2cf8eedb014862baaf7e81cdb16c6fbfe9d440dee83a86ef6ebe003867e2174d78da37f37c59ca92cb90f7a27b44feeea0ff8cffa5380adaea66f00f88001c82ae3735cc0413709ab71f090145592dd15badb8670892b0091a3e8a0840edb6eb1219c1a400f32874b18ce8f5187204a4120afc417f1d48c74671ba648497a781581c50221ad257e2c7dfc10352a4fa0bc759513a515266746e5f9a4620fada3b67aa7c7633f099328d31e6a8f52ab0a6055b6ddeb90e9f5ecea82a67b2b072445dc70610ce966a246deb79b538b1b55c2881eb7d4641b3b583cd7c8f05bf51ab7bc51c563bac414f76c0fa9bc0f10996b342fab9adf103521bbcae276b7c227c2a2357162830b85dc72add344b8b5cfb35418690df70991c1cef013496a49e6bf4df9ebdf39e47d880460661f0103e63c2817f5cb60a147f65ee4589ad869a3e30516b1d16f0561da0f2f95fda8f05995104e7e738e66482a0f8aa9a516c4b4dc9656032c8e2ca0c7891b4cb1a8cfdd4ee11a199992ef85ceb7552cae5174932cd760f0c6b14a136d5a26e9ced1c2c84ad772370c6202222284005bea1b8bbb659f20ca8629456f2c0811a0e82c1d9b38714bcc372a2d198aeb6d123d65966f00f87e01c82ae3735cc0413709ab71f0aa145592dc9585e5100863de3e3f521e86b95d0935c879458906d1a398f6019e7bee15c6c1cfea04c69e91861bffe6d53a84a8728e24ba2f754eec736562c996045e56c665b8f462fdab241a1459c45188f9d084d1dc8b72397e605bbf06c8e44d23aeaeb9d8ce6e895da9943414ea52e82a606af59590a351fc0377c3644ea6c70830d27b5862a776519106b7f10c80e5b5cd5a1f9acf3dc3e85ad72f355c14f3ba9e6d8fe75b716f9e420017c0ba618e9d8ba1d99f379cc4e388c9e79fe3f5bf6f5a4e52546e27e0ed0f47e35e8e7a03204d6999265146b630954f0cac851f4e95faf6d81c3f760eccc61f83627691f027712992eb0c407d074cde01cfbf49ae3dd9e9c69c4da43395874ba296673bef34c91ee6a4f54c39d7be323e8be6f7d3ef48fd1d2f660dcc001ad94d3b975bf5aabe02addbe02421e5fb5bd33b967e2c434579bf9bb8660c696f7a8d2bd501bfaf21023ec101646629806bf77f576b605bb9f3b6f825aeaf40619711cdc05875936f00e88001c82ae3735cc0413709ab7170981455924989750d3647f6acbbc707abb9e682753b1ee86e46eebbb137d1dbb86c24e90ba3355c806b20d2c37b520ba4832ecc80f59863b629dbd693d1bbaf5af9c0b11901a9991053549c7d7e9bc95c46a6b994680fce68853cc1c59b4ef303b476f6fde60d611ce0da1f0a602796624d434528ec3de54d9f01fbf3779f7be7fe695211b0a627ce086eaa3ae9330be5b7a011f9f030a8ada2b839b072e634c96296ab2ab5f04e409fc64a88c758a076e224de5353bf5c1bde2f50678b5e9259f98d036c62b51d89e4175680cd09688441832d9fb74518097e3cd6ea6cbc00868d243f6ae6cb67e934f96c37436db3f87ec2fe187d7f51a8c66f1900d761d1e5922f6d9192afe756c8aaa0912dcdd742257e20c6aa069230f1ef7b79a4ebc1181fd5f3bf15d49b0f8f856d8e34c6fd4e61f0b8772d834eb13dd041ad20e7bddda106e08d25dd1799f5f3b2092f11f06dec10ee2a488fd91f287a6f0f30b86a3576cbd59103f39d9fd9f3f9de2833c5f74b840b906f000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 'A'),
(2, 2, 'fdsaf', 2, 1, 2, 0x00f88001c82ae3735cc0413709ab71b09d1455928f7f0498a949801c78da8a0ed65c570d929c5024691cd15fa1955a642948c99576c5dd280bb6db76f945f91703a1733d81f25c8274586c3d1dde351179bab01c1b44227750c9937e5cbdc9f759651c1190da01d0f99492ae1711bf84dbd5dd4205d45b75f9454c7497eb894f50ed0edaae025729a8d9058965e1a581ecd7cbbd4eeb166c646542dfc49f36d7bda5e1ead62771a57b26c76a9b6cc9c3857c549c91d42affd0cdfb68c1ef283274ddcbd8dba16b9e034965997233fec0bd2184086ba8820cbbf8af33e9aecf82172da5dfd7ea82f3716778c1103615315c31e11fe949afa7888e8d73f07b63b0208980cdf92b6299bafe1d74ce8d7c4bd5e4fef8464d1ae8eb16653b543f8a8575eeeed8eff66d880368631da6829022711ae118115f45209997f5310d026c859cf6d5680d9412c0d85a1e8207146a4fc183359f96a294574bfcccff14255eb0f9beeb71eadfa44426b9d28bda625eb7a6ec89765375fe8d4afdafa7aa1c3dbb9f88e99d6f00f87f01c82ae3735cc0413709ab71f08414559276c875bd0cacfbe91c24117cd5a52c080f8a09346dd972bf7ee692ebf4977fe01c9540ed96af8803eb394b9423a6e67f9d7199323a519a8321a85c1ed24ca3d62468125f8df41396fba5c8966f8aa8b7d84bf4f77e72cb0ddf9b922cc83cc1ffaedc412443cd1a6c754fb8dc46a13fcd60816927e6e902da70fd972bc37a2960662c1b8a6c88b2e114e7ffec39f305f491a76ec9533e95b0470ed7b9535ef2770a5a0a7ab81156dead4a7eff1e40933d2a3d30c44ea4c2515d96178a7218daba309f0fe32e745e900acb29dd4d17b06846c377143eefe2381fd1da076d4a865ec207208c735581bdc210300fa2db00d57cd564dda675f8d81bb5a20c3175d20ecfe98806f59d25959fd86c1b817cc9b643121f0f455ee253884aef8d3334ef77d287cba1bf8e4ad7cde67ea1bb70de4b8117bbb0a50884b9e360b9d6bc7bff2de9b4574f331979de96c82677f64885117f5413c340ac0046831965c078b800d852a859bd8e78df7557cfc6f3fd451b6f00f88101c82ae3735cc0413709ab71f08814559232e73974b9eba611db5b090c72dcd66673fdafd22cfcf2b9dff4e77a492520abbf60319af1d34bd9c6a1dc80e8f795f135418cc232db1912b9e6498bbd3c87ab8865d98379745e9a873093c69d3c41badf5ab92422a585c7bf81c751bf8d9e89579ef5a4082d5492680d3f29a40ed42c3abea25a56cf2e2475854d1a97f875305bddff51161341d17f3a5525e4d141ec42039308395fd2dd9e5e9c56a717eb68a1a4b2e8abac89f8997b2e7edd162dca673ceaf44555232c082123b7d2a26b52e65dbd51206ac56a32803c1c539de2c5cf0d17fc4203e102d27a306bc199db229a5dab3b34d5aa4ab3a3295982ab04da492e98ec70858bb618c6303f08069d5239f8758b7a48579aeb95d0b311e65db446ec1b71b4aa79eb9fee5d5ed851ef5649969a3a5aa43e5779c39471b3bec2a3380a62b9b002a4f67980636296f2d3708e265a7ec5d9a52ecd8347966c848fa3f832032f34c14464ff16656a494f2f226f5b4d845a954f40b1b0873eb7916020eb6f00e87f01c82ae3735cc0413709ab71f08614559294f98412a17a3ced0290e084cf78ba6ce07ec1f40ed2986eac08f598a14bb4a2700095837d178efac33c27610b5dc6c7700d9d623fc4a0447470b7b0ea526ae37590871fcdb4ab2b604a12414cd9863676285a528b1789f6287eb19dc43b784da963112d39f22f9b820eec4f519b96b17eea7618df53dad9159ac04b5c54eb2fc0d6cd73a44011b4c9f644572962cc764591b19c9b13cbdb3fd2ff31ca9161ebc71c9ebd33aeb6f5f9fa9a9e2ba41fc0554a7e7e4288e39cb8c30c7d10d10fe33dc8207b44d6658ae439386a450d8a1cb31173ef027427eca2d9c34c6046f53e530cd42f639f4b49f2536fb093d3642f41b1b4fb26664d2548bcc8ff859666d68a4842b505c84a3c3794c9818947f08e3eac10601a3fee67aa9503fbf52758a366ff9ec4b4409b95497ee7071c1262020a36fa269966f1cc067428b1ac0f72125fc0cb1ed7c5414237d61cf903fdd93b87658b8c8250c711a4b7f618e29cdbf911c35688df1ad503872708da0205ab6f0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 'A'),
(3, 3, 'Gym', 1, 1, 3, 0x00f88001c82ae3735cc0413709ab71f091145592210071ed2fad5a9a4717b2178bd6561762912e1320ff46e06eb6d87638b83d2d1133ace20ece6670d4df7d9d9f7942ec99c700b63ac20031c016d157262a161f357874d01e9a8d90ea243fbd5864f193f00c2f872a418ccbe735a6882b653ff3fdd7745b9fe2b3fb138996099ff1daf1b41c2e30f163036e6008bcb040983084b3d5ee63ff002345830607fa6c3a57aac01b36cbffee5bfb5952cea0d9afaf9e768d7e9ba838ebbdd3563e16179744c995069d97b3063ed270772eb2798578dbabda9f58b4a2ac952c3fd6beb6b86526327600b6ad3bb96c49339fa8d57e52ab103561ac27784ccafa39a005a8721fa7a507970677036104a3a1fe09f73695c6460a4edd888639bb46338461da4cfd95e9cbca6f306f913a92f4a19defbd6e1f053f20766254a1b552f4f3d517f76daaa7cff7e2497b3127ca4953cb4294cef42978579d3479051e854828d4a129bb01f32327079f971c9ddeb11a051b1872910be244512c714960b9787228f24c15786f00f87f01c82ae3735cc0413709ab7170901455921b93a849b66a677002963a19d7368a16253b8028da4a6022c9aac43655cf78a431616a89b0fa9dbfa916521b7eaa13ff4f47c07aa4b38e805483e21e87fc47154e9d686be7429b60e62f1eb567799b3e80e24afc2b80992fe67476a8e8eea5b1c3e96b93e58401d54bd1cb815c93d3bba62abe2658b13d7f48013650ac4230d2e146f7641764b5b2b4acc5621cd31f284797306fb5ecb89b7f821df0e0936c4cceb3a60a0ada2076ae1168344f679fa4439a57b9d1f12e75084ee4fd7d4f5d4a9c7987adebf1d6ece365c9ab8587ba7a5046b57a79da80346c13cd3fc1e8e7402be299029648b82a02778f1e685b0011d88f13bc301b2fc8234051e277c118a6d1942f5a11d6890dbdfd804f844869ab237305da5793ec23603d80abf0a6d7d399b5c9158639d2b637c2a89781590b630c1a423821905095c4241dba23b6de14a09aba5a08fdc5a86c09a9e7b0234982cd9267d5c9cc7c61b6411c2115be6781f8a464a84ae3584e7ac54e7c9819f66f00f88001c82ae3735cc0413709ab7130af1455920f703e3d33e15d56aa17ac502bc2b4fb01656e3338db49befcebf882ce854b09154335e159d990f3799a21718eab27802359ca18b33ecb20c1e807cefec8d25176309adf4d3eedd3acea9acf678704b6474757b4b996f5072d09b6738a421cee007a8c9efc968c4b5b1fb5b5824107b557f5a95154710a9f3fa9d309c4e3c202d45b5b90193714b80feed018c64ef4c8cd3cdf2f31051ca4a7ed11017e4bc840cbc9b0345e95817208fe419382d81edf6e83503be557e2555b56d22996f962cbafab47bfac877ec941d1f864adcd80fc10a1f29af1b8df07cab12547affceb4dedda20998cc76c8009ee2aad2b3fe9c5f2f1eb996065ab9b28bdb9655bd55a5085ad2a92291ca57709ca1df8078df715b25bd83a11bf792b983c9a4488ceef841ed56c1653b3b9c87778b044b153383e0becb56a37463311c3a1a7e236507a70c7208ec2498863c1ba341445ace76acbc36193b923ef986aea7ca08b28fdb987335a32f98e98a4d59713e06b5e2ca3396f00e88001c82ae3735cc0413709ab71709f145592295ab66488e2f03208f1f298d56b2518b87500ef66d8067e4f8a49fe20446d5a137a7bbdbf04463cac3e81e567454108e3fe8c8c9896382f973d3bea9e26fab77f3b162c021883bc761db7914799d00ba65a61fdfacdd9ae7bd91e227b497ced028500f03787b38a7196031e3402d8efebf7f3d9163dd7d33037016655b0c15732dc0247fa6b0a7f19fdbadb6a445bc5c160296da6f6b64745035f3a9cdda9ceb76b4b60b1216c0a11b37edc7b20d4aac34d164459b7cd97b1e61eaeff833a10785b7f1e070bb2ea4035c19259c34841188d1882710e60d187f28c4b4d7a2b948bd3c48f07cc60da7010d036dfebb87fce97714028ca7496e88dec29db34d227fd3209e7381d01c2b8d1371cce72bd6063928215fefa7cddc9f986727eb5479f818f4627f302e575934b0430aed56e3f2c5b00b9beae2c44218c4f6e12c7226c5cd756b6330de622bdcb33c5f20c5777087410f0f14c87cd351b5ea87c5f20f66e7424cc61a07c3a9b62f2a07aed38046f0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `iva`
--

CREATE TABLE `iva` (
  `idIva` decimal(10,0) NOT NULL,
  `descripcion` varchar(10) NOT NULL,
  `porcentaje` int(11) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `iva`
--

INSERT INTO `iva` (`idIva`, `descripcion`, `porcentaje`, `estado`) VALUES
('1', 'NO APLICA', 0, 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `log_errores`
--

CREATE TABLE `log_errores` (
  `id` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `error` varchar(250) NOT NULL,
  `causa` varchar(100) NOT NULL,
  `linea_archivo` varchar(100) NOT NULL,
  `notificar` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medidas`
--

CREATE TABLE `medidas` (
  `idMedida` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `idSede` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `FechaMedida` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `medidas`
--

INSERT INTO `medidas` (`idMedida`, `idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`, `FechaMedida`) VALUES
(3, 3, 'Gym', 1, 1, 3, '2017-10-19 00:00:00'),
(4, 3, 'Gym', 1, 1, 3, '2017-11-19 00:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medidaxmusculo`
--

CREATE TABLE `medidaxmusculo` (
  `idMedidaXMusculo` int(11) NOT NULL,
  `IdMusculo` int(11) NOT NULL,
  `idMedida` int(11) NOT NULL,
  `Descripcion` varchar(60) DEFAULT NULL,
  `Medida` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `medidaxmusculo`
--

INSERT INTO `medidaxmusculo` (`idMedidaXMusculo`, `IdMusculo`, `idMedida`, `Descripcion`, `Medida`) VALUES
(3, 14, 3, '', 43),
(4, 13, 3, '', 85),
(5, 14, 4, '', 50),
(6, 13, 4, '', 90);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `menus`
--

CREATE TABLE `menus` (
  `id_menu` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `menus`
--

INSERT INTO `menus` (`id_menu`, `nombre`) VALUES
(1, 'Gestión de Usuarios'),
(2, 'Módulos'),
(3, 'Ingresos'),
(4, 'Configuración');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `menus_usuarios`
--

CREATE TABLE `menus_usuarios` (
  `id_submenu` int(11) NOT NULL,
  `id_menu` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `menus_usuarios`
--

INSERT INTO `menus_usuarios` (`id_submenu`, `id_menu`, `idUsuario`) VALUES
(1, 1, 1),
(2, 1, 1),
(3, 1, 1),
(4, 2, 1),
(6, 4, 1),
(7, 4, 1),
(8, 4, 1),
(9, 4, 1),
(10, 4, 1),
(11, 4, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `musculos`
--

CREATE TABLE `musculos` (
  `IdMusculo` int(11) NOT NULL,
  `Descripcion` varchar(60) NOT NULL,
  `Estado` varchar(2) NOT NULL,
  `tipo` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `musculos`
--

INSERT INTO `musculos` (`IdMusculo`, `Descripcion`, `Estado`, `tipo`) VALUES
(1, 'Trapecios', 'A', 'R'),
(2, 'Deltoides (Hombros)', 'A', 'R'),
(3, 'Espalda', 'A', 'R'),
(4, 'Bicpes', 'A', 'R'),
(5, 'Triceps', 'A', 'R'),
(6, 'Antebrazos', 'A', 'R'),
(7, 'Pectorales', 'A', 'R'),
(8, 'Serratos', 'A', 'R'),
(9, 'Abdominales', 'A', 'R'),
(10, 'Oblicuos (Abdominal)', 'A', 'R'),
(11, 'Caderas', 'A', 'R'),
(12, 'Gluteos', 'A', 'R'),
(13, 'Cuadriceps', 'A', 'R'),
(14, 'Aductores', 'A', 'R'),
(15, 'Femorales', 'A', 'R'),
(16, 'Vasto Interno', 'A', 'R'),
(17, 'Vasto Externo', 'A', 'R'),
(18, 'Pantorrillas', 'A', 'R'),
(19, 'Pierna', 'A', 'M');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `numeradores`
--

CREATE TABLE `numeradores` (
  `tipoNumerador` varchar(20) NOT NULL,
  `rango_inicial` int(11) NOT NULL,
  `rango_final` int(11) NOT NULL,
  `valor` int(10) NOT NULL,
  `Incremento` int(11) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `numeradores`
--

INSERT INTO `numeradores` (`tipoNumerador`, `rango_inicial`, `rango_final`, `valor`, `Incremento`, `estado`) VALUES
('Factura', 1, 1000, 42, 1, 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `numeradorespendientes`
--

CREATE TABLE `numeradorespendientes` (
  `tipoNumerador` varchar(12) NOT NULL,
  `secuencia` int(11) NOT NULL,
  `estado` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `numeradorespendientes`
--

INSERT INTO `numeradorespendientes` (`tipoNumerador`, `secuencia`, `estado`) VALUES
('Factura', 3, 'P'),
('Factura', 4, 'P'),
('Factura', 5, 'P'),
('Factura', 6, 'P'),
('Factura', 7, 'P'),
('Factura', 8, 'P'),
('Factura', 9, 'P'),
('Factura', 10, 'P'),
('Factura', 11, 'P'),
('Factura', 12, 'P'),
('Factura', 13, 'P'),
('Factura', 14, 'P'),
('Factura', 15, 'P'),
('Factura', 16, 'P'),
('Factura', 17, 'P'),
('Factura', 18, 'P'),
('Factura', 19, 'P'),
('Factura', 20, 'P'),
('Factura', 21, 'P'),
('Factura', 22, 'P'),
('Factura', 23, 'P'),
('Factura', 24, 'P'),
('Factura', 25, 'P'),
('Factura', 26, 'P'),
('Factura', 27, 'P'),
('Factura', 28, 'P'),
('Factura', 29, 'P'),
('Factura', 30, 'P'),
('Factura', 31, 'P'),
('Factura', 32, 'P'),
('Factura', 33, 'P'),
('Factura', 34, 'P'),
('Factura', 35, 'P'),
('Factura', 36, 'P'),
('Factura', 37, 'P'),
('Factura', 38, 'P'),
('Factura', 39, 'P'),
('Factura', 40, 'P'),
('Factura', 41, 'P'),
('Factura', 42, 'P');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagoservice`
--

CREATE TABLE `pagoservice` (
  `idPago` int(11) NOT NULL,
  `idUsuarioCliente` int(11) NOT NULL,
  `usuarioCliente` varchar(15) NOT NULL,
  `idSedeCliente` int(11) NOT NULL,
  `idempresaCliente` int(11) NOT NULL,
  `idPersonaCliente` int(11) NOT NULL,
  `idCaja` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `idSede` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `idTipoService` int(11) NOT NULL,
  `idTipoPago` int(11) NOT NULL,
  `FechaPago` datetime NOT NULL,
  `ValorPagado` double NOT NULL,
  `Devolucion` double NOT NULL,
  `ValorNeto` double NOT NULL,
  `PorcentajeDescuento` int(11) NOT NULL,
  `ValorDescuento` double NOT NULL,
  `ValorTotal` double NOT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechaFinal` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `pagoservice`
--

INSERT INTO `pagoservice` (`idPago`, `idUsuarioCliente`, `usuarioCliente`, `idSedeCliente`, `idempresaCliente`, `idPersonaCliente`, `idCaja`, `idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`, `idTipoService`, `idTipoPago`, `FechaPago`, `ValorPagado`, `Devolucion`, `ValorNeto`, `PorcentajeDescuento`, `ValorDescuento`, `ValorTotal`, `fechaInicio`, `fechaFinal`) VALUES
(1, 1, 'mherrera', 2, 1, 1, 4, 1, 'mherrera', 2, 1, 1, 3, 3, '2017-09-13 00:00:00', 20000, 0, 20000, 0, 400, 19600, '2017-09-03', NULL),
(2, 1, 'mherrera', 2, 1, 1, 4, 1, 'mherrera', 2, 1, 1, 1, 1, '2017-09-13 00:00:00', 3000, 0, 3000, 0, 0, 3000, '2017-09-03', NULL),
(4, 2, 'fdsaf', 2, 1, 2, 4, 1, 'mherrera', 2, 1, 1, 4, 1, '2017-09-04 12:00:00', 25000, 0, 25000, 0, 0, 25000, '2017-10-22', '2017-11-20'),
(5, 3, 'Gym', 1, 1, 3, 4, 1, 'mherrera', 2, 1, 1, 1, 1, '2017-09-13 12:00:00', 3000, 0, 3000, 0, 0, 3000, '2017-09-13', '2017-09-13'),
(6, 3, 'Gym', 1, 1, 3, 4, 1, 'mherrera', 2, 1, 1, 3, 1, '2017-09-13 12:00:00', 3000, 0, 3000, 0, 0, 3000, '2017-09-13', '2017-08-01'),
(7, 3, 'Gym', 1, 1, 3, 4, 1, 'mherrera', 2, 1, 1, 2, 2, '2017-09-13 00:00:00', 35000, 0, 35000, 0, 0, 35000, '2017-09-13', '2017-10-12'),
(8, 3, 'Gym', 1, 1, 3, 4, 1, 'mherrera', 2, 1, 1, 2, 2, '2017-09-13 12:00:00', 35000, 0, 35000, 0, 350, 34650, '2017-09-13', '2017-10-31'),
(9, 1, 'mherrera', 2, 1, 1, 4, 1, 'mherrera', 2, 1, 1, 1, 1, '2017-09-16 00:00:00', 3000, 0, 3000, 0, 0, 3000, '2017-09-16', '2017-09-16'),
(11, 2, 'fdsaf', 2, 1, 2, 7, 1, 'mherrera', 2, 1, 1, 4, 1, '2017-09-04 12:00:00', 25000, 0, 25000, 0, 0, 25000, '2017-10-04', '2017-11-04');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfiles`
--

CREATE TABLE `perfiles` (
  `id_perfil` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `estado` varchar(2) NOT NULL,
  `create_at` datetime NOT NULL,
  `update_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `perfiles`
--

INSERT INTO `perfiles` (`id_perfil`, `nombre`, `estado`, `create_at`, `update_at`) VALUES
(1, 'Listar', 'A', '2018-01-24 00:00:00', '2018-01-24 00:00:00'),
(2, 'Guardar', 'A', '2018-01-24 00:00:00', '2018-01-24 00:00:00'),
(3, 'Editar', 'A', '2018-01-24 00:00:00', '2018-01-24 00:00:00'),
(4, 'Eliminar', 'A', '2018-01-24 00:00:00', '2018-01-24 00:00:00'),
(5, 'Buscar', 'A', '2018-01-24 00:00:00', '2018-01-24 00:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfiles_x_rol`
--

CREATE TABLE `perfiles_x_rol` (
  `id` int(11) NOT NULL,
  `id_perfil` int(11) NOT NULL,
  `id_rol` int(11) NOT NULL,
  `estado` varchar(2) NOT NULL,
  `create_at` datetime NOT NULL,
  `update_at` datetime NOT NULL,
  `create_by` int(11) NOT NULL,
  `update_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `perfiles_x_rol`
--

INSERT INTO `perfiles_x_rol` (`id`, `id_perfil`, `id_rol`, `estado`, `create_at`, `update_at`, `create_by`, `update_by`) VALUES
(1, 1, 1, 'A', '2018-01-24 00:00:00', '2018-01-24 00:00:00', 1, 1),
(2, 2, 1, 'A', '2018-01-24 00:00:00', '2018-01-24 00:00:00', 1, 1),
(3, 3, 1, 'A', '2018-01-24 00:00:00', '2018-01-24 00:00:00', 1, 1),
(4, 4, 1, 'A', '2018-01-24 00:00:00', '2018-01-24 00:00:00', 1, 1),
(5, 5, 1, 'A', '2018-01-24 00:00:00', '2018-01-24 00:00:00', 1, 1),
(9, 1, 3, 'A', '2018-02-02 09:02:17', '2018-02-02 09:02:17', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `idPersona` int(11) NOT NULL,
  `Documento` varchar(60) NOT NULL,
  `idTipoDocumento` int(11) NOT NULL,
  `Nombre` varchar(60) NOT NULL,
  `Apellidos` varchar(60) NOT NULL,
  `NombreCompleto` varchar(100) NOT NULL,
  `direccion` varchar(60) NOT NULL,
  `Telefono` varchar(60) NOT NULL,
  `Sexo` varchar(60) NOT NULL,
  `FechaNacimiento` date DEFAULT NULL,
  `correo` varchar(60) NOT NULL,
  `Estado` varchar(2) NOT NULL,
  `foto` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`idPersona`, `Documento`, `idTipoDocumento`, `Nombre`, `Apellidos`, `NombreCompleto`, `direccion`, `Telefono`, `Sexo`, `FechaNacimiento`, `correo`, `Estado`, `foto`) VALUES
(1, '1113626301', 1, 'Mauricio', 'Herrera Isaac', 'Mauricio Herrera Isaac', 'Calle 67 # 26-42', '3158467777', 'Masculino', '1987-03-31', 'mherrera10isaac@outlook.es', 'A', 0xffd8ffe1146c45786966000049492a00080000000e000f01020006000000b6000000100102000e000000bc0000001201030001000000010000001a01050001000000ca0000001b01050001000000d2000000280103000100000002000000310102001c000000da0000003201020014000000f60000003e010500020000000a0100003f010500060000001a01000011020500030000004a01000013020300010000000200000069870400010000006401000025880400010000004c0400006004000043616e6f6e0043616e6f6e20454f53203430440000093d001027000000093d001027000041646f62652050686f746f73686f70204353352057696e646f777300323031363a30333a31312031353a31303a30330039010000e803000049010000e803000040000000640000002100000064000000150000006400000047000000640000000f0000006400000006000000640000002b010000e80300004b020000e803000072000000e803000000001e009a82050001000000d20200009d82050001000000da0200002288030001000000010000002788030001000000900100000090070004000000303232310390020014000000e20200000490020014000000f602000001910700040000000102030001920a00010000000a03000002920500010000001203000004920a00010000001a0300000792030001000000020000000992030001000000100000000a920500010000002203000086920700080100002a03000090920200030000003837000091920200030000003837000092920200030000003837000000a00700040000003031303001a0030001000000ffff000002a00400010000005802000003a0040001000000580200000ea2050001000000320400000fa20500010000003a04000010a20300010000000200000001a40300010000000000000002a40300010000000100000003a40300010000000100000006a40300010000000000000000a5050001000000420400000000000001000000640000000800000001000000323031363a30333a31312031353a30333a333900323031363a30333a31312031353a30333a33390000a0060000000100000006000000010000000000010000003e0000000100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000f82a006c030000c0af1c0047020000160000000a0000000000010000000100040000000202000000000000000006000301030001000000060000001a01050001000000ae0400001b01050001000000b60400002801030001000000020000000102040001000000be0400000202040001000000a60f00000000000048000000010000004800000001000000ffd8ffed000c41646f62655f434d0002ffee000e41646f626500648000000001ffdb0084000c08080809080c09090c110b0a0b11150f0c0c0f1518131315131318110c0c0c0c0c0c110c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c010d0b0b0d0e0d100e0e10140e0e0e14140e0e0e0e14110c0c0c0c0c11110c0c0c0c0c0c110c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0cffc000110800a000a003012200021101031101ffdd0004000affc4013f0000010501010101010100000000000000030001020405060708090a0b0100010501010101010100000000000000010002030405060708090a0b1000010401030204020507060805030c33010002110304211231054151611322718132061491a1b14223241552c16233347282d14307259253f0e1f163733516a2b283264493546445c2a3743617d255e265f2b384c3d375e3f3462794a485b495c4d4e4f4a5b5c5d5e5f55666768696a6b6c6d6e6f637475767778797a7b7c7d7e7f711000202010204040304050607070605350100021103213112044151617122130532819114a1b14223c152d1f0332462e1728292435315637334f1250616a2b283072635c2d2449354a317644555367465e2f2b384c3d375e3f34694a485b495c4d4e4f4a5b5c5d5e5f55666768696a6b6c6d6e6f62737475767778797a7b7c7ffda000c03010002110311003f00f554924925292492494a4924925292492494a4924925292492494a4924925292492494a4924925292492494fffd0f554924925292492494a4924925292492494a4932164666262343f2efaf1daf706b0daf6b012746b5a5e5bb9ce4949925075b535c18e7b5ae2090d240303e91853494a4924925292492494a4924925292492494fffd1f554924925292492494a49249252972ff5cfebd60fd5aa3d1a8372faad9fcd620746d044faf94e1fcd54dffb72ff00f07fe12da85f5f3eba0fabf40c2c383d5326b758c7182da6b1ecfb43daefa6f73fd98f5ffa4fa7fe8aef15ea195f6cca73defb322e7b89b2cb0cb9fbb5ddb9dfcbde924074aefad5d573b3bd5ead94fbf79d5a480c0092e6edaebfd17a7fd556fa865d1998da52d25a09c57b8b8c76b693be7d5fa3f46dfd27fd058b474ccfc8dc69a3754049691044771fbae564f4fcf80db1be9545c5db4f62d21bbb5fe4eed89ba2fd7b20ab3a8adc18f71ad900020bc9020fe8c73fa2f7399eeffd28ba6e95f5c3aae3d1b2aead7fa0c80d04b2c7344e8e69baa732c635df4d8eff00cf2b90b68351df756e68fcd047d28fdeb255bfb6b99edaaa0f6edd5ae692f0e8e353fbbfd7ad1aec8f37d8feabfd77aba85ace9fd4aea4e6d84fa3650d7b18f6ff0083f5196eef46cb7fc1fbfd1b6cfd1ff39e8d76f5abe79c3eab43a01736ab6b6935ba082d73bd96ed71fa5bfdbed5e9dfe2f3eb8bba8b7f62e7da72332804d393a936307d2aefdfef6e451fbcff00e7abff0085f5125a4757b84924914292492494a49249253fffd2f554924925292492494a49255fa8da29e9f957198ae9b1e639f6b5ced1253e01f597ac3bacf5cccea4ef77da9f38adff0082aff438ad13fc96b6c7ff00c22d3faa7d0719cdb1d9b4bbed3598732d69da5a4075658efa2efe5ac7e8b8ff006acfa1cf3b8b18d0df01038fecaf49c461dbaebe65459655a06c6285fa8fd1b181d3f16a6b76304470adbfa7e1dc0b2cac3a7cb894d408f8ab8d1f25185e5e33adfd47f5775b89164f3439d00f9b37fd0b17219bd0ba95158c6b31afaeb6b8ecf501807cb96ffdb6bd89da8fee552ddb6b3512d74113ff00914ee32169803d1f0dc8a6dc5b4d6f04388fce1120ff005d6efd5eeb95f4eeafd3f3f1d9b6dc67b3d52e31bda41a2f6eeff84a2c72effaa74dc1c9a1d4e4e3b2eacf21c248f3adff00ce56efe53179c75be8d8f839e29a27d271966e33a1fcd79524720969d58e58cc75e8fd180c807c53ae73fc5ff55bbaa7d57c6baefa749751ba665b59db57e73ddb9b56c63f7bf7ef5d1a7b1292492494a49249253fffd3f554924925292492494a59bf5972bec9f577a9e4830eab12e734ff002856ed9ff49692e53ebbf5166461e6fd5ca6b16656561bac6cbf64025c2b35b76bbd5dafa902401aa631323405be53f53eab1f9965af6c3296807c374c6dff00a3b97a1e0b4bda08e1723f566834f481646ae7b9fe64e8cd7fcd5a74f50c9a5ffcfb18eec2d3007c9bbdca09eb32dac62a01ebababbca36d3a2e6b1faf753658d65f5e3dac7986baab353f01646e5bd8d96db981cdd27b1e5214a36d8da210acacf7eca199d42bc566fb346f972b0acfacf9765fe9e1f4ebb239f713b27cfde8e8a00b7fa8086195c47d6ca36d98f60d05a1ed9ecd747b3fce5d065756cdb00175229ec596020ff9c3dab2beb354eb7a3bdfcfa6f6904f6f76cffbf211d26113f94bde7f8b1a455f5330cea1f6bef7bc1f1f5ad67fdf1754b9bff1754be8fa9bd3996b76d8e63ec20f3b6cb2cbaa7ffd72a7b1eba4561aa54924924a524924929fffd4f554924925292492494a5ca7d6dc46bf32ac9208b6b60b29b472d3597976dfdedee7d6dd9f9ebab589f589e6bb31ac2ddc06e8f8cd6ee7fb29b907a59309a98fabc5f40c66b31c536b0b5cddc5f59e439ce73dcc77f537235fd0317edadc9750322b2d2d752f3a6b2dd3fcef6a162d8fc7ea7914bf47079204ce875fed2deaad0e0141d6db15a11d36703a5fd4fe99814e46d65d7bee01ac75e4454c6bbd46b2bf4f735deffcf7ff00db4b53058ec6b7638c82263531f0ddeedbfd657ed7065649fcd5428b37dae777f04646caa200143661d4d8fcb7fa6c311a98244f96e6fb9adfdfd9ef58dd63ea8b72b1f1c615bf64b5bb9b941e1ce0fdc43bd46ec1fa3bab8d9fd4ff000ab73786e44f72b44fd113c470947436a90b14f334f40f4f281c7b6c6e2358d069b09782f036bad635e5fe93acfa4e631375dc21fb232e9acfb8b439b3c68e695d13ec6341ecb17a83eac8b0635bfcdda431e263da48dc975b456807d1d2fa9d564bba9d6e637d0e9f8d8c6aa591efb0fe8bf4d73bf359fb95aedd61fd5cada1f7b9a7706b2b6877c9dbbfe8b6b5b8a5c62a2c398dcfc800a492493d894924924a7fffd5f554924925292492494a553aa60fdbb11d4021af90e638891b87ef7f59bed56d2488b48346c3e73f58ba4e6f4dc9a33ac0c1eb4b36b1c5c25807d37b9acfe75bee6ffc5ab183782c0471d96ffd766b0f44f51c24d5754e69f025de97fd4d8b95c57434b5bcf21419051d1b38e4643574f36c71a0ec697910763624c1fa2d9859d8dd42ea725cece6d54d2e04d041703a7d2af21b635bb2d6ff0021de9bd3df9d563eb6fa90392dadeef90f4da53d7d5fa6dd5965d55a180877e929791238fcd7fb93416400d6c86bea37645aed98cf751640a2e124bb5fa6e66ddb555fbb63ec5b86d1b6265645bd5f15e67d0c8637b3cd4e831fc91fa4dbfd856abb03d80b663c1c0b4fdce84ad4456e298e664ec6924aa9d3a9baecafb47a6f70612039ac73c6f23db5fb43bdfb5dbd1723639ce73b860d3e2575df55f19b8fd16870d5d9137bcf9d9ab7fcdabd3627423c4c739f0ead9e918470f0dac78db6d877d8398274dba7ee3035aaea4929c0ad1ac4d9b3d549249248524924929fffd6f554924925292492494a4924925391f5aeb16f42bd8782eabff3ed6b80c4bcd79068b0c595e847783f45dfda85d9fd6beaac1e9f45a1a2ccac969c8b476ae8a7f4ceb3fe32d753b2867fc6ff00a3f7f27d6301ef68cba3f9d035f30143977fa3630fcbf56fb1e1c751a9e514d35d844076e1ddae2dfc8b1307aa8d1b7e84684f7f9ad6ab3f1e243c495180ca4d26f49a34d67f95a9fbd35966d6b8f828bba8634497011c958f9fd503816d7a876823977f55221176b64e5bdee6e3d5ad96124ff25a3e93cff557a5f4860afa4e15638663d407c98d5e77d2ba5dbe93ac7b7764e490c681fcaf6b6b6ff2355dc741eb15667da3a7bc0af37a6bcd3756387307b68c9a67fc15f58ddff04ffd1ffc2592e2eac39ba3ae9249295854924924a524924929ffd7f5549249252924ce735ad2e710d681249d000172dd7bebd61e183474b2dccc9e0dba9a59f17b76faeefe454fff00aea4a7a3cccdc4c1a0e4665cca296f2fb1c1a27f744fd277f2172dd47fc6474ea03fec38f6e516c86d8ffd1309fcddbbe6ff00fc058b85cdea59bd4f27ed19973af76b0e7f0d1fb94b06d655ff005b6aab71981f87f727529e8fea6646467fd62bfa865bfd5c97d76d8fb08ef35d21a07e6d6caff46caffd1ae93a874f141f60fd059fcdf803cfa27ff45ff21737f5021bd4ac69fcec77c7c9f5b9778d657915d945a3731da11f91cdfdd7353670e21f92f84f84f875785cae995bdee8f6ba790a89e8d92e32c78f9ea1755d4302cc6b435facff00376701c07fdfff0079881e8b889833f8aaa4106b66d0208bdde6ace939956b63da001d876fed39cad74ee9cd367ab67b88e2569dd43c8982e7762780b4ba374af54cbbf9a67f38ef13cfa6cffbfa42249a519002cb67a5626dafed4f11a16d00f81f6bedff00d175ae67ad750bfa47d6e7e7619db686d7ea34c86bc398c1653647e6d9b19fd47fe91775910d6c34406f0070005e6df5a5c5ff0058330feeb98dff0036bad5b8c444535252e236fa6745eb785d67146462ba1c205b4bbe9b1dfbaf1ff50ffa0f5a0bc46ab2ca5e1f5b9d5d8c3ec7b496b87c1edf72e83a77d79ebb861adb9edcca80fa3768f8f2b99eeddff19ea254b5f4e49739d2febcf46cddacc871c1b8e916fd09f2bc7b3feddf49744d735ed0e610e6b84870d4107c1052e924924a7fffd0ee3a8fd73e8382e35fac72ae6f35e38df1fd6b65b437fedd5ce67ff8c3ea1702cc0c7662b78f56c3eabffb0c1b2a6bbfedf5c88769a0f8a69274eddca7525b79bd533fa8b8bb3722cc81ced7bbd80ff26966da59fd8ad537c9d3868e53ea1bfc1313c01f87de8a976c0103b21903ca113907f2288673e7f0494ee7d4b706f5a63477aad1ff00477ff05dfe29993e26579cfd587fa7d6b180ff00086caffcf639a17a1facdc7c775a5a5fb47b5839738fd060feb24549b3598d6d428bc6e36fd068d1d23fc234fe66cfdf587918f9184fd8ff00734fd0b0681c3ff25fc85af8b45c1a6ec987df6ff39a4003b56cfe4354c337b0d76b0bab77d2696e9cfe6ffe494738097815f0998f8871b130acceb0fa8e2ca2bd6c77793f46b67f296cb6d66286d7e99af1868cb22037fe347fe8dff3d361555d78eea1bef35d8f1649efcb37ff00d68b119ec1634d6fd43b423b2508f08f13baa73e23e0364592752df1d1798f5bb05bd633ececec878f934fa5ff00a2d7a4b5be996d6e322a3b493fba3564ff00d6b6af2cb6c375d6dddee7becf93dc6c522c46089efaf7f8279ee34fefee9b58d4f79e13c4f7ede5ca4a5e635f1fc42b781d5ba974d76ec1c9b28133b1a6587fad53b754eff315206444691227c531d07605253d8e0ff8c7ea15c373b1abc868fcfa89addfe6bbd463bff025bf83f5f3eaf6510cb2d7623cc08bdbb449ff008567a94ff9ef5e5e3c476ec9c9fbfe48521fffd9ffed186050686f746f73686f7020332e30003842494d04040000000000271c015a00031b25471c0200000292741c0237000832303136303331311c023c0006313530333339003842494d04250000000000101a11d1689470e4c1b2464afe348409ce3842494d043a0000000000bb000000100000000100000000000b7072696e744f7574707574000000040000000050737453626f6f6c0100000000496e7465656e756d00000000496e746500000000496d67200000000f7072696e745369787465656e426974626f6f6c000000000b7072696e7465724e616d655445585400000023005c005c004d004f00530054005200410044004f0052002d00500043005c004b004f00440041004b002000360030003500200044006f00630075006d0065006e0074006f0000003842494d043b0000000001b200000010000000010000000000127072696e744f75747075744f7074696f6e7300000012000000004370746e626f6f6c0000000000436c6272626f6f6c00000000005267734d626f6f6c000000000043726e43626f6f6c0000000000436e7443626f6f6c00000000004c626c73626f6f6c00000000004e677476626f6f6c0000000000456d6c44626f6f6c0000000000496e7472626f6f6c000000000042636b674f626a630000000100000000000052474243000000030000000052642020646f7562406fe000000000000000000047726e20646f7562406fe0000000000000000000426c2020646f7562406fe000000000000000000042726454556e744623526c74000000000000000000000000426c6420556e744623526c7400000000000000000000000052736c74556e74462350786c40790000000000000000000a766563746f7244617461626f6f6c010000000050675073656e756d00000000506750730000000050675043000000004c656674556e744623526c74000000000000000000000000546f7020556e744623526c7400000000000000000000000053636c20556e74462350726340590000000000003842494d03ed000000000010019000000001000201900000000100023842494d042600000000000e000000000000000000003f8000003842494d040d000000000004ffffffc43842494d04190000000000040000001e3842494d03f3000000000009000000000000000001003842494d271000000000000a000100000000000000023842494d03f5000000000048002f66660001006c66660006000000000001002f6666000100a1999a0006000000000001003200000001005a00000006000000000001003500000001002d000000060000000000013842494d03f80000000000700000ffffffffffffffffffffffffffffffffffffffffffff03e800000000ffffffffffffffffffffffffffffffffffffffffffff03e800000000ffffffffffffffffffffffffffffffffffffffffffff03e800000000ffffffffffffffffffffffffffffffffffffffffffff03e800003842494d04080000000000240000000100000240000002400000000400000ed50100003c0a01000025800000004a9b013842494d041e000000000004000000003842494d041a000000000361000000060000000000000000000002580000025800000016006d006100750072006900630069006f002000680065007200720065007200610020003700390036003000310000000100000000000000000000000000000000000000010000000000000000000002580000025800000000000000000000000000000000010000000000000000000000000000000000000010000000010000000000006e756c6c0000000200000006626f756e64734f626a6300000001000000000000526374310000000400000000546f70206c6f6e6700000000000000004c6566746c6f6e67000000000000000042746f6d6c6f6e670000025800000000526768746c6f6e670000025800000006736c69636573566c4c73000000014f626a6300000001000000000005736c6963650000001200000007736c69636549446c6f6e67000000000000000767726f757049446c6f6e6700000000000000066f726967696e656e756d0000000c45536c6963654f726967696e0000000d6175746f47656e6572617465640000000054797065656e756d0000000a45536c6963655479706500000000496d672000000006626f756e64734f626a6300000001000000000000526374310000000400000000546f70206c6f6e6700000000000000004c6566746c6f6e67000000000000000042746f6d6c6f6e670000025800000000526768746c6f6e67000002580000000375726c54455854000000010000000000006e756c6c54455854000000010000000000004d7367655445585400000001000000000006616c74546167544558540000000100000000000e63656c6c54657874497348544d4c626f6f6c010000000863656c6c546578745445585400000001000000000009686f727a416c69676e656e756d0000000f45536c696365486f727a416c69676e0000000764656661756c740000000976657274416c69676e656e756d0000000f45536c69636556657274416c69676e0000000764656661756c740000000b6267436f6c6f7254797065656e756d0000001145536c6963654247436f6c6f7254797065000000004e6f6e6500000009746f704f75747365746c6f6e67000000000000000a6c6566744f75747365746c6f6e67000000000000000c626f74746f6d4f75747365746c6f6e67000000000000000b72696768744f75747365746c6f6e6700000000003842494d042800000000000c000000023ff00000000000003842494d041100000000000101003842494d0414000000000004000000023842494d040c000000000fc200000001000000a0000000a0000001e000012c0000000fa600180001ffd8ffed000c41646f62655f434d0002ffee000e41646f626500648000000001ffdb0084000c08080809080c09090c110b0a0b11150f0c0c0f1518131315131318110c0c0c0c0c0c110c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c010d0b0b0d0e0d100e0e10140e0e0e14140e0e0e0e14110c0c0c0c0c11110c0c0c0c0c0c110c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0cffc000110800a000a003012200021101031101ffdd0004000affc4013f0000010501010101010100000000000000030001020405060708090a0b0100010501010101010100000000000000010002030405060708090a0b1000010401030204020507060805030c33010002110304211231054151611322718132061491a1b14223241552c16233347282d14307259253f0e1f163733516a2b283264493546445c2a3743617d255e265f2b384c3d375e3f3462794a485b495c4d4e4f4a5b5c5d5e5f55666768696a6b6c6d6e6f637475767778797a7b7c7d7e7f711000202010204040304050607070605350100021103213112044151617122130532819114a1b14223c152d1f0332462e1728292435315637334f1250616a2b283072635c2d2449354a317644555367465e2f2b384c3d375e3f34694a485b495c4d4e4f4a5b5c5d5e5f55666768696a6b6c6d6e6f62737475767778797a7b7c7ffda000c03010002110311003f00f554924925292492494a4924925292492494a4924925292492494a4924925292492494a4924925292492494fffd0f554924925292492494a4924925292492494a4932164666262343f2efaf1daf706b0daf6b012746b5a5e5bb9ce4949925075b535c18e7b5ae2090d240303e91853494a4924925292492494a4924925292492494fffd1f554924925292492494a49249252972ff5cfebd60fd5aa3d1a8372faad9fcd620746d044faf94e1fcd54dffb72ff00f07fe12da85f5f3eba0fabf40c2c383d5326b758c7182da6b1ecfb43daefa6f73fd98f5ffa4fa7fe8aef15ea195f6cca73defb322e7b89b2cb0cb9fbb5ddb9dfcbde924074aefad5d573b3bd5ead94fbf79d5a480c0092e6edaebfd17a7fd556fa865d1998da52d25a09c57b8b8c76b693be7d5fa3f46dfd27fd058b474ccfc8dc69a3754049691044771fbae564f4fcf80db1be9545c5db4f62d21bbb5fe4eed89ba2fd7b20ab3a8adc18f71ad900020bc9020fe8c73fa2f7399eeffd28ba6e95f5c3aae3d1b2aead7fa0c80d04b2c7344e8e69baa732c635df4d8eff00cf2b90b68351df756e68fcd047d28fdeb255bfb6b99edaaa0f6edd5ae692f0e8e353fbbfd7ad1aec8f37d8feabfd77aba85ace9fd4aea4e6d84fa3650d7b18f6ff0083f5196eef46cb7fc1fbfd1b6cfd1ff39e8d76f5abe79c3eab43a01736ab6b6935ba082d73bd96ed71fa5bfdbed5e9dfe2f3eb8bba8b7f62e7da72332804d393a936307d2aefdfef6e451fbcff00e7abff0085f5125a4757b84924914292492494a49249253fffd2f554924925292492494a49255fa8da29e9f957198ae9b1e639f6b5ced1253e01f597ac3bacf5cccea4ef77da9f38adff0082aff438ad13fc96b6c7ff00c22d3faa7d0719cdb1d9b4bbed3598732d69da5a4075658efa2efe5ac7e8b8ff006acfa1cf3b8b18d0df01038fecaf49c461dbaebe65459655a06c6285fa8fd1b181d3f16a6b76304470adbfa7e1dc0b2cac3a7cb894d408f8ab8d1f25185e5e33adfd47f5775b89164f3439d00f9b37fd0b17219bd0ba95158c6b31afaeb6b8ecf501807cb96ffdb6bd89da8fee552ddb6b3512d74113ff00914ee32169803d1f0dc8a6dc5b4d6f04388fce1120ff005d6efd5eeb95f4eeafd3f3f1d9b6dc67b3d52e31bda41a2f6eeff84a2c72effaa74dc1c9a1d4e4e3b2eacf21c248f3adff00ce56efe53179c75be8d8f839e29a27d271966e33a1fcd79524720969d58e58cc75e8fd180c807c53ae73fc5ff55bbaa7d57c6baefa749751ba665b59db57e73ddb9b56c63f7bf7ef5d1a7b1292492494a49249253fffd3f554924925292492494a59bf5972bec9f577a9e4830eab12e734ff002856ed9ff49692e53ebbf5166461e6fd5ca6b16656561bac6cbf64025c2b35b76bbd5dafa902401aa631323405be53f53eab1f9965af6c3296807c374c6dff00a3b97a1e0b4bda08e1723f566834f481646ae7b9fe64e8cd7fcd5a74f50c9a5ffcfb18eec2d3007c9bbdca09eb32dac62a01ebababbca36d3a2e6b1faf753658d65f5e3dac7986baab353f01646e5bd8d96db981cdd27b1e5214a36d8da210acacf7eca199d42bc566fb346f972b0acfacf9765fe9e1f4ebb239f713b27cfde8e8a00b7fa8086195c47d6ca36d98f60d05a1ed9ecd747b3fce5d065756cdb00175229ec596020ff9c3dab2beb354eb7a3bdfcfa6f6904f6f76cffbf211d26113f94bde7f8b1a455f5330cea1f6bef7bc1f1f5ad67fdf1754b9bff1754be8fa9bd3996b76d8e63ec20f3b6cb2cbaa7ffd72a7b1eba4561aa54924924a524924929fffd4f554924925292492494a5ca7d6dc46bf32ac9208b6b60b29b472d3597976dfdedee7d6dd9f9ebab589f589e6bb31ac2ddc06e8f8cd6ee7fb29b907a59309a98fabc5f40c66b31c536b0b5cddc5f59e439ce73dcc77f537235fd0317edadc9750322b2d2d752f3a6b2dd3fcef6a162d8fc7ea7914bf47079204ce875fed2deaad0e0141d6db15a11d36703a5fd4fe99814e46d65d7bee01ac75e4454c6bbd46b2bf4f735deffcf7ff00db4b53058ec6b7638c82263531f0ddeedbfd657ed7065649fcd5428b37dae777f04646caa200143661d4d8fcb7fa6c311a98244f96e6fb9adfdfd9ef58dd63ea8b72b1f1c615bf64b5bb9b941e1ce0fdc43bd46ec1fa3bab8d9fd4ff000ab73786e44f72b44fd113c470947436a90b14f334f40f4f281c7b6c6e2358d069b09782f036bad635e5fe93acfa4e631375dc21fb232e9acfb8b439b3c68e695d13ec6341ecb17a83eac8b0635bfcdda431e263da48dc975b456807d1d2fa9d564bba9d6e637d0e9f8d8c6aa591efb0fe8bf4d73bf359fb95aedd61fd5cada1f7b9a7706b2b6877c9dbbfe8b6b5b8a5c62a2c398dcfc800a492493d894924924a7fffd5f554924925292492494a553aa60fdbb11d4021af90e638891b87ef7f59bed56d2488b48346c3e73f58ba4e6f4dc9a33ac0c1eb4b36b1c5c25807d37b9acfe75bee6ffc5ab183782c0471d96ffd766b0f44f51c24d5754e69f025de97fd4d8b95c57434b5bcf21419051d1b38e4643574f36c71a0ec697910763624c1fa2d9859d8dd42ea725cece6d54d2e04d041703a7d2af21b635bb2d6ff0021de9bd3df9d563eb6fa90392dadeef90f4da53d7d5fa6dd5965d55a180877e929791238fcd7fb93416400d6c86bea37645aed98cf751640a2e124bb5fa6e66ddb555fbb63ec5b86d1b6265645bd5f15e67d0c8637b3cd4e831fc91fa4dbfd856abb03d80b663c1c0b4fdce84ad4456e298e664ec6924aa9d3a9baecafb47a6f70612039ac73c6f23db5fb43bdfb5dbd1723639ce73b860d3e2575df55f19b8fd16870d5d9137bcf9d9ab7fcdabd3627423c4c739f0ead9e918470f0dac78db6d877d8398274dba7ee3035aaea4929c0ad1ac4d9b3d549249248524924929fffd6f554924925292492494a4924925391f5aeb16f42bd8782eabff3ed6b80c4bcd79068b0c595e847783f45dfda85d9fd6beaac1e9f45a1a2ccac969c8b476ae8a7f4ceb3fe32d753b2867fc6ff00a3f7f27d6301ef68cba3f9d035f30143977fa3630fcbf56fb1e1c751a9e514d35d844076e1ddae2dfc8b1307aa8d1b7e84684f7f9ad6ab3f1e243c495180ca4d26f49a34d67f95a9fbd35966d6b8f828bba8634497011c958f9fd503816d7a876823977f55221176b64e5bdee6e3d5ad96124ff25a3e93cff557a5f4860afa4e15638663d407c98d5e77d2ba5dbe93ac7b7764e490c681fcaf6b6b6ff2355dc741eb15667da3a7bc0af37a6bcd3756387307b68c9a67fc15f58ddff04ffd1ffc2592e2eac39ba3ae9249295854924924a524924929ffd7f5549249252924ce735ad2e710d681249d000172dd7bebd61e183474b2dccc9e0dba9a59f17b76faeefe454fff00aea4a7a3cccdc4c1a0e4665cca296f2fb1c1a27f744fd277f2172dd47fc6474ea03fec38f6e516c86d8ffd1309fcddbbe6ff00fc058b85cdea59bd4f27ed19973af76b0e7f0d1fb94b06d655ff005b6aab71981f87f727529e8fea6646467fd62bfa865bfd5c97d76d8fb08ef35d21a07e6d6caff46caffd1ae93a874f141f60fd059fcdf803cfa27ff45ff21737f5021bd4ac69fcec77c7c9f5b9778d657915d945a3731da11f91cdfdd7353670e21f92f84f84f875785cae995bdee8f6ba790a89e8d92e32c78f9ea1755d4302cc6b435facff00376701c07fdfff0079881e8b889833f8aaa4106b66d0208bdde6ace939956b63da001d876fed39cad74ee9cd367ab67b88e2569dd43c8982e7762780b4ba374af54cbbf9a67f38ef13cfa6cffbfa42249a519002cb67a5626dafed4f11a16d00f81f6bedff00d175ae67ad750bfa47d6e7e7619db686d7ea34c86bc398c1653647e6d9b19fd47fe91775910d6c34406f0070005e6df5a5c5ff0058330feeb98dff0036bad5b8c444535252e236fa6745eb785d67146462ba1c205b4bbe9b1dfbaf1ff50ffa0f5a0bc46ab2ca5e1f5b9d5d8c3ec7b496b87c1edf72e83a77d79ebb861adb9edcca80fa3768f8f2b99eeddff19ea254b5f4e49739d2febcf46cddacc871c1b8e916fd09f2bc7b3feddf49744d735ed0e610e6b84870d4107c1052e924924a7fffd0ee3a8fd73e8382e35fac72ae6f35e38df1fd6b65b437fedd5ce67ff8c3ea1702cc0c7662b78f56c3eabffb0c1b2a6bbfedf5c88769a0f8a69274eddca7525b79bd533fa8b8bb3722cc81ced7bbd80ff26966da59fd8ad537c9d3868e53ea1bfc1313c01f87de8a976c0103b21903ca113907f2288673e7f0494ee7d4b706f5a63477aad1ff00477ff05dfe29993e26579cfd587fa7d6b180ff00086caffcf639a17a1facdc7c775a5a5fb47b5839738fd060feb24549b3598d6d428bc6e36fd068d1d23fc234fe66cfdf587918f9184fd8ff00734fd0b0681c3ff25fc85af8b45c1a6ec987df6ff39a4003b56cfe4354c337b0d76b0bab77d2696e9cfe6ffe494738097815f0998f8871b130acceb0fa8e2ca2bd6c77793f46b67f296cb6d66286d7e99af1868cb22037fe347fe8dff3d361555d78eea1bef35d8f1649efcb37ff00d68b119ec1634d6fd43b423b2508f08f13baa73e23e0364592752df1d1798f5bb05bd633ececec878f934fa5ff00a2d7a4b5be996d6e322a3b493fba3564ff00d6b6af2cb6c375d6dddee7becf93dc6c522c46089efaf7f8279ee34fefee9b58d4f79e13c4f7ede5ca4a5e635f1fc42b781d5ba974d76ec1c9b28133b1a6587fad53b754eff315206444691227c531d07605253d8e0ff8c7ea15c373b1abc868fcfa89addfe6bbd463bff025bf83f5f3eaf6510cb2d7623cc08bdbb449ff008567a94ff9ef5e5e3c476ec9c9fbfe48521fffd93842494d042100000000005500000001010000000f00410064006f00620065002000500068006f0074006f00730068006f00700000001300410064006f00620065002000500068006f0074006f00730068006f0070002000430053003500000001003842494d04060000000000070008000000010100ffe10da3687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f003c3f787061636b657420626567696e3d22efbbbf222069643d2257354d304d7043656869487a7265537a4e54637a6b633964223f3e203c783a786d706d65746120786d6c6e733a783d2261646f62653a6e733a6d6574612f2220783a786d70746b3d2241646f626520584d5020436f726520352e302d633036302036312e3133343737372c20323031302f30322f31322d31373a33323a30302020202020202020223e203c7264663a52444620786d6c6e733a7264663d22687474703a2f2f7777772e77332e6f72672f313939392f30322f32322d7264662d73796e7461782d6e7323223e203c7264663a4465736372697074696f6e207264663a61626f75743d222220786d6c6e733a786d703d22687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f2220786d6c6e733a64633d22687474703a2f2f7075726c2e6f72672f64632f656c656d656e74732f312e312f2220786d6c6e733a70686f746f73686f703d22687474703a2f2f6e732e61646f62652e636f6d2f70686f746f73686f702f312e302f2220786d6c6e733a786d704d4d3d22687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f6d6d2f2220786d6c6e733a73744576743d22687474703a2f2f6e732e61646f62652e636f6d2f7861702f312e302f73547970652f5265736f757263654576656e74232220786d703a4d6f64696679446174653d22323031362d30332d31315431353a31303a30332d30353a30302220786d703a437265617465446174653d22323031362d30332d31315431353a30333a33392e38372220786d703a4d65746164617461446174653d22323031362d30332d31315431353a31303a30332d30353a3030222064633a666f726d61743d22696d6167652f6a706567222070686f746f73686f703a436f6c6f724d6f64653d2233222070686f746f73686f703a44617465437265617465643d22323031362d30332d31315431353a30333a33392e3038372220786d704d4d3a496e7374616e636549443d22786d702e6969643a31303944423330424332453745353131423932384631383332423642313944392220786d704d4d3a446f63756d656e7449443d22786d702e6469643a30463944423330424332453745353131423932384631383332423642313944392220786d704d4d3a4f726967696e616c446f63756d656e7449443d22786d702e6469643a3046394442333042433245374535313142393238463138333242364231394439223e203c786d704d4d3a486973746f72793e203c7264663a5365713e203c7264663a6c692073744576743a616374696f6e3d2263726561746564222073744576743a696e7374616e636549443d22786d702e6969643a3046394442333042433245374535313142393238463138333242364231394439222073744576743a7768656e3d22323031362d30332d31315431353a30333a33392e3837222073744576743a736f6674776172654167656e743d2241646f62652050686f746f73686f70204353352057696e646f7773222f3e203c7264663a6c692073744576743a616374696f6e3d227361766564222073744576743a696e7374616e636549443d22786d702e6969643a3130394442333042433245374535313142393238463138333242364231394439222073744576743a7768656e3d22323031362d30332d31315431353a31303a30332d30353a3030222073744576743a736f6674776172654167656e743d2241646f62652050686f746f73686f70204353352057696e646f7773222073744576743a6368616e6765643d222f222f3e203c2f7264663a5365713e203c2f786d704d4d3a486973746f72793e203c2f7264663a4465736372697074696f6e3e203c2f7264663a5244463e203c2f783a786d706d6574613e2020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020203c3f787061636b657420656e643d2277223f3effee000e41646f626500644000000001ffdb008400010101010101010101010101010101010101010101010101010101010101010101010101010101010101010202020202020202020202030303030303030303030101010101010101010101020201020203030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303ffc00011080258025803011100021101031101ffdd0004004bffc401a20000000602030100000000000000000000070806050409030a0201000b0100000603010101000000000000000000060504030702080109000a0b1000020103040103030203030302060975010203041105120621071322000831144132231509514216612433175271811862912543a1b1f02634720a19c1d13527e1533682f192a24454734546374763285556571ab2c2d2e2f2648374938465a3b3c3d3e3293866f3752a393a48494a58595a6768696a767778797a85868788898a9495969798999aa4a5a6a7a8a9aab4b5b6b7b8b9bac4c5c6c7c8c9cad4d5d6d7d8d9dae4e5e6e7e8e9eaf4f5f6f7f8f9fa110002010302040403050404040606056d010203110421120531060022134151073261147108428123911552a162163309b124c1d14372f017e18234259253186344f1a2b226351954364564270a7383934674c2d2e2f255657556378485a3b3c3d3e3f3291a94a4b4c4d4e4f495a5b5c5d5e5f52847576638768696a6b6c6d6e6f667778797a7b7c7d7e7f7485868788898a8b8c8d8e8f839495969798999a9b9c9d9e9f92a3a4a5a6a7a8a9aaabacadaeafaffda000c03010002110311003f00dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd0dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd1dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd2dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd3dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd4dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd5dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd6dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd7dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd0dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd1dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd2dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd3dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd42c857478fa596aa5496458d49d10a6b91ac0b5956e2e6c3dfbaf0eb3d3ca668219591a332c6ae51c6974d401d2cbf8617e7dfbaf759bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd4dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd30e629292b66a24ab69ad14a5e18924748669dbf4acfa2dab4aa920136b9f7eeb60f1e9fbdfbad75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7fffd5dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf7506bb23498f457aa9e188b9b46924aa924adfea6243ea91bfc00f7eebd43e43a0d327ddfd6d82a98e973bb8a9b0f2cb278e36ae0c90b37f84e81e3e2dcf3c7e7db6d2a21018d3a756191c5556bd414f913d1cf4f3d57fa52d9890531b54492e6a961f09b81fba923abc7c9fc81eebf5101afea8c756fa5b8ff7cb7ece95b5fbe7671c7d2e446eac0ae365682a23c9ae4e925a3788a24e009e29594349048187fb41bfb7752d2ba853a6f43d48d06bd07ddc1f273a33a236757ef8eccec6db985c263a1a2965f0d7d3d7e46a7f8955474340b438ba4965adab6abac95634d0a46a3c900122924d1c60966ff3f568e096560a886a7aa52ef1ff00852cfc11ea3cfd1623014bbd7b2e929aa562dd95db7a921a5970085de96458282b3fca32353495ea2399008d51583862a41f65b36ec91b058ede493d683a328b6896452cf3227a54f47fbe29ff0036af82ff002f3138c9fae7bcb6862b7555c286bf606f1cad36d8dd58ba8750e216a2cc3518af4d245a4a7322588bdafedf8773b398e8f182cbfc2d83fcfa4f3edb77077188b47e45720fece8ddee0f915d47b6b318bc465375d1a7f15c94d87832f134726df8b290434f51250d4e68cab431d4086a91b4866e0ff507dac1229a50d47af97493c27a13a694ff00570e8a56f1fe695f12b09b8325b5f11db9b32a6b3112d4c393cad6e563a7c4472523b453414150468afa9322108a197500cc2ea396fea22a9ab800799e9f5b39c853a09af90e3f9fa743647f353e3cd160f279ccdf676dfa2a1c06dd8374e7ab8bbbd163b053d341529956aa8d65864a0759d17c82c15d82b5891eee6784233993b00c9f2e9bfa790be803b89a0e87aebeed0d81dabb671bbbfaf77661775edccba834197c357415b493395d662f244c424ca3fb2d627f17f7b8e449543c6e197e5d5248de27d12210de9d2f56f6f55aff00d45ec7fc6df83eefd37d77efdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebfffd6dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75d03727fc3fdb1febfedadefdd7baad6f9a9fccc7a57e28cd2f5d63a7c97657c81c950fdc60baa36463bf8ee6e92062a0e67719f3d263f0788a68c991a5aa9e24b2f24037f68a6bd8e393c08fbe7a5683c87a9f203a5d6f632cc86661a601e6715f90f5eb574f99ffce6fe4a41b9aab0b89cef55f582d64f908259a5ec5c7f61eee8e99500a7966c66d79ea30fb4eb04a018d15da5457d241b12525ccf3052be2a2d7f87b8fed181d1adb5a5b551882d4ce7b47d99e23a2132ff0030bf903dc84e0a8fb8f18bbae5944b8aa6a7ac0e32b5532411cf43485e66c7509a9abb6890ac2632e6398153ac13497728012791b50e0470e8f63b185ab2411003cf3c074938bba3e4851d436eda8a6cabe7e2a9c65266eaa9e590acbfc6679a8e8e9727836fbea168bcd130959249e9d1ac1825c7b4b34334b0bb4739d47ece96c4c88eaaf1f60ff563a359d57dd7b9f76c7ba36767771efeeaf78a85733b724db998c8d4e17255ae13179ac4d36dfc9e558e4725490557dec3474ae639e8199174b2a4bee96cd7312234fae8381078fda0f56961b5b863e0c219bcc1c1fb7ece80bf9ad4fda1b836dc1365317bab35bb258a4feef66764e723a7c276b61b0910a5a9cd750d0e625fb9c96f4db6f69b3bb5a6fb7ae78dd9a3d765706f09795d6a3517f84d7fd441f9744642446620e900d48a574fdbf2f4ea968d641264e93315d8f7cd6d3cfe298d36fa8646abcdc155494d0e332d066a92aa1a5923583251397c6d444b55402311b39285ca921a367d15d23041e23aa0d0caa64a54e45387fb1d07b90dbfd97b3b7fed5cbedfcf47b8f075b2cb91c75453e492218d5929964fb795e23164311531d4523831bfd63b1b9f758edadaf3536a0ecbe47c88f974c99ae6d981f854d6943d189ebbf98df216b36d56ec387b8f78e4baf71593adcbe1a9721b82a677c467b274b32e46ae8e579bcf39c6d0c122c4ceeaec1a35b9f7586d42b49a1d963af0a9a57ce83ab78ab369692252deb415fcfa8b49ddfbbba976defda4ceec0c8760536e5c46ddc9d66f4ad86b6baa3aeb093564df7193c4e2a44a9c757cdb8ab25871d3d5cc3cb452de22b1170e57849dd4b4488f1d299e23ece9339804c0176520fe47f3ff000742e6dcf9c3da9b63aeb3db27686eaca633aafb0b686168b7c635e92b69e5a7c7e3eb96b1a8b258cc9ad65661d7f89d104aa4a4a8349346a8583467482f91260dd9269523b9706bd2a6f0182931558703e7f6fcfa397f167f9b06eeea8a7cfed3da9b9cedbcd0db9f6d83ab8f295f064b6fe5bf8cd26761dcd55b7313549b6f74edca7a159e102921568229788974dc6e193c179bf4821a60e7f6fa74d4f119d62553a9d7883e9e83ada5fa0bfe140dd1395c1a63f7be4ceefca4d2d30c765b0cf498f8a3801f0d453645eb5e212d54ba03d3dc2492b49a180b6af6b7f79c51a82c750a56a3fcbd170db25989ecd0de87353f2e8f9f5a7f39df80fd879ccf6d9aded5aaebdceedba4a6accac5bff6de73058a48ea693ef523833ed432e2a59c45c686910bb709abddd375b367119665622a2a0807ec3c3a6e4da6f5143840cb5f220ff2e857e91fe663f11bbfb7352607affb636d56d266a30bb73315d5d1e1a972596892496af6d3ae5cd1b479f829d04e2252e1e275b10fe9f6a45d4242b160118d013804fa7dbd246b5990b2943ac0a91e607af47f2391254496274923911648e48d83a488e0323a3a92ac8ca6e08e08f6a7a4fd73f7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ffd7dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd639a410c524a416f1a33695fab6904d87f89b7fadefdd7bad713f997ff003dcea1f8c3b7f7c6c1eb6dc78fcff68d27f12c54b3e16b20ac8f6dd62c2d4a8bf7d13494d2d6c35970563d4e197ea2c7da19ae0c81a381b3e67d3f3f5e8cedec49d2f28c7a7af5f3b6f921f28bb57b6b7e6e9df3bab746e2aec8ef69ea26affb5cbd4ae4eb51ea15f4d75789d6adbcba81092101945b4e9f6845a47a4a93527893c4fdbd1d19c8007041c07a745b53794d8cc9a8876cd7d6d3e3a68a2fbd13d44b58c2355fbb68aa10986e27624b162d716f6ea44b1a008287fd5c7a6bc757928ca48e8d9f5354ec4ec1cc5224b93c4fdfe4523862dd74d5a71d9bc46e0995a7871b988ad1d2d5cd5261d0e1c433024347231e3db333a2694b880156e27a5f101a87812904790ead23a4fb7eafaaab76fd3e4ea65df0997adaea3c0e32a7302972f4b1453aa66b0d24f4a6ba8f70d309a8ccb1d3d5c10487c6844aaf66f651b808c443e96aae0d7edfd9fe5e97c4b33be999ff004c8e23cbe7ff0015d18eee0ec4adcd63f0af062f1b23364315b9f1fbeb1d478fc7d7d46470b5b34b86a3cbe2b06e275c862e1ac14b2d7412c5352cac04b1cb09215a85aee7448a7552869c68187f9e9d5d2de28649ae2298f68f5e27f3cd0fece91bd73f23f6bee2d85b8f69771534b9dc6ed8dc35799c6e0e8e89f0dbdf0f9739286783b7364e4660d0d46e4c3940b308489c317765314b228560bda90a2a7ca9f67f97a6e556b960f1e90dc6bc7ed14e8a3fcc0cd6dcec8addd59cdaf1e0a97b3128a8e5cad7ed4c5c5b6b0dbfe4a6f2418ade9b8f1f4a63a64cb67e8e536ac8d6cd915a882a0bfecb054b742e2425c90c69c707fd9e8b24b76543a54023fe2f1d55aec7efdc8d162b3381aedb54f9ca8c552d4bd5e53214ed2575445474792c764308f353683433e3e49a1961948672f1a9e50dbd98183c15f12351a99b8fcbfd59f97452b70d2ccc9239f0c0e1e43a70cbd5ed5c4e176fd0e34e5ff84ee5a5a1aea449f1b4145b8e7aaada68ea6be6ac8e091a999a3f1245118810f05d8d8b585dd59145573e9d2a8ca50956c74216dfee9c4ecc155b6e9f7dc581c4e520abdbd955345595d54d49b8e9531db8116900c8255c55b408b0b061a030d7a432abaa2f1270fd8ada6a71f68e9c305b94632d0b9fdbf2fe7d2a30b4357499492bb6c6fc97f86e2630d5b165f6de7044fb74de1a5ae796295a49b175f7f1bd4c02a29d598aca57e9efc551e8aae5651915e9e58e534a814a79fa74bdabe9cd993ccbbe317be639f766169a872953458adb94798a6a08414892ba9aab1d410c751474e348aa67816a93510f1b7a98bf20d71b957a914e19ff002754d023751e4c48ce3a81f67b4faf7292e4366ef1aaa2a0cdd7412fdbe7315938f6dcf512c714d2356cc609976d492662475a56150c34005a0407843756893c41e2aac94c9a7ed14e9eb491d6502440501f5cff00b3d1e6dadb97796fd836f576f9c26586270f5749593c7b7851e4711944f1454d4d2d16e6c4d4d4439afb68e953c7475a4bc139631a82cd762159ed8012b6a5e20e08affabd7a54df4f33c8611492943feaff000f429f61fc76ec9ed2eafdbf47d1b91ae4a3ea2dfdba37ce0a9f259caada1bd76a5567f1d454790a95897eda2a6abab5a68516a232d6994c761abdae3359dd46619542b6aad09fcb1e9d14cb6cf6d39963726438e8ee7c5eff008504fc83f85db3e3f8d9bf7ab32fd8dbff001189dbf4f49b87b1b72d765689b2a6a4c536427cc51d6d4ccd4192c2f8428558a386a11b5d8deea834f0a325aa02b4edd4d5e1c73d239e1824910dc3e963c6829fcbadbcfe057f314e9ff9a7d7981ada4dd3b5313db4d14b4bba7aea9ea6b683238ecd5144b2e4e8a8b1b9d8e9b2750b45abd44070e96742c86fed5c13f88aa24a2cde63233f2af1e8bae2dcc4cc52a62ae0ff00c57562bed4f497af7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebfffd0dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75c59d5012cc142a97666365555e59998f0001fd7dfbaf75a867f3f9fe7b3b5fa67aeb77fc50f8a3bf296b3b8775b546d8de9be36d6415e5d97878aa23a6dc3458ec8d3337da656ae167a74743e423c8ca542ddca6e676ba26d6daba6b4661e9e601f9f0e8ded6d7c21e3cebddf854fcf813f675f3bedf7d8bbef7c6527aaa7a9adce64e7a90d2435b2cd5d2cb206908ad9813ae591e424a924966e4fb551c51431e8274803ecead2cf293fa7dcfe7f2f9f483ad877ca54a0dc14c98bac25678448699ea4f8d439565a90595b528be91f43fe1eec8b6e7b43d6be63a6bfc6750f153ae18edd0d356c38ec8b5753d4e42a63884a6ae1a1c64eee4a7dc1f089218a4f5fa892b71c9f6f88d050d07cbab8b91ac23034f5e842dbf9cc462775a494505551e6239e0a4afa79ab16076a5a69b4490cf534ec0cd08962056a232d247c3a907ea96e2dcdc2e996857d3a5505c24726b88d26ff57e5d581ed1ee6936c50cdf751e33779aeafc7e4e5ca1a6824cf6ddced3428fe24ce230a9c8d63409adea34aa541624ab3dd7d974d6a0b278798a94a7a7d87cfa328ee9955bc5ad4d687d479f437c394a3dc7b437341d739adc3b90d1d6ededc136325cac34953025552550c92e5444ecd4b533cb68e0c9c06282b2de09a31328bd1e34f154aa760e3f23d284775462cc3b87dbd07477f3515461172906563fe2352315fc472863a6c86d6cf2549a9f2c554d4d106a6ad894d3ced50038fadf4ea05d96cdcfea2b0d2c3f98e92c7771acaccf5d2bfeacf4fdbfb6f62b764b88aedbf936c666aa76d656b2a294555b019bc5514d4cb9ea6c4d50aaf2cb5104a239aa31ac0209847534bceb1ed829a8c727874ff3ff0087a74b7892bf7f6d31f9fa7f9faacdce55509dd993c564f02d412e58d651ee35c7554d495794349343e4ab35049a78cd5635c4b0b2a5882ca78f664859bc338229d143471a33a11476af5eedddc94b5998a2a6c7b5520a489061f1748dfe56ef053250c32a4d25e48cc74915f445fe27f3ed5806426a28847fc5754665851503e73f6fcba93b3f29b5f0f8f9ebf376c866eaf1429319429ae34db956843be6abab2a3c52d548211ad29d9d4924ea3636f74291282335f9d47f83af2cb40ac5a95ff0ff009ba1dfabfbbb656dba6a8a1cf6f3347415153354636b61c9d12cd82c89a7920758222af52317928dfc75d464bd25542c7526b0ae138b5a9f1349e3d3eb711c84789262943d08186ce6dba94a9afda5d9d82c764a7a879a876f57d4d552c350ce4a18f0d9291c4af0cb7f4c64a90a6d7b8f6e25ac7f1789a5bab89e48c7c3a947cffd5fb7a1b761f6f65fae66099ada6b9cc7553f832b4c992c36eec35553d546f14ffc636c6e2a41fc5f1859d8b449571ca07a90ea0ac135cedd3905a2662d4f239fcbfcdd28837086be148a07e58afa57cbede1d18c3b37add31f8eee6f8ddbbbb93a886028aa2b37ce5fa228e6ed1d89435d434de6c8d067baef762d2ee3db1530a48d515092d5d7ad3c22e8ee9a484a5d658d7fc589d26842e0823d54f1e9fd2d16b559c29635ab66a0fa1eb275aefadf6bbae8fb5b03f271bb2686ad8d2e7377ecdda735767711845a5a89b2190cff0050e45a1cb64e8569509a95a769a08deeee63523db296b15d6a7b32de2a0cad2920fc8f1ea86e440d124fa68dc0fe1fdbe47a3ad95dfbf163749da936f21d5bf20ea576ce9a3de1d0d431e23b0e83f8c23a478addfd79b9b234a5d2a05745a26a4ad260983230d28417eca7bf0742c6b2c55c8a6971f91e3f974cde47672a1f118a4f4f88f729ff006c3874783e3076aed3ea6c5ee1eced8dbbb2949dbb89cd53e63706d6ce75962a8bb0332fb5eab1f5180acccc196188fe138f3b7e9a1c5d54d42e8c6388cc1a42e58adfab0ce96eeccb29caea193feaf9745af66f1a16550d1019d2d5153c7ad85ff9737f396e81f9d70566cbcc9a0ea4ef4c0520abcd6c4cae6e82ab1d93a1392fe0eb95c0e40541a8a647c8911351d7a4155197423c892239bc37baa67b6b98fc39c70fe161eaa7fc20e4748a7b368d04b136a8bf98fb7fce3ab90f6bfa45d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7fffd1dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd30ee9dd3b6f646ddcceeeddf9cc5edadb1b771f5396ce67b35590e3f178ac751c665a9abadaba8748a186245fa93726c05c903dd1dd234692460100a927ab22348ca88a4b93803ad1d3f9bfff003ececedf54751b1fe32b67badbe35e51f378fa6ecf9dea36e6e7f90f8dc554c98acdd4ec09e45a7ca53ec086b91a9deb697499deebe4fa8f6477325d5f04d0ac966c3cfb59c1f3f50bfccf422b1b4b5b662679035d2f11c42fdbea7ad22f7c6f4c86edc84595cdcf54f3bcf5c44b527cb5224c8d4cb59e49aa64735557742103b967207d7d9adb402081634c5169d55d86b7666ad4f48ecd7626176ad4d3d2e3d6bbee4d2c0f553e3634a695c9e5992b0b6bb16ba91c9047b60dbc92e5fe0f43d50ddc10b617b8e6a3ac07bd5b2d4f3d16e2d954597c6c8225a5a8a98c5465293c47d4cb561165d351c093eba871f5b1f7e166ca4787200be63aa7d7a4b8680d7d71d233354794dd4f4391c67f0e586935478d78e04a59a82086f2c54b313186a98697f48f20668d401723dae5040a39a9e93b42640190007a45d7d3c95133cd49535716728555eb7cafa7cf1c4aa16a69e6003cae96209b7ad79b707dda8490389e9132b966c9d60ff83a1afaaf75d750455f89cdb545563eba99e68e1a474510d6b0b9a997c88409d631e86523492197fa7b6a54d430bddd2eb4918f6bb01d1b2e9bde8fb03734dba71b987c43d4554f435d4553234b435747511d20c96dbdcd4d1c4d1c14596a191646790498e9dc07530c8357b46529a430fd4e8da26575259ff4fe7f2ff07474f726dfa1dc3b7376ee0c2ee17aba286b292a7f85e4a8d32b8cac8a2c68ca5556d3450554f93a6ac094cf4b91a65691a5a4a81554e6f0c8a770dcc61c44e9fa841a1f2aff00b3d373c52a21757061f3f5cf0fb7a25dfe98b17064711b2eae9ce269a872f4b97db95d4fe3af9a0c464fc753051d5c13bc62adb1eec1237a792191a35b5d8b11ef4b1be96602a87f91eaa9206a2b395718f5c53cba0d7b6a970f5fbd9725b7b2a3ec9e96b687034f5aa22a811bd0992aa591d0164fb8ad794683a9a9d7480429203d0685671439fe5d52662ce1c8c01d16edd1969b1995ab5c9414d575b4f86c367f11912d22d4d2792995c524658e934d904ab8cba0b91a030b5fda90b56c0f514f5e8a649bf5751a56951d02d595392c823e4721533cab513ca754b237f944e48697c69f421030d47e83e9eddd002835e91b48ec4039ff00075ec761b23919923a5a62c5cd83ccbe3a700f059a49348b2dff00c7dd4f0e3d592379080aa73d0d185d8bd8d414f4b262d30b9da0aa48a29a3a99e2aaa5a3a8966644a47323a3c5285b49ad080aadf5041f74611b8a1718e962a5dc40af87a97f6f46376be3b7bedfc665e86bf0b365a19560f0d051eeca3ce6271d340ca24936ee6239c66713924bea5a7796689c7a6c7dd5868551a853ede94c4d33b30922207f83fd8e8cd6d5df798d9b96c761e92b7298da7dc74947599cdebb6abb2b439cc650454b353d47dee069aae1a1c9d4ab2689a6a887cc46a89d658dc7b460452487c58c71a57cc7fabe7d183eb8e302372540f87c8fcb3fe4e93d90db394d9bbde867c67674fd6f94ae927dd5b13b1767642b683666e6c8f92215135245899a99764efe92925459fedca4554754332271edc97e9c90d4eeafc4b8ad3854f4c2788491831d2801e23fd81fcba1d31bdcb2478aaeafedfd8784ed4ec0c5cf48309dfdd73fc63a73bfb1b8373299bc1fc169e4ebeec8a44a299d5e97234ed34b15cab17e7db37925c4a6111b2e851906a1fd6a187f97adc36d1db999483a58d7d56be5507cbcf1d3a776647e4f6d9c5ed9ecfea0cf41b8b6dad660b71d1ef6daf152617253d0e6e113454db93058caaadc0ee6af55a56f3fda69c9d14e8eb243a7520a1f0afa125642020a270d4a7e47a69d1ed660633dec6adfc041e8ddfc71ef3a6efb83abfb936441b4bacbe4874f67aae8371ed8a2dbb4d81c676ed6f9e039fc66e5aac53415f5391dc34807ecd4a352d44b6f178e46164a25bac43794fa81f0c94c30f527e7e7d2b16f148bae2f80f1504e0fcbecf2ebe801fcb87e7c6c1f981b2f33b421a7cb6d5ed8eaa4a6a0ddbb237251d55165170eda69b1b9cc7c95609c9e3c4c8d4b2ba33341347a25d2e6decd6dae84c5a3652255fe63d47af44d7b68f6efa89051bab30f6afa43d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd2dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf740af7efc85ea2f8c7d6f9bed6ee9de18ed9db3b074f2cb2d555b87adc84f144d2ae3b0f8f53f7393c8cca9e88a304fe49039f6c5c5c456b199656a28fda7e40799e9e82de5b990450ad58f5a07ff00366fe763babe714b91eb4eb94dc7b47a0a9b2094f06d6a0aa782bb73d079caff001cded3533c70cb354c4baa2a5d669e9471eb7d4decb9964bc292dc2d221909fe02df3f9797422b7b786c10d1835c11427d3e43ecf5eb5c9ee8eccec7ef1ca455fbe331539d8763edbc56c7d8945278e9f1db5b66e2e668f15b73158da68e2a3a2823526597c318f34ac6490b39bfb524b4b319247353c6bf2f41e5d3491244a551680ff97a2539e735fb97354cacc64c1c904b5512696592178e33ae952e8d21a79080b6173cfb5ca4103c8749481249a0d469e929fc132b356255438ca5af58ccb5512d7c731d313c86578d994926225c8fcf1c8fcfbd9a71af4d9b7ad180c7f93a16f19050536dea9ab9b154988c948af1d2ff0093570c6a1652e54561867f24729163ad00040bdfdebc450c148181d3df4c850346a41e9078cde8fb6733435998a45928239a9cd4d2a1816a3edd1ca9afc5d5d3de9e74490fee4320d6a3f02e7de9f550953ddd35e3f86c1655edae71c3e63d7e7fcba17b31b1f0fbd69ea772ed1c8d0d65799986160d30e39b3ad3442a1f11202ed15165e9d6e699ee1266f4fd48f76478d89461f9fa75b960d559d5b80f5e3d23760e126cc64bf864f27f0cc9e423ad384ac581e18b39558a901aac1d74014a52e629e44d0a48b33690d6d409a1ecedad4d4f4ec4a0a87d22bd0c39bae1b46bf0d9fc9d3c9f612d12d1ee05a781a9f21471a465e396a28a65b5608ee4e922cd0a9b1238f6d98cb9f10921bfd8e9d66555d2402bfcba14b09da5b8fa736f52eefdaf514bb9766e772b534f8dab4af8a48b1d1bc11d4d5e0b2b8c0cd2e37398bfb8fe2184c80e1a0f2440940e9ed14d6cf220923601bf9f4a23b80a4236534f0a629d16cdf93d0ef49f3bb86925a3a2ccd16561c8cf4c80454b2d0e4ea6392a2a7150c4aa3ecfcd2f98c2851602ec146900051083e188df811f9d7a4f2d1cb9f3af0f975cb7554d754e5f6f8c75499a8b151455ebf731219e4aba0684c8fa3f42c55619e37d2583802f73eef10d3ad99a83875a954c85147c1e63a0d327805cc6428eaeba466c6e4228a916ad86895f418e0a782990a80c903c5e31fd955fcf1edea8a139c748a5b25272f8f33e9f2ff3f4cd5d89a28f36311844866aba68fee67cbd740bfc3f11474ea4454b411ca0c322ea525ea48633487d00fbb33788a0f9d3ece92b41a311b062723e43a9d8fda5599876582ae9ee6ac87acaaaa5899a372cc2a55143cc881d8f1a54d87bd574827c871e94dbc52138229fe0e8cc6c8d87bab6fb3e569b1b45bc63869d06466a0c7d4c152b49c47e295a79e9a0aad418159134caadf43ed2baf8c498f88e8c55950856142703a30b853b4e964897b3365ae6e259a89e9b2d8451d79d9f84a12c7c631fb8689a7dafb90d1a9e60cc63e66942ff009e079f7a952544063a69a703c6bf23e5d523588c859aa581a8a540fcc79f46697114fb8a9b1d9eda959b57b87af66af911ce7f0f49b33b876d51d2f8a264abc8e1aaaa68a2ab5d0a4c95347518e9e3fd0e492a03d25c5262c972d191c55a8413f6f47651ca2ea8839a020ae31e75affc5f48caedbfd63b5aaa6c64d8dafafda998aaaaadaca2affb1af5d859449e45fef5ed534d594d25564e85991d62589629d633a956d1e95b15f2942b228d5e95fe7f6f4c4d01f12aaddd5c6283ec3d67c3d7a61f1d5186afc4e137f64ab24582bdb1d909b21b3cedfa7aeff703b971d4a050e4b1998aba3a894d4889ad4b6fdd7e2fed46995d59e060f4e383c3fcfd2533421b44bd87e7ebd0f7b7721b1323454fb5b395f5592c1ee0fbd4dbbd3f5b8dacdbf8dfbb9e9169f1d59b777c6d5304b45938ea2a1e6971f94a3759e671542466bbfbf59dbab487c48d95989a83c0fd9e87e471d357f2cb6c11e26565f51f3e35071f67417e37a6a9ba33bd2869f393fde6472f0d1569c5e1729554bbaf1988f3a19ab21ac81614cfe736c54290f4ce59b210ab22b79147b6afe0784c4865aa11fcbe47d474a2c66d6b24b0d479d3e7f67a74773757ce3ddbd63d8f5e319da7252f6426071f94da7f21fae32592dab43ba29e96d4b13ee4c65253c75d8d9a7a387ec3310d445aa0a98d2791580d4371e99a38e2796ba2bdea6871c2bfe0f4e9975d0cefe1d751ae86fe607f8475b527f25efe6c7bcfe41e4b27f18be4de56a33bbf3094385abea7ef3acc4c588a3ed4a4ca523554db1b74c9475359848bb2b07a5beda7a79bedb3d469e684994321556b2cd0bac3732078dfe07e07e6adf31e47cc7cfa2bbdb78e9e3451e83f892b5a7cc7cbfc1d6c89eccfa2cebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebfffd3dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd061dcbdcbd6bf1ff00ad775f6e76e6ebc66ccd85b33193e53379cca4e914691c48c62a4a48998495b92ae9008e9e9e30d24d230551ed9b8b88ad6179e77d31affaa83d49f21d3b0c325c489144b57638ebe7b7fcdeff009a3ee5f9a1ba6af30646d9bf1f36bd7525075b75ecd34751b8f74d4ab4863cd6669e3fd993299869b58a405a28222a252da3da18639ae585eddae983f047c4d3d5bd09f4e8491c29608604eeb823b9bc87a81fe7ea8d638a6dc786a8ae929d716f97ced3518fb427ede8a8006acc94934fc8af9d0a08a46000f25c00178f6ac0f1250a5680f5a1fd9b3f13d06184cc614ee1dfd1e6321a5308b11c5c71244ea667c546b8f690b2857689cb16524025bf3ef65542d69ebd351af710060e7a24fbfa0adc2efaacdd73c02ba9ab32244f2d248099a8cc11a1869a388940f4b1adc1b5b817fcfbaab486a8bc475492311bbca467874a3c36668f235d045468b2c1e51a2a68ed3411d2ca81a196a699e613c06298959109fdb63f52bef4c2475a9343e9c3a723923caaf1e9df706737fe2254a78b3114f0d4b9a5a6c4663192e3c78dd3fe51eb7c93d1b46adc2ba4e03fd748f6ce9a5086cf57acba882b9f5ff3f45e3778c84d512357d08a3ac9643e72ca9a0392da919a31a25566b10e6ec3fadbdbf1ca5880c4529d175da549ce7f974e1b172598db35550f55055c98298253e6e811996634c4ab26431eba85ebf18ee268254e6da85ec4fb75e8d5d2c358e9ab4f1118ea074798ff002f43fed9cd4f0c992c7cd15f274394a6dd3b7b3a38a79b20b659fcc80810be668026bb13eb173f41ed33b569535a7fabf9746c349f846467d3a7bc8e56a77265b29479da8a88687318cab969a18152a4c191a30d554eb0bbdd523aba39e40ba58a6be3f27df95a83506eeaf5571acb0d383fcba4349067309b52a76c542bae20a2e631cd0c44ab52453373c1659a369252e9cde12cca3d248f7677ab861c3fd8e9a5565564095a703c07d9d2a32db32928b0b81cfd757253ad7e2f2ffc44ac6d12cb10bc74c9a47a16789268c9517d42d6b7b644b9a203a81e9e3116d3a8e29d08794d8b4b80ab68aba0ad35d36cec7566df4943252d64d594114f4728592e29f1e23324d37375361cdf8df8864621a9a6bfea3d6cc7e18a1a57a042aebabe5c16076e62a38cd561e9f3b1ee0cd4623ac4a6827ae6ab10d1cfea44a5a785f4ff0050c4d8dfdab565762c24eda53fd9e9230d618279f9f5176d75a65771d44d1d76629b1d4a7eda7a59320d2524f591197c10c94cb278dfece18d4105ad64e7f3ef66455216b8e938b592a3234d7a102b76ce6bad15abe86af6dee08258a16a95a1969eaaa1849a9447041387a87d46f75054b5b8f6d990c9550c00e962a08c63d3f3e87de9faac9f69c4b3ec7cda61f72d3c15713e1a3930f8a4cac98ea7d7538da26cdb1c7c1b8eba962792382b8d3ad5321585da465435530abe9958ab13f2eaaa66752d0a8615cd788ff8aea26f0ee8ec7a5c4490e3ff0087d6c42674ac997058b4aa482321163aac1eb32534d1c8856531168c92dc2dfda9626863d4749f3207541214605e3193c4647400d1f666fbc76457709c2e4960a98fedb2559b7e0c94348699dbcb3d1d75152fdc51a53d4420ea8ce8f402c3917f6827dba39d4868c39f2c67f6f4a45db44f96d23edc742a57f746efa8db78fada3a8dbf4b051b551a012e612682be9d61fb7aca7a48ea52a3c95682603edea0c7332aea05be9ecb136d85652013afd3ece964f7e0a8606b5fe7d2d7a7be42350e73132d56e1836e8a7c6cbe256a24a2a73244ff00b13d0e4e681c5564a966225d1503ede631e86d5ecd1035b8341f2e8b64d1735527bbf6f423fdc4d9eec7aadd1b8b72e6772546e59e77dc1b92b571f94c767a7558a2c7255c34f514d3d24f4a74c65c308cc67d2adc12dcb2ab0695e23a871cf97d9d284b500a428ca57f97ede8ec52f60f6252edacef5fef2eb1da9daf8bc150ac9b53b4b616366dd1bb7af20ad439a86aeb22a5fe175fb9170933c8b345295929c33150436b3b79e696dcc76f02cd079823b871e00fa74ca5a450dc879a578e715a509d273e74e8bb765deb69311d8393aca4ced7d7d0b622b773edd8457e0f705249248f8c9f2f4baa2c8d064a6d669e691e3f2a3130d4062818a4db9524b76d06a54ffaaa38e3867a5f78aef2292bc47e5f91e1d39f50f68f6a7c57ccd2ef2eb3c9eefa2dbd9ac5d31d979f9a927a44c06e3a2c9d2e566c052cd5625a1c8e3f1d92a0f250b830d5d1572a91a11d95d73daeb552b511f9d0e47cc745f2b8d6c8e6ba456bebf2ff003f5f4b3fe59bfcc8ba83e7c7c7ed83bab15bc3047b9297094789ed9d874f234794dbfbce829d22c8ccf46c8a62c7e64a0aba76174512f8afad08f6e5add2cc0c4e69709861c3f3fb08cf457736c6262f182603907edf2fb41c7566bed67493af7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebfffd4dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75c259638239269a44861851e596595d638e28e352ef248ee42a2228249240007bf70ebdc703af9d07f3e5fe6947e597c83cb7586cedcf2afc61e87cde476be128f0f5cd343d91bf282a0d26e1ded530423c3571255c471d878e4d5e28c3ce402f7f64b147fbceed6e5ffdc4434407ccf9bd3e7f86be553d08e0036fb47007f8c3ad58fa0f251fe13d6bff00b9abeab2db3ea7b7b7955d3d3d5c127f07da5b72127f86ec7c29200a88948f1d76f0cc91a232c09552ce4803d9dcc1628dbbaacdc3e5d331334eca581a0f3e353fe6e82ac7f6a2506d3c0e1e695e8f219fc8d76455d5d0bd262a5a87a2a0a6a5724da52eaf2cda87ee3311fd3da48e46504fa71e953004150c34fa744f731bb2aa9a6ce54c12324f5b90aed4a92baf9a3a61a23d2a09d7a2245255b8b1ff000f7e47f10f70e1d27790c550002493c3a69a9dcb96ccd4e36ad1619e9678e392a685d034b4757046525984578f547523d6a57f50247d47b7015881d5c49f2f4eaa18cac95ca5323cc1fb3cfa51d1ec9cc6429db736dfc6cd571e367126457035c4e5b1c1d6df7b262e754ad14ea5b97559223f46f695e642e4ea1c3f97fc5fa75716a4b060081e4474acdbf84ddf5d0cd452d755d4d04d2074428268ea5cfafee20a7911fed6aa2b80552c18f16e3d974d751020ab0f43c40e8cedece63ab53d457a1df19d21fc7e2032b518f5ad9962fb79c0f148b22aaab452d049146f79b82c40241626dedb92e160919567575f55e1ebe7e9c3a5c76fd60298eb8e8c5ed1f86996dc1859a89f191d44f494f291f6503ce29cd481263aa239bc7ae64995af1904ab02c2c0ab0f6dbee054060d8eb716d8809120a81d426f8a991c74d4947558891fecf1f9aa296a29c48664afc7a09299cc5722368e53a7d5fd83fe1ef437066048e3d5ced80354574d7a67c2f44e6303bef194f554661a59e2c655c54b228a98d9325131f0d4c6448aa8ab2b346ca3d5703dd8df1c02687d7cabd546da845554915e855ed5f8f945075f6d0c95242d26472541ba715514210222c78c8a211568810b353d2d4d7d4164d5a4f1c7f4f7efa96aa106a2bfe0eb62c233e22d283cba4cd674fd7b6cfdabb15f1745579c8063a68b1df6ef3d4c272590a6902551751a40431aa94d4ef73c6904fbb35d00d512649e9836da6945a81fe4e993bf36949bb3756eaa7c365e5ca52ed2c5d3e1731936a4aa8f095536269a38f211d2d64688b8ec3e23c2f1395d258285b12ded646eb5e3c4f486e230ca4d0fd9fcfa24557d7799c7e0a26c2fdc54a54254d7494b4b14b24ad1cb3086944ce024b3b39bb2c47f47d7923dbfe2ab352bc3a49e03786554507fab87d9d63dc387de582c161f7248692bdd679e9aa70c2695f74d1b4514263a9ada1649125a670e5600c49d48c0aad87b795613827b8f4c3ac91e961565033feaf3e821ca55d4e6aaa4c8a53b45521d05563e4a8304f3a22ead2f0d4b2b46c45d94ea0437d3da85894640cf0e924ceeec4e923f3ea1e2b3bbaf646660dc7b673f9cc4d42cb2186b28d3fcae05373514999c6c9fb750aa2d7055e2910dc5efefcd6f091dc071e1fe63d30b3cf15191881feaf2e8d0ff007cf677c83c747415b0607aff00bb699425266b0954db6b69f665385d6464713248b4bb7b77c07d2ad118e1ab53c8b8b7bbc418931c86abe47cc7c8f4fbcb1ca54a0d327980704fa8f4f98f23d07d1c1dd9d75979b1f511e47ef2089299e8320594d4d1cc0a0587278f950c944c8c4067d5a39bd87b71a3c01e267aa319594e352fcfcba0d371c1b91aaaab259cdbd3e365ac9630ad9090d3c521a720b470f8e35a6c83e93c33fee691717fafbdc8aa28d8d5f2ff002f49b5c85c82802fdbd3e603b0e87673d054e4682b72d42f154453e2aa1f1f92a19bc80c6df6e95d432c30c2eea1b486bab7239f6cb5bb1a482bc7a509711c5a9589a91e5e5d2d24f91d8bcbe6e9f2d57b66b36cc33524186a8a7c356c72e167a4a2a58a920618f961861a670b1a99022b2024903db4d0d23208ab7d9fcbadc57ba58003b3fd59eac2ba4fbde80c7889313bb37375f6e822ae8f15178e9e9707958aa208fc53e3b70d3556afe213d183198ea5380c4472aead009ee5440f1cac8e1fc8d7b7f31d08ad996ec3c4195908cd7e5d2a697b5e9f19595b563118ecb89e092933d89ac81286aaa6b61696f96c8e3d0a51554951a4099d116598e97376f57b4b05c4cb79e3a20058f762951feaf3e955c468205898f60e1e647963e5d6183bc7aff704393c6520cbd053d3cd4f0ee5da55521d38bc4d4206873f8ec7ce1a0a997059293d354977785f4bf2abec48acb2a099148a71cf44ada15cc7514e03edf9f4627e25fc99cbfc41ef5db3f213a9776d7e2bfbbf59433ef8c6534667c3e677061034103d7e1165a64acc46fadbd5aceccd78a1ad8aff005b7ba948a4632a2d260727cffd907a4f3bc8aad1e9ac24e3fe2fafa43fc20f9d1b5fe5dec3da1989b12bb4f776e7d9989deb4386fbea6ac833183af8e78a7ca63c452354538a2c8d14f0544128d704a8013664bb91c9afcba2c9613193e838fcba3f1edce99ebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebffd5dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf754a1fcec3f9896c5f845d013ed5cac8b59bdfb7f6ceefc7ed9c0c75e686a32021c6494149197858560a5a9ccd64425f10d4d14522dc02482fbfb8d09e02a0691c1c569f2fd9d196dd6fe2c8652f45420f5f2d3abddf3e6b2db8b272cc64c26c9a648ca094c50d7ef2cd5448b1ea0a07dc98ea2677373a82213cdfdacb48c436c87d0790c547a74a98adccac99a03c7e7d243b0b7c653744d85c0472ae2b0186027c5d031a80f95c8a4691cb99aba18eeecd27221d600118bf37f69266f11f5b8c8e1f2e95c6342ac6b4e829dd193861dd1b7a160953063b1b32f322c493cc649889b4200f1f86a18b58126e39f6fc26b110067a62534982839a7410e52b8cb356c30524466195748b976918491b2492a48a6307c8e9c81c58dbdd915235d478f45af2bbb3695efad3f2e85eebcebacae5d2183254b4d0d02fef364a457866a4a59081ae1a892cf0a539bfa0a957bfd7d975e5e2a0a83c0747fb76df232abba0d5fe4ff3746e7ac7a6e0c764132f8a7cde4d28a66fb6cfd3d1e4292814c63c9786b1b4f918db91a8a1e7ea3d846ef703aa91e0791ff57f83a1859ed8b50185479fa7479b6b74551eec4a6ca51a883355332b57bd142ab0d48725beebc220f08ab8c8b965fd573f9b7b40d7af92e7bbfc3d1e0dba240a123007f83a3c7d5ff12c0c5c35f5996c554c94cc25a5778f1f34f2a484a982a0944a813ccefe9b5b4b017e07b4edb90881fd435e1c71d3abb7203fd99afc8747c764f4ae492876fe5e9929a9968f21454da608e0326470f04edfb8ab4fe96856ac583b2a8b92b63ee90de48da6432d457ed34ea935a4757411d24a713ebd2a72ff0016b0393aaced7b413c3256e4b23532b4112997ee6a868820815c2a08941d5273c9f4fb7feb54062b52413d33f4ada115863a49577c46c45367e8ea9f17410e493eda968650cb234b4f4f4e624892328b4cce2a245f230e2320db81ef6d7e5c0566a7a74c8b2550594b7138f4e90bbc7e1a427cd24d1564b434314d56c8d1e86d44346290494ece268647d5294417f5807da8172c80057c7f2e3d5d2ce2715719e8adeeff008e19ec34af93c3e37234fb9eb7f7e371368ab5aa9616855994452fd9a53d182aa01ba71a46ab90aa1bc0432b0c8f3f5e989b6e5af610411f9745a771f48e768e8db1fb8f07958f0b2ba55e4367e26a21c163ab6ae291a78eb77465636aac96461156a26969bf6524660ce0d80f6b1276aeb6a9f4cd29d2192d010b110028f974056727da5b2fcb357e3f16b3d64712d347b7e649929a9a79a44f2252491ccf269947efb3c826600954b7b551cac19413c7a41731a458d029d160eceda5472e4ea727836c4cf8caba296ae9ea703592cd25778444f3c304534d24cf918f5ca5b5e963a40d3cdfd9a2cd1160a1e87a24785c82e16a9feaf2eabe7b028b234b9d9e9eb71a62cbaad34f8dada60ef4f5d46e09d4ad202f56ef172d14a4c88e0e93c5bdaf89c2806b55e892e632643ad486a0a63a7ad95bcf0d473c585df98f74a39bc6f1ced67861940229dfeed15aaf1a6e6d617417f52dbda9d5a7f5114134ea886324248943c2be5feafb7a353b73e3d6d3ec89e9b2d848a9aab1d5128fb8a9a62b356e365444d51bc9037967955ec6eca97e0ab1f6a51b5f003f6f5e7b58c55c9a35714e91bdbdb637675e5663e1932b4fbab1b8f6f1d0415c67832d4f0c167553f7350cd534b620005811f83ee92474ad1b34fb69d391b941a7c3a8af417e4fb96833db74e1b756d29a96b61a945a3ce636a6aa0aba367670c115de7a3969c81a424b1b816b022f7f758cb295502bd279dd5c0f27fe5d03b93aec455c4ef8eab5748641213f6769bc6bf8ada51198e3b93cc886c0ff0087b56ac299e8b66035290d51c29d26679a9677d53c718a759a1b2f8c3bcb196d4e62589ed17982692401c727df80500d457f2ff557aac828ddab45fb7a1476e3e1aba8abb0d33e536ed754cb4b53b6ead58d5e2e991c846a7aaa596a74cb4e0a5fcbccaafe91c1f69a58e17210aa9af4a2de5992ac8f4a0e847c5f65ef2dbf5c906e44ab5a9b26de92bea696f2d4c543e3969eb29057c724f0eaa39d34fe96b5ec483eca27dbb4b3b237ce9d1b5a6e40e94b906bebd2d3b2f6be7f395147d81b2bee66a8c262925cc478db1a8aac79d061a95810ea9bc350de3a88cf2aa4707dbfb74da95add852bc3ab6e36eeacb7309edae7fd5fe5e973d4dd855f157e2ebaa7153e4a9bee0e12a69a99df5514b92a2aa929e90c255de4a692a47969f5afa65431fd0dbdba63d2f838a9aff9875a0cf2425940d75eb7abff0084e1fc87c37c8a87b93a47b1eaa9f68f6decfa2d9dbd766e4f6bb51ed6ce64f054151e1c8d760e8e962969e9293ccb4eb91a2849a5951b53457d44552442df4adc6950781343d2298353ea00a83823881ff17e5d6e28a0aaa8662e42805c800b102c588000058f3c71ed57483ae5efdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebffd6dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75f315ff00852c7ca6a6ee8fe647d9f8ec366ff8e6cff8cfb2317d618489668eaf1943bce2a7191ddc281e33e25997335a6395b93a90adf8f6570224f7973724120108be9839a7e7c7a3e8eb6f670ad28c6ac7d73c3f975af86d7e3a9b174397a57fb59b7b56eefcb43e254a9cdd5a4091e3288ce1448b4f44cc4b5c90355fd98dcc92284446ed03f6e7aa5ac2c434b2120935e9bbf84ab4dbd7b0f715446062e28aaa031b99456d74a6386871f4d1c7cf8208d09246902351ed01249a57bba57408c5c8f2e3f2e8b46e9aea95ccd1d6fa93d5e449645122cb475ac1df430063d2baec47079ff0f6ad54a424d3bba2e94912d49ee24fece875e9de91afded9f35541432e5a9eae6f252ab2961180be5768ca6a3220f235858588e7e9ec9373dcd608f483ddd1eed3b499652e57503c07a75741d0bf14b13575144d97c2b51d32554509a6aa759abab8a46001a48304b1bcbc0bda38d7ea09f600bcde59bc6556cfc8fedcf526586cb00d25a3ad38ff00b1d5aeed0f8cbb471b888cd7c50e46a2361511e3a9e159e930e02e85862a44458eac4496bc9269b313616f646d28a090bb13f33d1f47da7c38e1509fe1e865d91d07b7f23552cf472378743af89638258a62a39054a04a7642029b136078f6c092498d75b1fb73d3b2bb4629e1a827f9743fed4e80c46215ea9e8e98cd38bd3c2b1330a653facd4c8cec27966b0b290401f4fafb7963a29d6727a65e532b56bc3f9ff9ba335b23af23a2a38e9a6814534cd1b5c200fa6391255855154150f22dc806c2c3dbb02b21d3e5d20b901c9f3e870a1d834a164f321944aecd2318a3532bb005806b13191c5bebecc015a53cfa49ac900538749ba9ebda56c989050c50ba2aa53d6c8859604562342423d172c6e58dbe9f9f75032001f99e9e500a166cd3cbcfa7197a831993a1a68aadde758a777997d31c722a93278ca45a14f925e6e6e6c3dbcb80bdf9e9979183555069e82ecf7436329ea6af215b470d5492ae98a0450890c2a08d28ab1195a76045dae49ff79f6a232a18b33935f2f2e9b924d60285a11d10fee2f8df88cd3e4248a67a1c9a52cd18586803b2c218682ad5b435634a23fa9c59403edff1d055b555a9d796262143260f9fa75537df5f0e6a6831d5f5342b88932152865a6a2c8e3a9275c8286d76a0cce3f271d451e44107c6cb1a589e508e3da98f7129a6a707f3e91c9b47d416295269c320ff0083aaab9faaf756d93978a9aa27a0afa0aef2e476f65292335cf42d2698f3d4b529108beea900019c3289e3e1bd561ecd619a39893a857e5c7a2b9ac24854d528de9d36e77aee3ca6db4a85c745599ea1a85353278e2a74afa7ad252b22a07a849169c543324c848fd89f50fd2fc1a4170554ad0e0f4592d834ea4aa0e8a06e2e9ac825456be168eba4559a7fb9a1afa3d35544c8a04a2780b49684927532928c2cca47b318a74d755153e9d10dc593a64afcb89a7e7d2fbacf6e65e8e9e4a7832759b2b73e347dc504b4550c28f71434aacf350cd048cab3c821bd9186a75fa1b807da8564676325454755104ad1f601a7ae1bde8f75e49129e9f77c3b8f155d48c69e82ada0a89a8e35b4f51452d34c23c822c5545f4dd3585e351b7b7e32a54e872547af1e989c90e49401be55a7458b2982c9c558ff0073b7a334f5b0cd4d255d1168e9d348f4b6867d2951f7097018023e9eee0d723a2f943bb502547c8741bd5d2305a9ab869da0789626a88d2a06b8a3ff003129f0b35e443281afea6e7e96f6e12cca6a387488a856351d3453cf1c52213122141c0916ec58b12046aa6e41bdac6e3de83b2a151c6bd25995b56a031e9d2e305bae6c24eb94c851256a533a7da43582290f945ca468b2a3a288f51604afa3ea2c47b6c12300d3ab4733546aee5fd94e94f8fddf165f2b575390a379a0c954896b23c9cf57573457864852b29eafd5a658564b2bafea51a4f1f4d9d4d9ae7a556fa490178ffa8f569bf197a865ecfd85b96a62a2cf6131fb63125a977bc31ce30d4066a88a0a419c3206350990af6486458f5f8c10ce005b14f0c41ae0871a58d48fc857a3592775882819f315e91b88db186cd4bb876f542d3ed4eec4fb5d183c5cc89b577c3e366153fc73035ec0494fb9e9bc29530c0bfb756a8eaa048c2ef997c42237402878f97fc5f55455835c91b557d3fcbf6797567ffc943e52637e30ff0034cea8dedda59c8b6e6c6ec99b31b0f72e72ac3c74b84cde6f1b3e0e4fba528ad458baacb0566d4a00d41ade907da2b9d10cb6f313da1a84fa571d36aa67fa944e2cb503e633d7d4829ea20ab820aaa69a3a8a6a9863a8a79e1759219e0991648a68a4425648e48d832904820dfdafe391d14759bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7fffd7dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd104fe675f2a2afe1bfc27ef0eefc3c4f26e9c4ed6adc2ece96d782837567e9e6c7e1f2b5675232d362e773506d76668c0039f696f1dd2de4310fd4381f2af9fe5d29b4884b3a2b7c35a9ebe46bdb55736e3ad7acdc75d5b57b837e53e6776ee5c957cd25455e52b32992fbb96b2ae5762ed5b90aa3200cd7b13fe1eeb670a416f146bf847f3f3fdbd1ccebe349a98533fcbd3a45475d047f7f4b52ef4f87c6e0ea71d140ac4eac855a24f50b05d8796a9d1accfc08d393cdbda908a58bb1c0f2e9a9256450abf1135fcba4f6f7dc58dc56c0cad051c6c95f97ad3554678fb55a05963a48578160008c2a961ea5ff5fda4553e3547007a7a620c069c4af9f4591e5c86471d098a396ba5a0927603c666d5049cce88880992182452ca7f00d87d3dab664182d93d16f84da03e491e9c3f675777fca66a36eefa4dc5b69063a2dcb8e2d3b2d53ac359578ba878c4a31aed7555a67b99805d5a5c7e39f71b7377890cbe18345600fecf9f527f25a453c0ee73a4d3e60f5b1075cf56e1eb1deae72ced1cd2c4a29e9bedcfecb0091c2c4799e18dff00c6cc79e7dc7e87c5a915a70a7527c8a2d63502991d1868b052242296991210be389c456894ab02175587d47d49fcfbf4b5034fae3ece998c230a8e1ebd0c7b3f07478f68a9e0a105d623254d5468a2cda45a273f46d4793c7fafede82550422ae7ccf49ee817ef2d8e03a1cf138b5f3413cd66b22cc636b102d7d019145c826dc7b5671dc479f480f0a28fcfa1031b2c88a4f8edfb88ccae9a3d4b6b693c853ac7e3debc52298ea8d0823b8f421d24e6780a5ca16d16e0ea6240216c3850c4fd7daa4634af4c98e873d4f92888879b3333293eabe8b71c1e6ed616f7b0c41cf0eaca00ed1d66a38cd12379262d1062ec840e54d8fa9cdecc5ffdb016f77f100fcfaf489afb40eee9eca50d522ea8e3bc9c0056ec3ea6ec6c4837fa1f6e07a706e90b46682a0f419ef6eb4a4cec6b3bc9cc6a48915344d01b15d69343a18581b107f1fd471efcc430a1ad7e5d5a194c668a0f450b7f7c6ba5cab4c62a2c7560a8429531d6d3c5350d5436b36ba07a6730cd228ff390c8855acc00fa7b69e1d79439fe5fb3a3386e828a360f953fcfd57df7bfc36a3dc98dab9851a4b5d8ac6d4d252bc14a68f291e2c446f475d18598ee4c7311a75ea5922201d27ebeef6d2cb6ae5e294e9e041f2fb3abcc22bb023923a3e0eaf5ff003754b9bdfa9f72f50e66be2aaa5a6cc60658da8e4c4cc5564a878d7f65692b2708f4190f15bc295005d8583b5f403e877b8427873b8278023d7e7d14c9b1bc6cf35a2920e6873c3cc7af40ae52968775d2be536f4b0cb9ec11532d0c692c1b97154c230893c88ea9e65426d2c6e383cfa94fb5f6d7f2460a820fa11c7f6f44f796314f560287ceb419f98e8b26e19f333d62fdfe1a9eab20b34b4c9318971cf9494399149821d34af572ab8f546c9239b5bfa7b364dc35d353aeae83f2583c649446d3fcba0cb235cb8f8d8565466b6cc523c80ac34b4ad11774fdda5ab33a7dd46adf452cd7b588bfb7c4f256abf0fdbd30d6b132d5851be7d04d9ed874b934a3aaa5dc1929e311c8f0fd90659a426a1daef1c2eeaf2103d5e93620deded7c7331a328e8b9eda9a89703a4be73ab27a48df3b88c0d736a6577ce66244a3c724eea544d0c120513ea1faecadfb83fa1f6b44c186a7c3745ad6281cb22d7cff00cbd04431f438e32cfe3fb8783cdf779291408164018b2d2876fdd7d7c02bf9ff006ded4a3a8518ad7cfa2f9552363a933d21eb324f592a1740d1c464f1a9014e96b92cc57ead6e7fc3dd34fa745da38d0d2bd2836ee482c8a272523a75999e41ea6313a3010a9790302c780071ef4306bd3f03ac4c0799eacafaafb2a830d8fc060f379c92976d8dbb2debb15548d062f2ac82ba4c453e56cd151e4aada3466e240c8c7573c0412a18dfc5727402787cfa38b69ab1b28a173ebe7d47dd3db3b0774d7d3cf4f36e141054534b81afaaafa6ab9682aa9998ad2c993869e0ae8a679e3d74158a408a6568a55d07ddcc85f86aa53d7aba8a85ab281f9f1e94188dcd365bb8bac323b970d84dc71d3d7459bdca7386a29b19bcf6cd2c65c5556b5054d15447592434e56a7c322544754a1a33a88bbb2a318189a1a807eda7974d784a2659a3c69e3f2fb3afa657f27bf9d557dfbd3fb3b61eff00940afabc27f11e9bdcbe5a8aaa4dd5b231b4b1a546d0c864ab23a79e4dff00d7a6334d918248c4ad0aa4c7f57b757b5223a484618ff3745b22963231f8c1cff9fabb7f77e98ebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7bafffd0dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd6a07ff0aeaf931fdc5f8d7d07f1b314c465bb7bb166ded9961248a536fec7a57a6a1a7288c04b164329937d41b806007ebecbef3f566b7b7f9ea3f60ff67a32b0fd3d731ff4a3ed3d687f97827c9e44d50a2134f8ea0a7d7331b45153d1449e28a4162a902b496b0fd52313f8f6a0c2eaa3d07463e2027278f454b39978a9a5c855d5e4de6a89eb6a94c70b6888d5bce088e38eda5563503511c9528a7da824501e19e904a4a303afbabd2de5a0a6dcb2e631e4b57418fdbb84c7bc4d654fe291e5a3abad82265d0a02ab2a1209e01f68247f0031af731fe5d19ac22e49009a019ff0f471fab7e2c642bb1916e1a7c2553cacb04d2d3a51998253380a0a47aa2214b1f4a2add80e091ec31bbee12c51288a5a49f2f3e859b3ed6b24c35443453acdb4ba77b2fa2fe466cbdc5d150ee4832397ced250e5b0c985aa58e929f200c59599a4ac89a825c7b4323b14901d07e87e9ec8ef3714dd36b963be422e221556c0afcba11edfb05c6d9ba453580adbc8ddc33419ff0051eb6bcea5dcb9ec64bb5f6ceea8a7caee5cde3e4ac9329438a4a7c7e3a9a911414ad9636758a796f655fed37b8fcdd7873a40a848615ad303f3f9f520dcd8196292e15c68434a57249f41e9fe4e8cdd4d20269aa298b52bb48523253c8b23b82ced329e742724fe07e3dea6919a8c2bd25b74540caf9c7424ed278e9d520661796c279069700eb2dad6f6d373f5f76865000c67a6ae61d42a05057a15f1de1fbb12b54b016b0456bbb804695b0baf37b8bfb56b28d40eaa74904474f0eeaf42951c51490794c6ca34f0f2585b9007a091c73f5fea7dae0148a8e939143427a53d0471fed2c46c8d70c545919945c7245c0bfd39e0fb71707a65c1f3e23a7179521899011aeeee15981d3cfd4851ab9fa81eec4e3ad2ad73d32432d464418563922851f53338b79aec6cfc5dd4703f3eea09ce3a74a8435ad4f4aaa1a7114a9a9d83040087173c5ed6e6fcd8f207bba2f9f9f492419f9f4a6f189a200166048078b8e6cbc2db9623f3eded24f01d2761c33d33cf8f821421a006304fac80fa39f5292392973c7f4f760597f0e3af549e1d067baf6861eba9a68de969de6904a619428f244ee8c8c141b3340ea795b8b9ff0061eeb2ba804019e9d899c3122b4e8aa763fc5beb0ec44a94dc180a76a8992311cab4f0bc8c5a1292069a58a6a4a988ba83a2a11803c817e7da70b1ea2daa8d4e972decf10a53527fabf31d546f7d7f293a1dc35b579eeb7ce536d4cf95a814b518f79714cda90c7a6ba1a7967a79c95007a163045b806deef0dd5e42e0c72065e142294eab24761768cd2c6c92fa8c827e7e7d577677f96bf7e6dcfbbc5ee8dbb4f9e82064ac87290cf5cb4d908ee4a54d29250bccd220bdde396365b73f5f6710ef8b6ec56eecc32938a641e8b0ec7e3a8fa7beee1c47a745db76fc56dd1415b2d26f4d87bae1598834d6191afa3a98a954aff9222b4e646bb7ee47c48bf51c1f66e9bd4223478214afdb43d173f2eca49596e5c81e82bfeafb7a0fd7a8f716dc9aae976a626b63d2c5a3076c475325378e14124b4e6684c114abaad2075d44fd45fdf977eb9049550a7a492ec5146304914f306bd031bb7a5b7de767aba9c9e1b33b96be9535434d948eba48098d0cb0c91d35046b46046dc691c03f51ed6c5bbd18348c49fb703a4cdb2f88874a69a7f33d578f6ae037c3668c3b8a923a1fb45922829842b8da1a548e46d51780aab9a95fed3badd801cfb12595ec3730eb5718c7408ddb6db9824d050903863a04a5a64849533c3237e4c464648ffaaead0039fc71c0f664ba686ad9e83ac082453a9f4d1e2453cc65a99cd447a1e3489028a8d6caa625f27e968feb7fe9ef6ea94054f5a001157c7f9ba536dbcc4d45e58e8a77c6527aa52ceff730356ac4e209ab2298353b440595974d9bfa1b7b64a87c11d3b0d7c9883fe5ff00274b1c7ee239493194265880131af3574d04700a2a97732f89a35554fb5f321201fa07fa7ba8558831cd3a5893060b19a0c8cff86bd1b7ebfcf419ac7edcc05545041b93135c72bb46baa6b3c1f7b97a59064ab302d2120c989cb22490292410cd65fc1f69d0972e8e7b4f0f91f2e97c9a5d1745437e2fb3ade57fe13dbbb70bbbfe2af6eeff00c464296837a6c6f91bb4f766d3d8b595d2d7d475e6dbdc35d418ec9d0d30adb570a1ddbf77534cd337aa61125c92b7f7786e04b24d03c95650081e9d209e01188e441d8f504faf5b9d7b55d17f5ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ffd1dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7cf93fe15679a97b0be7cf4375ca5434b075ff4cc593969d34ba432e4f2792cd98a4faf8e5a8468cf36ba107d97fc57d2b71d2807edcf46f68aa6d9750fc75afd9d6b43d972e2f69755e6e3a1950e6b7b55d36da86aa0762b04342ff7db8a6a5720298a98b94690706436fa01ecc5876aa93dc684d3a78d56be629feaff0051eab3b22c33b9babada4a4f1d0c75ac319410a814fe383f6a290dbfcf4b33c4a4817058927e9ee8c4035a8a57a2e27c4916a4f1e8fafc68eae9f2597c3d4e5d17213e42b0c8eb228928e9f5f8fc924ab75865647b13724023fafb0edfde28d47f0578f432d9ec9d9753fe235a79f5b357456cbc26236cd253494f1d508a082392a4c71bc92385f20769046142aa9d2a07007b8c771dc249267d321d3d49db5d9058d069efe8c9d26ccc6cb5f455d498ca44732879268e084cb290a022f942eab2defc1b123d91dc5d4cfd86434e8556312c552c093e5f2e8c96d5c346cad246eaad2005c00bf5fc5e40012011cafd2fed387f56c74a64d0070cff0087a10971ccf50972e569d123f18e0379012f237e18b017e07d3dea4625853e1e925574b7ccf4acc7d14348159544977d45f954d6c6fa4aad99c20e7fa7b71542e7cfa4ecda815381d2d718669aa6189824458349fda525622354884fa8adc8e07f5f77423500707d3ad32d149518e863a491d69e24913cbea40c0a008c38fa28b7a79bdfd9b2376f45aca4b1f5f974b0a6d4f11074902fa0fe816b0bea03e963fd6c2fedd06a7a648c9ea549453bc88e114398ca16b852fa5755d413f91f9b5bdde9e5d6aa3d7a73a2c7aa02590870a4b06e5b51bf2a41b0e3fde3dbaa9c7a61dfc874f94f1858eeab7bb282e05ec75136b9bbdd7f36b7bbf867041fb7a68b00727a7486076d4c548242f2ba858fe02adee48faded7f6f2a9030b8e99722bf2ebb928e32345d8717726336360495faea1c7f87d3def40034d3f6f4d17a648e92f94c623c6a1e353229d70385054953e8018dbea39fe9ed2cb19e9e8a400e4e29d315650430245ad5918c89a7c7c832107d4c9f4d24fd78b7b4ec853d3a7c11211a4d3a60aec1d255bbb3d32a3a26a8df4477d46e1998a2826e45febeea4934af0eac315009af48cc8edb8e9ca4f508b554a2275a88dd41216e59592224eb313dfd22e4eae3dba92951dc6abd5592a680518f401eff00e9fda1b9a8eaa5cc61a103499a8bc6a21aba695c1f1cf132036ac91ad603e83837e7db2da1892c95f4e95c134f0b2e87ff00353d3a26753f1bd3135089538ba6cbd249573ccd5e2089aa6d58929682ae8edae595058168891c7d3db0c64a8a3929feac53a532dca49c000c7cba0eeb7a3b092cd5692e26286082748e09a3a59e19a999237d4aead1a16a7935d89b7d0f3f4bfba2cb236a0d5d3d549017b40af4577b27f97ff5b77655d5d3e576fc38a6fb20914d14117964a995ad1d4cb3f8d58ba421ec05cd986bfa5bd9bd9ee5341a424c411c3a2abdb2b5b84a4d0820f1a0ea92be52ff00267ed5d8b165771f55d0ff0019a1a07925a9c72c8b2b4d095322c902c67fc9fd3f8d2413fd3e9ec6fb4f3409ab1de444538107fc3d47fbbf26aa832edf282c7f09e27aa57dd5d79bcf65656ab0db976f64b1592a295e2a8a5a8a791648dd09040001d69fd185d48fa1f62c8aeade601a39410474089b6dbe82be2dbb000f4998bef25029122a89816d7f6d1aca4b49c0d6628c5ddc01604836f6afc4aa15a0a7f83a45a1c1c023a554b59362e1a797f87981a752e8658dd5165a72229c18f86765717f5587d3db658b01ab207543ae33a9906b27a51e2b355c2a293731cb7dde4a95e36868a2d693d3d3534ab79e103d31cb08506dfeb11efda62d0540a7af4ba09a5ed91d871e1d6cd7fc88be6261fa03e567543f64672aab7aebb437262f616f2a5a1c953e35320d599ba0aad8f57bc31d5a3c592a1db5b8674ab8a5882380af1ea1f4254638e3b9fa90a71db8f427cff3e97cb299e2f0100fe2affabd7afa9823a488b246caf1baaba3a30647460195959490cac0dc11c11ecdba24eb97bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebfffd2dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ce93fe14aa2831bfccc6aea292a26acdeb92e9fa69f22d0ba9c660f6a4d83c5e1f6aad5245219172895b49929a62e141825800b8e7da40019e50bf19153f963f6f46d6a5840091da0e3ad54f7257d66eba3c46265aa969b138ea4c8c114cf2075c7d0432b55e73266ed6d7552e955b9f5580f6a51b48259f23fd58e9f90976689548af9fa7412ed0c04db9f2cbf6347554f858a411d0b1a711547d953b11f74e4e9511916fdcfd3e46b73ed3dcbe94af16e3d6a0b6d7220ad12bc7ccf5711f1f7622d2ae2e4975c3454668169a8a9f99657765656aba8b105dc9be841c9fa0f602dda62a1c3392a41ea43da6daa17d1683e79eaf0baee81f33814c4ba3d051c90986514ced1cb2b34035b24b1f919650a6e6ed707fdb7b8f6e1c3d509c7af5256de82221a9561d19edad4512d2c14b12ccb4d4a16960b317d11c602a9998d8bb381cdf8bfb4150703a38a509f539e8c0616911e9522898c6c3f74f8f4dc2dec40e39008fc8fafbd509edf2e99249343d2c68a36d73146b84b25d6e5cde351a0b10401c13feb7b5002e69c41e98602a09141d2ab1e0092279a25b952a110131b107d37fce8b0e47f5f6e20afc5d25900ad070e845c76356558a66fa8f55c6956bb0b717e749b58dbebed42c0a4eaae7a4cd332d5464f4b1a2864009058afa4282de952381c31fafe7fdb7b568be87a64d29a871e969884f2162edaa12427e91c9005f5286b13abfd7b9f6f2a64f4c39c81f8ba53204200d1fe6cfea36bfaaeb61c0fadbfd87b51a49c839e9863c71d4c4320e15dc6ab5c117045c9d4430fa11feb71eec031ed1c3a6481c4f53e193d36fd72721c3aab161ff000450348b9e083ed40040a533d30e403c71d64f355c6c02a2705645702427936311b4a40d007d473eedfa8bc173d6a911e0d9ea4c33c85e4322c7296657e236610abf1a41770c4283c707df81e3515e9a70001a4d3ae35730285094018dd4f8550861c7a5cddc2f1c0ff5ff00afb69cd6a3875e51420e7a4a54c2b249e432c9750c82225bd1a81b851f917e7ebc7b48eb51c7a54b25005a74d7e5014dd9a3fd4b661ea720142aaa7fb26f7b7f87b62b4c74a08d4411d272a042f3fa98191085fa16d173fd98d895bbfd2f6bfbf74e69007499ada54804a834ca8d29b0b1768f826c49b81c7f4f75a81c7aaf9fe5d06797a12d5d0b450c6604916628793a80207e81ca92c79faf16f6c3fc40838eac14538f77497addbd4b66d016ccec01524d9a462dab8e4824dbfd6f7bed0bd5949a10454741fd560d296b2798c24b696d6ea2ec4aa808d702e3d3c1f6d6a6cd40ad7a7962d62abd065b9eae8e3aa831b5416193254d31a68a5607ef05358d408c3584ac91c97653caa8bfbf0bb552119b4b1e1f975b164594c8a95d273f2ff57af55d7f243e1d751f74e3ea6a72db5e829b2a91c8b0e628a92183250c80963796345f3a5f950d73ecd2d3769adf1ace91d209b6d8a773a97278fcfad7e3bdfe170e9fcf55d5d1c7055635df4d04e0d651d5533fd44f0d5242504eb1937493544cdfeb7b1d6dbbc1b848c7895f5af403dd7678ade47645efae302bd10bde7d61506969a0c6d5ff001612d4cf514f3d74b08ad8a7913c669328c1fc524b24b085ba9b39218017b7b14c52a01c7cba08de59934a7967f6ff00ab874084981c85237965c7d6e3a580490e4a8993c124415842d2c6af6fda42e09b7d40bfb521948146a9e8b1a075048c0ff075719f0537774a6d0aee9bc4f7df5f4bbae8b6cf6950f70d1657119aa6c067aa70782c557ad66c0cd643ed6a2b64c2ee5abc6d1b524365782a893e458a67f6dab0d4cac8346a1fb7fcdd6da2755560482053fd9ebe89dfc863f984ee1f9cbf1833549d8f856db3d8bd65b972506371b234ef1e53a9b3595c93f5e6531935533d4d7d0e228e95f14f3bb331928c6a3aafef50c8e649e2900146aad3cd4f0fd9c3aadd441045221a82287fd30e3fb78f57abed4f48faf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baffd3dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7cc0bfe145bd96b9dfe6d3f20a970d432c4b41b67ad3a9b29590c8c18d5ff0000c53cf2a90597c8c2a196dc5ad7b5fda2b7526e6e580a55a9fb074731158eda162d56f21f9f5afcee4a5c5e1f154f4552ae31f514b152d7988ff944d47157545555d1d1bb8d60d6ceb1c3c8242f3ed4ba9124617d3a79183c6e6b4a9a74b0eb086aebea5669e8a0a68b3353141454c87c54b8ec2d1d422c74accdfe7195858ff66e3fa93ecaefe711d680fa7f9fa34db61f11a8e3870a67aba5e87c27dd61b118da18a38e33511d754574c2249bc7fa135c81596344b5916ff4ff001f71cef138fd6d47b8e3a92368b6d4b1855fb7f23d5b16cfc3d251e229a9a983934f1431fed82ab62a0c8c105994b30fa9e5bd8225afe55e87100d25479d3a1cf6cc4a7c968dd0246aeec470b73a2cc05eecca3e83fafb6958d0d3a5990575703d0c1b628a68a379a6975c5390f09171e34fa016b7040e3fa1f75456524bb57a72668c850bf10e3d0938ea0916466b86f24211559acaac9762d636d2ccbed4c6349f9745f24a2a140c83d2829a978f1eab84742fa0f3a4804d80fa803ebfebfb7cb019e93b161c381e97b41510c71aa9938bae80a0922ebc2db9ff0063ed645225307a40e8dace0f4a45f21467a790248ab7b904a8e413abdbe08c15fd9d5c04ae5bb7874a8c2c8cb4c595db5abb39666628ed6fd409e0fd38fe9ed4a32af9f4c4eab515e9614cee5039259c26a5624902fc1361c1737e3fc3da8415248e933d380e1d3cc13254162ab2372558b8d24b016016e7d407d6e3da9518e904ba80f974e6a9fa6de35fcab486c2402f74d4a8196dfe3c7b728303a62b8c8e3d76637d2446e355f58d2c591c5c86001b2937e79e09f7e341c0f5ec56a7a8ecd30010f053d2ec05828d5c6ad06ebc8fa11f4fa7b6c139af57c1cf9f58a56d4c8432b2485974ab02a0f37b31e471cf361edb75cd49c75603c8f974d552f1239b18c3f8ee06bb335b9d4031b83fd7f1ed33d3d33d5d149e3c2bd242a2b692a5cac750af25dbc63ca8c548e3d4a0ea047f8f3ed23115f887460aa5467a4666eaaba9f4d45023545c80f77b369d562a587009b71cfd7db6ee540a2d474b608d245224341d619eae47a5591e2f24a5430490e960d6b7a85800d6e39bfbabbf6034e9218ff508ae074c92c2afae464556952eb19b831917d61481c283cff8fe3db2033135e1d39a41141c7a4bb53f2f1b36a88b82b726fc8fadec2c01f76d3f675ac741c6e013d3c8123899ae5d0b16608cbc90415f52916fa7e7dd242ca0002bd194081d6b5a37cba05b772a55c94f535d4b0c32e3c490d294d331479d744930974831974e0e9fa8fafb48ccac4349181a787f97a522374560af5078f9569c3a08ebe3d4d2a48978194a10ede9775e7d209badc0e0fba173ad428a8e914881b23e2e88a7c9ed8f84dd186a8827a37ad56426658648e3ab8a68d99c4940ce04734da07e86235116fcfb106cf77e14d18fe9007a22dcad3c489dcc60c8063ad6b3b736e53ed6dcf9f9569a77c254543632a2258888a6a9625fca88b6549045196703d4847d38bfb956d6412ad569a7a8b3705f09db59001e809c7523d16e8c460fee12b70555593c92bd598aa44d8bcac3095891a62446d1f207e91abdae720e9655a1ff0028e89d5493a4d0c44f43e6d4dc991eb3cee373556bfc521ce55576c8fe1cd007aa5c4532c901a7a95ab8e68e9479a4a796297d2ccde91e9e4388da81603233c7ad3c813b5d68a456bd6d71ff0009b5f92f9ec4fcd8db3d2f1507f0ed93b8faebb0b0f4ab2c6f16469a59ab22dc876e55cceda3218bc56e5c749538f717f1c7592aa9d07df9478728153427fc3c7f9f549e20d6d2b8a6287f663fc1d7d00fdabe89faf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baffd4dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7c9d7f9e2d34fb6bf980fccc91e7ca54ee0aef94997cc07ab12b983058cc6d24d8e952475f2ac124f548915aeba53836f6dda0d09206f88c8d9ff0007462e095849c0d038754ed95ca47bae5db00422412c75b0474510479db254d26885e7d5778204966f212459957dee52c7fb307574a2170ba14f9f1f9f4617ac28619778ff000ca6114f45b7d21c723b14929e418c064ad95893e26492a19d8deff41ec39b8b1cebe84fb58a0603d7fc3d5dafc7ac4d66616932950a69e86a24a6a88208d628629e9a20129d62822178a187492011ea63ee39de9833161c49ea49d881500d2829d59f6d78fcd4ba02f8562589c98d38201006a278bd8d893f9fc7b09b54139c7430d3a6878f431ed908cc88102893826f76678cdc9d43802df41fd3dd035085ae3875a6d5c47436e3a011450aa36a5d5678f4dc20fd4bcdc700fbb807d7a68486a7d7a52aa3d624d053698e68557c52bf28242d7174162dc8e47f43ede01980a0a1eb49a43297caff003e95786a79123bb91e602cf1e8b6a97804c77b1d07ebfe1ed42c6389e3d2695c12547c35e9554ea91151e370c48e7f3c8bdfe9f5bfe7dba15568699e92d789273d2cb1932470b13a1c7ab529b1bb7041373eae17fd8fb54a28a3a4ef96e9454ad37961929e768e3508648b424b0caadc32b83f4651c8ff001f6e20ee19fdbd36e57baa33e5f2e971451c5c12eac0a97d0cea51587d05c91cf1fa47d7fd6f6b612b8ce7a4533b7d99ea6a2152de301c297f42856019ec5b495246a23824f1f8b7b561ff0060e931248cf1eba4915d8452de222c14580f51b90a012c1828fa8e2e7df831278755a53875d48d53106658dbc63d5add5505bf2472c54a1fc7f4f7e62cbe58e9d5546c573d658658d4bacd3c71061777d40c9720150e9737b7f5fa73eeaa4107511d36e090348269d775422954b452c52004173ad230df46b2a958892c0ff89f7e71ac769c75e462a7bab5e9b6a6920998c738731b2158de361a90480d8ab0be91abfd7f6c346a49d471d3f1bb2807cebd170feee6531bbb21fb284ad2c6b542aeb63048ac591da48ccba9c8924426c1801651ec8be9e68ee91523a462b53eb5e84bf510c966ce586ba8a0f4e8426a0d71c91b878e2940244648e6ff004b58f3cdff00afb3064660548e8a8487563874d32d0bc7a155bf6d414d12fabd209d2ccebcb31ff1f6ce820fcbab3b83c467a68aa8a50cc8224b950a9a5efa8dcdf516e0717e3f3edad1a6a074f269a0a9fdbd354b4326873f6ce1f4dd001e9fa91626e7eaa38f7b1afcc75b250d2b4e833cdc4d1b3eb57121bb02ea34806e00b003e9fd3db721af970e96c1dabf0e3a0777553432c324a8610c222ae8a0de4720f2cac3eb6fa7b4668751f3a757f1594b0fc3d178ca45345219ee0041229466b6a008d2e39b2b2dbe9fe3ed85ed615e03a6a818107ccfece8af76bc42b62a85312b53d524d2181dd7cb475d1a15825474b1319b6a36e411fd7d98da8efd483351d2590d1581f2eb5faf965b56aaa057d2486a6941a996a9a96c3c52d7bc8911a98644d05aa648e4041fa15bff00adee50d9266744a926808ea2ae60853c5a6284f01c7a20d062ead37050c6118c186a6a3a596a4d309a54a2114892d34ea14ab08eb240031048363f503d8b0c6a23f9d2bd057518dc81c3cba14376eebaf7876a552e271a1b1ad5d8caa9a4a4a86fe21412bc7e179a1132c5512c171672be4d43ea3db51034634fcfaa5cd5940a62bc7ad9e3fe13efbc69bb13f98afc67a0a8a23367fadbadfb076de5b278ea46fb0976dff03abdc9b2e2ad442c28abb6f4d57558f2cc01687c7a999adedd0402895a9d5fcbe7d372d3c09981c5063e75ebe8c3ed4f451d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7fffd5dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7cc0bf9f955ecccd7f36df937b7680255d5cb2f5952cd134ccb0d1e664c35253e72b2561756686aa45f41e0902fee96eda9a703807ff267a3215f06124797f2eb5c9c0d35261b7fd7523d698936fe437d554d1ca08d10e2e49e3a6891edeb8e56894585bf5f1edc9890490334eb76e293a006a327a335f194c597c998678b4c2b34f5d9395fd114b0b485d29d5989e1cfebb726d6f610dda6f081722a4f42dd9bb8304ce7abfaf8d90cb5b0d298635a7a7a7458218950878db51920a688b2fee1480af23f493ee31dd25d52952686bd4b3b3c2163522b4a7563b84a514cb0a4910266d29652fe967375370753594deffd7d9193923cba1071507ce9d0b384a76a5f1aaea0de526e472b7604dc3716b73ee841a8038d7ad6aae4f0e866c581281a4db41019585c301fd0022d7fadfda88e992787491b069e5d2d71f001a19404799c9663ce920100820dbf48f6f06e3a7aad09ad4e3a5653200cbca49a09d326a612a9616f428b0faffbc7bb2e9ad4d6bd30e00c8afd9d3ad2d339732bcb359a42ba64905942720ad812757e3dbc8031c1cf5a6d34e02bd2b69695810aa15c9008d200166e3d4bf91cdf8f6a968b81d277002d69d2cb1f4851549b954501ec4806d63cafe9bdc0b1ff000f6a5145093d2466ae3cfa53c04f882fa4301e4b68e75726d238b588fc7bb8c7963a4b271ad7ac65016d71da13704bc44a7d0eab8504a93eecbea307af56a06a008eb8ac350c5dbef27e1d1cfa940b58d982d88045f8ff001f6f28918d4b9a75a25147f663a90b45fa9da4a86f270cceec0b31e41e1d81247d471fef3ef7a0e4927ad093c828c75952912117314497160436a7d5fd977218e963f904703df82d298eb4cfaaa2a7a9d1a4874191039fd05485d0cb63c31e186a1ca9bf07dbc07991c7a63028074df3a0494a699550a6b4005e3656360d7fc9b9e47d3db2ea5723857a52942b91e7d26abe1093a3c68f18721ac4d9351bab328e6c5cfd7fafb68ea208f2e94ab0d24115ea3d482241222a680a4305d2ca581b5c8fa0207b6e857aaa9af6f495c84c00b2c6f6d76760a0e90e3eaa2fc58f078f6d3900569c7a531ad4e474cef08611c8249230a7c82c035c03ea46fa0e7fc0dfda724d413d3b4a541153d44a97554f224e636617502e5163b11cdc12bcff00bdfbb16c70cf5e5eec53a0db38249a39d4690cea543b5d83480822cc39161feb7b66405811d2e8f42d3d3a06374c48f43a882644e650b7b824e9278b6ab11febf3ed1945d2d5e34eb4e6ac7f84745af2b0b55d7cd0a49789e3d28e3e9a4fa59b4f07582bf5f691a9fb7ad16554af1cf45bb7f60aa03d5254bacb490acd241508744a4850a430e01950b6ae4dc81ed740da49c8238f4c4815d2a064f54bdf23da832b5353b7b2c8e208d258d7289088e6a6a8f2958a3959bd4053150d71cb0bfb91b97240f1212d92dc3e5d47dcc31773855cd31d57c52ededc9b7331d814553aea21afc0cd83a6aea445991676c85186aea6998fa208de30e1bf57d79f6370c1582915c7405642cb53e47a0e32399fbea0a7c7fdb9abcdc7919e5883c9279a825f29699a9215229e5a5c9088332bddd1901fa9b7ba85d2cc5ab9e93dcb8d2a8075b36ffc24f0e7f21fcc9b754f0464e360e8add95bb82760e55e3a918fa4a185fea3cdf7ec0fa8f001b7d7df86a5b98852808351d2495696cfa978914ebe927ed7745dd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ffd6dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7c74bf9a476966372ff0030ff009cdbcb255df75909bbaf7852e1abe25240a68b763e3e969e3914ea8feda2a7b1fc6a5b8f692cc911b90725cff87a379d915624047c23fc1d568e4336d94df55b9596a19a3ada5fb23042232ab1494b024d575440050bc8b6d372cc4dcfb79f510c4b67a6928b286afcba36df11a1aece67697134b0b4cb59945a508cac5647323c94dad233afc29372dcfd40febec1bbfb254353b3a1bf2c8d7a969e7fe5eb661e89c043b63078da45d4d511d3b01532f24cc414a878cfe4f9948bf37ff5bdc597ceb24aee320ff93a982c93c38428c746f28ab24967a0656909a631f0ac15a4002fd34d96c5bebfedbd9603e67a3250003d0bb849dea266723505e7d44dc92d600ffb1b0f77152c31d36c00f3e868c2420c51ac8c51a404d96e0afd2faae3e86fed48534e929c1278f4266369a2701510d86900bdef7b5ae01361f4f6f24649a79f4cc92e9001346a74a5a7a649255a6fb7662fc190028a84fe49001240ff001f6f888614035e99d469aeb9e9594f84810941aff429b067e6de9b8e7eb73edf5817e7d30d296a57fc9d2920c608513c6ceb272c4dc8e00b589e6ff4f6f08694a754f16b5072bd3e52d34e2c7eea44fa6b5d2ac3f06d7d372d7b8e7dbc11aa086a74c33212689fe1e9f91582a86d2c9621750601cd87a99915ae41fc01ede0869ea7a4ce4139ebb0596e5d55871611a9e0017058d8dbfd6e4dfdd950f970ea94f21d738b5ebd2c841e0d980564537d36b8b9b9e7fd8fb50a003f3eaae2a38e7a728501015a3776b1b85400587f4d45587a4fd7ea49f6f2d07974db56b5f2e9c90471a16f1db81ab5b0bb5f8b10e2c6c3f2bf4ff1f76c798e9825aa013d7521860b176010dc9881f2a1361c47a496d607038fafbd5287fa3d597537019fb3a82ff6ec02c74f501356a5d51a848c372e3413ac5cff004b0f6d3b038a1af4a14483891d305647348ecab102818a232f175e083737652ac6d63edb35f4f3e9e0c0533d27258674668de250a1acb1ab10c01fa86e2cc41e7db0e0934a63a500822bd306629aa1b4bc0ac6eea8575597490437a42dcb5ed637fafb4d246d814e1d2b8596a2a453a66a78aa23f224d4e7d24954bb71f4e4ea5b7faded908d9aaf579da3ad55ba68ca53d4c65de9e3632b10ca148642b73a81040fafbae8635ebd13a134a8e90d97f452cab20092b9d4aac3fcd900870a5485b7b6cf6835f2eac1cea241c745f779ced4cae16ebc8b2683a5c30e45c717bfd39b7b452b71a0e942aeb52f5e816aaa788472491a78d9235d02e0e80f25e52c4fa8024fd3fc7da43c09e939aea209c74176e7c5415b8faaf4a48a56499f51e4484140633c9bbdedf9f6f447b2a0641e9ce04a96c53aa44f9b7b51f131d5e55228e349ea61390593f6d12a218cf85c10a4aad4101881f4f638e5e982814275d78740be6281989907f6671d571e1f2f366766652aea1a79aa61dc14f4708a65911d29aa4d4549a75617d692cd2a90c6f70bfe1ee4c88eaee6e351d46d206a95afaf45eb1b41326771700094eecd5895664d164484fdeb966624ea7590057bfe6ded480d4aeaae7f9748b43a3a00d5fb7add97fe1225d3f90ccf697cb0f91356b15263f1db736df5ed25242891acf95ce57b65aaea511785822a4c35811c1694fb654ebbb933f0a81fb73d377a74c08b4cb37f21d6f59ed57455d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ffd7dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf741277e769633a43a4bb63b7f32ccb8eeb6ebfdd5bc6a34287790e0b0d575d042887867a8a989100feadedb99fc28a493d01eaf1a7892227a9ebe27ff0021b7865bb03796f4dd39092a67ca6fcde95db96bab554969a4cee6ebb392ad416d2cd3402aee403c5ff3eeb630aadb2073460bfecf4b3710091a47763f97f83a03b6d52c95b98c8070e2399646121210054422226c395b28ff001ff1f74b9aaa6719ead608667557c1f3ead53f9736c4ca66376cd93f0cb1454b23b535680c91c735633c533201c06448fd04716e7dc79cd73a2a9553df403a943956d1c4bab49d34eb630dab4f0d2252411974fb38a1822f4fa16286303d4786669642c4ff00527dc6723e49ae7a94d508519af430e16ad1e48142eaf1b1005ac09522feafc726ff008b7b6056b8e9fee00faf43f6d52ae50c71104ba8656b8fcea66b9e000bc7b531f9f99e92484d08639e87bc352bbca80a12d3465d42292ba0587a891652c0fd2fed622104023a60b80b5af43061b0ec34380cc411a94b0163a47007018fb5e9101424f45d2cb539a74206371ec5eefa7d27f5720b722c493c5f8b73edf0be54c754d5415cd3a52d2d17ee8f22020b5d402751d4d626dcf171ede48c9e3d34ef506871d28063082aea3d4ca2c0ff006c5ec16ff5078bff004f6f08b3815e98f12b8f21d4a5a68d435ce87b5ad6241b7f6430362493727e96f7711035a9a375666a0141c7acab4de46b46dfb4bc2baddc794f25750b80481c9fc7b7426069181d26770388cf58e4825d3a4dc824f0a4b693fd781a6e07f8723ddf4815038f5aad54e3af45130b06791869d218956208248563617161c73c7bde9ae08eb4587970e9c57d042f26c416d4c194b5bd2741bdfebf83eec16951d3640a9f4ea422a905444c4071fbaa9c8fce8e012bc8fadedef61403c3cbaa31a703d481eafa95900faf1a4ad89e5590022ffe1ef74fd9d5452a4d7ae5e216bfa981e35926c6df4176278ff7af7ad209c8cf4e06a52833d46a8a6063d4222194eaf51001d3fe00dee01f7ad2b5e18eafa8f0ff000f49aadc64924c0323089d5650ea41706f6f5fe08b71fe1edb68ce3a7e391547f4ba89558f0a4dd4285501ac09b807d2c00e6c41b7f87b6993ceb83d5d65342470e9355f4a602580d2a013c7d08b71f527965ff7af6cb81d3a1b50e92b91a7a99d84c8caa8548d2834ba16170587d4823ea3fc7da42ad5d54c7560ea29c2bd05fb8e09d63958a7aec468402e00372cabfd4a8bdbda5952a1a9f174a2370450f1e8ba6f4a88cc6aafaca58ab0d246a0a7824dc0241fa5bd97b0d45ba5313769c8a57a0272952d19aa8bc7ea9403afeb74006a6e2f627da53e63cfad8d058b5723f9f4c0f426b29c52c01dd27556578c963a40d66e7ea0822d6fa9fa7bb440b395ae3a6ddd949661dd5eab23e7f6db59f6854c748115e196a64ad7962f5b454f426732d829b303f4b9b100fe7d8d396885ba284f97418dfdb5da0623ccf5437d59535b498ead4857cb8bdd190ca53d38d21a08b29492ad36364469092da69e67175b72dfd47b9461241a1c8ff2f5195c020d48cf49edc1b432c95f59997a57a3a5a1cfd763eb612114c30cb8da7aec7cad293e314b3410abe904f1c73f4f6bc2e9518a0e91f880c9a456b4af5bd27fc24632321d83f29716044f10aeebdc8c9551ac8054d44b4f9a4240ff00331c712b5940b31b93f4f68edcd6e6e3ec1d31b80fd287d2a7fc9d6e57ed77453d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7fffd0dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf755cbfcdb3b70747ff2eef941d868d806acc7ec34c66369373528afc3e47219fcd62b0b0d054d0965fbc69d2b58a47700ba827807db172c1623c2a4802bea4f4a2d5754c829d7c8cf724d154d0d44d5d4f4f539d9f1b98cbfdd9104482bab6792829c4691858e3962a58ae2c3926c05efed4a6bf0692000ff0087a5da2b2835a8f3f97e7d035d6585aac86eaa2c7885fefaaea052c51ba79163a658da266922b9d2f35493627ea39b7b2bdca409133530057a57b7c5aae9230082d8eb63df86fd3b2f51f5cd1c35acb5192aa6832b2b2ded009d1dbede53a035a3331fcdae3dc35bfde3dd5d391f02ff00a8f5376c365f4d0a0cd6838f560d8b596a228aaa163695d490afc114d63a41e6e0804ded73ec315e240cf42b0c141565cf42361d6657a69f430fbbb3ac6096665594afa52e02c96e4ff5f7708da6b4ede9a926a0600647468f6753c91d3a19497774131b911a84d5652faac02a5edfebfb5b6d09620d33d2069000b5f8ba30781cc50d1a78e3227a8e0808c18259574a9e490ba4f3f4e07b33d2067a68a3bf1f87a1970f94a7a88d1e246bbaa826ff00561605c0362013f4f6b514ba8614e92ba95ad78f4b2c7ce0991c9d4350b8be9556b8e18f3abfd7febfebfb7962258134a749d98fe5d2b69a544915d9d563216c5bf502cc005507e8037f5fadfda8115284f1e93c84b0f9d7a51c20cc973e91eb6ba9d2ca16e41523d36207fb6f762031a70e9b52aa73c7ac4c639156346255816e5500d241d4be9034ba9fe9ef6116ba470e9cd54153d38d198b434657c3e34f5681a1588b28016c156e3f55c736bdfebede14228dd2596a3cfa96f4e9af52ea4f4afeab8d1a89048907f6effe16b7bb68c76f4d86eb0b533add42eb4163a80170c7fdec01c8ff000f7ed39e19eac5aa0927ac8b48eeb605759362ccb75455fad870da87f4fe9ef7a33d37ac79f5996378934f0cda8a0643a59d7faa0fa8249e4fe07bd946a1f4ea8483f67531696c0128500e3d048b93c78efcfa81ff006e3ddc21eb41e941d6410332fa8855001d26e2c0902f7b702df8f7a31d0f56f129d7885652b2b25a320a3daeccaa481183c5ad6ff1e3fc3deb4d70788f3eb6091439e9927ad413112056529a105b8043581bdbfb43fdb5bdb47ca9d3814f497cbd5c61c2024300c1c1ba8f51e00b9fd5f4b81f41ed97e34e95428684f497af1e6a76f2012b2e87f1d8a02a08234b0bdef6feb7f69a44a823cba7a9438e9b67a9a76a752625576525579bdeff004fc72a78f7bd074d01edea803f416e78abac8023313a834b73a437d554f3aaff008ff636f68655e2aa07f9ba7c540ad3a2bfd811914d2b8e1924d31d940b90082141e010c7fe27d96cd11035035f974a63a9238e7a2d93d5fee5507768c8d2d193ead4e010c2c41d2b706c3d95b022a5be2a74f532b53e7d48c5d514a64647d28f10574d3c36a667b2b00191c91fe161edc8188622a00eb52292cd518e8a7fc90d890efedb3b821a8aa34eb0e0f3550eda15a436a47d01998dcdae57936b1f679b65cb473a69142c69d14ee30acb04aa4f05af5aba6f182b361d1ed38b16f3b1a58867129d81484cf4d96992546600595d23526e07247b95eca767209ea31bd8740a139e854c06e8c4e612bb69e64a5551e50c59faca67467952be3a47960820993d4c2962acd2e838d201fa0f67b1cc0232b9a91d12c90ea70ea483f2eb763ff849e67b1f8fd83f23761c7362d2a47fa3ccfa5344a5320e160cf524b216601a6a758a58ee47a437ba46ba646ad3201fcfa497f52919a702475b847b51d1675ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75fffd1dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf754c3fcf9f6dd4eeefe5dfdbd869aaeaa8f6d51637fbd9b95e861a496b2a23daf5b8eaac2d1c1f7caf488b3672a20694c83c7e08dee4707da7b9a040cdf0835ff00374fdbb697a8f8bcbaf9327666e6a9c96eac9d3d1cd0a52d150344f4d04b1cf043262b232a53fdb55698cd546598ea905bc8a6e7dbc8eccb9e8c2593c3d223a5695afdbf2e8c27c5cdab4f9eef0daf20a648e11454b34a1434b1cb573d3b4c82471736f23d813c7a79f641cc52e8dbe4a36687a3fd813ea2fedc95a9af5b2eedda110bbd0c251a35a382220a13a6e849605401ad9908d3f4039f70ace49a93c4f538daae8fb00e841c2c1594f5359004d1414b4b451d1550725e569417aeb80469f1bb055b7d6e7d96b29182052bd2f775d20fe2af4f35dbaaa70beba18e8e4a984abf9aaa7611d1c318ff008e7ad03cccc7e8cca00e7dab45aad3f2e98d2198861dbfe1e9ce8fb7b71cd4f49573cd04d431830d4d50ae6a5a088abeab532c06412c9f9d26426e3fc47b588ac856a415e1d69a08c86a13a87fabcfa1a7adfb3728a8f571bd41353501167a8962a3a5113950016ab65ad9c03f4214800f1ed64285549038fece9b92451a55850d3a35bb77bab151c8687cb1642b9d00d31c89e28a6472ac249a6652c88c00361f5f6650c9c108a9a748655d5fa84523e86dda5d871e4854a3ca9195606d4fa64853fd56b95f428d36e4dcfb5094ceaaf48e6016854e3a1131bbdb135520856b2964751a5ca5444412ad626da89d40906c6d6f6e0a1f3e986d43241a74bca1ccbb5d29670516ccc410eb62bc9520921816f77a6a3d344ae19867a9f1d5b863678963008642c3583fea869e2e757f883eea7b4d071eaf8619f3e9d69ab5644d7e4b83c0537b305e0fd7e86ff5e79f7b56f2e9975a118c74ed1d7370adcafd001604abff00649e785f6eeaf5e9a2a3cba9695f1dd134173c82a0393f5e1aff009b2fd47bb2c80903cfaa18ce7d3a7888c6c2e8ca140219518308c5acc1979b30bfb747af974c357d3ac9aa28ef2120dac57904853f41c8e188bfa7def1e781d55bd079f51e6ae58d55d4dcea2e2ff509701ae16c3c963f4bdfdef58000a75b4425a8dc3a669b30c4b7a5c5c15e3fc4ea6bd8d9028fc1e4fb6d9fca9fb3a52b08c1af49f973de3ba7d183395562406d4073c5f55cf17f6c17af9e7a522dce08e1d27ce77c970923f9954a33bfa34b3124e9d4d6b2fd05bf1ef40935c67ab98c039a53a4dd56683d4eb6612784b5d640a642d6fa9b9f50bffb1f7b2b515af4f840168303aea4dc48caab55a11189d414a95d5c78d081fe6d7fc0db9f6cb0c63874d7683839e92957bab10934d492b04aa1aa57b5caac0df490b286201b5aff004f6c12b53c6be9d3ba5ca870069ff2f41eeebddf8e8a076a4aca579acc4c09280d2055bb6852419428e4e9e78f69a741a752d31d6a37d4c51c53a2b7b9377e2b2f3c98e35312555e491e291963a81110551e30e74ca2ed62ca6e3f23da17d24846a507ede9721f0e8c16a29d17dcb24b8f4aa49e567969d5956636d73451bdd3480a5410a6dfebfb2eb884e40e23af070f20a6149e99a973295b3345e6d2a691dc05054099542888aaf2aca07fbcfb40469614e95b4545041a9e917ba3c3594d90a39d5e58de8da9ea6392dfbcb20b012a9b0280937bf1febfb34b3701854770cf45f73082b5f5af5adafcb4dbd49b6f7be5636a581608a6aac5d0522472450ad34c2a6abccb6d5188e39e654b8fab06f727ec329752091feaff0067a8d37d81619282bd138d9b96969f7aed286864690ced0d055d4c8d2223cb571b52546a939d50846552df85f62d442c75741a7602a299eb7c3ff849ded7a8ce63fbfb7d55d4d3d2bec6a6c2ec48f1f4d4c91cf95933b5153966afc9d5173255c78c8b1a22a4e005599ffafbf44434ec0f1414fdbd20bf3a6345070c6bf653adcefdabe8a7af7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7bafffd2dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd146f937f333aabe3153d0d06e496a3716f6cc4267c46c8c1c901cac94b7d22bf232484c58ca067e15dc1692c74a9fafb24ddb7eb0d9c22dc316b86e08b963f33e83ede84bcbbca9bbf32caeb610d204f89db0a3e5f33f21d531ff302fe673d73bfbe1b77dec2debd5fba36f653b17abb7b6d3d9591c54b8fdd50d1e7721859e4a2afcc62e5a685ce168ea69a27ac91524d110365248f64ebcdf613811c913465b813c3f3f97af427b9f6d37bb21e2c7247305f894541fcbd7af9967606dcdcf4dbf33ebbb71b474d9b93111cd958f14d4936365fbdc7475b0e431a6850527da562caaea102aa2b15b02b6f63381927895c302c4711c29d02aea231ccc0a1041233c4118208f2a747dfe136d96afed4db32cb10f22e269018a956f18929608884d5f477119058d8af36e0fb0b7331d1b7caa3e2a53a1472aa6abe847901d6c03b668256abaaaa74d4b1a4c4c760a7535a3552ab63a431fafd3dc3d709404f9f53544c050799e9419c9bf8553378d81955410884b3ba32029a17e84adb91c5aded22a9d54e3d59a4352070e80accd0546e4aa99325908b19410bad52c905249593cc9c5f595995564329e7f50b71f4f660814d0d7cbd2bd3b00142ed53f9f49faadb7b7e9a9a9f213eebcf8c662aa8169618ab258e99a5601e2fb48a8aa59e0942f29198d79e4fb571431b0d6cdda3ab497062074c752dd2b1372ecda7f19fefb65605935d453fdb63a9a8a67550123d5254d0d61590a4761a8017fcdfdacf0540d21f27ca9d32af2395fd1151ebd246abbcab76cd6c836c4baab5848292af31338a9a9998b33f964c6c3f6cf0692b206015b51e7db915bb00a5450fcfa6ee5d74b2b01abd0749c8fe5e77a63a79e8eaeba89291e1b4692d6cf0d248e143cb252d647396670aea5449172fc7d7dadfa4760692769f224f1e898ddaa3e93002479f0e975b53e70eebc1bd3cd93c95540b13f87c6d550d7c8b544fef473534822a9a48ef620904153c1fcfbd7d0cc8d4f14787e808affaabd5daf2175a343427a3c7d7ff003a72d353d32e46678d2a258904b4d02cd08859437a9e290bc418727573fe3ed3c8973116a29ff2f4e22dacca09fc8f9747dfacfe4263b7488617ab58aa268164a66925bc750a41314b03feae4f051ae2ff009fa7ba24e461cf775e96d59175a8aa9e8cde377652d5d341531488de46a56542a148495807ba93605245607fd81f6f99303a46636a9041e9750e54ca90c80591c93622fe96be8b5f86bdbdd8393e7d32505597a708ab81742a194a9b0606c41fadff00adb9f6e092bc38f54f0cd09e94705486455f100ec3d4fa881aafaaec410147b7d1bb78749ca9e3ab8751aa3282262a8e1500d5fb843ea6bf0da2ec0116fc7f4f6db4b4a8eac89a8770cd7ece92d5db86cf60fa5631212f1b68200b92401f5f51ff005fdb467a50d7a50b011fe9ba44d56f28a0ab923a8aa484aacaf1795acba6140cc5b921f837ff0063ee9f524b10c71d2958288085a8e91bb9bb0f058ba3fbbc865a96858a09555e78fc92165d65238b99253617d2391eecd2a05059bada2b93a11491d164de7f30f63e0a80c3459ca0a9ac66a90b4e068aab468c5e4d3269ba46aa4d94dcfd0027dee27626aa411d3df4ea08330d23f60fcba29f92fe62bb031d23b3e5619eb51256ada3fbb4964855588322345a6580122c048b6bfa49f7766723e13d3af1c6b55d602f974cabfcc5fac732f4a8b9c18fac9a6510171f754f36a240866a6898580d3ea2b761fd3f3eda248aea8c8e996809ad1d4823e55eb8e67e5553d6490e4e9ea63a8a44697c75549572ad34514b6d42972310f34457ebe09d1b483f4b73ee8496018251870e94c50471828ef5047f31feae23a0c3787c86c3e5e9e2928b235f86ab68d6ae2ab92aa3c8e3ea27f534656b7152b4d492b2a5c6ba711b13ea239f6d5c04910846a483cbe7d6a18583d4a02bf2f4fcfa066bbb1f7f6731753e58a932f24718a8a3ac6ab5c557288d8c91c52642922a8a747aa2de812c7e26361717bfb4542158cb4c8c7af4a4a22b294fcfd3f2e955d79dc75f9f9a5c565e37411cf1630c556ac992c555491288d72109697cd1349c7962768893706c6c116ad541a707a4d242a2920a83d0b58e97455d42451a89e1d4da74a895c3385943827508c20fcdcfd2ded2bc454d4af9f4aa363228eeeda7fc57e7d44dc54847dd31562b58506ad44deca2fa858dd437d07e47b76dd2ac481d259a55145a0a8ea867f98a52be3b7be26a1e93451cf401048a8da55a8d6a9a52230a0ab49e65249b8e2fee42e59358c8cd41ea3ee680030a62bd55a6db12c55949945a492a0515551d64c119f452ad4cf612316b332696163f404fb1ec6f55604e40e812e29a40f3ebe89fff00092d6a5ff657fe4aa9895f263b970cf515ec21134b4d36da32c34c347ee7dbd3339d37b8bb7b62cdf54b703cc53fc1d24dd9682d981e2a7fc3d6d9bed7f44fd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ffd3dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf750b255631f8eafaf2bac515155559417bb8a68249b4f1cfab45bde98e9566f41d6d46a655f53d68c1d9bf2bb66f66f7beeecc6f0df148dd89b9376656a22c7e4aa99244a0a6c8cf49458ba2f2b08a1829292258e387d3603817f78f9b95e25d6eb7324b36ab97738f41e407d9e5d668f29ec53edbcbf6896b64c2c563156030588a924fdbd0c753d7f87ed1cde172b975fbac7e2f0b598e5a0d65a8a739110ba4cf012637004566b8371c7b7238c3b86945405229ebf6f4f5dca2d61711ff0068c6b5f3c790eb54bf9ebd0dfe89be51f666171d887a2c3e5a4a1ca62569e9b450bc1968965aaa6a4b858d017562c8b6d3cfb93b92f70796de4b495bfb33404f1f97e5d63ff3feda91dfa5ec585980340315a67f33d0d3fcbeb6354d07636e1cb4b4ed226171a9052140abf6d59908c4b58a54f1a56e834fd7fa7bdf38c80451c6a6b56cf4879422669cb15c81feafdbd5d4606813edaad82bbb4c1ae4d95a326503c8ec3868c3fd3fde7dc597243139a67a94a22f440dd22779c724752268c3932524f4eba745ccf1a8f003aff6d358fea78f6883152718e97ac58a13d0289475869e592b6bb298b567902a40ab1324eb6bb3d4c11ce3c05ac07d393edf570157a528aa3b6951d45c5ae5a3982e373352d52f28925a6351a27f5382b244b2398e6d4c0ea0ca9c7d0fb511cac7e06c74e7828a0d63af43353ee4a8a54a77dcd4186a9852d10c8c191586a0c6a4f9bcd01a6117955b920b95b5efed5c5336b1af80f3af49d95a87c2143e94e8425dff00f1956923a6dc39ad9355512a18e5a093f85d456447d3aacd491a2ab2b1fa91c7b546f2dc575c95fb3fd8e8b85a6e32c855213feafb7a4b67f6c7c46de313959e891c28632c12cf572e8600a242ed1b1a6042f1a08e3e9edb96ea220b24b20fda7fe2ba50b697ca7ba0423f2e90755d0ff18b281862373d3c1907b2012e429ccb205034a4e9318a76604dd4daff00e3ecbdf700a7121afe7d5fe92e0664b1057fc1d2669be25d25056b556d0cf44d48eeac29e49e67d2c5b59281670a8b7371cd85fe87da81bb4ce06b96a7e7d50edb6c2986420797465b63ededcbb269a902492d43d14d13cd1317f1bdd82c82061c283a4691702e2e7db62ed58d452bd285b750a1159881ebd1f1ebdec432c147473b3bc750599255054c12dc10ccac4b110ca0abadf806ff004f6a96e13d7a2f9a12a59871f4e8e5e233c9510d12bcaa5e38c32c711256e6dfda160d68ff003fd3da8f101a0c7456532c5474b0a6af50da5b49207a6351c5ac181e3936bff8fb755c8af55d3515fe7d2b29a526220bb28056ee08d05825fc44be9d3a81e47b5095a31d5d246d35f873d263235de20492c3d76e7ea03fd349fafe79fc5bda67622b8ff57af4a6301beda740f6fbcec385c457e54d41630d1d4688b504692a352911a8e3fc2e7fa7b4eeda50b6ae1d298eaee88ab927a203d8bf21e9f1d5cc73225a0c663a92286ac440cb90c96632922ac5474846948e969a921324a49e2e00ff00149f50af5af01d1aa5aba1001ab31c7a5074403bcfe509a79ffdc35635664ea639668e9f1fe47931f0b2909fc4eadcb784328d2163d2f7363c71eec0adc48a0be94eb66b6a8c190bcc7f60eab7f72f627626f4a9643b7249565a91253ceb0e4057a2fa8d4bc957ad6258a6bd908e7f36f6730dd5a43a518569e43fc35e8b6e2df709c16d348f88ff00353a0d76ff005df714d92ab8705b76b7192649aa565ac9a92a2b2a1e095eed1c51a433b54a22b70d230e79e3da84bfb3899b4f13c3e5d36fb7df4a1751a7af437ed4f83bd93b8e9967dc759b8e2827a9fba922a8a3a989640b6292208a6591740baf00b7f5e3dddef21990a87c9e9bfddd25bb1d7202c7d0f42ce2ba53b0faf676a2c752ef5a5c5d44e824f255e60e2ea2ae3548da49e87f884b1b030a58060a07e3fa7b4cf2aa00be4c69fea3d2d8ed1826a5901d39a713d0f583cf5762f1b261aaf68d05751cd67a9926c25442f25546481aa18e6a98d2a3eb6651a9ac0f17f68cdc4710a30143fb7a785b48ec1d5c8ff074dd55531d15699f17512d13d6448e69ab29aa453d139b81189e28bee608cf21a2911a336fa8f69a5915d68b924f4faa15147031e7d2d31312e572b82cccab45495915441495391a748a2a892ae949961499e9af4b5b4154a3f69d824b1b02a41e7da32455694a0ff57ece9349f0b293d0cd492bc9b8aafeda3b563c85643a78a88ec19b41437b83703fc7ebee92a8623c8f4d40c1568ec74ff80f4b6cc62e49ea7ede2d490246b3708ccb66897fb5c00c1c5fe9edc8d6952064f49246d4c588e1d534ff00323d9e1e930f592c6ad2c54996a6a8a99256315c45aa1b28643fb8b21007e4d81e3d8db958d7c45fc43f9f40ee6650caae3863aa9ac1e1e27c766a38685e0269b14229aa1d75c98fa1a491a6a290c7e8bb55a2ad8f2c17fafb1baf6d7e63a05815e02b4ebe947ff09adf8f79ae98fe5dfb7b776e6c4ff0bce774ee1a9df512d4d00a4c8be03ed29e8710f34b60f514b32c2ef0920594ff008fbbd92f6c921142c7a2bdd640f3468382aff3eb612f6b7a2bebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebffd4dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75032a635c5e48ca018850561901e418c53c85c1ff0002b7f757a686af0a1eacb5d4b4e35ebe761f397e21c15bd975bbcf67504b4124b5999ca38a60c2632cb99a99a9e5429664280dd4fd401c7bc68e60b2a5f4d3c04ab71c71e27ae88fb53bf05d863b0be707006694a6900835e963f153e456e5c4d7e3baf3b0d9e3cbd1462971d9398b2ae4e0a5002c735ec4d6449c9ff556f6f6db7ef394497fb4ff000fcfa2be73d823b2335d59b56d5f34fe127cbece90dfcdbfacf1996ebcda5da940117290ee7c1f9aba3d2cd2524e8f0b53c8dc1317ac86fce927d8f3971be9b7b19a248849fb4758f7cde0dced2c0a8d50bfa673d154fe5e9839ff00806e9ae9da366aacacb5653531647c8bac91d3e93a9fc71c7668d9fea381ecd399a4d6e807cff6f447cae8234d6ca75756cdb73071cb1d5488c87f702ac3c8d2b00d4ca18d8b5e43616e2e7dc773649381d0fd4d5531d47cc6c56a88499d65286415222546214b0d29e56506cc556f71c2fb29604b1afaf4a566f506bd045b930af8d1252554575404c535c0692393d2449a410f7b736078f7733e8a64e9a74616cbe210c387449bbbf73ef5c1d34d49d73835abc9a698df359050b8dc5472108279ea2328d52135fa6252093ee897134ae121c0a8a9f41fe7e8e6286154f126e39c7453eb77a6dbc31149d99bc372766ef7ad40b4f80a5ada9fe1c950e541a48f0187643153bb12a19cc8fa4737f628b2dba193091b4d253f157fc03a0e6e1bb3dbb1d5a618fe5c7edae7aef27d97dcfd7fb13706fac37426dbdbdb5b0423a98e6dc3253c32d57967486082969d298d74c1ddc5d59869b9b9bfb1647b15e5bc625768a28e9c0669d02df9bed6595923333b92479e7a0c3617f33ff95d16e4c7d06d387a9b60cd94cc62f0423ce7dad162e861cd64e8e8d2babeb6b526929f118f9e54927959b4d341adee16e3d9eecfb0df5f0325adfa042d4d5a6a2bf6741dddb9b6c6d5920b9b394b71a6a23f3af460fbcbf980eead8ddbdbcfa4be42f57f4bf79e6b67e664dad59da9d1dbab1bb936f6e014f474954f59b4371d1c3053e67171a566849558fee46cabcadfda3de3976ea2065b868254ad012ba5bf98e956c7ce56b2b7876ad3c4e33a756a1f33f974326c0cd62f7263317ba7a3b7c6f4eb0c8e629a3afa2da9bd64abacdbf9265219a8560c933c057570cf04b717fa7b8ef70da52da4d4f03c20f022ba4ff93f6752bed7bcdb6e10aab18e53e78a30ff002f472bac3e45e53f8941b47b8b073ed9dc074c70e6e00d53b5f38c8746ac7644711ccc7eb0ca15c5f8b8f65451a3f8b2a3cfa319ad6a864b6155af0f31f9747936fe771b4f2d2565054452d3ca633aa3d0556e47adac6e2e0f1fef3eee93f864007cfa2596167a8643a8746d365ee257589d8de37d09aaf72a1781cf2587d391ecc609aaecc7a2a92dc0a8af461308c6b234957960f7b917161c9d27ea0007fe35ecc149f2e9235147a0e859a6a66148cefa481100da759054060da901b0f197b8bdf8fe9ecc9052227ce99e8b58b789f9f41ae7aa1208e68e3264645d0acc3492349627f035def6fc7f4f65d24832a4f4a9016a1029d139ed7ca495204266f0d30d4191aecde48d95d8d81b7ac2016fa5afecb6790ea51f83a36823187fc74eabafb36931795aba992a246550cf24a5fd28aefa839404dccaca00d46c7fa7b299a6abb50fe5d1d59c652842d4f44a7b037f74875a472b6ecc8e36269ea2f4f8e8616ca64e79c282a292829d26aa925723fa1e7ebeec9e21c2826bd1c88e571445f2e38fe7d2476d77aee4dcf168eace8ac94f8307c8fb877e1a3daf41244a4932c6b55e5ac48987d0b2a93fe03daa8edeeae1b4c2a028e258d3a2fbb6b4b504dcdc1d7e8b9ff63a504bf35e7eb39a8a9375ee3f8f389c955cb1c6db728b7465b2f90a7258dd679e8e87ed51a32b673ca8bff85bd9dc3cbf7ac8ac2f210c7e4c7f9f41db8de369c9d13e3cfb40fe7d282aff009ba74fe2abe9f0b55fc233f1b4969721b4735098596292d2c3434394869a740483c315d440e6c7dbc76ddda12d4449107a122bf60a63a2dfafd9dfb9ae1d3e4457f983fe4e97fb67f9887c76ded18a9cd4726d8a8ac9e67a28771e01aa29cd33fa607fbbc6d4b4492ba82cc382a6dedb985e41a7c7b3600e6a057fc1d2d864b69558da5f2151ea687f2078f438e1f78755ef9a3aacb6cfdcf81ac7a8897c11c35280a968ec258e082692a619350365717b7d6ded119d18b51806af9f4afc2b955158c98cfa67a027378ac9e4ab722b2542c94d45528b4b20a6c854ad6da22659079bd748909fcdac4fd3e9ed2996acd4e03f9f4b5e9e12f6907d3d3a79d8db7f274b51f7128114556116687ca4c8fe225d4982d2c685bfd53686b5f9fcfbf162bdc0745ec56ad8fb3a1ab114b534d94a3965666919258b5e90aa10d8a488a3d65956cb727f1ee8d25554b71ae7a4a5402d439e87a5c71921f38fdd912942ac6a6ea5825db9370df8e7ebcfb55177034e23a4529d2f41c3e7d549ff00326d9ed51b23195f0a218f5c9255bbabca52078658d84fa4a8d29330373fa783ec5fcb642cc4134247f97a0af3082f000062bd541ec6c3d74993cd6172eb490475b4986812bda5098fa5713432cf54ee078dd0c0cc4dc7258df923d8d67916289d99a8a178f413b5859a5550a4c84d3ed271d7d043a27e6f760374274775ff005ad149d73d7dd6dd6bb1f6bcd56d4f0bee3dd52e17074549559189d9644c3636baa23678a15bcba1aeed7f480949cdac5238ed134c4a3e2392d4e34f41d0c6dbdbc881967dca5d77321242ae156be47d4ff21d1b9d93fcccf2143bab0380df3b629db6e4d53458fc965e09679733490cecb4c327359529a5d127ee48a40250f1cfb5969ce11bc891dc424292057edf3e906e5edacd0dacf7367741a4504853c0d334f5ad3ab88a3aca5c85252d7d14f1d4d1d6d3c3574953136a8a7a6a88d658268d87ea49237041fe87d8dc10c030383d454ca558ab0a30343d49f7beb5d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7fffd5dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf74c5ba1cc7b67713a8259305977007d495c7d41007f8923db72ff652ff00a53fe0e9c84566887f487f87ad56f7bf5aaef2dfad89d53234d4f1c50a4aa4c32791dda449af728096f491f9ff000f70bcb682eb70922d393c3accae5abe161b1a4ecb806b5f4c53aac2ef8f8e15716efce49858db1b97dad2b57c3340f663594ccdea0626e1085b117b1f61f3b6496f773903b96b81d0bef3785bab3b389deb0cab5f534fcfa2edf313744dd93f0f2aa07a865c8e1e4a796a6104acd4d97c1c9e59142b83a12568dadfd41f67fb55e14bfb073c01a1ff0067a87b9a76d7860dd2ddd7ba951f31ebd25fe0c6d19b01d79fc4aaaa50cbb81e3c818acbe42a618e3a452e2c098e250545ae0dfd8837ca16f8bb7493d04b668e9111a684756a5b47191d64aea91ea8d20847adca3b11a4bb70058ea636bfd7d80a70ecd50698e85484851fb3a1ff001bb2d2a6c8810890342b2bc66cd4fa406f2213a5acdf9fcfb6921c71c9eb7e2681fcfa0cf7f74d89a2964869e37917d31040473fa8dd882046dfef03da2b8864152a3a33b1b80ce2a78f5577f23baab72cb83cad1c49538bc6461da6aac62ba4ec05cbc818954468ff000c4903ea07b4f03c8aff00aa0f820ff3e86b15bdbcf0d237fd761c7d3ece800e84f8a3b72973389dc588a187c9571414596cdd44b515b95afa83522a63c9bd5cc6455a91ab432c7a50228ff1f7216d37ead25ac6a046b502a3cc7404e63d8161b4bb9226696e0549078d3d3ab80ed1f86f85edcf8fdbafaeb0f4b4c33997db5fee27212925ce66882d5500998ffbae6a88829b5ac1bdc9f73b79b9b392dd1aa597078e7a80a2dd7e9ef96565a047c8ff000fe7d69b9ddbf1837bed8dd194db73622a70bb976dd4e471f97a6cc51b4120a866d33c02391416a7d518319b323a1bdfd84b977989f976e6e769de2ddd101a83feae20f11d0af9939657992decb74d9a58ccda68413c471cfa11d2bbe2b7c43df5d87bdb6f6d2a4c3d665f71d7d78a0c75062a965a88e84d5b7efd4ccd1c7e2a7829f517d46ca8a0fe7d9bee3bf47cc57116dbb72bb47f133d2807dbd25d8b9625e5db7b8dcf77910495d2aa0d71f6fcfaddaf6b7c0bd99b37e386c3eb2dc180a6cccdb5f1717ded6bc71c3550d5cb124f5957054852c09a91c10750b7d7dacba8e07b21697316b8956991f2e8be0bfb88f753776b706390b5450e3ece892bf5743d55938f6e76dedd6dc1d27bcf272e1f19bcab696acd66d699d962d26a93c6d554d4ee504b2c459e2500dd4f3ee2b92dd768b91e3455d9a67d353c52bfe115f3ea75dbaf27df6c0b58ca577eb7018ae29201e74f23e83cfa9b375fee0e95ddd36d0932c9bab6b4cc26dbb9e8e533ad7e2a77d54ac661e967489945f8d447b25bab616b792db6b0d171561e60f0e8c23ba8f71b55ba58cc7380032f0a30e38fb7a365d6d989e378686590a800788b2b1f4922c49371a947d7fafbdc048755038f4533aa82643c7a3d9b1ea616a68e547590582dc12cba47074bff00c181fa73ece22a8d27fd47a24b8af0f9f42ba642114ee11944ae3f5173fda016c17ea0587b32d40271c7480c6c5a9e5d06fbba581e925623e9f91fd547d031e791fec3d96494ae3e135e9f42c0ad33d100edcab648ea5e9dd55879bc7248ccc54fea2dc1fa211f9f65139652d46e38e8f6d5833a861d566e6b6e765f75ef71b0faf4c1411c9288b31bab22d245418d80b7eed64b21f42430ab17791bd2805fdd6cec8dcce9102039f5e02bc49e84125c41b7db3dd4a4f68c28e24fa0f9f50b2dd17d1bb2379c1d69d1d470f7ef7352d57837b771646396b76c63326d1815989c2cd510bd253d1e2e52dae5362ec38b83ece9c42664db7646f16e062498fc0b5e2abe58f5e8ba29afcda3eefbdab5b6da456387848de8cde79f214e8d3e5be04c27a67b13716fbc96577aeeaa0d8d9dcae27118e99f13b6b1d91870d51253a51e3b1d223571a5a920879c92c57f481ec7fb36c3b5d946ef7559ee4a56a78034f203a88f7be65bfbeb844b6020b4d60632c457cc9f5eb51a0fb17a2bb3faf37c6fee9ec3f6a6dcdbbbd70bbb771ecbdcf91c950e2f79e371f5cb264f64e4eb71cc2ba8b199a50e249233ac5b8e38f669c9af6d7573711b9433203a569c3e74f97f87a26e76fa9b1b3b5742c2da42031af1fcf8fe5d2137e9d97ddddebb9f756c8eabdb7d6fb237a6e2dddbab19d71b6ea2a6bf6eec4c4673335791c2ed7c364320d256d4d16dba09a3a384c8c64912204f37f66fcdf341616905dc4c8262c16829dd8ce3f9f44dc98b2dfdedcdbc9adadc47aab52686bebd5f07c36f803b23bc3e304d3e5b13361f72e277065e2db9baf0d24d8faf5a1822826f14b38654aa8e0a972a35eae2e2fecb36836f7bb6acb35b82c58e471fcba34dd66b9b2be6486560a00c1e1d10cec4ea0ecbf8c7d99518faa933192c5349254459ec13c9459fa4ff0028d29513cc84d3cca1c8d61c147078e7d833986cb6f779a0280b56a080011f98f3e875cb7b86e8c8b3c6eda38104ea1c3f87d3ab02f8f9dafbf2be9e8e9b7a51cb908a4d268b71c51958aba918e98c5627a929eba3b8f2201673ea000f71adc34f64da18978fc89f4f43f3ea4f4852fad848a02cc0647cfab18dbf838f3b4618c63ecaa005956da667461fe694ad9a3058fd41e7fafb54b702440adc29d07e7436f2103e21e7d2caab674ad242d494a23fb2a65013962f750aaccc586b3101c0e0fbf49a89511ffc5749e23dc43f9f426ed0c3cf93c642c56659626685d1d2f2248494660a093c916fcd87b34b24d54c71c745f7c423100557aad4fe64983aba3ead13bd2ad4b51676860a9a56f244f55435824a7a8834822373e2663cf17504fd3d8bb6787c3bb5a9c69fb3a0d6e6c2580d3aabef895d6cfdc3db9b0f19250c35786a7c8a54e52985c24b8bc25453444d5a02639564445fafa4ded6f66dcc977e1edcca9f1c8428ff002f4c72ad924fb982c2b1c60b1fb7cbf9f5b6ecd96c46cfc0c149e28e8a0a6a5822869174c71454d122852545902a2fd7fc3dc7730541a55a8a07529db2339d45b1fe5eab6bb7be766c2c5760d26c3d99495fbc73f595294f95adc63a0c4e234ba44d4cb21b8acaa0c4dc270b6e4fb2a7ddadeddd5154bb93d0cedb93f75bddb26dca451159a2923560be3c87a7cfade63e3bd356d2744f50c391a892aab7fd1e6d596a26945a42f5188a5a808c3fac2b284ff907de41ede08b1b40c73e1aff008075855ba90db9ee054003c67ff8f1e864f6b3a41d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ffd6dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf750f23482bf1f5f424d856d1d552127e8054c0f0926dcf01fdd59752b2fa8a75646d0eade841eb5dede38f936e7625164e186454a4ac782ab502ac935056bc7244c0fe905a337ff0f714ccad06e21fc81cfe47aca8e5eba4b9d81a0661a99411f98e3d130dda61dd9be7b2b3b4d128a7cae7ab692998e9655a7a68f436bb82d6790373f4fcfb25964f16e6ee5fc258d3ece8fcc8d0da5946e6ad1c63aa63f9a036d6c2ae7d9d1e422829fb0691e35c7cb630a6615498a43a3d110a8234dcd8163eca16eaded774811deba987edeb771617bbdedb7977e112d121cfca9d08bf1e70efb5faeb6f52d4209125a58ea2277d2b785a57f123800117fd4bcdadc7d07b1aeecc5e302b9a71ff0007517d82e966a799ff00075641d6b4d0e45e8e4d60492d3244e05ac1d7492cc2ff00a4a8fa0bfe3d835c2b52873d0841d0991d1bdc3c54f040e11514d3c43cacc35c7a52da547e6e47247bab10895e935496f9f539a1a6ac8bcd242d24532a98eea43bdaea6e8059157f1ed80da81eec74fa123e47a023b5faf2933189aaa58e822686a6178e5591149756424a26a047eafa93fedbdb8e8741555e3d1c585e3a48a5a4e07a22583e9ecc6d3aeaa6daf572d3223b4b2d3d548e2150ad791510868d43fd2f607fa7b7ace42b401492bf3e1d09a4b9b79c7eb0c91c7fcfd1b2d81dc195dbcd4d8da9859e9a1850cf3cf4b2c7490a258be8aa770026afa1bf007b1fed5cd1736a8b1380f181e7fe7ea2edf7906c371925bb8898e627f0d3f98e9e7b0b63fc7aeff9a9eabb13abb6b6e7cd246ac72c2be0c2e67ece252c44b54b2413554712037d6588b7b3e6e65daef428bcdad6471e78a81f6fa741b8793776db598596e8c887ca8c41fd9c3a1c3a6366fc71e86a7347d41b1f62edfdc1570c4b3e72af70d1d6c816a74c9120ae8e2a8ab61e3379103f1fe3ede1bdedf6f1b26d9631248c3d47f3f3e989795b79be61fbc6f6568149e0ade5f23d08db9b746f4dcf472d363bb0361d1bcab54b92a58e2c84f0e2a9e1f12eaae78a8e60f4d50d2e957426e41bdbd91dedcef376098ee2dd0798cd07dbd1c6dfcb5b3d8b86bbb5ba9080287193f218e8a2761ed7ecbdc9454b80aacee3aab0e28eba8a9e38239ab30acb9471fc41e828b22e7f8789d902cda2350c630493ec0bbbcfbab34714815e01514195cf1201ea48d91762dbc4b7104322dc12324d1f1c01a71f974cf8dd945369e170794513d7eda3fc3a1aa911497a78880815aec74a20d2b7fa051ec9ad9653a44b1e54d3f2e99beb84f1e69e13d92669f33d38d1500a0c953b5386501bc6549b00c80faaf60486047f8dfd99151a811e5d20525d5b50c746a767e5463f1f4b08b6a65d28896b2333166216d637bff4e3dac8880b4ae6bd239232ecd8af42c4b5ff00e422675e622ac749b7f40c188b5c9fc7f43ed59ee8ce7a45a49629f3e82dde3b8a47c5d508017b25d813c80dc11c72de937e3da4928548f3e94476c15c0e891ef2a6a9cd0ab48c99238d4abb5b515d5c7d7e8431f656d112cc5bcba318a90d091dc4fede834afd8d5b0f5f64f6e6c7967c2667748921cd6768814aba2a0760b530c127ea46ad8c98d987201247b2e9bc5525135056c1a60d3d2be87a3ab1781ef6dae2f503c319a853c09f2afd9c7a5b74aeddcbeccc2a6d0c56c58aba971b594d5b4d86da54d0534d3e4a688d0d56572350eaf5d535d54a115aecc24d22fcf3ec43b2de4b0406d61b2ac008215066be649e249eb7ccd6d6fbc4cb76f7c16665a56434000e0140c5075633b27b2b05518d8f11bbf69d6edb32d2bd154c351452bc0f100696615508843a0b82ac4dd6e08fc1f63fdb37cb76291dd5bbc0e3f8863f33d42dbc7296e30bbcb6772b711d6bda4547edeb5f3f9dff00ca5b379edcf9eec1e8ec3517636c6dc159535b2edac6cd49fc576e4d5ced3cb4d1d1543c4f3d179998c5601901b7e2fedebbd9ae5ef4eefcbd76a923648040353c69f23e9d5ad374b77b21b56ff644aaff0012e0fcfedf983d13ee89fe535ddf9bdc662c8eca3d6fb6627a569f3d9e5a5a64a1a4f49ad34948923d5d6d685164e0283f9f65cfcbbcc1bcdcf8bbcdce989052a48e15f203fc3d3efbd6c7b45a341b4db83239e0a295fb49f2f5eb620da5d7fb67a43ae709d77b55e08f0983c47d822a246d5d90a8743f7d92954121aa2b2762c410393ec5923d9ed368b02c8ab105a0f53d05adaceff78ba693c063216c9f21f69f4eab73e4074a60b764b90aea91532e6eaa314d8e88095e99227983a53cf0a92ac59f96d575d56fc01ee29ddef1a5924d32115e1f3ff3753aec1b747670468d1297fc47d31e5d2a3a9be2f64b1b85c751d453a4d44a90bb30b6947d2195553820a1ff0061fe3ec36d6f752c619c6a43d1acb7d671b32c474b8e8f46d2e9f8711028090031c48483e42c1547a88bdef622c3fa9f765b411a8a353cfa0f5ddd09589af9f4e59cda31d32cc158473e81287446fc1baa6a3c58af07f3efc0770a9e92a354eaa63a608617a6c655c54644757548230ca8ea69e4913419124e002bfab80093cfd7d9c40084d23e32307a4d7214b066c81fe0e883ff00309db11e6fa3678200f3d4e266a532d5af9c891e96312b55992c1a6676570c4f20fd7ebec63684471c62a4c800a9e8253f799548ed2703e5d107fe5b3b7570b9edcfb9a668d28a9bede931f269612bbcdaab6aac6524c4ba9d015fc5b9f68798e7f164b48892154548fb7a10f28db9862bd988c35141e8c4fcb0f90bbc37b55c9b0baeb233434324f262f3798a52e257a8555f2d163a5527420d456461c9b102dec09bb5c803444dda4d3a9c392f638279c4d7e9da3b829e07d09e947f0a3e177de6e6c6ee9dc94aad2c4d14e62aa8cb335e5472ea5f51d458dc93cdfda7d936b17176b2ce383703e63a13fb83cd6b65b3cfb7da9ab1422a3cb1c3afa0eec5a38f1db276850442d1516d8c152c60fe120c5d2c6a3fdb2fbc92800586151c028ff075cecb962f713b9e25d8ff0033d2abdbbd33d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7fffd7dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd527fcd4da136c1df7bab294f078b1b98a4977ae3264371aaa03c598a5d2de9510e4d0b803e8b27b8f398edbc0bb7900a2b0d43fcbfcfa9c3dbedd04f629048d5910e83f67153fb31f97440b6fedbc5e076036e1dc3098a4aba6a8ad915c00d24d53aa795bf1ac12ffef8fb0a69486d0cb28a1a57f6f5204b7335ddeadbdb02cdaa83aa7eeeff008d597f9139acfeff00931d3e3f6aedd49970b34ca6f3490962b5214f217d3e8bf1f9f61e8f6796f965dc28123071ebd49505fdb6c56d0ed3332bdd4c3b80f2af91eb1ec9298ac160f1527a4e3f078ea204fa89ab81de231a8161a1512e48bf17bdbd8d673e2dac4e4d54a0ff0007509dcc4b6fb8de448940253f90af4793ac3289252d328d2b2baa2168d82a2b47e90a0f1cb0e6dc7b06381a864d013d18489c3d3a38f8393ee29e28b54be3b27940201202adf569fecb93cfe6dedd61ac007a4c10eafb3a5f515025e2113470fa238a2463f555258281f4b16fcfd4fbbc76e2951827ab31143d7b29b7bef60649d7cacd6652e352a3fe9278b15bfd07b5621d49a7abc4fa7e139e81dcdf5c452c9317a665d4ca7ca849bd9b505f4f2b71fd78f6925b6d2c18d457cc746115e118635fb7a4fd3f5dd9258d2b29544863f2455746f29f187ba2b0074e9522ff004fafb7a26910050dfb7a542e8369254fe47a7c3d4745909e2a9aec950eb4463e3a4c63ab17647523f58500ab107837bfb328c17d3aa55afc97ab26e2620552263f691fe6e96384ebdc2d109245db0fb8aa635a7d1539509063e88d2a18637345470224da61e1f583a945c7abd9c5ac51f71316b6f227007e5d26b8bd9dc516e844a6b50324d7e67867a11536be666a6925ac9a14c5c68f1c7474113d36310496589a2263fb993508895bdc86b702decd0248cac24650bf2e89de78118692cd2fa939ff0030e9319ac2e3e3229a8a263549a64a99d1c9467640582393eb22c07e6dcf373eca6f9202ba54fea7f2ead14d23312c689d2565c1d3c14e6595559892c56faf9e4b3f172aea0f3ec3b222838f2e9d66666ed27a07f2ae1f31146ba3c6ae3e8da7853cb0ff001207fae7dea9ebd1ac50010934cd3a1476fd4096688463c861552030d36d56f5b3022c3fde4fbda6588e9b105012781e8576ab45a011895b5853e495ae17f5723eac2c09e6ff008f6bf01387489a2efa81e7d041b9eb6460f186d48f1325c280aa45ff004904901ffa7b4ed52683cfa50116bac9cf40f61e18aae4ad81c1f2990aca9a40048724693f5fa8f6d0cea1e55eb5730920357cba1131bb5529596abc6eb4f221490c28a5d432923d3f46fdce6c48e3dda2b78ddc9a53a4c2764a2d4e3a52a75cd762de8f334da819745651d6d2d432dde3689de927962914ea81d3d41583a820dfd99476525aba3c7f6d463a542fa0ba4781c02054107cbfd8e845a0c866ab20a982a72995a5cd3fdc4695bf793cb4923641d9ea9ea9d91e787c9133202070e7558dcfb395ba6910ac83bfd788e8b64b38a26531c4a6df1eb5c70f9741fee6c676161e8a7aac7d4e3b704294f5b15318e1586ba88f8e386094b524b48c6aa9556e84860c4dd94dc8f691e7b8b5a3a3065f95411fb3a5496fb55eb04994c6f8ae2aa7e59f23d226a297736468a9e2abc94985a93474c2a189afb7dc2733958ea03477995ae47e80c2ea00bfbf1dd2774d2d72e31e64f4d1dab6d8a5256d95d41f2038748e5da958ed32e77309949a389bc32843e49907a42caa855448a87861f5fc8f6552ddab57c59351fb7a328d228c0f020d2a7e58ea147b069f235b1471d2ea8e368d83cd72f65e446a4fea22dc9bfb2b6659a4f3a75792e0c48487cd0f460f6f6085252a53182da92e2eba412aa0692c05b817b5fda965a2741f918b480d7babd3a8a7920964097ba953a5fea23201b801acd6e79fc7b406a0d33d394aa807a4ce5e1478e72d19205fd2cc7548c05ee82c6dfd08ff001f75d15049e1d7be1341d02b93ae682b628802aa246756952f135c111a0d372749163f41c5fdaeb5909910007a66e97544dea3a2d5f232824cf7596eec5ce5638a5c53324d346c62a66794c465523fcd06d7f56e0587e3d8badb52216f974169943ba8fdbd554750d1e6b01b6bfb93b55ea1b766e5c856ea318b363e9aa263049909347a40781478cfd39b8e3d8537cbc67dca448c54850bc7cbd7a93393f6c02d219ae682d012cdf335c0eac0b68fc65a3c353ecaa19a23356d4d6d2b54cf2a1d66a663fbf2c92b7aa51724f1f5f65ff40a56157aea26bd487b76f212ea79001e16934f4c701d5bb75af58a6dacae3f158d752c5a960d5247a7cbe5f1c6aa12c4a6b918581bf3fd7d8a20b148678d636ab57f2e809ccbba2dcd95d4f28a0a1a75b3161a9de930f8aa5905a4a6c6d0d3b8fe8f0d2c51b0ff009297dcb480844078803ac49908692461c0b1ff000f4e5eefd53af7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7bafffd0dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd566ff331a589f6075fb43116c9e6374d46d7578c5a56c765696292b50b0b1d0829811fd09f615e6a506d6de83bcbd3f68cf520fb76ec375b85af688f57e6a71fe1eab0bb2769d56e25db9b2b1be45a355a4a693d44298b42997593f850083f8f71ede43f573db5aad7c3ad0f53a72fcc96a2e6fe60358ad3ede91ff20a8f1db47afe1db781c6458da1a7862c5996058d66a97a8522a6a26209d4588216fc007daadd1d6de05b48134c4a00c79fcfa6a179a59a5beb898bcec6a3e5e94ea96457451675e8e359a1871b34b147a485819e791a45fecddc86737b9bfbd42cc6c152b809fb7a21bb526ee47272cdc7a35fd6f90a94a8c5c33431ad34f4eb2d6cc8c142d5c4f188e38d41d4632a0ea279b81ec273061220e0bfeac746a9a1918eaabf01f6747cb66e422ab49e38e4d25fd570084f4c6a008d85c9b28e79f6a14ea000e98923d1a481d0c545551a08d597c8e3c652c74b1274adc35aca539b8fcfb501eb424e7a6556b503e2e95b34f2cd2c2a88ac86eb2391a6cb63a59147d59ad7ff000f6f890a6075e445a312dd728b1e657bc8a921504062bc4874fa0b0bdaf63eee1c35356475e6619a57ae54f41043236ba5835961ea64b2137fa926ff0093f4f6a9340180074db313c0900f594d105950c4d4c921bceb7b128966018821404d4ba40b8e7f1ed4c4c8a734af5e0cfa4d6b4e1d2ae96abeca0b2f8cc922fee22449ae532701c4a87c6eb1f1aae2e7917fafb335bf8e05cf1e93bc2d2352a69d26f2991c8d5b801cd353aabc491c4a74c6a480fa34bb056957836f68e7dca496ba0d17a7a3b5892ac72ff3e91797a582380c6a8caac2e4bff9c6622ffaafe9b7f4fc5fd95190b9a1e9f5a863c31d059ba321153531a7852d19460145f55d072e5ae6c07fb63f9f6d3d303a596f1966d4debd0334d1255e46599d8aa84011c0e6527937fe9f5e2c39f6d56a7e5d1a31d098c9e862dbfa4468890b2a78155b5e900b0b0b31fd4cec7dbf6eb56cae3a60f0351e75e975242b1d3c90c8c13c8351746e2136fae936000fe9fec7da921a95a1a7495c8d4182f40dee2534e8c9248645d5747d47d418926e3eba6ff9fc7b4ba8863e7d3f40de5d07f84b47935992d26b76054d88176b9279b9d16faff4f7e0b5d4c0e3a7a440d0b8e8ca611a9aa28d23d3a86a2a6e75736b80c08b92c7e86fedc8c8522a33d11488559bd3a13f06f2e3e36a728b518ca92ad250c88af11992da260ae0aab8bf245891ecde0bbf0ea8d943e5d226875b0910e993d7e5d2a9b01b7ebdc55d35e898e9d4a0f91bca482cb32d95903f01413a589e3d987e84aa0a1a1a75a17170954906a1fb31d3764f6bbc12155f1b072ad20bac77360016400800703fc4fb43342c09a1a8e9e8a7571e7d272b7105534491452293e857d2e189f49d24a926c7ebf8f65d346dc5b88e9e5706a55ba4f49b622a8064fb2446504ff9b552d602c05c1ff88f681a2739a74e099d4535e3ac349b5e21ea306957254315e554124136008208f768a034cf49e7b8c7127a51c18c4a77fb546bb3287576bb46cbf5f5137209b7fb1f6e3151415ab74c267b80e98735451956d31fad410f2c560eb6bdb82071cfb4732a93db8e9e524018e836cd4423a630b193ce01d72486ccc139d5e9b7a994fb6d1086009af562dfb3a0037294f1cc3cfa8306857cdeb48d5b870a15036b7163f516f6676c8a5ea73e9d17dc48c0d070e8a677fee28f0dd43bea7a977965fe0c6921883b04a997eee38a9609905e42b31b29fa127d9ebdd2db5a4f712500443fb7cbf9f48b6ddbe4dcf76b3b2881d72c817ec0789fcba277f04b60e6f71ef5accf678c82b721561d1671a3c54ca54434b0ac86f1d2c11285551f403dc69b74935edfc9712b1d4e49cf0fb3ac87e69b0b2d9369b6b0b550638d0548f323893d5e8ee7dbf8ec52edd9e2d1254e37238d9e558812ab1c53c6b380dfd3c6c49e78f6359a1441167b811d45b637331924e3a083fec75641f1c368536f7edfdb91bc4b2d0e3231b96b8b697028b1423928a1917fb4b559168c027f00fb156cd6e2e2fa36618515ff0037405e74dd1adb6796356ef90e91f9f13fb3ab91f63dea0cebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7bafffd1dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd5697f30995e6c8f4563d9c7db479fdd195961d4017969b1f8e860934fd5845e66ff0f57b09f343516c857f131fe5d48beddc61aeb727fc422503f327fc3d115accc50439b1530c9048eaa6200daea592da8706c633ec1b1c91a4fe20a13d4db6f6ccd6a16847408fca5c15754758499fa48659a3c7d5d2d4e4654b542a53b308fcf39899caa03283cffc41f75dd2d99adbc703038ffb3d2582541335b96a3f97cfaa03ced5cb4bbaeb63915a2352f280ceeec3d320951147d5662aa40b5feb63ed9b3eeb7404e69d175f5119988c56bd1b2eaacd2aad13b9631b46cad1160743ba0b90b7242ab0ff005ffafb20bb431cafa8d684f4b6dc6b8c50e7a3d9b1b331c1063de9dede566462d67b291a582aaf0c41fa7e2dedb85800b9cd7a7245d4083e5d182c6642099a364915a4595567fa1316abb2875fa0207e7f1edf5357207493430391fecfd9d08315541392526088a9e1b95f4f95869b86370594fe78fafbbb0a8a754d27351d4b432431a24735d9c290cc353156e15adf80a07fae7fd6f77456a64f4e9553dce83a77874c634cf31250e9d528bcacda75bb0201054eae0dbfde7dac8d322b8e9a2a092703a9b143022fdd4d1a48245558d598bcc0fa95239058855e2f6f6f98f1dbc7ad362800c75ce46d06cc7d3a594aa00080c3480bc5d563b7d7ebcfb698500f4eb630093d449a942c40c6de35e0966b10bfda0da7eb7637e3fd8fbad063d3aab31f5e82edcd94d0cea86cd15d4fa87a88e4906c796039f6db15181d390a93c787400ee4cac6eb233bbc71095c02a3f702e936423f0ac45aff00e3ee84e6bd1a420af000f498dbc054169193f691982b96d375d42ff9f50ff1fa9f6da7766bd3ef9008e3d0d9824285232c1632a040c961ad0dcafa5ee449aae7dab878d3ece9991a82be7d3fd7ca22814444f9913e9a434aea400c198900b171f5b7d3da87f4af4997b89e830dc4164b58036fc94fd0cc6ecac4124eabff00aded2bd2b81e5d5f3923a4561e9d11e47450cfadc7a7ea0337acdcfd4f1f4f775030abc7a70c80d05703a1976e5678dcab5c84503c7f4b5f9d6c391f437ff01efc56841ad7a4b2ad568067a1cf00f1d52c52290438e00e49d3cdf49b8b35ededfa12011e7d17b02b407874a4915e3024a6263f559c1f4c6fa09206aff03c7b71657420a8c7540aac3bb23a6c9e69ab26064f1ab6b2b2480315b5ff000b70aa574f16b7f8fb5066623bba7422a2d075cbed0eb37123162cb1b3b0d21ce920581b2861eda72483d5411923875c842e356a0ca47d03f373ff00208b0b5bfafb4a715af97542de9c3a8f512b2af8a9f446cc848b8d4a5b83cf278bdeff00e3eda772476f4daa126a4f50a553049032952ba7c720e34870070b7bfea23f36f6d1ad474f0ad00a7495cdd644baf81731156084fa8dca965248fc1bff0081f6d49c71d7941a508e819dd35f68f4ea70115999cb5da4454f50d57e4afe79e47bdd01e1c3aa1aaf75327a2cdb9321154b968e7d30c47508c0b6a92d677b9b295000b06e0fd3ebed75a55a4142683cfa417382430c9e8b37644b4dbab0926065f1d4a5664223246f1ab3c94d472ac8eeed6d26333152bfdae3fa7b5b7fa65b39227ca30e1d39b04f2edfbbdadf47f121ff000e29d0f7d5dd235fb0b2f83ac8605869b234b455f4be38cacaf4d588ad1b83700a117fa7d40f64f0ece2de48a445c1a11f307f9752f6e3bbdbeed68c2b59071af91f31d1fdcced854d939bad999aa6a62c6cb2430882c55a34d4a2e6ec00617bff0087b10496ea63355eea7400926293471a61757563ff00cb8b1b36570dbbf7c54282a28f03b568e6372cc69e0fe235eaae47d1259230d6fcfb1672cc5fa734df601fe13d449ee35c28bab4b253c3539fb49a0eacefd8aba8d7af7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebfffd2dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd5667f318a29e9a0e9bdcf101e1a2dc19ec3ce481c36468692780127f0ff672707fa7b09735a91159cbe4188fda31d493edbc8bf5db8c07e268811f91ff0067aaaedfd95c679a862a069e3cbcb03cf531d2c6e57c2a402e588f12b6a22d73ee3cb978fc408a4893d07a7539d94b3c11bbb81e15715a71e821ec0cd767e73acb3586c7d617c74b4d3b1c654296fbc4552c8924a835eab2d85b807db7717b78b67240a6b1d323a6e38ac25bf49e54fd426951c07549fd954b574752b915f2455492c356eac4a491cb1b049a1258f2acc847f4f6fedf3130c450f97455b9c5a669a22381fe5d0d1d53998a4fb6f4b989fc6f118cb69425d18589e48650413f4e7da7dd2225d5e9da457a4d6321a6827e5d1f2d85988ec8c23f1c70390c18db4f91b5a37248363c1b7b288b4a96d5c3a346a914639e8ca60b26139e5669dc3348a14b145000d4c469636fc5ae3dbe34254aaf775468da80790e843a2cc33c4d05b529d3288c7a43042087506c5d89279e3dba8e0e298e9af0e84b53a58d0ce1e4592696e194692fe958e40a1574861aacdfe37e7da95e1c71d55876e17a51d156d379cd2ca9e49cad9482b75f4eb5d2174937e7fdefdbea6a3a68c32680c063a5300ac07ee850801501752975e0024837b036febfd7db9adc1e3d33c0f0eb0aa2cac64731c71a82c14dcbbb037d44dc01191c7f81f76ad470cf5b248a7499cdd6014b27865d3eb4372dab45c5c2dadf9fa7d7da77614343d5a35d4f918a1e8b9ef5cffdb90ea6da498cb1000d40dd4b2df81c5bebed2092b8031d18c11824f4036e4cdc46394b92752a99995c8d45cfa154022e4fbbb03a4d463a59e110005e1d28b633254423caa6ede98d49d2aaaa4804a82352b1f7b8000aaa78f4dc85d7e11d0e5882415f46b28a155dc811fa4f0192d7b0faf1c7e3dad88003874cc82a829c7a73c84ad247ad5d55c878c369fdbd67d44a16b6a29feb9ff6dede7556a1af4ca8a71e82dcc3096595119994049359ba8690837b7d069523e9ed1b2d49f4eae7b73d24e8eb523ab753221543aac80a8b122dabfda81b8e3fafbf28ce38f4db135a79742360eae29e70750bba08cb21d37e6c0589bf00fbf6af33d6aa540038746136a0e102df46b448c090863fe048e47d7ebedf8cd453f9f4925f5fc47a12846afa86a4d3a752dafe91c8be93c17bfd41bfd3daba6074981e0298eb04b4f03c663758dc792c4c6c43737e57ea3573f43f5f7aff00075eab0a103a6cae9041e986c0431e905986a472469e7d3aae39b03ef4cd41ea3a711359c8e9a1eb5a22caecae7f6c850d763233124b7fb437d6deda7e3d58c35181d617c9d2adc31049b8b2fa9d4f0401c7e900f1f9e7db2538d38f5ef0997d69d3064f27e26b85bc71b1045c07d66d62bf456b83f5e7da67aad08eb612b51c0f480cfe554c4c58a2ba23055422eda8ff0068dce8b903fc2e3db5abce82bd782918ae3a2e9be73522291672eeab188a27241695956ec4fd1431bdff00a7ba96614c1a75bd35ab745df70e524a68e5657b7ee78d9d6ce86ed7769101bb20b5aff536e39f66fb6a355988ed34e8a2ee8ede75a7404c5925c9e4aea19d22aa6a7a66374d50a4ebe47b1019b54dc827f0bedfbd954a3a8f4a7fb3d6ecd3b830af11d5c2758c4b5bb4b664b94a55fbaa3c552c4aee59dcc2aa1a1f51b78d429fa7f4f6e4323182032792d3a152864d6aa681b27a1773d5d02ed5dc4fe51106c7cb186b2fa95919555589e0023fa7b70ce4a31af01d17ce8dae3c79f5655fcb8f13263be2eed7ac9999e5cee6b70e58b3a856f1bd71a48812002c02d2704fe0fb1cf2e215dae263f8893d419ced3f8fcc177e8a157f60e8f77b3ee827d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75fffd3dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd151f9a5d7337637c7fddf4f8f89a4cded514fbd30c2352d21a9c0179eae340b763e5c63ce2c3ea6dec9b7eb4377b65c2a0fd441a87da3fd8af424e53dc46dbbed94cc69133686fb1b1fe1a7543b5d5348b828b39540c713c204f3b0209f411f9e57d5f8fa5fdc56e116312352bebd4f6f2c923f86a6b9c0e9ef674b471e31e9f25125652e46179a82a069649627178d78e15947e3dd90a2a6a900d07cfa6a59581010904608f9f54e5f2db6b43b7f7c64e9e968163c764c3d6d238553aa6663f711022da74bf361fd7da3b2655b89e2028b5a8e96dd133c10ccc7bcad0ff0093a2dbd63b95b1d54f492b2de191601182c243133178d8fe2e8c0a91c0b7b33ba43343abd3a278182cf4fc247561fb1b391d6414fe35d4253149244cc0058d54a4c01ff5ff00af1ec36ea2a4118af47e86aa283a32984c92c62a6296552b028651aef21338d481187d74aafd7fc3db5af486d4787f97a76a6808f8ba16f0793491a80c73b6968e476e090a9cdd5afcb2d9bfde6feed154f757b7a4eeb4d429d095413a488cdea70ba2362ea08d0deab122c75902c09fafb5a18914ae074c8a8ae7a52e2e183f8879b4c7e62ba119d8889588e7c7f9d5a0db9fcffafed44440c74e492308b481d2e524544f10d262e1d086f52704e9602da85d411f93ed52006b518af45f5a9d55cf501e4d45e2603cc4030a927c6c5c963fa6da095fa06f7720914f2ebc698c57a496e7850638f0235d52146121bb32f3af52136656fea7ea3da49d3b4f0e9f80f7fe5d139def943969e2c7c0c81e297c72bff674ab1d4493c3155fc9bfb4b1af89504d074711a84ad4e4f41c6e0a08648ed0b9bd3436560be8373626e45d9eff009fa0f6b18c65348f21d2a58dc8d6303a7cda295544904f3b3889bc4882426c54b9d4c245b7d185efee8aa02d0e0f4d150c181e3d198c3c66a6001aef1b95432160c5940241b1b051cf27f3ed6a20ed00e3d7a40efa08238f5272f1c540c616b18c461c2920aa126c015b92350fa1e2feeeeba5684e3a691ccd9a533d05b9a791599941d7a58a467d4a45895b7e9d2b63f9fa7b48e0ea34e07a78e96201e1d03f513548ac3e3002c81ddc58dac8e2fea06c39ff0063edb40dab03ab945a1a0cf4206dbc808b2346ee4ff94486391646b0436e2455e6fa7f491feb7bbcaaa1830c57d7a6a9da4115a7471b6a18a18209010238d4ca5adaaedf506c1b9524d8ff004fe9eef1568481d17cf5fcfa1469e9f4c2b78d82b8924bbfa257622f7d3f4d25bf07f1ed6804018e9254838a57a6a944903285bb464b691180ccb7b10ba979bc77e0fe3dd6adacd69a7a781045070e9379092462c91a03ab4be9670dfdb3ea23eb7b0f7573c683a551e91424f4cb52c35334cea1906a0558850a53e8c74dcdfea3ea7db46bc09cf4e2918d35e92f23d4c520a85227a323cbe40a068516d6436a1abd26d6b5efeda627353dbd5d886045003d25abb28b27dc786a236d058280c4150a6cb70c4d8aeae47b61a9e7c4f4d94208a8e832cb6565788c88da91e4f0ebb00c4c658c8393a5547e09e08f6cd00248e1d68a5030af97404eebc9c8f154d513758c94567ff7604fc0b100853f5febef69a9c80c3a4d250023a291bf770c7148b4d0b13555c2492c8d7d3a990f907aada8a8361f8bfb3d8ff422a7a8e89d8195cf49cc24e94f94c2c0b493e425ab9e9215a6a620bc859f5482f6f494b1b9e3f3eca2fee022a8615248e1c7a12ec5602e2e0ea20468ba893c29d5a66d1df195231d8eca41fc2a9beda1869ed2077942aa808a401eb20723e9ed5addd111345169d1dc915aaf742f56e86fcfe6a866dbd5304129a88e482f323218a42ea96f09420fe927ebf9f6f2b295603208e8a25462fe230000eaf07e1ce0e6dbff001b7ab28a785a09a6c13e49a26055917255d5759102a40d378665207d2dee50d962316d9688450e9afedeb1bf996559b7ddc9d4d57c523f663a335ecd3a23ebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7bafffd4dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd629e18aa619a9e7459609e29219a27174922950a488c0f055d18823fa7bd100820f03d6c120820e475ae777475e8d87bafb13acea205683159daf931e27431f93019576afc5cd0bb28f2014d385536e593dc4dbbd87812dcda91501891f61c8ea7ed8b7137b61637aa7f50a007e4cb835fb7a2d381fe2d83a583013b4b2e992a1b16ca58b087f518c126dfb20ffaf6f64947a2c590070e84aeb1c8cd35695e3d146f96db72babb6ea65aa636336366d66558c6a44766e0dedfaffadf8f6c9474b885ff000d687f3e9f408f6f3a83902a3aab232ff06cfc15b4c8e75ccb14b6f5ae876d4be9bd998dec7fd7f677151f544c7047443382a448a381af47aba8b3e95b12c45dd648d42cb20ff36b1bb5a32c08d435bd85c71ec3d770149581145e8de194342187a746571791aa5c85305496552642655d2122922b47e29143126fab8e0fd3d93caafe2006a474630bab467853a1fb035978d01f2466cb25c11e8e496644054d873cfd39f6b62934a9c74c31ab11d0af8fafaa648452c91ca922aea8a53c4ab093eb2e3494608783f427dbc8ee4f6af9f5aa279d7fd5fecf42663278e47b3850c29c544aa0fee11a7d2cac400ac845ef61fe3ed7a64135eee92ca4e29c2bd2d28159a149da76957c23868d406f5693505a33ac7078fad88bdaded746b45a9e92b79ad287a8533d32ba062e1c2b48deb0c1d097b80ec2cbe40458fe2f6b7bb330008ae7af0046739e82eec1cc458fc2cf1c151a9668ce8d2ece52e0390ec6c42db8e7ea7da0b86e0a3cfa576a9aa4a907a2b786a4191aaa97d6e753b5d8e8172ede9527f161f4f6dd28287d3a3850146a6e94351848111de4a7410c69a24697eb702c64b0b8b73ff051cfbf25430f4eaed74d85073f2e9ba80d1b22a021e1a7904511665f58d4585d3f52f26c0fe7dbc48240a63a658354b799e87ed9743515955498ea485aa04af745b890b12be94d4c6c0a5fe87ea3d9a5bc5e23045cfa7482f59628da572001d3f6edc1a5009a9654d132b30292326a465e58ab024c8ab2706ffef5eed3c24554f486d6e04b478cf6faf9740364e39692609330679090cd61a590dc00aa4f1c7e3ea7d9732943a49e8c59d586063a4fd3e2164a80ea069d4e6e01d40b72010c2da1afcff407dd6a4640e9ff00154a8a71e9bf2d491d2349e062b3533a544447a8100a968d6f63764b8b7d2e3ebed991c9049391d6d45687c8f46b3ad3331d650534c8da0ac6be3332dd86b1666f19f4ea3cdb571f9f6edbc8ae052bc3a2ebb8ca96d43f61e86c193ff27850bd5c76d48d269888366259b5e858db529fd239b73ed70258501cf45fe19af975e9ab218d1de28c31092ab15b8957804594820137bb1247f87bf71eac149200c74839ab499aa64b29898a907494f1b11771ace9d560bc1f6c92c0e4f4b3c3c000e7a64aec943a0b84b35881700ea1feb0040b01f5febef44823e63af2a328f97faabd22f2994796214f2811a0b48862fdbbafe15dae34136e7fa81ed34a589a1341d39423233d0735f594c8669d5ef0bc81dcc362c640a4150756928cd604dbfc47b6083835eac647602ab9a74176e6cd2220a6882c7e272c6c6e1081a8f93e818f36f7e62a001e7d324bd0b31cf45e7776e312accf39fd98efa92c0034e8034d3a8be90b1a8fe9f5fa7b576b1f88e2a29fe4e8bee982a501ee3d15e65932d959f292052ad3ba538297094f71e36d2c482ee82c07d2dcfb5d3364e71d31044681c9c9e8cf7c67d998fcb6eeacdc15f17921c339a3a04750d09760be66e411a87d2fec9d82cd760b7c283f9f4208276b6b0951077cbc4f9d0747f73789c457cf0434f4b1aa010b2b32e96126a02f1d8ea50b7fc0b0f6b8c2ac4505053a411dc4d1f76bcf4a0da1b02bf7beff00d9dd6942f50f51b9f70d150bd44658b52e31a45a8c8cee47a9e18a8e373a8916f6bec2cde5b98ade3cea61fb3cfa6373ddc59ed9777ac05123269eadc07f3eb661c3e2a8f0588c5e171f188a83118fa3c651c43e91d2d0d3c74b02ff00ae22887b96d14222a28ed029fb3ac6d91da591e473566249fb4f4e5eedd53af7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebffd5dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf755b7fcc03a1b37bb7058eedfd878ba8c9ee4da1472d06eac4e3e232d6e636912f3ad6d3d322992b2b301393268505da9dd80fd207b0c7316daf7112ddc0b59505187aafafe5d0e792f7a8ecae5ac2edc2dbca6aa4f057ff31ff0f5511b633f88afada24a992370e1fc25d8078247b0949b8ba903eaa79e2d6f60414ae454f52c4c5d5494e1fe1e82bf9470612b764e5a9d1a2f0434cfea675bc8c15ceb7247d09fc7e3da4bbd0b46c769e946d8ce5cab939047547397c5b486a150ba4f130b28b80a54ea8802bc692b6b371c1f6a908fd3750739e99946ad6b41e9d2ff00aeb7c3ed996aaba6d2d4eb13475f1a07778a91154995514b323a329e45c7d4fba6e106b412571427a6ac25a4ad030a647479f63eeec6e77158acf61aadea28f25025553ccb62ba1ff6e626f63c69bfe7d879e303c8f47259a390ab0c0c7462f6ee4d6493c8e4c84c6218d82e8b69b02bfa8aea0b7bff0087b6140d46a7a7b18c63a19f0752c046bac312a504a08e084e38b582936fc7b54b4a8a1a74db30ce3a10b1d9150ea8e487368c59802aee02b6a6e5952f7b0fa73ed6c6e0295a66bd3541d08345591c6967b450a80ae1cab0040f1c6542b279627bfa97fa7e7dac496828c7a4f22939e27ae190a98e34774213412b1a22840d23724904bc679b1047fbdfb62492adc3aaa02cc17a2edd9b51a3035b52aaa4d34155348a1f487608fe861f43a9b93cd811ed2b127b8f4696d8902fe5d00db77765363b6fa574ac1e6743293ab56994a5c060000547373f8f7b49691ab11ddd2e91599f48e1d128ef9fe643d37d2b5d360f7764f255594f104aac6e031b365a7a482624096a8c24410040a095660f6fc73efd0492ddcc6de150d2fcc81d3b26df25b41f5928d36e335c9ff067a7de8df941b07bc71477175d6e9a2cf51c5531a57411978ab28a4701929f238fa858ea68e4d1cadc686e6c4fbbc915d5b368b88cab0f5e998e582601ede5d484756c1d53b9ff00862c1918e5092cf40a35bc0b3788d9591e289880cf75b03c820f37f677612f84449c71d146ed6e2e61f08d695eb36eccea4f5b1d4c86cac8fa0aac7a959b534b34aa8aa9146cd7b0b7d4dbdee794962e4e3a62da110c1a00cf44c37cf640a0cb557dd5553c147445a47ab9e78e08218f572cd33b2c482df927d94c8f23160bc7d7a334452809e27cba97b23b8f66e6e311d16e5c2e5245a8d33be3f27495a12452b68e678269575927904dedee8ae07639eff4ebcd04a012b19a7fab8f4a7dd79aa6a8a39674996296300c3283c9e79506c0120d8d8fd47b6a552a09ad3ab435c447a323d61333e131d55a9753c31964fa23064bea205c30ff000f6ea92071e3d269c51ca9e03a1d296aa5959183c70ac6ea1831631ac8be857550de173c900116b7fb6f6a2391856871d21750a08a758eb6a50799beee791ccadac29bf98702c53c6098750d5737bdf8f6e1941a538f568949a0d3d246b2bcba543388d089008c36b1fab83c136365e07f4bfb6d9f06bc7a561694a7a748dc95769796159249ec033286d4feae6da458855000b0f694b1cfaf5ea5684e3a4764b24cc84184157468954d830257d21c1fa7a7ea09bdbdb25b5d479f5ea119ae7a0af37948e8a103478a42c6eb71e31186b1b0e7936e00f761451c3af16a9cb669d0139fc9295aa99ea1943f26491bd31c4e49b01724be9fa7fafeecb1863f6f49a497e5c3a2e5bc726b907fb1a7762679550b87e0c315890cbf88dec01b9238f66512244849f88f44f23b4b281f847f3ea0414a918b8bac6a0469f5d5e55b062107d141fcfb69b493ddd2c5c690307e5d1e1e90da53e1f074d511090cb50c6ae78d39d66470e5ac3ea7f1c73ed3476c753b8c927a3995a2f0a38b828503a31f87d591ac4ae21e28c39815e506368d619089174903891d6d7f66106a67404601e8a671e1821bab38f80bd6ed97de9bb7b6b23441a8f0b44bb5f6cd44c81bfdc855912e5ea60247eb8a91523247fc74f637e5db50cf25d951451a41ff000f51af3cee5482d76d8df2c75b0f970507ab5ef62eea33ebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebffd6dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd105ef9fe5e3d37dc793aadd3b7eab29d53bd2b276aaaccced18a99f1992ab6259aab21b7aa74d11a9763769206819feada8f3ec8370e5db2be2d221314c7895e07ed1c3a15ed5ce1baeda8b03b09ad87057f21f23c7a043647f29fd811e528eb7b8fb1f70769e2a82a62a98f6bc78ea7db586af681c48916665a6a9abafaea476035c2b244ae2e092091ecbedb942d525592eee1a60a6a148007e74e3d1addfb81b83c4d1d85b2c0cc29aaba987d95c0fb73d535ff396f8f380ea6f929b7772ed1db18edb1b33b2b63e364a5a1c1d0d3d061e2cd6db1fc1b23143494d1c54f4b3353454d23050356bd5f9f65bcc56ab6f781e340b13a8e1c2a31d1ef266e525d584b14f297951ce49a9a3678f9f54c8f89aac3e62530c6bf6b5281668f48b48ba6ce96636632a35aff4f64a1c4b6e626f8ebd09dff4e712a9ec3e5d19aeaea883178ea4a0a278a969d01969e142c61491daee152ffb41c9e40f4fd4fb0f5d47a59969435e8ee36120a9069d1b8da35ede48086f2296f2c8a546a8d9ef1b11cd9a107f3fd3d96e9a54afad7a52a6aa7571e8c062669241e489c5acc91684d00be8278b9b14245aff00e1edf535a1ebc48a8af0e97b89c881e159a662ecba43ac41250c80eaf2a13a5d549b0fea7dbab2673d688a0a818e97d4551082b24e526110364902f2a809b690a23d49fd4dfdbbe228ad4f4db5694534f5eb0d7d634d1492028b0bea0aa1ee63506cb6507f00826def424ae7cbaf28d03cb574156f1c536571391a3462ad55473c065f53001d08bb02786d5cdffc3dd988653a4f974ae0701d0b6403d54967fb1fb0badebb21b5b736ddcbcb8da7ab9c52ee5c7d14b5d8e7a62ec0197edd2434c4a9b30616bfe7d94addcf0fe94cbc0f1a63e5d0f7f7459dcc49736b2a9247c24d08ff003f4e985e84f8e5dcf85ff737b7a8abb2b9b9aa2af2998a98049595553529ca3d9432aa1016dc0000f6610ad9b05d2184e5aa48f5e8aae66dd6d7524815ad40d3a4e71d1703f1376d7c49dcabda7d615991a4c4d35784deb8212349066b6fcf3132a2c66f67c7a912c240f468207d7dac9e6b893424d31655c67d3a410ad8b24a896da266c823d7ab8debeec1c1556d9c4d4d1e5237a6aaa4a6a9a7a8495086a7a8892680c645cb2346d76e78e47d7d98c7514d07cba23935b31aa9af49dec3edcc5e231592a89b21474d1534734d3564f378e9a9e9a35b999d890523551700724fb6ee2502b53c3f975b8e22c4501a7cbaa36ee8c26fff00997bd24c56337157603ab30859e9e8e1a89e8ffbcb5264f1c998cc2232796072a45342e4aa27245cfb2f1772a9616c16bea474736d6b6d18596e50b1ae29514e94bd43f1df6e7c6acf53e7b1dbd1a08ab69de9331452d628a192457063a8f09754f2291c3ff43f5f692e0c8ae269a705a9fe0e8ee129711bc115b9f97d9d1bb9bbd287704d8cda1b6eadb71662b6782190639bef969e091c2792434e5d208914dc926edf41ed24fb9246a117b9fd38f4c47b2c81da69069840f3c75713d738e38fdb587a691250f4d454f1c86ffb6d3344b790ad81453fd2feccc3ab2c66b9a740cb87d571228182c69f6742752653ed25883b2c71ccc6250c5b53b8fc716f19d02f7361c7bb472e96a1383d32c86453d73abcac06668d650b22db9f50662dc0058937d00f04ff00aff8f6fb3ae349eb71a1a1af9f497af2cd675f23e92485d4ba5589bac80105d8037bfe7dd0b6aa66a474e335307a43cf909229e4291eb934dbc961ac17b823d571623e97f6c313524756605c0efc7484cc5669d05a50d32a48a141e0076172402433db8bdbde82f7558f5a2c147cba02b7a6723496c7f74a11724920141f90090025bfdbfb7146b6a0f4e939639e8ba6e8cd34acf04529f5a076b731a123d45985eec147a47f5f6ba18fbab4c53a2f9e51434e3d07b4425a9ac9aae54b795a3829c10007551e963f5b1626ff00e3ede7704e914e9a4520163c7fc1d3e20852bb1d4ceae92d455c70484b155791e55516045ee79bfe38f69a4ed8cb1f3e965a309274400fafeceacf36422e331d8392925a25a68a1f1d74732ea678cc24278990811bac86ed70411c7b5b0472288b411a3f1578fe5d2895c389032b6af2a7f97a12b6660735d93bbf1bd7fb1299b239ddc35c62458d0b53e32955c35665eba450452d050457677622e6c3ea7d98c36af732a436f4d6c7d387ccfcba28dcaf60dbed64bbba7a220fcc9f203e67ad89ba97adf13d4db036f6c5c39f34387a40b575aca165c964e63e5c86426fc97a9a86245ff4a051f8f7245a5b25a5bc702705193ea7ccf5036e17b26e17735dcbf131e1e83c87423fb53d22ebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebffd7dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd53cff3a6e9b3d81f17a83b0686984b96ea0dd7479a96454bc8bb7738131398bb0058471d47dab9fc7a7d907315b78d66245f8d0ff23d0a7946efe9f741116a24829f98e1d6a7f57462b929acd695238f484035badbd407faa45b0bfe78fe9ee3557f0dc8e3d4c6eab2a0a8ede3d2a76a533e3f5c4a4bc74728b00795a7a8e7487e54c68fc7038f68ae8b17d7f84f4bed58050a7a357b0aba182a20579821948005cb10cd60b13df80bc91f81cfb2da6893e5fe7e96495646a0f2e8c1c758697c6be455484894c2585d4358b95d245d7d5f4f7591f4b771c53ab428582d7a5ae36b499d5a47410c881a32edaac64b924926e3e9f51f4f74d6c0f1c74f1501683e21fe0f974af8b3e8c8b124e8e884faefc36904328fa1620717f7ad67d7a6bc2ae4f0eb28caa984a4406bbe95f23e98d751b9604feb6b71edc05b4e08eb7e17127874d951918ea96442c002a52452c000781723f2c2ff008e39f6e2bb01f6f5b4010839af48c9307870cf426929e78eb4c9298e78964239d52bd9d48d2c1b8637b8f6e09411dc3a5ad71201e26a20814c1a741457f54edea5ccbe4b1b4c948b34b1b4a31f1081649daca84c71854506dea3f9fafb695c6b2caa012470e9e1bacfe17865aa294cff003e993b67a5f25ba36c4c98ce257a7f0d4d24835c2f12a35a39632080aca4f07eb73ed73fea2311f1ff0087a4f6d7f189499303d7aae76de5beba94b6ccaedb397a718e89e928960a29a5a4581243f6ff006f221d0b1693603f038f6c47b83c03c2901af47276a4bb1f510c81949f97f3f3e92f91c076f7c80aaa1db91627258bdbb255c2d5f34e9306ae317215ae46b8d09e148b71cfba3dc49744848c85f5ea862b7b104cce053cbfd8eac0365fc52c76d0d978fc5fdc34753571ac3553a0f1cc3c1ea313ca39281bf3eee91148cea7ee3d15fefad535228bb5787f9fa95fecacf4dd74017756db8f72cf3855a89b275335612c253614b1eb0b046578fd3eebf4d6e574c916b1f3e8ca0ddb72a9304ba3e400e850eb5ea7ea1eb0a9697666cba1c5979516f4f405e606fe32247917cd126a370df4e3fa7b6843690ea31c193f2e98bdb9dd6ed7fc6aeaabf6d3f90e8c75367678c353244de032a467c8a2265fafabd26ee14dadc73ee8cec694e1d15f81191abc41aa9d2b23c8f9692488bb466375935b00b7e05fc6c57d4c4ffadc7b7a36d4952323a4e0056f91ff0057ece9ba7c9faa49199c6963fa9d4b38b5ac540b90def4b2791e3d3c1380f9f505b26ea8f1c6ece1ec4106f2071fd907820aff004f7657f439e9a96304d48cf49aaf7648d9106a9e600b122c2300dcdc9e4923dbe323874dd40afa0e831dc12a512d44b33de59175101b4f1fd91c71652391ef4405273d32f21382303a2c9baf310f9e6f50b225c00d73269bb3436e4334ac3f3fd3da88968323a4d2927f2e82b8f1f5b5f3c8f28bafedd405164f12b306447256dfdafa7e7da912e9ec1d2678c13a89ea6cb4c94ee88179b88d0006ccc4ebd4c780863ff0fa93eee80ea07aa484e83a7abf5f81bfcbf7a9fb8be30e67717756d592b72bd919a357b533b4b3cd8fdc5b630f837960a0c8602b94114d25656bcacfa91d255501811ec6bb56c5697bb7c8d7b16a321c1e0401e63d3a8e379e67bedb7798cedb369f046411556278823ece865a2fe5449415bf614bdef9b8b6823a2c54bfdd5a27dc1f6a0dda09726f92348d291c19041cfd748f6faf2aa210a978c231f2cd3f6f4fbfb892ba166dad3ea0f9ea34afd94eac17a2be34f567c7ac5d4526c4c44b265f2288999dd5999864371e582598453d73220a7a4120d42085523bf241201f67f67b75b58a910a771e24f13d02f75deeff0078975ddcbd83828c28fcbfca73d0ff00ed77453d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75fffd0dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd05dddbd7941db1d43d91d6f928965a5de3b3f39840ad70ab55534329a094db9fd8af489ffe41f6c5cc427b79623c194f4a2d2736d7304ea72ac0f5a1fc1849b19b833183c846b1e4b6e6632183a88c8d3e39692a66a394056fd4aad1589b9ff0f70fde446298851db53d4ff6922cd6d1ba9c151fe0e9fe9293edeb8c2978e09f44335b8ba8b0d28c781f5e09b9b91ecbe6d5c470e8ca1145d44ffabd7a1976aa391144e1c3283132dfd61d1859891c926d706fc1f6592d735e970d340471e873a29e4748bc8cb2343168bb81ab48e579b92d6ff6e47b4accc5854f0e9e43a4e3cfa52c5914a7d081813e360140b69217d60737fa9f7e422a2a73d599b560759573e29c40cf702ecad1aa804a5edeafc0201bdff1edeaa5053ab2d483439e9f26c9cc2861929668e532abcb19f41f02a8172fa8e9042f363724f1ef5293a404f3eb48c2adabd7a62932cd0cf8fa39d6aaac90b2cb56b18102b85565fb95055549d5e8038febeeb1e0a2b92c7d7ad9034bbab003d3cfa7d796a5eae093ce234954f8ef1ea7485ca048980b5ecc79e7e87daa65343538e9287aab05f2e9414d4924c40923d0ab2246d21551a4213660431f4dff00a7b71283f3ea85aa453881c3a1431749381e19638e48648ca1d5172c82cbe421459dd56c39b5efecc2de3f1280f49cc7a96b535af415efdeacc067aba9fcd426aa69244495e9e9efa60910156577d062f5fa0ab01f524703dfaead94320a57fd5ebd1ad8cd3c08e43e3d3fd5fb7a5e6cfeb0c3e06368e0a1823929e38fd30a2688b54624262660199d0f04f3723db8b6855695a6380e8a2f2692724d4f1f3f3ea5ee3c3d34ec682aeeef2b18e96017491d19010ec53f4a826e4df8ff0078f699850e93d278cbc635019f3e90390daf1454b38a68c45594abfe4e7c88afc9275317057eaa0836e7f3ee9a40ad4f4616d7f26b01fe0af480826ca0a8aa92ae685e2ab8c52d4834f2a1134765f2c454a2aa0b72c2c09bfbad75139c74612bc642e826a0d474e74b5151155112e4e16f0a0980a647d52449a5630ea4ea0cc2f72783716fa7b4e50a925db1d22796951a4f4a9c7e6e4ae82310a096392eae11f5aa02fa0cba99b90adc69ff0063eed1e9a5075ad416a4b67a992cab1c4febf2cb0b10cb7bb22fd03020f03f1737f7590115d3c7a72394961ddd468e53e404ca23d5eb553fd9726d7fadc2a8f6d46d9e39e9f7208ea25697604972ccc080410585c12757e5509e38f6ac1afe5d22908071d03dbb9a45531178c39fa48eca91a06366e5ec02aff527db8952c030e98670057e5d17c8b0ad9daf9653eaa4a4691fd3eaf2cc874f91d947e6dc7e3da8770a0faf4cf1615e27a975f45263a2f13c712b39967923080965b2945257d60722dfe3fd3db31b33b54faf5e9142e54f4dbb2f6a577606fada9b1f17134d90dd3b9b0d8584246eeeb2e4eb2289f546a75da347624dbe8b7f67b690b4ed1c6a3b98d3f9f447b85c9b686798b61549fd9d6eefb0767e33aff64ed4d93878a3871bb5b038cc252a46ba15968292381e6d3feaaa255691bfab31f72c4312c314712fc2a00fd9d4173ccd7134b339ee6627f6f4aef6e74d75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ffd1dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7445c11fd45bdfbaf75a237c8c8e2c3fc8bed910d928e5df9b95c7a52374aca7cd5653d6c4112ca8239a32490093fec7dc5fb94557738f88ff0023d4d7b1ce7e96dd4d7083f3c74c5042d3c5e6521ad224cd1e9b80480a4dc1e350b1239b1f61c9948183d0ba160401a4f0e842db73caaf23303e400481ad6553a780083620a82bfd7d94dc861523d3a54a380af1e86ec54d14f461d2f725587f68faaff424d880bfd7f1ecbc6411d2815141d37d5d54895c1d599b4ba44eb73a48b72cdc1b9d2dcffadef49ab553cabd3da868e1d2bf1f4e92caf04c8815116a0316bbbb0360341b82aeb61f5b71ecc046b5d278d3a48d230008fb3a76adfb6a4014683195d72296d2cde9b3294b69b371fe3c7b6e5022a7a75a8e466fdbd33c39294d2b4a1d56398ab24a4068d7c674140a7fcdb10390dc13ed949c804e9a83fe4e94100900f4b5c7297829e760240a156a4541223891ec53c60900dd09f51fcfd071ed746a5d14f547555d4a0d3d29d2ac64ef201f734d03d5a3ac4af2a692b658f52f9082a628b9040e7ebede642715a75a4880ce9a9e85fc1ceb2513491a52d60452078aa03543cd0c6ba5aebacf86c3f03d9cdb2322d464f4dc8554e9ad2bebe9d7192a6abc666aaa496012be88f45fd611f5aa2b3860c013fd00b1f762d21a6b523a74229145707a914d2d7f975c28108f197fb990f8846559490c6c8a6d7d5f80393efc6390e6a69d3322c5c18f4cb98dd1b769aa3c15797c579ce85d1f73119626e4f895c31f482bc017d43da494c21a8596a3e79e938b59dc178e1729f61e90793fe1997ae81d3211699d884026b43a25d2b7362359f4f00fe3da4740c41af5b01e1aea420fd9c3a486e6f3e3d2214e454432bbd3453c2808d31fea644e4117278b59b8f6d3562c85247af4a2175724568dc73d3553247e548ea296d55240aa6a154a2bac8432972181123937239d245bda7762580604353aab806a435457a7ea3a37a5883d3c71f8e3f246b15cab94d41d9c02487642a4f3f527da94501411d2367a350f581264156d1c86647916ef7460caac458cab6d23ca7e97fa8fc7b6a409903cfada31a8271d4cf52c71173726428baf4ab369b69009e490bfec6fed2aa50835c74bd24aafcfa8f5b550c50396550f6764166e74a720b11c0d47e9f5f6b62602a2bd2796b526bd00fbc19ab5453bc721925002b863a1a4d566500156e179fe800f6f2815eee9bae4d08a74e3b5f05163b16649134968d9c01751c72a1811cdff003fe1efd50c49a63a65cf700067a0ab76c8b356bb44c4c6c1d5a600bc4882e10823f2d2fe4703dbb6eba98e335e9a91e8b4639e8fa7f295e987ec4f91351d8995a62f86ea9c6cd9a4668ef0bee3ca7931f888f535c6b82232cc07362971ec75cb96825b91291db18afe7e5d47dcdf7e61b4fa753dd29a1fb071eb68cf63cea30ebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebfffd2dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75a16fcb6aba8a0f95bf2530f2dc887b637865b185d2ccaad9495aba9c0d26ca55b500073f5f71a4b2ebbebf85c77076a7edea66db232bb5ed73a1c18c03d26b6ae53ca94ae234d3245e278dc907594b2c88aa7e8845ae401ec3b760ab15d39e8576cc0852ad8e849a5aa28daf5e8bbaa2802d67d2afa45b8d279e7f1f9f64d3542f468942571e7d0bbb6321aa248c9177e2c08b7a4702e0d81b1ff0063ecb88209e947cbcfa52989fee272c15a39741889537f2a0e003f4038f7a5aab13f87add2b4238f5ec965970d08aa924114b1c2c2489816ba58f0a7ea2ded4098023b870ea9e099aaa079e3a2d5bdbe400dbec165c5e6a5869ee4d4c38fa8aa478c5d85fc68ccd1803ebf53ef4efe229c1c74ac6da400de20a9f2e81fcefce8eadc2462f9c549da3479a86586a61921922e4eb825892ca5fea0fe7db623c16e3f2e96dbedb331a37fb1d003ba3f990e6b2531a2d938bdc5b8230c29e34c4e1ebea182b12b1c45c44b0117e012dfeb7b51a370902f8785e1e9d09ecb61dbd51649dfb8f957a4cc3f3177d4cf03eecdb5bfb6f692e127c8612be48e23abfb35147e78901fc03fec7df9ece75a09646d7f238cf420b6b2b2456105b82a7ecff2f42b6daf9cb93c0cd0d5d26e6755854ad4c1e0c9a48f1923c89346d102a4adc7a8587bd24777131682721e9d5a7d8ad2ea32b259d6a7071d1b5db1fcc8faf1f1d4b054ef7c4d2d6c8847f09c8e493cbe650011124cfe58d828b85b0f6650ee170ab493e2f3af41e9b93db5314b6603d40a63a40f617f316db192825c7e2f7f4393a82a615a5a299e4717b9202d1248cfe33653f8e39f74bababa9d42292108e955972c240e246b125bd4f457ab3e446fdddf597c3e177864ea0490c91352e32aa8e23e4622191ea6b3ed2208ec480d7b58fb4ab60f28f3d5e67a110b58e14d063503f68ea4d1f6c7cbba1a96c9d2ed7a78a8a8d049f6990cfa1c87822567214470353eab29046a3627ebedf5dae7c39b9024a70af45d73b66d322b2491925bd0507424d07cddedac3cb494dbe7ac77443474c501a8c6aae5617d7ea0d7a42ce012d72748e3dd05bee07529753a7a0add72ed802e6de6d24faf460b687cc6dbdbc6a122c7613754f934f4ff000f93075f4f244a80eaf234b0aaa20278e7de8a4d8d711d7d07e7da190ff6a9a2bc6bd1b6d91d9b166a055a8a19e9aa18da5a4992d340ff004d0e0fee0601b9040b7bd2cfa46975208e8a27b22ad820af91078f430834d38fb894bda3b5c3017000f4136b5c01f4fe9eee644638e9214d3c457a85511e98c36a492213192320dc8d23d371f40e3fc3f3ee806081c3a7e2068319e92996ae2b092c00b2f02ff9fae9e6daaf707fc7dd9389eb4e38d719e8269646c857c5740ef2b15b83fb6aa8c755ec34f2a3f06f7f6a78802bd24383527a55666b169a8e048d8a2ac1693e9675d2da8eafadf8b1fe9ef64114ae07565a1a9a745af71e4bc33034ec1199e451ac6b5d2dc595870bc93cdbd99edf06a6c8c797457772d2b9af5b13ff0026ac0d363fa1b7d663c70b64735bf48abaa8f4177a7a2c6c4b47039524af85676254ff00698fb92b608c25bcc7ccb7f93a8979ae5325ec22bda13fc27ab82f67dd05baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baffd3dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75a34ff00319c2b633e63fc87aba65264a2ec6aeab555e2d1d6454f348ac1081a5bca47f4f710ef1726cf79ba71fefcfe5e7d4e9cb71fd472f5a0f3d18fdbd16cda95f168583589559236a066b79187f9d65ba907f6f4d886e7deef55248fc48fe1618e975ab18dbc36c3039e863a1ae596855d446cc5c031816b6af4dcdc7a7521fa8fcfb0c4b550c1c747d0d4919e975b3b2d18678e4219e2711b71ebba1e0d871fa2decb640350af4be8695ad0743b512c7551c3c80ae8da4020b95b0e7fd85f91f5f7ae20e71d6d4807e7d4eabc0e3678a38abe16a9245d1ac42a1b1b02c3ead6fa5fdd5d5173f8bab233d7b303a8d518ac61c76838da3869a2508634a789de556fd0ceec033062395f7bf1c84d2300757f0cebd45896e82dce74fecbce2d5be4769e06bea64d3a3eeb198f1318d893746306a1a41ff1f7e8eedd095d66a7a5904cd1b001c81f6f41643f1db6ea562c78da1fe14acfad3ede186284146375645541aa300d80b1f6e35dc8dfe88c0f428b7dce281433a293d2de97e3ae26185b464e165d52c8fae9bc80174e57c335ef7fa9b5fdbf113c4ca29feaf2e8c5399ad5bb5ad2be5e9d7a0ebbebdc5d5cd26471f8e75448d6534d8f559e7d0aa2591bc804727a8106c7573cdc7b36b5bfb585c995f03d071e941b89ee5008071f53c3d3a65afe9ef8d5b82aea32b94da1888abd8f8a16a9db34357354c6c144862678ccd018c024103963f8f6b9771d9e666d7251b86474c48fbdc2161425a3f93f0e961b67a33a070f48cd80c36231d24ef2881c6169e99602ecaed79fc0ed761f507db86e76b09a56e16a3f974c3df6eaad4962240a706af4b03d7fb42965862a14c6c8869e085d22fedaa97241ba96575617bf16038f6c99e107f4e506bf675e17d3e9d4432807cfa65c975b495d0c95907dbc216096178ce829e15263f1c62d72da5ae4907da2b8901ee0c3aa3ee917f66455abd24297a6e3aa9512650d18645fdb16ba0406dfa4136b5c13f5f690dc81414e90cb7d100786af9f43eec8ebac06dc890458f80b336b6acf10139bfa82ca0afe82dfd3db62e9d4d6b9e82bb8dc19dd8038f4f2e8446db74893b56c14b4bf7121bb908a352dfd2524d21835b827ebee923192ac4027d7a2f535014934e9da8a22a1a4b142469f1b7a94816bf3fef57f6c856059870ea8c0569e7d40cbe8a7a5b44d6691e4372756827d47421fa0bfbb866d183dd5e9a56a1f90e81cdc390d3a7d40331b1058afadaffd9ff003fdb9f6aa304f11d6a560787497a3961fb9699449a50adf9002dfd36b004fea3c8bfe7da8183f2e91b1a9e39e9af77e63ede9885b825447a6420928c0eb28b7000d27824fd7dbea9e2907f08ea924b41e54e8b766f3349143555b50c56929da56092c9a7cc63d3a2355f512d2390011c727d882d53c384c87801d10dc39793403dc7ada17f934d2cc3e218cbd442229739d83ba2b4902c1d23fb2863b1b9baa2ae91feb7b1a72db33d8348c32643d465cd634ee9a2b5d283fcbd5b2fb107419ebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebffd4dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75a61ff0031dc234df327be6548e48d6b3762c528745d0f21c4e36446f49f509549d3f9ff0063ee1ae6753fbdaf287f17f90753df2610762b207383fe13d56352d64982cd498ba83e22950d514a4dd6efa88f1904fea6d44820dc7b4db65d0915eda46c9151fe61d1a6e36e15c5c2f0e07a1d36fe6e3f04a7599525ff0038590fa543a9665bdb498c9bf1f5f68ef63392471e96da3a100f98e97b80ad582b5642d74bdd5ae34caa093763c1bb0febcf1ec96514f9f46bc6bd18edbf5824fb5372226b49adcd80d4b60840fe87db25b23d3aa95a027a111aa62658e28c492190e92bc104a2924b9e401c707decb79018ebd1d49a93c3ae523c32afa53d62c742b85b953c7a5883600ff4bfb6dd94f019e94ad7f2ebb8b1b21819a7896a25578d8c81c231f5ea53cdc10bf4363eea10f9a54d7aaeb5d5839e9fc63a09648244f5ca1c0672163214feb561f5f493f5fcfb782a920d3a63c575a863d28061e86a030684bba00de50185ecba3d445d4311c81f9f6b1349271d261732ab543e3a4165fad29338d30928fccdadd91e2d6a430fa390968ffafe39f75922566341ddd1bc1bc5c5b6911ca47fabd7a0b723d28f47279e3a791cc68e5a4865956651fa8200ee02fd3f1c8f6ec1b4c5212d2024fae7a57fd62bb7edf1bf97f97a76c575254401279ff884f4d3ac6e907dcc8e11dbd2eb62c6e3e9eab8fafbb3edb12569ab4f4c36f9775a6a1abece9698ed934d43312e95b1902cc950ed671a7fc3d474916f6d8b4890920b74d49bcddc88159874ada3c4522232f8818597525c791939beab330075371eee2202a6bd253773935d66bd4e5a6a4891cf886a3616b59401e9f4b91650ff00503f1eda718af13d50dc4cc40af52a99654a704c2ed2489c095d75460b5bf7000010141ff5fdb63569a1e3d36685b2703fd58ea4c4d0c09a5bd45f5310c4b2fa9b569573fd91fef1eec011e7c7af3024d40c0eb9bd5470cb1c376919f50b002d0920586ab7abea3dec122bd32eb5a9e1d25b3759000e1a58d9e21229d207a0e924a702fac7fbc9f7646270079f49d988e0327a2e3b972d1cd5022575454258b3b10da9790791a4580bff005ff6fed5235400075b39ae31d345065405919e716a82aced70be956b91ea1a7936361c587b508096d27a49210a31c7a07b79ee1353535022ac062d694d182fa9740942b48a10dc06617b37161ece2da066d38e8ba79a8294e8b9e5b3a772ee38b0b8e77969a09c42ed60e0cf13b1b1604a69b926c38b7b7ef26318481781e3d20b280cb2995cf0e1d6e9dfcac30036f7c2beb08029535d53b8f26d75d3a9aa731511ea51fea4ac03d8ff009701fdd16cc7cea7f9f516f34b86deeee9c0507f2eac47d9ef41eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebfffd5dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75a92ff00309c24d51f30fbce883c5e6acc8e132347a39d131dbb8d921d41be8eda086febabdc39cd0a46ef7a3d483fc8753b725951b158b11e6d5fdbd54776fed6aa42b96a75f14d19d72310418ea2361ac13f4b5c7e7d8512568a652868474377816642a0608e1d26f676e696682392491499098e6849b78ea1418e404be82ba80b8ff023d88a41f576de20f3ff0008e88a2536f318db041ff8ae86ec264054bc52c4c511ec0a1b160e9c1005cd85cdd4fd1adec393295241e3d1ec0411f9f46476ae4e330468f21610940ee47d030e196d716b8ff6fed19a0c797572a41a81c7a5a606a3f841a8f2646ab292bd5c9247254bddd4ceda9299550008917d00b7007b657b0658b1af4f39f174958c26387f97f3e96a7230806ae550646658de34372acdfaaea0f37fafbdb609620f5b50d5d00e3e7d3ac758fe2891640815947d45cebbdc3eae035be83dec1620007ad695c923a55d170eacba9194a194589f229b9b6a20d8b0faf1ed42a93c3a62402b4af97422e2e6414b10f1a01a8cba5ee0b02d72ac52cce9fd3f3cfb5b12b014e8b650751a75dcf985819a4a408fa83864d6a021074333e94d454000806fed523aa9240e9d8e12f4ae074d353b8a70f0aad343208f5c8a255617b8f58720125750b8b8f7b17ce87481503a5496684575907ac499eaaf198cd2c51fa8c8be25d0d1b3ff0064e92a193f37b587b73eacb0c8eb46d954950faba788e6a2ab8ec508a9545d32c8cb20662b617160ca55bea7fa7d7debc447a578f4919190d01edea23e3a21148c8ce6520b3212046cccc49001fa0e2e3fde3db653cc1eae2538040a74d6b4857c8c87cbac790f940b0d2fa4e91f551a38161cfb46ea41eae5aba69d7554a3f675155b21591803e843651a81f49b5ffdbfbad38fa75e524548ea0390920864d4550a9545b85e7fb5ab9bf06e7fdb7bf003551ab4eafaaa2be7d60ab9625902c64c68aa497b0fdcd36e01fc9ff791eed80e07974d313a49271d05fb92b9291e4420165d6c4b6ab0924f55d981fad871fe3ef4a4862c00d24f498934a9e8b067b2b157e4e5866d669412f3396f185d0fe4640403e970a179b71ed444c35903ad82446581ee3d25f70ee47a1a794d3fa2129742ac247863b58c45978bb03c7e40f6650a06208e3d17ca74eaa8e8a2f64762cf8a1fc3e91f5e6b28ed152c71bada1865215aa24455660b106b2ff53cfb32370208c177a31e8b0a19a460abdbebd2fba4767b52504b9bab42e5165092ba82d3d75402669496bd963074823f27d92bdc349231d55cf47104261897146ff275bcc7c14dbedb67e24f46e36452b349b2a932332b1b9593293d457117ff0082ce3dccfb2c662daec90f1d03f9e7a8177f904bbc6e0e387887f963a36becd3a28ebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebfffd6dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ab27f310a2149f363b15a22564a9c56d1c880589fdd7c253c66c8013a5d62bffb0f70ff003682bbcdc11e614ff2ea74e44fd5d8a153c03b0fe7d57d767ed169293efe2851a9322e45487e5609a4babb5b901661c81fea87f8fb074c8400e280743fb6c9d0c3e1eab437f67f1fd459ca1837355f828b335f22c33244fa230a6cb52fa790908601ff00c3f1ecd36767632c4cd55a57a2ddd04685255c313d0dfb3b75d2a4d44cf3834b58b12ace5d1a26f20d504f1b820189d08371c1f77bfb7a9665191c7fcfd52d6e0900746d36565296786d1b9319631c89a975239360f1f20943f5f6412c656a698e8d0127a17a8a9de2b3d3ca5d480ede4f51241003f905eeed6b7f88f69b4b2e4019e9cd41a8294e9fa9de312ca1d82eb8d59daf72194037e2f74ff1f7e075125860f5b624004797f83a7fa4a8491614d0c04aae59f8624a8d4a4dc5f9fc7bbab528b4c75e38a834e97d4f925bc54f1ac81969c5e45520855e5b90083c9ff63ed6a3282053a44549c93e7d2824c93c51f8a9c9124802a69058aa90a59893c8b8faff004f6a15ea29519e9a58fbb3c3ae31a998a2852ba4ab39059e40b72c13f0599dbfaf1eee1751c57a7b5e9e95d89c3cf51e49cc6ab4baac1665f24b2b104305238b20faf16e7dac8edaa3501fb7a624bb08295ef3d3acf84563c4290ca015541a5c330be83a00e3d2bc83c7bf34473ebd505d13f11a9f5e982a68a5f35c9d2e1401e35b2003fa116b8623fa7b4cd190d55e9c122d3edea2d3d5b452fef6bd0cc230ca0b0550b641a39286df9febede0d835cd7ad346080578f5905506791514c91a3f8d085b104b16d0ff00436163ed36aa375e2a56848c9ea3666092ae92a628aa1a96a19032ceab7752a549501828238e3db7302f1965601baac4c01058122bd2526a8a87789d1d6f185b931ff9d17b35ae5429e2fedad4757d9d5d8a2e0f1ea0642a64f1861a4942c23d2a3582ff009b5ec147373f8f7b9189a53a6f586c134e80adff009614501b92cc5cd9a3372c7f4feabeab293f4febef6a450015a8e9a22a07a745433790324ed09fdb3348f2cb22b349c28b5db51ba961f4078f6b618eb93827ad16541aabd039d85d814b8ba0aaaa95e5861c7c4ab22cda545548a3c7047185b333483fa026fece6db4c28656f840e89ee599d820f889e8b5f5b62f2fda1bdbefde3d5555b21684687f1e2b19112092c7d0b65e05f9627d91dfde99a4d54a1ad00f41d1ad8d8aaaeb70740e27e7d5a1d36dfa6db5b671f4888608e18505b497bbb0b0925d3a6ecefc8ff63eed6b18278e2bc7ab5e3fc657e1a63add0fe34c0f4df1f7a660906974eb8da7a85b4db561e95c71f8e1bdcef6029656a3fe16bfe0eb1cb723aafef0fac8dfe1e86ff6afa45d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7fffd7dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75acbff0033ac31c6fcce9ab97f4677616d0c96952dab5518abc7cce4f1c0f0fd05c7b89f9cd0aeefaa9868d4fecc75377b77206d95d29959587eda1e8a95661e8f21849e8a5513c33b175560093a8ea0406b59d07201f61097c329a5879f439d4eb28900c8eaabbe5d74536f1c6a52248f057e36a5b2581c8000f9212862a8a4a852082ae8c5587f803ed9b2b892c6eb58155614ff0057cfaf5edb2dedba9028e0f45c36717dab89c76d8c9c966a3863a786ae572638a48c58c32eabb243a8f0df41feb7b3817a93377573ebd17c76ed18a7a7f83a33db0f7ebe32be2a6ad91a2428b1df56b4900d3a6656be9f5a9e187d47b45776c002cbc3a5f6f281dad93ebd1d6db1b9e9ab69d23f22b7a4b01a94dce90414218ead5fd3d9348ae95c5474b1815d24703d2ce98877fb82e644736900208d454a816e081c5bfc3da6507f3af575714c9cf4a8c4d4708c12ef1b91231b6a55bf0c093636503e9eec010d5d3d699b35ae3a1331753a34b9d0352fa98d882a08604dfe8b7e47f8fb54b5143e7d2392bab4fcfa79824804cf2053afd4a256b953a82fa7fa903f1c5bdab8b2c08ea8c580c9e9558c452ec08b9bd89161e85fc31bfe9b91f9bfb318d4f98eee986734e9758e9929e997caccaa19e3ba80de15721220c8a012a5b82d6f6609f00e8ba563acd3a7794c6f6645b4ab22d8aa952fa87d585871f8f7e7008c71ebc858609c74d759461958e9d480d9027f53c11a8dc0bb1e7f1ed2327123874a83815cd3a4b1c528f4b300cb23c8ac4b1d1f901edfd96fc7b4e548f2cf4ff88719eba0b6525e3450c13f708d275022ec7f02e7fc3da66af981d69d89268dd27f32228e29c7acab1b68562f22dc1f5295b000ff00b0f75f8b1e5d5e3ad3231d06d5956b1d42aa92be3fa2dce8f52f04b726dff15f6c119e38e9d3a587a9e983279a869a19407b2a2166522ec5f8be962f7207f41f51efde469c3a61b506afcfa293d99b9d99ee926911b3d98150599ade9453fa47bdc21989c63ad3514127a2a5b87703c8952e6695a436676d6151554a920b3fa6c83fc39f6796b6eecc19b029d229e5014e3a283bab335bd87b9e9f6f6195aa68d6be53abd6055562e9479a4b1b2410b7083f4802ff009f69f74bfd3aa38f283f99e9cb1b6f10876f3eace7e39755c1b53088f2c0b256ca91355cfe33631a9bf8d491afc687f1f43f9f64684cd20908fb7fcdd1dca42a0854d107f87a1ff298f6cae469d52295b170d5414eaad64fbcaa69912087816745761a80fc71eceed7b9d0018247f87a2ab95d10ca588d5a49fe5d6e7fd6b8dfe0fd77b13145046d8ed9db6a8de300008f4f87a38dd40048015d4fb9e205d1042be8a3fc1d635dcb6bb89dfd5c9fe7d2dbdbbd33d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ffd0dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd789b727803924fe3dfbaf75ab77f327eeeeb1ecef9734543d77b8e9b73b6c0da906cbde591c54d154e328f754394abad6c541531332563d2d34c1267425564054136f718f3a156bd89d41c474af971af5357b6d0bfeedbe2fc0c8081f966bd025888fcd0842ace8f661cf9015602cea7eac403c0fa8fa1f60a7d35c8c7520b8a0ad3a0c7b4b66439cc6d45318c7dc3ab3d3cfc1d3320ba329b1b6b02cc07d7da3900634f3f2ea904841038afa7fabd3aaa3ed3d99534ad50f253982784b47550db492caccaae1ac3d248bfd3f3cfb695c86218e0f4ae7845032700388f31d037b7378498aaa8f0b9d3ae8411f6b59a4b4d407558a07bfaa00792a6f6fa8f66d6b3d4689723a2a923286bebd1b0d95be2a71c634fba59a95de37a69d1c35ae3d054ff0064103907f3ee97566a4074e1d298e76a5180af0e8d9ed2dfb4d591c51cd2da662183023d4ad65d7a4725ae79fcf1ec9da32868463a70d5b2b4afa74376272b4d533246b22fa96c5d34697b85e79fa31b5bfd7f75a50d01eb7563e5d09545530af8e3fa9fd4e4117b7e2e0defaad6b7d3da8080f03d272c5bcb3d28a9a713ea560216b06565b8202137d40f038e2df83ed5442a7867ad31341e7d2c28deed1c91b8d41c006c48942adecc4d87ab8e7d98c6b80699e92b9c1a0f2e9fdf28d13f958aa69b095741258122d74d2418d9be87f1ed55682a7874948d469d48a1c9960cf1ea2010a01bba20049202ea3c8feb7f75560df6f5bd34e3d3fc750af1a2f902065bb86d4f71662adf81707f37e07bf10680755d5e678f50e49699048b24c8f20d3e6b370dc701803704dae3db0ea0d7d7a7918b8aaf4c357591d5c3a2252a35020937baa1e1996dc723da464d7c38f4e8241e83acae4258eaea5575342b0ea2757124ad73a5db816b0febc5fda663a5b2303a5428630471ff0027415e5772431c3532b8851d109d5a96cafc8285dae9a4b0e07b65aa6add363b1866bd174de1bf63a78677570cc6e74b1d2a413ebb80780bf803ebeeb1a39615183d38f406b5e8a56e3dcf5396aa2509209768549b040c186b21b955e757fadf5f6776b6ccb93c0f49267069a787452fb1b78b5633ed7c248f23d4369adad865244b36ab7da46ca7d51467966fcfe3deae2ed61478a3f3e27d3aac56c64652df90e8c8fc5de98d3353667294c64966b3eb7426da4ebd3f407c77fc7e4fd7d86262b33af712b53d1bbc42d60500fea9ead331bb6a75860a5a14fb680c40cf3ad9453c456ee0e91eb9194fa47f8fb5912d42a47fb7a4e92541d66a41c75c32551438ddc3b371f4f8dacca243b9b0406268d8255e582d7d3cb3d253b1ba7ded524642df8d445fd8936c40d756f10153ac60798af0e906e40aedf7b3b3807c36c9f2c75b5dfc72f90dd47f24fae31dbefa7f71d3e6709033e1b278b90ac19ddad98c6335156e077162d8fdc63b23453d3b210c34481752165e7dcdebc00a53ac6c911918eafdbe47ece87bf7bea9d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ffd1dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd51dff399fe61e3e3075aaf45f5865e28fbb7b53115072159495318aed85d7f53aa8abf32880974ce6735b53d02fa59175cbf853eeba5a57f052bf3a790ff0067a556d1ab31790f68fe67ad763a1fa7f27b7fa83adbb96bea6a5b1fdbfbbb7a62fc93c6e7ec7f82f81b1f3d44f25e692a2aa649e4bb96661ec11cf962608ec6638fc3fe6ea5bf6e6f543ee1664d5b486fe79e8f96d44929e38286ab449530a88f59651154a5ee2589978d2c0dc5fe878f71b693a402734ea4599812cca6829d2bb27b7e9eb698858cbc611ac86e2c48e491fab51238b7e7db661048ce3a42b23022a7a23ddd7d603290c95b4b0a9aca61202cd19ff2a8c722199402491f83f83c1f69e48c21ad053a33b6981d4a781ff57eceaafbb0764bd354492d2c406992452029bab283a91c7faa5716fc71ef4180a10734e9f7b75a92f9d5fcba4b6d4dcd59859971d5acdf6aac4a86179210491fa0dcf8aff8bf1ed4c1395217576748de0cd7d3a32982dd4f12d3cf1d468d4c852a2370d185b5ca9008319bff004f6abc3826c1f8ba698b290470e8d0ec8ecca49a38e9eb268cd40d263981004ca2cafa4022d2023e97f6865b0916ac9f0f5e370095ae08e8c462b79d354c7492464952960be71a9f41bb3292783c5edee814ad2a87aa135ab13d0a541b821abfb7f1cb186173a1643e52000e56c7866914f20f1ed522922ba71d276702a0d01e96f479d748cb7edac519d463f28d6baac0b00412141bd87e7dac8f50214af4db95d3f171e9c06e5a23246a92b429a5da5794aea11a8bd86b2da94b1b1fa91ed41c8a5283a4464009151d471b8694925244d2e4949526d2ba4162cea91fa246bfa6d6b93cfbaa0c104e7aabb1390dd66fef4ca2658e8eb12576078762238411f55fa00e0371f83f43ef7e1b0073d5a3910fc583d6693700a5a491eaa78de4608af50b22fa892496746b950bf93fd3da6752b5f43d2a565078e3a8126eaa686195da58caa1263d2e0178800751426e08bf3f8f69a46f0c127cfadea2c71d045be37ed0d353355f9c2050e9f6cb62d29b0d2cedaae16c6e2df5f699bf5285780f2f5e94c24d74f451f73765254433c42e812462896fdb25493cb1babb291eafe9edc8ed656a631d5cb2862471e8b9e6b71566598879ce93239908364241bac76b1badf927803d98476e1284804f4d3b963e7d005bfb77930cf88c34cc67963f0d5d643761666b353c6e0f007d0db93edbb9bad20c719cf99ff375b584960c41a7533a7faa25cde5a9aaeaa066533212d28bdfd5a8d811c05bf37fafb229a4327629fb7a3782d4c4be2bafcfab7aeb3d8e169a96871918829e131ad44e100042f0c11ffd5b807fa5bdb71c26461a4d29d2799b493231249e1f2e8cee4f1d4f4988921a48d904308d76e0be802fadbeace4fd4fb3403c3145c1a71e91c352c4b71eb9fc51eb09bb2be456dfc9d40355b73aea39f7a656428cd0455d4774c45231b04f24954d700fd6c7fa7b19726d89bddcd2761fa718a93f3f2e835cf1b9fd26cd25ba1a4931d3f9799fb3a24385f90fd8dfcb73f98176de5b65475076567b7ed467b71ec758e5184ddfb2b76cd1e61a2a78d24645cb533cf3c90cfa078254b06b1b7b98a789482ea7bb1d42e81678823e0814fb3add17a33bb361fc87eaeda9db5d6f935c9ed8dd78f8eae10e556b719581545761f2b4eaccd4993c6ce4c72c6dc820117520944ac18547481d1a3628c323a173defaa75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7fffd2dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf745d7e557c95d81f133a4779774f61d6c7163b6e63e55c36243815fb9f72544722e1b6ee2e1bf927abc8552807483a220ce785f756340282ac7007cfabc685d828e1e7f21d7cec3bc3bb37b7c9aed8dfbdd7d855b5190dd3be3705565aa51a458a1c1e2a996518bc563aa6495841438da302048154048d430e7926b636ad12549ab3713f3f4fcba5acc8c02c6b40381ff57af5b2ff005474ca6f9fe5bfb3b6f63a01fde1d838ac576262238d44cf2cc8af90ae8d0a5cbb5563ea64e47eab7b24e75dbbf78ed932a2feaa00cbf971e843ca7b91dbb7d82576a4727637d87a0176bd24535352492c3fe531aa32396263d0546a89d473e363f4ff00527dc1342050e29d4ecd26b0581ece860c7c5155d25901fb8896c509b15f51d2cadf4d1a85883f51fe3eeca2a0e3a2f653a87a7417ef0db82a1654789ece18ca23406dc5edaee75692783c71ed2cc84d69c3a590104834eeeabd7b5baaa09057d7d0512f9dcc92d65320d3e76be83511af377d03d4a2df4bfb424216f4e8e2162d40ff000d71d103de3b22a71f58f3c31920a13655371f82841008d207d7ded0f8640ad47af56940352a3b7a6cc35757621163954cb4c00d48f72d19d56bd88b06507f1ed4abb6aa839e8bdd4c874e9e845c66745d3ed6a99155832aa9175e6fa98fd5ae47d4106decc63b852a039a1e9892d9864ad074306dbeccacc72c54f5e5da017d0e391aae081a811a5890793f53ede10dbcbdcad46e9148654a8e2bd0b586ed883c6c8b5ce0822542b218d8042c7c320e0984a9b5c5fe9edc4b63abe2a0e934b2803b973d0838eeee91a9a0fbccaaea4f4a95f1ac653592b1b4a6da8aded63fd3fc7da816d2960569a474919a3230327e7d4b5ee58ebe634ff7461861546749620aac03d9bc7306d2ecea3fd87d3de9a370c028aa749c2aa9d40e7a96ddb58f8c4a9078811ff01c97b2a29b17205c7e07d4717f7af05c93a05074f0656a549af51297b5aa03460561485e46691575a381f48cea3756e7d5607dec44541ad493d56b91518eb85676d412d682b3b7943253cbe7a922255d5eb9969cb80922df963f5fa7b4cf6c32751afa74a1240468c0cf496dcfdd8b4b1c94904f154a80b1a547955e4d2796842a8fdd32116e4db9ff000f695aca49490c74f4ba3d118ad6bf2e807dc1d995f9e955da4929e30caa8b2b100aaa9b698835f5ded7bf1ed44160b001ad831f975b2c5c9d008e90751959a65796a279129e2024981b23b2137d24afe98bfa0fadb9f6f3bc695a9a0eac91123cfa09f746f69666fb1c3caf044c648e79348d4f16964fdbfed042c410dc161ecba7bc4d2556b5e94adab0a70a75cbaffafe7dc591867a94614f1cdad0904090b1d4d2393c13fef03d93cf2f103e33d1a41082199c75661d53d6c1529e38613152c6c8cd3142be6bdae913581160383ed948de438eaf34a00a1e34e8feed6db9062b1fa634448e30af2a28556234d8bb700836fa9e4ff4f6611a88d683a269650cc2bd4dcdaa474afe390dde27b7a6e044e972df524bff0041f507fc7dba4161403aa2e0d6bc3ab1cf8dfd5d1750f4b55e6ab61116ebec1f1e52a8ba699a9e927468f1344c080fa96090c8c3f05bfc3dce1ca7b5feefdbe22eb49a4019bf6607509736eea772dcdd51eb0444a8fb7cfaa1afe70bb086037e757f6753c354c770edac9edbae9e8d48922c8e0241251bbbaa190c7251d6b02012c2d7b581f62dd20afc22bd066290ab38231d64fe4f1f3f2a7e3276e537576fec955c7d31da35d478fca1aba97ada7dafbaaa644a7c6ee9496ede0a57927582abd2bae23a985d01f686e601111227c3f8bfcfd392699eab4a48063e7f2eb76f8a58a78a39a191268668d258a589d648e58a450f1c91ba92ae8ea41041b107da7e907593dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd3dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd277776eddb7b0b6be7b7a6f0cc50edfdafb6317599acee6b2532d3d0e371b430b4d5353512b9b05445e00bb3310a01240f7a240049e1d6c02480067af9feff365fe633b8be74f7447b6f6a5464b19d27b1ea2be93aeb02933d1b665c49e0a8ddd98819d63392cc001612de9a782c80fea2545a4059fc69450d303d07afe7fe0e95955484c60f79e27fc9d572e3705575341f6f1c343f7159538ec6d343f788f58f5b5b5b1429126965fdc8a2faa9275eab723d9d15f8146a03d7d7ab84052a1b875bcdfc5cc6aec8d97b2f01a5051c7b470584afa72816068e0c553c2dfb5760aa1ee187f436f6ddd20955c3640e9b0e54abaf119e8adf75f4bcdd57d875b1d1d2ebd97baea2a72bb66ba23a63a6f29f256e06605405968e6949887fbb2065b7e93ee07e64d9db6dbf7d2bfe2b21254fa7a8ea71e56de63dcf6f4d6e3eaa21461fe023f67ede83f7a318f8966490206b02156c18477fdb92e082ecbfef5ec8d554ad01c74233de703a69af586a926203ab28fcd99469e3d1fd47e6ded3c8986f5eb4b55391d001bcb6e23234c29d04eaeda87d52588963604d810c7e87ebcfb2b9d34b06031d19c2f9d35ec3d132ec4eb9a597564a9a96f016759e10a7f664716ba01cac5fd7fc7db2ce3fcfd2e8c9234b7fc5f458731d76d18a978221a15fc843283656fa102df55bffb1fcfbb8720547c3e9d6bc205b8741654e0a7c6bc8d1c1244509d25412095b6a1a41fc0fc5bdbe2414ad71d7991863cbac1164668974cced602caf7285947f87d2fcfe7dbd1cfa69a5f3d249228e84d33d753e6aa503341202ca07d4b037e7d202f22e0fb328aee80286cf45d2dad41ec0471e930dbdde393c723cf4ee849550faa3249376d25832ea27ebc9f6602e66f26e921b288f119ea652eedc8d5cacf0d7d526853082f2300a5ac4682c7e96ff03fd3dd0ee1228355523ece9b6dba0739d43ece9e867338e2211d6544c574843e624017bdd85f95bfd47e7da73b8b56a231d5d76d8f2109229d3dc5bc7740a714ef9002352481242b746b9b6a62548207d2c7db6770988e0283d3a7576c8568749a750bef72ac1e49ab26779afab512cee4926f76b31bff00b1f7517cea0ea515e9e6b146a761c75cfee665505e571a02872cc033285e05cfa401f43ef66fd8e0f0a7975b366a8076f502a370d2d2eaf1c9e59001ad696d248cdfea6594dd545c7e2e7db4f7c6986a9e9e8a15ae9141d32e432791c8c7799a58e9c440474e0b056d44db5006eec00e49fc7b2f79dde9a8e7a56534d3428a9e9f7676c69b335626aa8898f502dad48baf1a0dc01fd9ff006decbe598f01d288621c58747ebabbad432c1e4a5fb7a1411aea75d324ca2dfa0581d2df4ff63eeaab8a9eb72c8b1ea2bf111f9747d369608d22c71d240aba151635d03f6c10ab761f46b5afed725570a3a2d9083ab59e3d0ba22929b1f797c42753c9736120bf05edaad706e00fcfb7f49c5327a4751534f87a18be3ff53d476beffa5aec8401f666d2961aecd48cacb0e43271912d0e1507d192ea259ffa20b7f6bd8b7957686dc6f3c7917fc563209f99f21d0679a7794da76f68636ff1d94517e43cdbfcdd58feefac4afad828235d34d8d042a20d31ac8134a0503d3a514002dee6e86308951d422c49d449a927aa6ffe6efb1df70fc6bc5e7a9e5344db47b030559555e1a64a9a2c665fcd8aac9a8e5811a54a9124b194e0a96faf1edc0090ca16a7ad291f953ad5feade9303989694b56c6c8d1faa8676a6a89648c2c91bcb332b21335f54814afa85ad71eead116401cf571552a475b9c7f261fe60f1f776c1a0f8e7dabb8a3aaed2d918b0366656ba7ff002adddb468d004c7cb24ba64a8cbe020d2aa5bd7352d8dae86e5af13c2da1be1f23d56e1037eaa0c1e23d0ff9babeaf75e92f5ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd4dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf741eeededaeafd87ff1fa761ecbdace4d8459ddc988c6d431e785a6a9ab8e763c7e14fbd8563c01ebc013c075a83ff3c1fe6978eee4abadf8c9d09bb84fd55b73c557bff7661aa1becfb0370c6e3edf0b49531e933edbc24d6d647a67a9b93a9557dfa389a47d4cbfa40fed3fe6e95c69e1aea3fda1e1f21fe7eb586c5e51721b80b54caf5115044f504c93b69768a3015229822858f5b695623483c31fa7b3a8028a3b2e47556a935031d1c0e99c5c9ba7b33a67093d3d352d66777eece76a630c2d55f6c72304ef097895d55923523975245c917160a918b4c94a91d6893925f3d6f0bb0695171d0e9b80ab188c580d29181100d61c5b4fd0fb6dc64938a9ebdf69e857dd9b1717dafb1ab36865ca4352c9f7380c8d834d8fcb4018d2d54727ea211fd32016d51b11ec39be6d70ee7692dbb81ab8a9f46f2fcba36d9f759f68bd8ee626eca8d43c8af9f556197c0e77079ec86d8dc98f928b2d80ac5a7ada59c0f1d5c719ba56c4cd659a86a62f5a48b7041b7d6fee0e9ed26b5ba7b7b84d2e8723d7e63d4753f595e41716b1dd5bb8689d6a29e5f23f31d40cc62e110a4b12b8591c490a20090c135c2988f1c42f10e2ff00461eef342acb54383feaff0050ebc246d47d3a0a721492492d5c3554804c81ef1bd9ae2fa7c88a3d2fc723fc3d974d102b4e96a914520e3a023706da8a95e7445d74b36b6785f531d3212cd7d5c326a3c7f41eca1a2f0d98797462926b5fe9740665b682475829c85fb6a8490533b47e87f50d50486d7f2203717fa8f7ea8514fd9d381b15f3e828dd9d70b5305453c31ad1d6104c33ac61c7d3fd49fed9b7fbcfba06209d46a3a7031238d47402e6baee64043c2de5450b22aa1504852435bfdacdef6bfbb230ad57f675e782b4229d07359b36ad4b08f58d3c5fd5665b9fa1e3fde7dbaf370d383d35e0328274f489cc6cdc8901fed055f8d832205b30600fd08e4daff4f6e2debc6000c74f4c3db337705c748ea8c46e68995a9b0c8b1abe972c268cdc7210016517febc8f7a37e18053ebd6be99c0e1f9f5c5729bde0578936ab4965b2b255ba00e0deeb743e9b7bafd4ad687a7a382a05169d607dc1d8332301b46950caabcd5574d327a6d66281148bb2dfdf8dc05eef2f974f0b7a7ad7acb156f6657c875350e35352dc52d2bcb3b7a40215e62c8b7ff0001feb7bd1b85268081d51a37f24c74f54db7b72559be4a7ab90b32b7eeca4fa0704e850b180589b80bfec7dd0cca6a4d6a3a6c235684f4b3c6ecd98346ee8cabe92a9c0d4c0900b71722df93f4f7af1180040eac223e941d0898fd9d0bcd1097d610a3bb0bdadcd80d43f4df8ff5fdb65c807b896e9e108a02467a35bd61b012434d53514c12156578e9da33aa4627d0d2ff00b410381eda8d720f9f5e9595469519ff000747636f61ac69e18e254d0158a0506c52d603f057fde2fed646daaa6b9e8b9811a98934e861c3c73d22ea542d349204d659742471fa8b024db505fe9f4f6ad5694c67a4ef18635271d2df138ac86f1cfe2b0385d5555990923a3a60b1b2c6d50e47967987f629e923bb331fadbdaeb4b392f2ea2b6872ce683fcffb3a4175711edf6d2dccd845cfe5e54fb7ab75ebfd958aeabd8d8ddad878ed3ac265ada96b79ab72132eaaaab9dc7acb3c96b5ff00b200fa0f73a6cfb645b6da436b10e0327cc9f33d40dbcee536e97b35d4be6703c80f21d409232643210c49670ec45ae4df926f625aff009fafb12003481e5d141e14e8967ce8db6bbb3e2df77e14c713ca7645764202ca19526c4b47928dc291652be0fa8e47bb227782a33d3645064d057ad3772455e349e24fbd6a98a9e28d649634579e7d428ae6598b34b0d4a3a00c016420fd05fdd29193553e5d5c8a9a8f4e846ea9ed2dd1d69bc36eef1d895f59b6b716ddaba0c9e132914e94cf4798c64f1b5443f7113967565b2b86628c2e0020dfda7b888952a4ea3f2eb71b3292188e19f9f5be2ff2f4f9dfb33e6775462eb27c8e2b1ddb981a18e9f7c6d48a758a6a89a9ad03ee2c4d24856593155f2292c1757824ba9b02b72ca32d430c8e9b9a2f0daab943c3ab0df7ee99ebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baffd5dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd05ddaddd7d4fd1db727dd9db5bff6cec3c14085bef37064e0a37a822ffb7454859ab2ba5622c1618dcdfdec2963403ad852c68067aa34f919ff000a01e9fda0f93c1fc7ad8d5bd8d92a733d2c1bb778543ed8daa6aa343792931aa1b3392815882198d3861f8f6a52d246a1231fe0e9c58abf1353aa36eedfe6d1f373e408aa53d9d95d9580a9055705d7d7d9d878c0d41e0fe2340ed97ae248b8f34ae4fb59f48b164d2bd38100185eef9f55ebbf7baf714b415ad59b93399dcc5751a2e432f93ada9afc8c95720f1cbf693d6bce61919a4b6b522c39e7dba8a0b2851dbd592a94e898d7646b67156b541aa453898ac8b51a98c52fa6412863725b55d9bf26c473edc68b40c291d3457555aa69f6f58760d75453c9384c7257c922bc71172cef6927d31c4892112945ba82c45b5a806f7f778caad49040e9ca8a03a4f562ff001326fe25f277a5f1f5918a3a9fefcd2c94f413491c88ad8fa496aa434d3a10b2b19d7d600d4001cf1cbd02a3333026bf2ebceb5556e1d6eb5b4e9c478fa46520064477b0b1f5804db8b1058fd3fc7db0fc69e9c3aaf434615bfcd2a82be3d254f3fd49b9e410430ff5bda6900ad7cbaf1e38e835ef9ea1a6ecfc10cce2922a6ded828246a1a85015f294b10f27f0faa91459919bfcd923d0c6d7b13ec1dcc9b12ee30b5c42b4bc418f98f43d0b395f985b69b9104ed5b073ddfd13ea3fcbd56d46f3869a0ada7682a6132535650caa55e2aca77314b1c88c2d13c6ea47f8fd3dc4a1e489dc114a1a11f3ea684f0e640cac0834208f4e9259fc1c2cb1d4d18691d4b9923672fa508d4741e2c41e40ff000b7ba48bac6a5353d3d1b9074b607f87a07333494f570bc2f1e9a8559248eea48906a2049190de90349e3f1f9f6597098a1e9747a810788e82aa8c3c6a6482aa2d71c878908b989c1bac8a49e181f68c018561d3e4ea15539e9375d8201648eb22468dfd10562a831b3824a2b35c98a4d238fc1f7e652734c75e0e47c27f2e9179cd85f731f922559d828d400f5d88e08e0dec47e39bfb6e85470cf4a12e17830a1e83c9fad61a9b5e02cca54b3850ae84dc0049166ff5adedb2c5734e27a79a4c5453a80bd43054ea558d65b1e011e29540b86526c56edfebdf8f6dea67a7767ad09140c8c75261e9ca7914a4f4d35d6e0b48a197573a6ec2ea6d7e3dd163726a475ef15470a53a94bd194f278ef0c910d4c14f80166b2d8aa39214823e9edcf0da99534eb42e141ad01eb04bd0746c8ef253ca6304108b4ccbf904f3a6c2c3eb736f770af4cd69d6cdea0c002bd22ebfa968e81c8a7c6bc8c1ca9d519bd80fd28a38e2fed861a78827ab78e08209a0e935375ef8565796992952e18c6c35d4003f22dfb6971f83f5bfd3dd806f53d6b582680d4f4cf2e128e23a1636d0b620b825982820b3b9161a49fcd85bdd8b1029d38885b2474246c9d8c95722576422094a814d3c254f9263fd963a87f996fc01f53fe1eeb4c963d566934f62f1e8daed2c2786cda0e9280451aae8b580f49245cb7f416f7b89a8d5233d247c0a1e27a1cf138e8d5a9a49578450401757d2a45d7d249207f53f4f666807c54e90b9621b3f674a769575bc7118ca82acea834a4486e7c6cca492dfd6c3f51f6f906b4e27aa705a927ab1ff008a7d552e2b1b376167e8920c965a2f162a19a22af478bb6af258fe89eb48049fa84b0f72bf286cbf4d0fd6ceb4b871da29f0afafda7a88b9d37cfa9986dd6d2936f19eef99e8d457cacf2c85895560748636212d6017e87d43ebee4189723a8f18d4d3a49541d2b6b001c820122fa402aa38f49b9fcfb5bd68ff002e8a67cb2af8e87a2bb7e79d0344bb0371871f40ca71b50a54b006ca7e84d8903fdb7b7a01fab181c6bd33293a401ebd696b949680501f3cd2c3253d347350c3eb9e28fc90ac692c09e335753043210bacd9956c54727db0eaa1801c4feceae4549033d365322c425093fdcc74eb4b5131630da48c4de268cd40f17824a87b064176214df9f7aa020d78757a16201c53a325d11df7bdfa17b071bbfbacb3f53b67736daad82a28aa2965648e28e600d763abe99fd1518e9fc451927beb8dac7ebc259a0f1051400debd3a1c9ec22a9d6ee7f01ff0098c75c7cc4dad4386c94f45b4fb9b1d468b9cda73cab053679e9e21f7397daa6671254d3920b4b4dccb05ffb49c82f6528687a4f34263ee194ff00571eac9fdd7a63af7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7bafffd6dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd217b13b3baf7a976d56ef0ecbde5b7b646daa08e492a72fb8f274d8da6fdb5d6d153f9dd65aba92bfa62895e46fc29f7eeb6013c075ad67cd8fe7ed2d2d5576c2f871818aa20786ae9eafb83755033c82588b44cfb436c54948dedf559eb79fc88bda98add9f2463a73c32b42dc3ad73bb73e42762f756e17de5dbfbe37376064eba362f94cde627ab212753e4a68a9d19a8e8e3a73ea10c68ba05940b8f6630c08a5a8a7503d298d97f082283a0703439b10543c350943690cd9147bb1112b430bc9e7081142e9d4a1b5151720dee1d6d34f9d787542baa95e1d669f252c10b63e969a5a5a36a5821a77a8d11c1053a2bb9ad5a28a4559a59c720b35fe96e4fb658161c335ff57e5d5486ee6ae7a02f72e76596aa58a210d4aab967a85989f2d3c11aab451a86d31eb651af458ab0bdcf27dbd1835a05c79f4dbb151a456bd0795122ac152b515169a79bca69e66f1c91a2ea229da400cb26a68fe96bb29048b0e1e924040d0c08ebd535e183d3ff005552d654e461a8a868e9529269a512491ac5284649646824002f921d318f51ff0061cdfdd0480a303e4303a7758d34af566df0ab07553fc9be91aacc53d44d50b9ea8a9a566ac8cc3453b63eb18c2c919319f3d2912806f7bf0c6c7dbd6e154b8197d3d799aaa0fad3add0b6c532ae2289ec428856e0fea550a1458b7d5873f4f6d370cf1eaa3a11716ef06a3760235b93fd86d40315627907fadfe87da765d58ae7af1f4ae7a58d0541770ea7d46e2c58045523d4381cc763ed865cd0f5acf0e8a0fc98e921551557646d183f744427dd787a786eb531c7c4797a608016aa8947eea8e5e3e7ea398cf9c7975887dcecd3232ea071fe90fb3a92f937993c331ed378c74f08d89e1fd13f23e5d11782b96aa27713471cd1fed888d8bb3afe931016f32328bfd0903ebee398d8f91c8ea5264cd298e911b830eb918a5fb645fb9426468805864594db53c2cbf551f52bf437f76913c45247c54e9c8dbc36009a7405d5234333d3d6ab24f1b32046468ddceb235206f4b80ff4b707900fb2b78e878e7a5cd504329c750fc722931c8ab34126a4910add5d4fa880ad6d245bf3623dd92a0d09ede9a346a11823ae676f4b5044b879ae3fb54739bd8017611c9c722fc0363efc600c6b1b0fb3ad095403e20cfd9d42389589c254d3344c1ece5d48e5be809600e95fc5ef6f6d3c4170471e9d560d907a704c2c5a54ac312eb3c9e035fe97623eb61fd7db021d4dc3adb3d31e7d3bd36134c8aad12f8c80f60fa0d89d26c4f0e3fa8bfb522203cb1d365811427a5852d0a53fea4a9362af1a8d06216f4bded62405e2df91ed6c4b4000e1d2372581a0eb0ee2ae496065d60247185d03f68b99072a143cadf4e2fc0ff0f6f4c4b295ae29d3714255ab93d00b9ac7c65a57552da01d3a3510c49e5431505b93f5bfb279168cd5af460a6b4e807dcd343afeda18cd554b3145a7a76d6eb2936f5328605893fec3db19cd3f6746312000331a0e98f1bb53edaa16a739028998a34540c5996327943542e433b7e179ff001f6d952b566e9df13c41a636c7af4306d7c6cd93ae60e3c71a2aa7a14dd6d6b80bf40bc0e6c2df41eea0eb39e1d30ca22a8ad4fcfa31189c50a6863d005e20843124b330b02140e7936e2ff5f6a110d6b4e9317d44d463a11b6fd055e6e9bf6e11466456bc2ecab50d123e976673e955722dfe3cdae7d99db44655ed14af49e7d303d2b51ebe5d1aff008fbd1d4dbc7350cf534bfefdbc0d67dde4f2122eb7cce481062a0819ae3ede9efebb716163cfb1af2dec0b773acb22d6dd0d49fe23e83e5d01f9b3984edf6fe044ff00e372ae07f0af99fb7ab474a282829e1a7a78561820548a3890150a8802a68522cb62bfeb5bdcbb1461405e000c0ea1691da46666352727a45e4c59e42480c1980d3c8b31b7173c13ed745c31d355c749990037762182ad94137d3a57e841fa6a26ff00e07dbdd6bfc1d104f9f7964c7fc60ef7a87d6c1761e4a0088f1820d4bc14c787b4643790f07f57d3da9b61fa80d7803fe0e9b90542e7cc75a74e592864856b16280014544cd34f5429c9a761a2073045268824560782ab6b2dc1b70959fbc29e00f5a96a1f50355a79749aaba76a48e29a9d12b689258e3c8cd4555f713190c841a911caeced342ce920554d25c707eb770c950469a2f97fb3d388c7cfcfa571c9d553aa55c75508a78a04a979246b4f511d4a7da95c8cb2030c951e450e2cd73a8580b5fdb446a5257cbaf6a2c4fa743bf5b76b6e4d8b9edbbba763e7e6db7baf6f65e0c9617294f59353d7d3b53ac720aba168d8030c4d0e97d370f7208b5d7da5681980d6b83d3c9ad47f40ff003eb69ff8b3fcf5bad3726036e6dbf913b733181dd70c1050e4b7c6d7869f2b81cabc2a20fe3359874921aec69a864d73087cc8a4965016c3da5fa693cba61e2ee3a0e3d3ab9deabf931d09dd94d15475876aecddd52c88ae71b4997a783350ea1ca4f86ac6a7c9c6e8783fb5607f3eda68dd3e2523a6991970cb4e874f74eabd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ffd7dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd43c86471f89a2a9c9656ba8f1b8ea389a7abafafa9868e8e96041779aa2a6a1e38618907d599801efdd7baa31f9b3fced7a9ba5d735b23e3b3613b5b7fe3fc94d92dd7515327fa3fdb1359e369239a9bf777355d3ca2de385920d42dad85fdbf1dbc92640edae7a7922ad4b9a01d6a9ff00217e54f73fca2dd12ef6ed6ec8cbefdaa58bcd478eab926a0db5842aed29a2c261a8dd31f8d489bf4ba43728a0b122fecd21b548f32263a528b194215734e3d16aabcb466925f157d2422590548819a68a782731869227468da59cf88d81424116361ede7062a32d29fcfa6b00d548cf4c58a515d4b575514b4d5f3415b09a7f53470cd150da5857ed8a26b94420f90f363c904dbdeb57680a73c7ad80d42d5c7536aab2b7ec229646538c582a167a7a2a78b4cc8c640069d455d48bb313fa87e9b102f552a5487143ebd7ab4d47cba47d754b56a531a9159510d70812584bbac54d141a969c48974758d65254f207a87245cfb7e358fbbbeaf4eb5a8100b1e83fc8525aaaa2085e9a46065824828104a69a28dcabc8b23c6238c143ad8020b13c0e3d2d2773105a82a31d529c33c7a4265e3aaab682961a584a182365779955648e156430cf5e58c49232c76461a64fc1007ba0a558569d6aba8d09e07a11f60d04319ad875d454c91525440b4d2558291ca2ccc8cf107d48de4f1ab373cdbf03ddf4351682a0f4e5146a22a71d59c7c015860f935d374ed481a96a6bebd5aa61aa9e5a1156b88a9821a5667611bd4d1c92104b296e3863c8f6be2ad5ab83a4feceb67fb35141d6e5fb7a278b134cac0151122dc589018002e6df400fb4721f2eabd2a69aa5569a4d2e03a39d649015feaa0f20ff0053f5fafb6b87975ba57038f4f187acd53322fe95550c41e4b1b2fe0dff0051e07f4f6dc94a0f5eaa7a1162861a9a768e78d5e39134307b58ea1a444233aae181fa7b4b22aba90c01af5b4628eac0d08eab3be45f4036cac84bbe368d2c8db7e7aa7a9ca62a2404e16ae66b35452dee23a1998f3a7fcdb1ff00527dc43ccfcb7f4123ee168bfe2a4f70fe13fe63fcba99794f9a85fc4bb75eb01740515bf8c7a7fa61d154922fe2221922a8fb5a9556114cd180257172629507a9244fcff51c8f61304483503423a1a3318ebad6bd22b3585a3ce06a5ca40b47928d5cc3321b34600b9fa69578a622ff00e079f6c48035411ddd391bba5196a53a086b932780aa58326ad2d338631d43444078c5b437dc69d0de9fc1e47faded03168c80c3a5c123957546687a79a18dc18e780a4d4e48d13c0543107fb3200c0315bdbe97f6e2935a83d30ca6b9e3d2960921a81e2a88d67f511671eb17b12087fc103fd8fb7d46b1dc053aa95d3953d629b1103beaa40d4f203ead2c65a693fd49f19f542c01b1b1b7e7dd4c480d5411fe0e9c12301439ff000f502a2932b03a68884c89fa8425ed73fa483e965fc71f5bfba51c1c282b5eac0c6789cf4cf2ee8ada21e29696ac106d72921b106c7431001047faaf77f18a9358e9d7be9c3643748fc866ebeb18fdad25636a2427910aa2b2920ea6d488ab7ff61eda79d9b2a327a5091228a3b0e91d5d83cc640e9c9e5971d4a4de58a2625f4b120c6acada178fcfd47b4aca5c779a7f87ad87892a512adf674ddfc330f858cc78ca4d3369bfded4952f269e0946605fc8f7e348b9bfba32aa0a27edebc4c92fc6d41e9d256aa96496abd4ea270fe491986a9615716450a35ac6493f42759bf3ed2c849a03c7a551698c1a2e29e5d0b1b531b3c94f153d2811f964f54a55c4d3b12094a6006a77bfd58f0bede8617900d031feae1d31310b52e09c70ff003f46536aedaa280b5564e795c2c6ab040973adc1bc848003222e9d37fea6f7f6716f6aa80991b03a2f9a672b48b1d0fbd7bb0ab3b373f4182c4c72d1d2c7ce4aae9a1644a3c7a30d459d8dcb04b88d7ea58dcfe7d88b6adae5dceea382105631f11f41fe7e83dbcef116cb68f3cf4698fc23d4ff00b1d5b2ecad9588d99b7f1f80c3522d3d0d140b0aadc79667206ba89580d524b335c924fd4fb996c6c61b28238215a228fdbf33d4177f7f71b85d49757326a918fec1e83a7eabbd9ae3f458296b6a50a2da4d81078fafe6e3fafb5f403ece90d7a0df367434b25edac83eae0293c0b11c5c11c73edf8b35c75afcba48cb32210355c70a588016f6b9feb72bedeebc7aab8fe69d948f1ff12bb422f3bc2b994dbf892d0a1350e2b73f41e88c78e452652961752bfd45bda9801fd5a63b0fe5d34f52c8838d7ad4d2b1a1a7a1f253438d8234568f33054cb0bac9531d4a2d2d3d3474f14c8d23c60b696215a520d945884e529515a93c3a70852baaa3d7a639aa2186b84b4b489575229bc644b4f570d41aa55961b528826092554519f442fa86900dec4a9db4748db5b50757c2b3d0e69fcfac13bd5498d9e3a8987929658d2adc1aa78a48dc8685453494acb01a68d8853a4091c35dbe976eaba400b434e3d52a15694eee941b7ea228a9222b2520aea29bcb1ca2131f9a172c2730ac4193c9031b9550341b917e3de8865080d74ffab8f5b8deb4ab63a11132b2cd5114943e786a23a569aa4d0bc7f6a249e616834316a812cca9a8a8b16bdfe9c9d1a1c75ed5a412723a57ed5ecdccedbcac15d4f91ada2a88aa3cb4b95c3e465c765a29a105202f3d1c91481a9dd7906c49b716e7dd00527579f564956b5233d5affc71fe705f26baaa7c5e0a5ec3a3ec2dbd0ac113617b25856d62c4bea31c194f27f1159e58b95b4b6008b8f69e5b7a9aa8eaac2376c0a127cbabd1e96fe74bf1fb79250e3bb5b1794eb8cc4e218e6ca502c9b836ab4f2bf8f52d4c6916429e10c7fe39cc41bf3c7b61ada404e29d55e02b90c08fe7d5abec1edbeb2ed2c7c594ebddf5b6377524d1aca0e172f4757511ab006d3d1a4bf774ec2fc8911483ed82ac32463a64ab29a30a1e844f7aeb5d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ffd0dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf744c7e5cfcebe8af875b6e4afec4cea64b7855d24951b7baf3093433ee5cc3053e392588b14c563d9b833cc05c7e85722def6aa5c80a33d5d2367ad063ad427e677f320f91ff2c7255d8eca6e3876c756b5732d0f5f6ddab96831df6aaa67ff0072eaac2b73332531179a62cbaafa1471ecd20b64507c6435ff00579f4a95047465f3ff002f556fba37753e3ec2a129a9903e86ad7a36fb49a0ff0094812434a163964a7421d01b1650393ed7a22682a28149ea8cc0121b27f9f417e5335492a53225279e96b5824352266815d86ab54d3d5c1e3996029a57c4ca3583f5fafbbd010406c7f97d3ecf9f4d8c824923a4cd0a6633721a06150b1d348f5152f510c8d59142cc22281d50bac91531564f514202937e7ded8d14540fb3af14d4c1801d0a74f34b1d3c751438d6929fed929697cf43207aa4867761231899d666255f5685bff402f6f6902852d5029c71d3b46d34f2f4e9ba4aba98a48b1d025327dd959ab29a5f3c1008cbf8ea567809f2d28a5f27d149d3f53607dba6868953f9f5545393518f2e98b2509a6adaba983ee4e3a902d0288478de12d4f2982485aa42ac91bb456b395d40d873fa5dd4281548d5d5b4d6801c741664eb66a96c453d6c72b493bbcd4df614421894aab463ee274222b24c3e859427fb016a38232b8238fe7c7a6248c92b51f9f58f2e904123524b1b4505a6a785b4413b7de300abe10ea69d98eafc95f49beae2feda65f363e5fcbada29c8739fcba55f5ee3e2a7c7e46a22a4ac93cb18591e9aa129a1fb88aa04c68e5bdbccd2ac2b2072742816febedd32b685082ad5a7571a6be7d59cfc07a8828fe44f4f552bbc54d57bcd296b04cb4f227df5649353d2c749530de0969a76d5fb6c434763a49bfb556c1809158d495343fcfadbb028582d40a75b986224538b40a9a5e2f4daf737b052c2c4fa073ed13d6bd7875c5b5a0778dacc19cbab9014a93c73a882c3dd3af7c8752f6c6435543ea205e4d03e8180b917361c588faff4f7e9171c7ad71e866a19b5229b1f503a40079240b1d409fa0f690fcf875b0722bd48cae328b2b41514390861a9a5ab8a5a7920a85578e78e45d0e922f378def6f69e78639e378dd41561423d474f412bc122cb192194d453aaa4ef3e94c975be5aa329880efb62b6a35d1cad793f86c8413fc3ab5d549118ff0074cbf52be93c8f70ef306c326d5399a115b463823f0fc8ff0090f53472e731c3bb44b05c505e28c8fe2f98ff002f402cf14194a78d2ba2293c4ba69ea0968a68d98700b7e8962907e4f07fc0fb0e15571423bba14a9f0cf6b54741aee0c6d4d1c12e3f2b14991c7302d1cc88526a7561c056fd332004dbe86c3da59032d44aa0a7f83a5d1346ddf19a49e9ebd22a8b153e3581c654475942d771082cb3c4a00bc6d11b8990706e06a00f17f6ca44cac5d0d63ff274a249639051d4ac9fcba5b525452cd1a3bc2aaf70a6645674bad8842400c3fd88047b5038d3cfa40eae09a1c7539a917fcec2eabea256e49bdcded73c907f171f4f7e2a71d595b8e7a8b51ac2ddbd121257c8a4a10a4f04af26f71efc171839eb7abb8547498aaa3a899ac657752bfd973abe9f5373c01fedcfba378841a63a7d5d00c74d1518fa84458e38de467e4790a937bd8599017e7ea79e7db243d28063aad6b535f3e98eaf135643cd2c50281ab55d9416b583d80d4e45be848fafb61e27182b4eaeb246300f416ee594d3ea2291a672c4471425da467b008b24a45d1403c2af27da6734fb7a5d0a6aa00d8eb9ed9da55750c93e4e734a2668e5fb645569a32c7d085002534a0e7ea791723dd23b7666ab9a74e3cc8a4aa648f3e8d7ed8c147434eab41491822310a55d62132952c1de641a574ad948247e3f00fb39897c340117874532cde24875bfe43a17f6d6d8cae77234784c0d336472156fa04a69dd21844ae11a7981f525327d4587e07d7d98d95a5c5fcf1db5b47aa463f90f99f9745f797b6d616d25cdcc816351ebc7ecf9f56bdd37d4f43d77b769a916d53949ed3e5320e815eaaa996ee16c2e91477d28a3f1ee6cd97698b6ab55854564e2c7d4ff00987503efbbccdbc5eb5c3d442308be83fd9e86ed3616f4d9540b050a402b7b853726ff00ebf1ece70388e888e6b5e9359136d5637f57d41e2c059b50b7a49278ff005fdec6727aad46687a0c771333685d4deb7e74f0022923d441b1371ed4a0a2f5ba7979741f56348f3940345a40aa7ebab8e6cbab4d8d8dfdb8306be5d5bd4f5527fce2f2ed43f1a60c7c50cd38cdefbdb38e921a69d60902532d6641a54768a506cd4ca2c149b13f41cfb5f0a830ce4ff0f4989d5245eb5f3eb56f6a6fbda79a92a31f24d54c053c6a91b4714da6997c84b40cda2b28a362c598a190a8b01f428bb445a589afa756d2a14868cf1eb0347532c742f0082963f132c348f594f247535863d134a2a963668ab8ac4ac4028cc3e8d6002d086ae7b8d38533d58a92f8393feafe5d782d5d3435b378ea5d8492c753051bfdde529609c2a2c39115360110c8c7c8da9c837627d40f9465030a0eb7144413535f5e9a92a24a4cb3d10ab730d534624a58c6b8a19ab29c2bc32f8d4cba073fd920917bd88bd59685aad9ea8e423693d2be9e9961c7c94d4893c5342698ba3ca4ad54a9762ea91b89e4fb50c1d194b5d24b7e7d5a47209e9ca02bd638dc53353419134705a4226432b324a2650ab1254abb995bc0575bfa0816523d26ced4bf6d07f83ab28a29040a8ea5a3c6f23c532b206696ae8a9d228279294c2424d4cd3d26b85640a2eac4ea64fe9efda14955238573d554a76b79f4a2c26e7dc1475423827aba75a7f04d1192a63969a6d41949a68daf1544491bf36002b016bf376df482349a8a74e1703cba1a7aff00e496f0ebccc1cbedfcd65f1d96a3950fde60f215580cad14b4f6904f5669a448d405059d15d6e2c40b7b67c22df0ae0f56128a77ad41f5eae5fe3c7f3c0f905b36a2930fbe24c3f6f60a182398c59ea76c7ee214255353c1b8e8997ee648d07a4ce928bfd6fed86b460a18ad2bd54431356868dfcbabd0e80fe6c1f15bbb451e3731b86a3aa374d40890e277ca8a5c54d349a55568372c683193a33b58797c07fc3da568996a788e99685c134c81d59550642832b474f91c5d75264a82ae359a96ba82a61aca4a98985d6582a69de48668d87d0ab107db5d35d4cf7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7fffd1dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf7489dfbd93b07abb0355b9fb13786ded9981a38a4966c96e0c9d2e3a02b1805d6059e4596aa500fe8895dcfe07bd804f01d6c027875423f30ff9eb6ced934f92da5f1ab6ecbb8f3320968d7b1774406930944644654afc360bd5595deae6196ac450bdafa08f6a23b5790e9ad3a7a386b97345af5ab5f6df746f2ed5dd9b8b7aef8dcd9fcfee2c9e53eeabb2f5aad90ac926a89d66aa579e7934c290426da14a46b100aa16d6f6670da88a874823edcd7a50eef52168129c3a0c6a32959490c93534d0e4228d1a5aaa65f1c5590c350557c73c6595eadfc7660aa34a0b7d4fd1faaead26a4f0ad7a68c8cd4502bd04f91dcb4f4b1e4667cc4f5b31658eaa1ad8e2a8ac868523734947e170b23f9611a1cc4a34440053737f6e1547560694af5561a72dd252a3173e59fccea91c9e2a79e355f3834aef08799591d1e47a7a48dd40898d8121497bdbdb6ca55405240ff571e98572c29ebe5ebd2d36fd0d4353a24f55032d5a43348b3d6494931a3a3790da58958b4692c84d9efad5d42d88b7bf1240abab70a7fabe7d2852aa28d81d29d6a1916aa0a0a6a64a169ad246d52f251d34165568de57d1224f3d42ff009e5000d3cfa81f75f0c84a0e15fb0f4f703860450f4cea2b4c35727d894ae13491696aa4f252d4ccccafe44557611d4c402865254802f6fa8f50d578e9f9f4c29550c4749158a5a88618929aa630eaf2491d4bcb5b8f7904ad5295119728562530299145f432d947e1540654242a8af1eb6c50a8a1eff3e92f1534595aa91b299013c51ce0c89040cd1493f11c3222a242cb15c90e2c3530b9e7eb562e0035a1a57aa81a813ab3d3140882a2aa4a611575552bf8619e9e048e9e58fce236a69c9aa6859c46ec63213513e924707dd08a1532027f9f5a8c001b50a827a13a9274a5a35a896b69aaa09aaa24ab5c6491de28a6532058a2991879117ea0e909eafa9e0349a9989ca9f2f4eae8686a063a3b5f17f3547b7fb77abf35570c9472506f2daf554b35253352c52d12e4637d75d1231892a6a1e65d7a90e95e755f9f6636fabc45abe08a53e7d6ca9f0a520e7cfadd9f132814d14b11568dd51bf22c5d356bff0053f436ff006ded238cb0af545381d4bc9089622d1128a058023fa292c086e2d73fedc7b66847975639a0e935839192a13d7e867600b1e4adc9e6ff004219adfeb7bb509534e1d78faf43d612a59a3039b90025cead1616620fd4836e3f36f691fe2e3d6bd7a5940a8cbf4e40000b2eab00405247a6e59b8b7d3db66bd5b867a62dc7b6f13b8f195789c9d1c757435b0c94f3d34cba91e3704372470f7e55bea0d883ed15d5ac7770bc32a551850f4aad6e65b6952685cac8a6a08c7553ddd7d339deaac93cf4b1cb97d935f3ca282b641fbb8ebbdc63aad870acbfeeb73e993fd7f70e6fbb1dc6d1316035d9b7c27cc7c8ff009fa9a797f9820dea10aec12fd4647aff00487f97a01056050e8faa6855f4351cf18768d4f02ea5b5245f8b0e2fec3c5c0346ad3a12f86c72280f49daddb54d5120aac0ff0090d5acbada91e467a592f72d1a124181c9b9522d63c1f7a0a9f127c5fcba756534d13e548e3d67856585f4e4a9401282aede22b2eb1617a808be3d42d70ff46f6e2ad70471e9a7a509427a50c38382a635fb49e40ad72819bcc8c073c5c1d0b1907f4b5ededd3003f09e9a59b34619eb054edd916e8b289cfd412d6e34fea06cec579f6c98d8127a74495031e7d27aa714f1d83c0e6d6219a31222db85274957b123fa7ba9527883d5c13fc5d35cd495a40f0d352c6436a5767781140fabc89a2edfeb5febeea508c802bd68380c4963a7d29d2572d1d459a37c8d2c71b0bc8f4e92cea2e00b222aa9671f9173ed3c88edf13803e5d288c827e13fe0e9190ed9acaf767c752c9e3d76397c9218c5fe8a68e1751f426e09e07d7da716ecf520d07a9ff20e959942005e83e43a1276aedec6634a2977c8ce5c99a69ca992a248bea495e62a7473c81fa87b792148ab9a9f5e92cf3c928c0d23fc03a1ab05495392c8d2512a0aaa998b4747468a4212e74c5248915c851fd943c9fe9ed55bc524f2a428a5a46381ebd249648ade196791a88a2a49ead0ba17a713656386532112cb9ec8af9679e4525a18daccb4b1a3dd9638c1b1fa5cff004f735f2dec51ed36bae4506ee4c93e9f2ea10e67dfdf77b9f0a26a5a21ed1ea7d4d3a3411214517be9b93f8407f049fed022c6e3f36f62615381d04fac73916b1040218dafcfd401a581fa3022dfe079f76a5334c9eb7c787492c9311abd5c5c7216d602e2e2df43cfd3fa7bb0ebde5c3a0e730892a96bdbc48ccbf8040e7ebf903fa5f9bfb50a348a75e18e8309e629396425c86be9e4fe2cb6b7f89fe9edcfcfad8ad0754aff00ce9ab5a1e95ebf81ea25864adec39658bc4d20321a2c1d5b88ed1bc45c3b4b6fd5c1f6b5357d3c801e34e98552651abe2cf5aedbd433d3d1c667ada2924781569269478997c7154470d28669648ab5dd0a90ecc5f51e49007b404d052a0afaf9d4ff009ba7c922a09ed23a46fdd3cf5d92f3c94515463fc10253d5d3c026a6a212c3592ce6686254670e7f5fabc62df53622ea582e85cb754fc1a8d0b74e74cf5104493c590824d53cab512cb142f3454a2ac55192a4ad22c5233836f5166362ba47e2ccce415d3ddd6a2772acd5e9aea69c8cbc13d3087251b56c8b515f0782962d2d0078656914c334c95313fa05b5ab8fa7f56749ad5ab4ebda03b0d593d2aa0aca2a58a8a97c7558aabc8cd34d1b6a1347257c6c122abfbd178dade5f1bea65561eafa9f5dd91b8a0ae7a706a00284e9b208af54f8dcb4f4a91c95d5731a7869ec26d6a0e896bb5a472524d20251ae84f36365217dc18155141d6b50f116a3a871d39c555cca60fb590c8294d22d3b4fe082120c52792274d53cb4c8da58962cf6b0b03efd56d65838d3f3eae415aad707fd59e9495d1e5de1a78e9e6928694ca92d01bcb552431189278e1ab91238a38a05b8d4cac358363fa7ddc08bb41fdbd55a842ffabfe2fa70a2a1959e696b1a9aa258418ab619f58468bc4d2381534eec5e48b48d05ae6de8ff005bccaa14145afa7f9faa578e71d4890d4c35a6ba9f29471d1b40f0d3411975f2530809112cd1c7198a2998722d7575fa037f75a36955a67cebfeac75a61a83696381d2b36ef65ee0c6c543367fedf290245198e07799ebe9e3793c7454cdc1fd5129249078007b4d25be0d168074ec72b4635371eac0be3dff00302f903d0324757d69d9594871d05433cfb533f57fc4b6bd42c5205fe1b5988adf343467c5caba08c81fdaf694c1534a64ff00ab1d78fea9a9033d5fbfc73fe783d73b9ff85e07e456d39bafb29571400ef1db426c9ed66798e957aec6cedfc56811bea5e335096fa0f6c981ea683aa343fc06b8eaeb7aff00b3faf3b57090ee3eb8de7b7779e1a744715b80c9d357ac4241755aa862735147291fd89511ff00c3db0caca68c287a648230450f4bbf7aeb5d7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ffd2dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd43afc85062a926afc9d751e3a86991a5a8adafa9868e929e34059e49aa2a1e38624550492c40007bf509e1d7baaebef2fe6bbf0a7a2ea2af1391ed1837eee8a5575feecf5a5249bb6b0d4ab154a59abe8cae1e9a49181b16a8b0b73ede58246cd283ab88dcd3141d53df7e7f3f1dfb9b8ebb0fd1dd7d8bebaa795a48e973bbbea62dc1b964a578ed1cc9494f1ff06c5d4cdaae88c277523eb71ed42da815d46b8e9f58507f68c7f2ea8f7bafe51768f7467573dd99d919edf39e79678e3933d595552b8d8a4732a52d26353450d1c94c470151174807fc3da9580280ba698e3d384a01441f6faf45dbf885564de613491cd2d488e396392a5563112c85a19df482f2ba98bfdd64966d2df4b1f6ac448aa142e6bf975a5eed4071e93d3e6ea6827596ba741494f4f2239685ea9726245d44579148af4aef1131d9858691a757d7dec469a7273e95ff00074d90c09d47f2e90d9ecc78e48eb67aba3305453524b4f152a789e9e9e605d575c29ae58e942f1a5c32dacc2f6f7a1a63228b83c33d3868b42bc69d05e31f3e6b2f51592d4c73bbe998cf5d209045244f1abe454aab78669081c1ba817e393eddd0ba480335e982a09d4727a1630b42d4c882824a5ad59dde9f23908197c6d23b6ba78964568478435ee3952e2e471edb7700e920f970e1feaff0755603c8669fb3a9d8cc7438ea5ac942c42aaa032d3f95e2114cb34d319f4b12fe69982ff9c3e9576048e6c2cec5822e6a7cbabab07a281dc4751e6c7d4533892a6181a2f0ad017a4991a9654a4323d453cd0208bcd55095691d896d40e953ef4a19b5a92467cffc1d3ca0a2aebe07a4ee432f531523a4b357545356d6b49056081e9da98d2cb245f6a4ba8954181cb117be9f496d5c8ac5542569c33d30405240c8e99a4c64d5553567ee1122983d2d10a698d4d353a7895e5483ca56a955e3055fca03a35ee40562bb321d2ddb91ebd5342d1fb71d24a1a2fe18f0fdc4af46b4c2b5e286f3b3982450624abff5658dfd2beb4e01e2f6f312e1581cd29d7a35526a453ae969e17a35c8240b551c131a48228894a9a8a4666915a3410ba1115895690174e4822c42eca12509614a74fb00546315e847da2d1c752659b1f14cb0d4319b5948840cd0a796b60692992364a6463e455372c4f03dda5d181aa87ab9a0aa93dbfcba32fd6797c536e7c74ef3428d4790c2caf3f927d724b8fa8a74021954cc5aad1e41eb62a1d5b5143607dde22c24888f84b0cfdbd6c052b22f969eb76cebac88acda7839c9625f118c76f29d44ac945038d4c01572c1ac081cf1eed380246fb7a4e950003c7a5a4acad492c6d670cac03393e9e0907fa8b9e3da5620f03d5f87491c1c83f89b424ded7f1ad8b0050faecc7f23f3ef608d2c09eb478fcba1f7048c3f0a4a7f424f24deec3e86e381ed24b83f975ef3e97f466d0a81f83ab9217fa5c96b36a005f8fa0f6dfcfad83e44752995587afe84dcfd09d3fd936ff1fe9fd3dd08a8c75b151d24f74ed6c56e9c3d760f314716431b9081e0aa86641a5d1c69b8e2eb2211a91858ab0e2ded0de5a437904904e819185083feae3d2cb4bb9ed278ee6090acca6a0f54f3df1d3195ea7ce998a4f5bb6eb9e4fe09984d45c28fa62f2320e16b225fd24f1228b8e6fee18dff00649f67b93db5b57f85bfc87e63a9c797b7f8778b7c10b76a06b5ff0028f51fe0e815a0ad10cc55836b640433926390f257d60dd5c7d0dfeb6f642a68c0d71d089d0902bd2ff1b5d14c019a288c8ca14c7280d1b46029b47337abe9cff87b568c4354f48e48c8a80d8f974fd1438e4f33ad31852a0df4c657c626275160c3843f5b95e08ff6ded56a52a00e93e87d42a6a3a7d8f1741530218eb2060a6fe20164ab8795d6923591483fd9e6c47e47bf04520770ff002f5e2ecae7503fe4ea0d5ed79250a4c9431c2ee195855a8a9789b56967899d842491f9fa7bb880b015a693d6c4cb53426bf674ca36ce3c0759abe3a9650ce55a60b0a0fc8d4ba59e45008e0f24ff005f7a686214d47bbad78cc4f6ae3a4a64a9b0b4635ad2c3532233f84a531b42a14c619c023515bf1737bfb452b2ae428e95c6247c5481d06390a99eba531212b1c28e0879426941f4d61088c3151fe3fd6fed1bb9209d5d2dd0a82a789e9f76f52b544b4d4f044f24733a243f6d186f3cf2955115f9772c5800bf4f7551e2b2a2d4b12001f33d33332a2b33350019f975683f1d3a39b10f16eadc74919c918d1a9a00be482855817d46461a5eadc2fad87084691ee5de54e5afa255bebd41f544768fe11fe7ea1fe6de666bc26c6ce43f4c389fe23f2f9747a69e010ad82af36d570a2e45c0b69bb6904fd3e86dec78140c751fd73d4974b28b02a00d24e951e924904dff00529ff6e07bdf5eff00074c956d60c4dc372349fd25791c1bdc803faf3efdd7b1c7a4964a50e0a9b9d608b9faf1ca9616fec7d7f37f76008e02b4eb7c29d20720e04123b806e0a70782aac5880a39bf1fedafed40eb7d06442b54c8493e5179545c300a2e42f362c41fc1fa7bb8c69af5bae3aa1bfe77394ab8f09d0984a494159f29bd32f52be26945a3c7d251c72844b949834cc03696d3f5b71ed7d42dbbfcc81f6f49554f8ad835033d50c94a06c65254430c669e069e8fee32351e59991203a424d22a8aa9e499c468ec11350f4d883ed14815548a7774ed4a9a1183d35c75f1cd8d9e9aaea25a77ad969d1cd2c547e78528639165a79656a56ac48e498682e2c5d89d61bfb544c77851a88e1d570149c549eb861b29494d8d8a07664b4d2354cf22b4c9552872b490a98a368e39e26894f04acb7fa822fef7282a03ad431fe5d59542a9c9a53a8d535b4c98f4923291ad4ca165a6c8462a21a4a1a6a8649a535f68960f34ebe9046a934d948008f77428085a771a9afcfad6a0aa2b5ea2c7542b616822486a9a9aa4d3a40ea2330d2d469a7f340122114692cc14b2b0faf3f96bdc86a77351c7a757a914a9ea5d452e169a7c746d354500134b43526b1a7fb589d27128a7ae656f02fdcf3e0746d1e917b104a348848c904f1e3d5b46a3a8f975dd55356d1b5e9679a74f2402967ac8638aaa60a240b0c4b13c9104a372354971ad7806c7dd854d755750f97fa875ad67879e7a9f87a8cdd6e32a61c9b538ada1aa5a693c71f8d21f21668659479489fcbc18dff6e22069b724fbd232282c4509fdbd3aa18a014f97f9ba8469735433d4d1d3bb53484f92aaae9aa9b4aac9795648ca02d2b4930d4aab725cfd6d63ee8356b05aa57a6447463e7d3a8a2aac80a68666a69a39847154435139a7f1eb91c39ad052279dde72b6914add480d6fc380b104ad6a4f5e23f0ad69e7d6079a786a3cb59106145e482a31cd38952b23a797c714f4952029a782052a13f51d6196f6fa5830511af107cfd3e5d534819381c0f5c1737518d9dea286a249a9e78c192778d9aaf250817a9f1d248a50c08834df8636b58106ccca801057ab8ed7a1381d08f4599a614af5188ad9e81658e2966a2ae693ec6446652cf4824918d34ecc6e232d626c3fc034b527f53853ab8662c3e478f43a751fc95ed5ea5ce4398d8fbef716c1dc14f286a7abc66724c5d1c485caa0c8d1520615f13290595d5945f9e0826a63572c4e4756d41b55475779f1f3f9f1f696da5a4c2f79ecbc5f63e2a94c34b36e9c401b63724914002d4d7342a95189c8860c083a62662793ed1b5b139183d37e1ab54a9a75767d17fccf3e1ef7bc5494f89ecda2d97b86a0c713edcec00bb6aae3aa92c3ede3c854bb61ea0dc8d25671aafc0f6c3432293db5e9b313815d38e8fe51d6d1e429a1ada0aba6aea3a845969eae8e78aa69a78dc0659219e1778a5465370549047b6ba6fa93efdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebdefdd7baf7bf75eebfffd3dfe3dfbaf75ef7eebdd7bdfbaf75ef7eebdd7bdfbaf75c5dd2247924758e3452eeeec1111145d99d988555502e49e07bf75ee8867c85fe665f0c7e34c75f4dbffb970193dcb421c1d99b2241bbf73c93aa175a76a3c434d4f4923b0d3fbf2c614fd7dbab0bb50d28bea7ad85270075479dd3ff000a16ec5dc7f7d8ff008e5d218cdad8c324b0d3eefecbca475b99582c552b22dbd4be0c6c1210c1c2bcb51c71627daa5b54046b627ece9d488d4ebe3d53ef777cbcf901de99115ddbfdd1bf37c47901a86dba1cb54e1365a7959b4d3c185c535252451a016f529f48e6e0dfdbc2dc21e18e9c148cf6fc5d150c96521c65678f15e0a7a673302b054c855252024a4cc50cf1888482ee4fabfb27f1edd5452294cf4eb31602bd36c99a8e3864a38de65ab291ad3d54b2f8a076d6cf248b2488feb44ff357d4ae7ea6e750b60100f0f3e992c6b4ad4f4cad512d378cc3f74b58f24ef52957234f5693246cc92b964f0ad9188640007b8560016014958ea08209a75bd0c6ada71d37c5959e9f21109626a8598ea4af8d5c329a859621e564426486e19b5e9b46bf4bdfdd58b30a2a9069d535d0e1bcba64ad9ebb27565e8328b598da734f0c220d0ef25551c3a122927a48fce6568dcea0f1db5704b13c58c5af486000f3eb6b256ac0e3a47cf474b99cd49498e82069034f3e46931d287c64b2a8f2ccd51206a79a9a4865469254075582587247b6f4e90aa50e85f3fcfad992a3e1af957a54a528c55a9e38616a65a89a18e59e38653334c8aeca6b3403e0a7126a50d1b5ddbfaa822ca874f139f2eaa451450e7a526370b2649698448218563abfb924842d4c4852a608e1594494ea1df5228593fb42f6f747c3104d4d3ad690c501e23acd3d257d0934daf14d4b8f87c4261abc5e0589b20d2cf47368acbcb22c318b1e357d57ddb000d2956ad33d6eb46041048f974db5494f5d472e37c9537f0c0169648e68e7a69668d9ab5e29d6440648d86bd0dc8041d4579f7e62f4d457bfab12de66a3d3d3a4bf831cd574d4f3e56b2bea4d31448bc0cf0412484c50d3d346e045aaa1a25243d94b29b81c15b32b3516802ffb1d79bb969a73d63867448e7a79a82a7f89c152ad51495156296968a482368e328d1c62a69a59a3b392c34923ea2c08b1d3e1a93267f9d3aaab0a69fc5f3eb1bd1ac90cb5ed8ea5a4305552475b2c95265a99a4782422ad22aa5d0b2c6823ba82b1b35c5c5982347429cf70eaf22bd038500f49dad39118fac90d44335ea84f8e829e29290554b0ea82a1aa5032c2d4aa1547a583c4c4db8fd3e6750ca465569d5002457cfd3a57d0c5594380c07dc574da26a5aa5ad89e9a3a91455323c2d4e2ae58181b78ea0189c90c09b9d4beee28ced4514a79f1af5ad3c359ce7874396ce9d31f470d7ac4f554d266f1ad2e52923589208e168a97c8d14b22c14ea655565599641ae32435cf16854f882b83aba50acc1080bd86bd6eb1d31586afaf366b17f219b6c6066567558db53e2e96405a3b8003a1bf1c1bfb51703bda9ebd264248cf1af426574eb0c474800481d546a06df520396b8363feb7b46457017ab57e7d326de88cb91f2231b2b10d25fe9fea837e74ff005e7e83deb481f175b39e1d185c5694863d3ea0c001c1bb200096361704df8f6918924d78f5aa67a58515868b9b15e579bb37049fc91616fcfe7dd4f5baf1e9e23b30565e17eaa39209e47e483c29ff006feea475b06a287af3c6af6d2429e7502459491a86a22c4920ff00b7f7a2be7d6c1a1cf41f6f9d9384def80c8edeced2c555455f13a3abff009d0e2fe29e9d8dda19a1241461c823d97dfedf05fdb496b70958d87ecf98f9f46161b85c6dd731dcdbbd245fd847a1f91ea9dfb7ba5b70f56e6e6a7995ea7073ce46372abc2ba5b5a4359f48a3a80bc31e031171cfb85378d92eb679ca3ad6027b5bd7e47e7d4e7b0f30db6ef6ea5481720772fa7cc7a8e833c5574c923452da4109baabb1578edea5f1b81eb1f5b1fa5bd94a310687874772c63e2073d2da0ca45e12c3c766b97864f4973f8297163617e6c4fb52ae294f3e91b2306e1d3d435946caa43c71afa5157caeca4b5b52b2152d1f22dcdc7e47bb8504135a0eaba9c60f1eb3c953491a1295212ec54a842c653c5bd4480c86dfdad37fe9eeea0702d8eac09602abd26aaf261a41148b4b197bd97432e9e6ea1cadb9005ef7e3dd6622be5d6f41ad41f2e91f94aa92a032a3092140e9e42be0a65bf002a7ea985ff37e7da292a786474fa512848a7495a6c749233440791a7753a7c68ce496b2886116215efc06fafb618120819afa7fab8f4e99402493c3fd59eac77a47a1a2d9d85c7f606fec5e4286aaa7fca31540f461e4c3d13851157cf48e523a7c8d621baeb05a28c820026e250e50e5548d63dcefe3fd5e28adc07cc8f5f4ea29e6be6a69e4936cb09418861d8799f4afa0fe7d1f3d979ddaf9ca129b62b60a9fb6b43514af19a5ac8d871e3789ae243c5c042c08f724942b93d46f26bd5de33d2d7c92af2c1b9d5e81652ac0d8102d7e3fb560093ef7d528075c6e194943a958596c4dc69bdc107903fa5c0f7efcbad74cb90361a980b8e2f6b7d78be9b1b1e6fc5c7f8fbf1ebdc69d20b232ea2ff5b81e9d5ca8b37d2e4dbf3cff0087bba0a91d780e90d9290243643ebbb02393ea37e3f1702ffef3ed479756f5e8389199279ac54b16f5311c7acd9c69b9fc7fb0bfb7105585075a3f09a71eb5cefe75db8456f60f4fed482a46aa2d9b96c9542a2485964afcf6881656565d314d0d115254ea17e39e3daa903085180a8a9ff071e9a84d0bd4e283aa58a3a9a4d3350e4aa0e362a35a9a734b414f53552ac81ccf4b0c32c913453f8a450c18ab81c5eec01f6c956342003c33fec74e90b9ae475026a1444abaa0d913512553c4a3212d03415d04b08528fa60678666ad4d4a65d31dee2c4fd2a58e74118f2f3eaa5549ad7ec07f9f51d94d2503d0d251bd5b041153bcd394821a14a831cb149f6ba9a299aa3521b44ec6dcfd796c17d2faeb53e9c3ad3531e5f3ea255254cf45163d70fe382d1ad64b4d5754d2411a109f6d247517f2ca951012bab4e8541e937f6d0502a493523f9f54d448a15c7974e189a88523cae38c51d50c8453d4c33c5545ab6775d31529a88d9115aad6585dd174aac43e9a40b2d82b51589a357cfabea62a430cd7a9b593965c92c51f94d2414d4af8dc8159e29eae0a66ae9b535324b13d5353249773c165b1e41d0eaab47a4eaeca6698eae1881c2a3a72c0d5bcd47e4a8748229a38f94092c651d1169e3fa47e368f85fdb5b0e0af07d565657a900d7f653ab515b26a3a6b7924a19a9b2b24904142638a8ea12626a23783854aa0b4efe4964a7d4574aea21880071efc1d9aaa63cd3aa8720525f3ff000751d63acaaa1a692869ea2b112a8486ae69e40b1f9246951e180a45e4409a012e539b2922deeda888897ad7ab85d2a749c759e69e492abc8993a355a90c93ac71b251c35023428b24682596a69ea514348484d4e4123ea3db22460ba4c648fb734e9866235161c3875ceb6aabea664a8fde6ad8d61a1ab8a18a2a88da9658923a5a9a68a55d14f15523844537d0c2f604293aa8209a8f97dbd3982ba89049f2ff003f5e1325424d532bc946d4f50900a395b5c70158d622f13c40911ca058002cbc9e401ef6a1cb8566c75456072719ea6d455d3d35532d3cf48a6b62229a8e46692349d29e3324722cdfb3e191db5445029d3765fec8f7a9100d34ff0051eb79d4750c74e4f151d54294790151430c32c47d0df71148de34bd5435a0995a991dfd409fa10083f5f756a76d0e7ab924928076f5ca2abc8c1552d043938a421092b08028aaa2b1689e3723c455e250a58725b8faf06b4a65867ad528d53c474b3a4df9353d34143554914be058e6a5c955bc691d2b46af26b65a555355512dbd2d26803d3febfb6c2ab9273ababeaa11539e8ebfc7cf9fff00217a35e966eb5ecbdc78ca5a57a77feed5764e6ca6ddc842a9ea49f019b926a544909f53c610a806c0fd7dd65b60281fab911b8258777eceaf1fa1bf9f345251d1d17c82eb6a57725619b756c1ac102972cb7927c1e41a5495f41fd304c809fa0b7b4c6d7562326b4f3e9b7b7500157af56d1d45fcc5fe1f773ad2c3b67b8f6ee232d54e228f03bca64daf95f291c2019175a29093c0d1335cfb60c320fc35e99689d4e57a3a54390a0ca53455b8caea3c8d1cca1e1aba1a986ae9a553f468a7a77922914ff5048f6d70e3d37d4cf7eebdd7bdfbaf75ef7eebdd7bdfbaf75ef7eebdd7ffd4dfe3dfbaf75ef7eebdd32e7f726dedab8da8ccee7cee1f6ee22951a4a9ca67325478ac7c11a0bb34b595d3414f1803fab7bd8049a019ebdc703aadbeeafe6fdf07ba60d65237655476466a90f8df15d6389a9dceab39708239b2a9f6f888c17206a133a8b8f6fa5b48fc6807cfa716376f2a0f9f5559dcdfcfff007ee4a3acc7745f4ae1766abc63ec773f65e58ee0ab224bf85d301843494f4f338b1d12bcd6fcfb52b669a4b192a7d063a70402a4171d538778fcff00f98fdee3234dd8dde1bd6ab1d573151b4b6bcb16cfda11b48c638619f1b877824969da337d4cd27e0b006fed4a411aa83a47f97ada048c83c7f9f44ca25599a6a8caad2412cf20f3d7412c92d43ccb2697a676ab53348582dcb024dee47d78b325748a12295a1ff0f5735d553c3a93599b922890d1d06b154ba68e14d4a62a5378cb493c87f4ea62431e2c6c3ea07b74434a166e3d799d49040f2e982b7233d453688ea66baeb8ea520a810c752cce1bc2d3cdeba7881ba92a46a1f436e7dd995300a9ad38f1eb4553e2a67a6892a95e768e174c8c54d1acd5d52e9f7294a94ee11291163d32d4d2293e2552ba9a41a8fa415f7448ff0011345eaa480bda6849ff0051ea7d1cf2949a4fb79966a7413a5048c448911f5d8c31d34b2353ae9f50d654358fa8ead4eb855518ae7aba8212ac3bfa8b5591cd8a39f22b2d4d44c6aead1e19a58d7c89563c91d1fdd5445acc9122dc2b480b58fe752fb654c4a4563eb61ce9a0183d27ab720b13b570892b52cb046248063aae492a9641f729002fe59625896edfa6e2f7fc7b7d655255694cd3a6bc3049270e3fd59e90b8ec454d6473a0af9b4159a91655a9414f1bcf23ebbc912cb10c8ab12dae322d7b371cfbf4cf4528070ff571eb453208145a74ed8c8569a49d2a863f24f0068a85a9d7ec720fe080c3e5731790d44291c7e575791830e45afc5033af730209fd9d690c91b90738e9f256adab8a193eea8238e18e7fbb79e171347e0b4b1c71d35588de5798c88d180e021e09b900b95a3805727874e12493a97cba50fdbe2325262e88d4c91e4e5a7499aa677c85391387892b5a39581923a46550a0b82ba8f040bdb7dd8d1d6c0405815cfaf58e05ae316874a6fb69dea243054c2d1e42595d9e58a096a66791de5b42ac64042345c06b903db2c597b8e5fd070fe5d52a7512a320750cd567c22d21c44b1d4d3d32544b49f73415303c88c9a5e1942355432240a1bc6fa838e6f71c6f590c55d7b49fd9d6bc4259432918e985e7ab98d64d474d3b5489a6153532d2a8868444018591633e58cc0ea430370c40603803dd5cae9249a9a71e9dad41200e14e99ea27923a5f156d5ad464a628a2aa15a68e20c192a3c4669c9955e472deab03a86916f4fbd0562000a2b4c1e35fb3a68934a69151feac758e9aaaa42472c8d2c02713d4c5474fa8bd618dfc0939689032aa1d2aaaca11d80fa32dfded6a18472572287ab0c50e757fab1d4a597231d397aa4926a62f3494a95113d15492ad1ca64786cfe52431074a80e79e0fbdbd457520a1fe5f9f5e0007d4c2b8e9ee9b214de3a496347851fede34069df1e937f95245514e9442a673506692faccaa4b2fa8e916f6e8a2e82787cbf97562476d3f6f429ed9ab30e23c50c74ebf679195e9991aa74cd3fdee814f3ace1245343a24282efe93765239f7a450b2573923fe2fab21644c1e27adcc7e32653f8d74e759e50ca246aed998377736d0d2434623531dc7d2d1f16b71ed55cf13e7d275396fb7a1a73d50abe2890f2559db82794e00b0fc1faff00aded21200af57f3c753f6bc3e17f2d8b796c40e01b5adf4b588b73feb7bd300460f5ee1d0d78a9384fcdc2aea23d25401c9161f4bd87b47271c75e38f3c74b6a626c839b924af0a38fa9516e381fd78f6d1fe7d7b15a8e9f2150a80ea3a2d637e0d89e6d7e7d57ff007d6f7ef3f9f5ae3fb7ace00705556cda4f2c7d2bc6b1ac581234d88e2fef54fe7d581f2ea2c888aaef3158d5119de577544118bff9c772155349bfa8ff00b7f7a23d3ad8e38e8b8f6066faef7e7deec968bfbc934ead4956228563a486471a5445555011269a226e34e9d26dcfb47b86d90dfdbbc370a0a30fcfed1e94e8cf6ebdbbdb674b9b762aca6bc707aad5edee97cc7546723c5562d4cd8ec8d3be4b09919bd51cf046e164a3592125056d086b4a819bd2435ac7dc27bcecd36cf746193319a956f51e9f6f536ec9bf43bddb0950059970ebe87d7ec3d056b0ca8236f26a8d417d0da59e4d408d2aeba4b03fedee3fc7d93d0f1e8ebd40ea355e4de88a48a87d6638e391a355e6f7225b92eec2dc8b71eee1f466bd59135d47971a7528e598a01e56b9f48b78d59ee0b6a453fea81fa920ff004f77f1491c3aa9400f03d4333c8eecc2570115b5247aa59ff5725db4850b6e0f27db6493c4f55665414ff57dbd428315b8770e5f1b86c0d054d557652a968e8d349927a8965611c5043102c57923d56e003efc91cd34b1c30a1691f000f3eb4f3dbc30bcf3ca16341524f9756c3d1bf17f0dd6f152e5f3b4543bb3b0cc51d44d90af0b36dcda1232022928699c78b2594a76fd73bdf4b0f4e9162657e5fe4fb6dbf45ddf8125e7103f0afc87a9f99ea1de60e70b9dc9e4b6b163158647f49fe67d0746d571f8f829e57c9cc2aea67d4f513140c6a4301ad496b8582cb6d0b6520737f63914e81471d154ed1c851e2b311e5767a8c764e845eb7ec557eca451ca0a8887ed36ab703ebfd08f6e81a454f99e1f2e94441a4521f20f4b0eb7f90d87dc069f0db9a4fe1999bac21ea4af82adc5829a1ac91903c8cdc8865b483908cdf4f7a280d747549a07886a195e8c23488cab34122cd148559648d8699141bfeafd4585b9536616e47b6fa63a6baf957417fad8020ff8daf6b9d3703e9febfbf60f5afcfa0e32aece6407505524b7362490096361c5c5afede8c601f3eb63cba44574d7723e809b917fc016badac0037e3f3eddf2eb75c70e90354edf72ca3e8355cdb9d43e809fe801e7db895d43d3aac9f09a75ab17f362cdd36e4f96990a657d0bb47616d8db9242ee83efd6b84d94ad14d148c23229cd55c9b3020106df5f6a67d5e145106eea57fe2faa42aa6a493c78755a2c91fded6d2515147518e315749474ab513c4d04b491a132356789a8e90344e6ef1b5d458db9b94e58aa2fea0d4388eae5883c093d44ac972c2a2aa3a7a3a4a9afa638f4968cbd1c946d46c8b531915e65497efd55d8c639d4010091ea142c846a0a463f3af9f5ea574e2b5f3ff579752a6c74a9478dadc6c9478da9a84530d0d74cd51e77926672a16ae155750b05cb068d75b704dedef4a58a906a56be5e5f6f5b2aa4b516a3a818c9e3931f9396b2bb1df71399aa7210c22689a0a7667568e9622d52278a99d4b12a19c937bfa85ed1d15b34a8f5e9bcd5406a2d7fd55eb16127a731aa541a6a4f1d635a79e1fb09a77702588f9a3f2a346b1b97325d1d9ac2c3ea35290ef4a8d3fb7a70e82a14e48ff570eb9a41279daa68aad608293252d355534b09f055d3ba06897efa385a9ea5249908951d0305bfd7801d94769af9d3ad220a119d3d4cabc4cf1e4e38278a6f1ad409d0522d1c98b8a4a899638cc6e90c332d3d5a31863d24f0c4850480d41a551a894638cd7ad7716a0f87a72aea3aaab8bed0c30446266a6458e4c7cff7113c43431890094ce82161a99ad25ef71eeb1494f8b8fe7d54e821b530a003f2cf49fc455646852b702f34e66829feeea1cb434b8c8313316d2d2b46cf5324915326a0d61a5bd1a8ea07dec3070c091abd3a723951bf4cd7eda759e871eeef2ac7928569f2cad4b46a98e86294e428d7cb2355d749e78044f1c719790598975014907db63238e07dbd518d68467a9d4f5f90a08824b55e2d13434995a2a95841813c978e732d1c74eccb1cc191af7650cb6e0a9f7bc31031c7a728808af1f51e5d475c63d1d6d550d456bc74af05456d1d4bb8844811927aba58d8a8fb879127d31b6a2ac8faac2e40db355c151ddfeacf5ac0153c3a8154a2be7871f8535540aceba2b4cba1e4ab8e194d4d3415351fb118310d3702e45feb66bba6a74552be7d559f5d53c874eb459313533428d2fdc98e9207920997cd56e925a48192755d13d3b0b9b15bfab55c137d36900f86bc38d7ad92b5a0f88759d8a526ba1a8a7a8ac96665a49169d9239e8a41fba94e6ae30625866b1d3195d200b13724068bb1278007a7307e2a7501e1f0e50cd5c14d3d315697c81d64ada5308588cc63fdb67a4bdae08f21e6feeac8a48553d5081c132df3e9deb9aa7c2b50227657131a397508e493ca4c6b15422ba379a317506e55507d3eb6b21142a69c7cfaf7c274b7975d26e1969299291ccb097844749aaa5e7fdc88955103d3b910a8bd8b5cb82495207036be182589fd9d799cea0abd292877b545352d35554d3c527dbc2e25811a6d6a6342a051bc7a7f5a8d219f5d88249fa8f6d503134e1d599dab4071d1abea0f9d1debd46f02f54f716edda042867a1a4ca4d3d153c2cc3c62b3195c6a31d5536b1a0810e9b71f5e7db6e834825411d557c3274b01d5bc749ff3e6ef4db30e3e83b4b6a6d1ed6c753429157e52955f6aee77f19b3c8af49af0f3ce05839687f59b71ed39b78d949a956fe5d6cc71b54a9a0ead6ba93f9d67c42ec010526f49f75751e5e534cad4fb9f19fc5318a6a47a5972d82fbabc6ac0862d0a69fcfb6dad5c65581ff57cfa6bc335a035eacb7af7bb7a87b628a2afeb6ec9d99bce9e550cab81cfe3ab6ad2e6c04d40938aea77bf1a648d4dfda76475f8948ea942388e851f75eb5d7bdfbaf75fffd5dc1fe457f323f8a1f1b29aae1dd3d874bbb374d3978936575ef87756e0350bc086afeca6fe1f8d1afd2c679d0aff00a9f6eac2ed4ae07cfabac6edf0af5435f20ff9fa7746e9a7c9e27a1faeb0dd538f95a5869371eeb9a2dc5bb1a91d74ad5d3d298d7094554b6255744e41fcf1ed5a5a2915a92dd3cb01ceae3d52f76afc8fee5ef2af7cc76c76fef0df151e62d510e6f3757558da671fbc4478c4921c547009080b1a422df8bfb5b0c4b18d2de7f974e1d0a0284a7cfa086af2f431910bd41357246b23c3053b4314faa26162cca100900b9d761702dfe1729a728bdbfeae1d78904f492afcc24536aa59ccaa69f524aaeec20504332cb60deb2e96b85256c0ded6f77501457467aa3a8c353fd47af4713352cd3796ae592a21f2b30782014e5c0f1bd3abc77264707549fa88e3fc7db8da89a82b4f3e9cd006140a759e7cac6b1c15b4d0c4265d112f9ca859664815e5884a58786a18dac882e5831ff82b453bc2b1343d55813c4fe5d266a667924357513cb43aa38cd43c33c8b1c7196224a39d523081a4bb00ae14f3602f71edd6a2a8d2303a6f15cd3a6269e9e3a381124a77926596a6690417ac82913512eed24de13328f4a02a486fc9246ad23e0861461d3ab5a5081a7a78c742d1491491e212356346c95136422a08e6a88a4d78da429111a847a96faec5947f4d0deec08ab10c6bc294e3fe6eaa07712171ebd3d4b3ad2d3cd55531529acaa74a31053552474821950453cb254860cf5095474c518d635803e96f6d921895a9a1fe447f9fad55eacacb9f2e990ae4598697a9a5695aa6638e9d25935c6031d6d4f3c6de09a4d26e62671763e9b1b7b6c1d20abafcbfcfd54120d581af974c39032ee0c8013b8c446161269e5aa8d71f5305240a5e582b12058e86a63d1a74c7a1dad6e39f762106a0bc067d0ffb3d5a81aa48e27a7d8c53e268637a66a0cbd3ce4c14eb2c8947542792e5e4a51224ad10485fd21c9d4492c6fefcac7432b022b9eb45c53e12457a6faa7c8523d98e229904914d0e467914c35d4e9328312c94a88914a8e5119d3d2ea002058db4b2518a84247fab3d5a8a4921a869d742ba9bc3afc31d5cd535a10307468a592a1c86a37697c71c30a23319a5404c6a05c7d49d18cb524d448e3d36159813e75eb2c791a88e6a98a0ae4f2aa4f1daa07863a6a72cd4f4d4d479132de492a25637917c6c540376bfbdeb67206907ecff002f56d24b52be7d4d4962795d29b2157054438f4ae589e099342a10225925c82fdd54460a006d7122d86b03eaf233021680fcfd3ad98c870598d47fa87581b214d1061533429a8d3c41fed43d5181c786a11a18c2ba6a79189624a002c141f7a766e03893d59bba95c749e4cacda2b689d6ad98014929a5613631da5016a7434ae6aaa7cd6fdd1c8524e9fc135a0f086060f9fdb9eaa0ad5541afaf4dd50d2d8515652505399e9eb64bd34469eba3692a1a929aa258e79355487840d25b4e9d40ff004d5ad0753903141f67e5d6e8b5a9f87a95511d2e069b1b04af5b9879a842c9068f3caa6aa42d4b348d100f0f89a3018ab1219752817e7475b789801baac800a1071d6089dda8a2c64d4b4d3c7534b4f2187ee2a5952a679ab8d4c26649a59a0669135f0ff4d3ff00216dcd11549a8eaa58e09eb3d1278728b2c14d166a5a6aaa47a94a6ad9e37491175d2c94f2544804b24654bb1b3170186afadae34bc6ba6a34e07fb3d6c9a50b2d47cbfc3d0ab86351498dc799b3023aba8c8b0593211535552d4a3554a6799a28ed0ea2ee8a19c3fd0add7e9eeea3e02e0547e5d3ab5552fa496eb71bf873ae5f8f9d57592864076c53d8162d7f13488fa4b2a808185c0b016f6fcc415a8e04749cad1dbede871a8aa15356c4067479b4a5dc58444e956d3f5e2d63ed28e3a7ab7e7d2ff09014f1daff008506c7e9f42a0726fee8e4529d6c64f42e6342811a5bf49278fd6c0adbf02ca38bdbda37e27af139f9f4b2a4704aa37a8284d44283a88e54726e0b7d7826dee9fe1eb58f4e9e0c8555541e4d8f8dbe9c70daafa4a9b37078ff000f7aeb7c3eceb16432b8ec1e2ea3319bac8a831f410eaa8a9a8902aa851c202e6ef2bf002fd4f1ef60162003d7b8d2833d12cdebdb799ecfc92e176d554f87db3f725004d51d556856b7dc54c9c3e861caa0b01eded21080327a591c3a3be419f4e87ed8b89c26071b4b48b4823ab8d35cb918749a89e4916eed3b3ea326b2790df5f7474a703c7a4ef2163c7b7a97bc3696dade789afc0e7f18b95c1d61f23c14c91c75f8bab6528b96c14aaa1a8ebe0fab2afedc838208b8f655b96d76bba4060ba8c11e47cc7d9d2cdbb73bbdb2e56e6d24a30e23c987a11d56976df446e3eb2635be24ce6d0ad91d70bbb68698b53c8f7bc54798a65bae232c8bc48afa55c8ba311c7b87778d86ef689595d2b6e4f6b8e14f9fa1ea64d8f98acf7941a1b4dc8f8909cfe5ea3a0266c7c1f6d7af468e617652d1099d891c4a8f728879f64a63c67a11eb60d446f975c1f050b52f922293b47fa92156badc2e905b4d849724dc7d3debc33414e1d544c6a01eb041410d3a42d2ea313b7a22d7ae6754621b593cb0b9fa71cfd7ddc229e3d69db5548cf563bf11fa724c4503f666531c29b379c8e78768a55299a7c66dfd461abdc56755586ab22418a987d4202e0f3ee4ae4fd8844a772b98fbd8512be43d47ccf51673a6f626946d76f2d625cc94f36fe1fb079fcfa37b9dcb4743e0c66261fb9a944d6620daa08101f54f54d60097637b124b1fc7b90d56b96c2f51fd6991c3a0f26a2dcfb89aa4256cb1d382526ad2e29a8e3209d4827d2ca0a8360a819afc5bddcbc6985e3d6c05a71cf5ca8fadf1c9054c1307ac98e8769a54b44d24aa496d72333ccf73c922e2e38f742c5b89c7571332d349e8b6f62f4f4b453cb5d42a5581f210a3405651a8580e5581fa7e6e3de81351439e96c73ab800f1e9c7a7fb92bb6ee423d9dbcaaa69a86aa64a7a0c9d648f2494b50da63829aa246bb78dcd824a7f49e1fd26e1c35715f3ff000f4cdcc017f51387a746eeba65052342489ecc857483c9baf321511abb8f58241523db54e3d23fcba0f330cc0c80dc90c54904b2ea50cac4bad948245aff004f6a130a3af0c63a0ef253b0363e927f1f53650471622f61c71fedbdb83d7ade38f494ad7229ea655f436a560cdf5d20816b1e6e6ff8fafbba8258538f5a6ca9a71eb4fcf9e5978f23f33fba27712958f314d03d58579660f88c362d19a15b98e1a5823f4adb493eb241b8f6aaec30f0141cd00e9a8402ad4f33c3fd5c3a28b14d9379a961a688d6556529646af0b2e8134759e5b46b2f904ad0c54c1140501d802bea20fb4b2293dd4d2037ed1d3adc182f9fcfa656a6129a468a6c7c6171f54f5a29209282a214c7c8442f3642a279999d24f1a448a8d20f501663eaf788e819635a0e02be7f67cba6c2b16ab53857ac2991a8fb4a68a6cc5a4589de133b54185bc550196a7eee05f1c5e512358b5d3528bfa7e95d4426823bb89c53ad166c93403cba6882a29b215d32347a1a9e5f3c2d254c8ba887581ad4cd1533cb0bab59c4601b066716047bd38268e572467fcdd5bbaae75557a8b357d432ac54157631a47f732d4ac8d2d54b03324669cde620c314a9a8817e07e793b6464142067af558056e1f23d39c3257f8165ab929abf1898f792bc62e65b4b2d038d6111560699c4720d123704b30517b6aa2330615e3d68e0120f6d3acd5ceef498ba9ac15015a75a44c4d24b4da97423cb11ad0d34cc67311d30c81429b06bddbd6e2bebed61fa23cfaf30f3624759f1f8ca111c75300af64a8a6829047595228264459e56a3ab9aba95a58e5723d3eb288b1dbfd50d4d9ab9a23601ae3ab2c449514143d656d3489324e947054d4d1c1057533896aa9d238603f6d532d5d4055552c8249154960c0016f4a8b28d54d2320f9f562400100a39fd9d7724538a686baa327f7150d14ede7568d9448b13b077a791a0582934f224442cb71fdb536f162c3485a0269f2ea8885173d70a7359908a2afaf3052d256519a5952192b605ac5a980c1493c92149a7aa1e658df4ad9b4293700a9f7b9bb542a8af4e1c8ad7b8f4a18b094f41421eb6a21aafb64a5a234faeee2b0c644a29848d33c71b790bc6485074ab1bddcfb6e3467457618f5f4ebca407f86bf3e9842e0a8e0f23b54d6ad205a986612d4d780a9208a334f238892568e6d4b208ae78fce9e546a0d19ae29e7d79caa9534c9f975218624d4c399a77a5a6c71a98a0aa78d6ba0a7a6aa6114acd554d2a0a89eb1fc643956164034fd12ec15018f793515fdbd3214ea6665c57a932e3529e3a8ada333d3254d43488a237869e8a96a15ccd34c64e6a4bcdc83ca18d96dc6906da1b4115f3e1d5c36bd41454d7af7dfc8ccb474cd45378969e9a776d31555345548fae4324a51059c73a35259af7e357ba9531b114cfcc75e2ddc5a99ea348ed13492acd3363a1b53ab5587658e9c693148f2b3ab34f24818a9001751623f02a45188ad69d7871249cf52ce5fece41249478a2665869e9e6a921440ccdaa0904dadc7927424b00005637171fab7da05735eb60d2a0798eb1d4cd5712349554d2d5c939744a9a3ac114051196474a98a3511a09e2600123f739fa8fad89241a28a57ad024b51860f515f5504f15762bc91c39295e214f22879454ae99051d4d4c3a53c7a9bd17fa2585cdee6f1aa327771eaba53d6bd383491402b44b1b25489e299169f2152724ba93c9355428da690401982e8b9666fa717b6847526a6848eb41583d7563a78a2dd396c5ced0cbe7a88e29e0485aa5e267fb4d0d3c71bc8ee53ee5cead5a48b580bfba945010d2b51c7a781d14d3d2f317dc1bbb6cee2a6adc6d76736c55b454ef4b93c1643218eaf15c016825924a19e195639220a5401c5bf3efc638f887fb6b9ebcf20f85bab20e91fe711f2eba91a9a8076e4dbb71d14b1c71603b1f1e77235697d11ad2d2d4d4ac594a62ec3f579069fafb65ad15813a41fb31d6a5118d274d2be9d5cb746ff003f9ebdce1a1c677b75956ed6a868e25acdc7b1ebe3cbe36394b8899df0d927a7ab5527d4da2a182f3607da57b27191d53c2f9e7affd623d94dca61a87ac79eb259a5a899854cf3ac8f555081982d6c4485590c609d6aa05c0b9e793b11aa9d2d434ff571e97310a49cfd9d22e3a9933121fbead487ed07dcbcb2fecf99dace881f51412a8605413eae458807dacd31ae42d0f0eaab56ad380e9e6a279e822a3a24aba48e49754b2c751053ac7e30c1a3967991e4137dcb82140178cdf804715f0d18e41afdbd3b4d235d7a69a9aa53235346d24f2544d51512d30a932cb4ae21514ef239579de864d27445c0d5fa4db83a4502a49e15e98590cace1074db3ce219da181a099616864312c32aab99630b5266ac54552d1a31d483d23e9cdfd56601b89209c7570283271d39f98d72c0d474d5b308e2fdda699e32f511d3132bc69708c5342fed801b4df86274deb22e84a9c28e1ebf9f550d4f8788e9bd3ef6b6149e9120829751544ad9a1f2348cc245862455532c90f0e96b49e9e0f2a4d7b8a02ca6bc6bd6ebe200cde67a4bcaf4958b534f515f2a99212f9068cca12acc358b28c5c5382d2aca00d3658c94b8b358a1160b21a15f83e7c7ad051e203a7b7a9e92d156c93d7c8b5092bd2d352ad254b9a99d029fb7a0929e7a78c06a57a760081ea773f4e45afdc6bfc5c6b4c7cff3eae597530fc34e9fe2922a159e8e798ca98f996179eae359a6904f15c53d4858ff00ca15d3812226a8a3b5ec2d6b2952a4918af5aa1e0996f974d35d59482712a335553c4b17d18c9350a33699e9698a4aeb3c13328f2305d4856ea001eeb5059ea001fe5f2ebc641a8027cba8b256d5d3450c134b5797a178479282a42c0669f20ec035424627781e052ba651a588d25403c7bab165535235fa8eb402961835ea463529f498e98342d4524945050d3a5457a53acb0e96f1cf5508aca952d20792562da41ff5c7bd9fd5552450d33f3fcbad952a18fe2af51bf85e33c8b495b578926a292aeae6aaa386a6398e8557335e6261bfea0ae53493fd2e00a85666622a02d3a6ea18ad38751f1030f58d5d0d4cf2ca98e96792647a3a8789e996907dbdd5c2465e9d51a4765b19005b816f4d8a38ab06c1cffb1d79fb4963815eb33c34d322e426a1ababa29e08d634f12d3b1c74ac28e13368695225b891dcc843392a16c2e026666a119069d6f5e2aa3cfa99494d4d34f17dbcd0694a67ab44622714f5c630d3bbc8de2782312c91ac77540b35d63079f6fae235a005bd7af2548fb7fc3d40a092b69d2686b8bcb53e34a25a8a8a78eba24f0d54924b574e2a191e24668c2c80b2c609d44fe3defc43fd127f653ad1578d882de5d35c991a868e2fb3c8792a69ea238e4a9929de920865666fbd3a26a6aa49a3675b052ee9ea240fa8f7ac925cc78fb7ab13da4e9a749ec90fdca4972147151cecd1d622518a68522a783c8669208da52355431fd535cb5cdbf57abc0ea460a7ce9fec7555229518ea31f354c865a1535ffb94cb4d5661155f6f4ccbe3961a92b2bcb5124b286b001eebcdaed77f307a22d4023cbaf062ec09151d3cd653c94f5f55053d6c3475b1d35140f594b019e5ad8a8e8daacf928e76d2891248508bb3dc6a3705b55d0f89562b5209eb608c2818ea0acb8aa595a67ac753929e919d6148a6f3bc508bc4dacc269e98996c6d6905dae48e5bcc357c54a8ad3eceaa485a1152dd71a0afa1ada25ac94d344eb57914c6b542b4d1d1bc28234925a658a3ad99de9e42f1339f18d37ff11403b6abe7e4307ad711a88a1af0af43061e9a9b1fb67032473189ef4b2cb4d4c86b291eb2c258dda3a97872492d43cbe47404004865f220f6e85a900fe55ff57ece9f8d9574873c7adbfbe16e4d63f897d495a2633c8db6b208d22cab22b4d4f5f52ae887d36097b58807f16e3da993245569818e997a092415c6ae8c660d5aa5295f496214c8d73666246ad06f73e927ebfec3da006921f4ebde9d0d1888d018c90c430b0b01e965b11c1363c0f7a7a53e7d7863a10b1f70cae2ff00e074f0a38b827f3a88b5bea2ded23d2a71d78ff3e96144ba10ca2c4d83056b8d5a985d41d4a7503cff00adedbc7a75a39f2c75c72f9dc66dcc3d6e7b3552b498dc6d3b5455cce6d6080958e05b00d3ccde95039b9bfbd80490a3ad815207af55edbefb2772774e7e2a48125c7ed8a69ad8cc3c6ede26553e9abaeb713553fd45ee16feddc28d238f4630c2b10f124f8ba1e762f5851d2e39054c5691d2f7274c9a891eb56001041e473ef6085a97e3e5d352cd538e9750459ec0b3d3b53ae5e8c92b1ccb208aad107e9492371676b0faa91fd6deed55971c0f49a8a781a1e9df1f979e43a843346cae55b506668cad8912851e48f4fd01b1e3dd1d540a8eaac00e07a50c2d4d5cb574724348eb9181a0c8d056429538accd3e923c192a394189dffa48a038e0df8f69a7862b88da19e30d11e208ead14b2c32a4d1395914d410735e89bf6dfc660a6b371f59d34d5304664a9cb6c49a666aec6a042d2d5edb964f565f1c82f6a73fbe83e9a80b7b8d77be4f7b6325cedd5687cd3cd7ecf51d499b073924be1da6e674cfc03f937dbe87e67a26291245aa3f1c89f6ec618e29191584aaccb20789ec6375b58822e3e961ec0c10eaa1a83d0fb5871a8528471e96bd55b160ec6ec8db9b6a5129a6a8acfb8c8c892c6821c550eaabaed0bc8732429a3f0016bfb38d9f6d17f7d6f01ca56a7ec1c7a2bdef713b5ed973743e2a517ed38ff57d9d5d052c294b89963a7896869c53c70c6b7d1150d0524620a2a28b80152081541b705ae7dcd71a08d12341da0003fc9d40eccceccee49763527e67a0a2b27aafb1a8a9a1c656641259ad050d0cb0525766e42cc977c8563c54b8fc745a49925625b403a549f772d4fcbaf2aeb6009007cfa56e0682ad6921a9ced4233a281150e2cba632846a0d150e2d5d62a8aa588712d5caab24ce49555403dd4646453af369ad133f3f5e9e5c0a521caac11b37edd36a693c4ac7f48772751361727f3eec3edeb5d27775c34b2e2a5664591a71e3506f66722ebfa8ea3fef76f774cb8af0eb6b5a8e882f6c6d39e14a8ca4318a6a550de39749fde7048630228f24ccae34fa7807ea7dd88a3691c3a3285c30a371ff002f4337c7bece97b07656470f9262fb936532d2486598495d93c515d3479292363e460eaa6192c48f2c6a49f57ba92a48a0c748ee22f09f1f09cffb1d08590aef244d312c49530b7318b9b01a9114dc1d245c951727da8029c074cf0e91158c0d4a96fa051fa85ecd6160a2f7d46dfe1cfbdff87ad7da3a60cc42ed04c0285631f0385b296e49fea40febf9f6f422ad5af5a7034f5a587c9cc8e4339f237baf374b3503d0d6f636e115151909a38e923a8a2a97a0a384acaea3fca61a41cdd4165018906ded45d85f1406af0031f979f5586823a11435e3d17ecd4d94a534791398a27a433fda89e18a186a6a634d1e5863107f954b323c451c11a5c328e405609cb15af61a7db5e9c6392548294cf4ef590d25296a79f10f5f242d0bd22cce869d9254352d147202cb54acb13c7f8620b07bd800d32961ab5d0f1eabe55d0403fe4f3e932b0524b4752f89c4e32360fe19aa6bdd63a6831c058c2b3bd4788f844cefe3216d25ae2f707c4192875927cc7a754152a9420ff9ba851d449157c828de8a24869a2aba0921825792b67a736a88893e60b2cb0020909e305949e3491e58b50f8b35cf563818e1e7d3d5bc746f5f53554f42f47f7114e66a4c7a253bd7c4a24a6a8d3318e3958808c42dd985ce9636f75c39a3607971fdbd7aba90d0e7cbfcdd3552d7575154c5f7f5b0d5d100d02ce905254d3be3c346232637950ac91c64d965d5a948b82c7d5611c746d04d7edf3eb55c806b5a74eb5b4f0ce648ce4a4c7ad6d4464c8b0c93785a99e49e4a6b54f90c23c5eaf3282bf5522f72fa47d2bf00a0fe7d78aea5a3370eb9cd04ebf698ba1a01538d41470196aaa3edda6ab31254a4b1b45089e78ab1d59d6c2c91baebb9b93bd5a5559681fd40e9c50cd4a9a1fe5d42ae8da78ea202b328c8d47de42f10154d4ac15226a713b3ac714768858aaaa80c7559b9f7b0e8878f71fd9d6991d4a9e2de7f2eb28925c8cb451d353d350c90d44741534a654696511a86a89d126a77a8922541e4b9b2a292ca1893edf76440348aad3ad062ec452b43d37d148d156c1e19e64821aaac8d51591e9d9e64686338ea68a0a8a8a5a5a98975116f2205b5810b748eeb5008a293d694d5413ebd29d25872923232c09551fdc2caac219a56aca584bcb25535640239fc913864b83e42a05c29e1e04706344c70eafe269750bc3a4d3c8d1d3c34924a50406231498c41243ea7f58fb9a8678d5e389559c0560ba752dca9f7a628858205f9d7cfa667d4ccb5c91d428165ad84435b5b253d69f3d541426bfd3958a827923263a458668848c8018e32cd293e9034b29f7a46eea15007d9e5c7ab55f4aaae5bacf8dcbbca0635cd7d21a99423513394a82d24645371397469543f2a34ea522d60469b291aaa0f9f1f975b50da1aa687e5d43abafa92934558b0ad4d3c9152f9e058d961a6aa310f2042ede496b386130d20b5b48e469701d45d95453fc3d7815a0cd4d7a9551924c7d542052d4448615a8626b98d3395d1a6a1d2232d2d4025c158d4b58b5cfa74ddb7a11dd40ffe1eb67e78af59eb8c3fe4f27f0e867a58e4d75f0c52ea64ac9c1e59a3023092eb0dad41556163c1e5332b641c1a75af338cf58cc032290e3e2f263dde391608e48dd83ca92b0d33b2b3b4c626bb2db960c471f45510fc19008eb41704751628f278e9741af59568a194cde3694896743a639e20c5164127f650e9f4020fe74e9f4ae1479fdbfb3aa2818038797f97a78093cb224b1b8025a684d45353d4bc2c1e489a4a89e9e394bc550ec7e82425d6c74f1a87b715d8a82f403abb0a16a934a759a5a19da282747614d514e84f9d69d0f9e2063f0c0aab1968191b571ab4370403cfb675618b1cf01d79d4d012c6a3ac93622690b8a6ae96a99298425ea975ca94b1a7aea2196391c130b920050483c8371eda15234e73e5d5748ed2ca2a4749f8bfcbea6a23aaac920730f9034c8cd334d4eaa914ccda1584b231504a5d40373c11edcacb190b9fccf5bfd3c86a57a50531aa863a7a1fe3cb013291154cf249e37980621540543525021fdb5d6a5b822f7f6fb1ed074d6bc7ad815a693d7fffd7ad7aa8aa6be5a86a9af4a98bf717ee46849a110c323c61a41135a08fe8d73fb9aafc70409a82400371e1ff0015d287c8049e9eff006d3130537ddd3a087edaa8a1a47a9348644412560710b1492082d2a2312bfea781ef4c8c1c1555a03ebd3f110036788e99a2a082434b4f16422f35559a93cd5ea15e68cc8e7215f4cd1b85796262e15982dff48d57f7e01954b1f8a9d55c025941c8eb257c90e05248a08a9f2fe68152a72b1a913ce613a24a7f25c46648e49af62516c48feba7686b553f157fc3d69005d413f3eb0e35a1a999cf84cf4de1311f148a940b25332491c949ae41aa58a6555b91a64d448e2e577a94d00c9af57ed20839ea1d454c32bc544d4f3b9aa919abeae8aa4b494c8a19e090490c73494d0924797c653d3fd3eabb6562a4039a74c695f2eb11aaa82828e7534f45470ba62fd46515442c8f1d638a91fbb0346f624dbca08e06a3a69af4af60c8c53af1ed1a471e3d3649057e221a482b55de5ab95e56a9a1823f24b1bd2212d1b942d04f2c53e911d82b331536b9b58488c38e69e7d5c3128c2b4ff2f4e01647c6401a9e66a78a28aa6364a79679a96865975d33d6e9b1ac8cb16424709ad758f57172563a0238f55191519538ea5d2d4b4b1d5d764e8ebe5a98a214d8c9662d127dcf91c433cff6c1a2a853102acc094b80481a87ba1d14558da8c73f2eac1f26899ea2cf52926469abe41e05543531494294c59d69f4e968e54f34516b4d7e4d7180879b2defeefa0066268491f974d9d596618af586926868ebf275752a2a1229e3a93585e55ae54ab9234a0895123956610cac7e9ae391ac15aff46b46a0006183f975757a004f1e9ec6731d435513d25eb2b68e5a8c5b1d124295347511b2cde59e4855ccb03bf235078f8bd8707c34bb5186083fcbad9cb60541fdbd25aaaaaa2b259249abf18cd1d23414e94f098fcd179d42fdaba09e0fbc5b5dd03a8940279b90f4d32038420fdbd799b4fc391d4647a3fb686827fb8a896a2a7cbf674f0c904f3421742d6468430a7874006590b30e3f36f6a0fc407d99ff000f5472090a4d41f2e961a6a2b2aa9a2a089a9f0f43571453c15343531d44d5a94804b4c7214dea8ccc7c84c8e543a0fc86f695c02acc4d4d7f90eb5e19fc3f08f2f2e98a963a99aae4a93456aec5a0ab9eaebeb23a44a492a0bcb51178822455158d034404e91a43a16ec4b9d5eee002eb923870e07adaa53496f23d3bfdbd551c8d455d513b5189c4f4c448a92a95a6bbd5cf51a1a2fb16856431a100177049e7ddb42b2934cfcbab1072e33e7d34d6c3959cd2c872267c354bd556c30d562e9604a7f0691473c8f0cb7a97864a8914874b024104dc93e4671452808a71eb4033509e27a4ec1237dd3cb511c758b343454d1cf2c112d453cb535a74d28f3f9c53c523cdadc48b60b6b332dfdd5c861a694233f6f5e6cf86be63a934f555f415153551e2b114d8f96bcc72d2fdbd64d063c5184a799ea9e156758fee9efaa1663aafa869047bbc75a559bbb49a7e7d394a281e67a9b354cd07dec95b554b4f5d97a9922fbb9e0d4a0e956690b5553d552a52f89342a9f0cda880dc6ab6928a68071cf1e9b218617e23d70c752acc89298d0d162ea29678a4a810a4a92a53b8a9a3aeaaa63e68a9d964678d6351a05896e06964cc4b6964238f957ab519c8ce29d31e36be8a9aa29a8e0a7f3d21aac87d8cf1cf24957e25918a44511f4cb4b1c6f64329d52823f16bd91632173461f9754092d0922a0f0e86e11ad2c9494a99089e9451d3562b4d24224ab91dc472bac221291c4a496f412751d365b5cdfc41a9637c91e9c7a50226a46d51abd31d6cedf013313547c3bd8b8e69354786cfee4c5c2e46876a1abae69e0262b315ba483e8c47e47d7dae98ea58dc56ba47f2c7490d4cd203ebd58aedea6fb7829f500352026cdc28b1fa820f163f4febed094ab13e5d5fe54e85aa4034c21542b5909e6d6f4fa0fd3f2df536b58fb6d865b3d6fcba5b51bb842fea2aaab702c2e47ea2b7215d940279fc1f69e40067ccf5aa74a38eba8e9696b3235f5505063682964acafaea82b0d3d2d1c116aa8ab9d890a8234527fc4d80f6dd3ad671ebd559f6f7c91aaee0dd726dbdbc95745b0b1358d4d8652aca33ae8d66cd6402fae359cff99471a552c7827dbaa55294c9f3e8ce1b531a895a99fe5d0efd49b63ec3ede5afa728d204756716ba9b1062905e39411f5b127db8aa0b12380e9a964c100e3a393432d2bd3a46963a14002d6e45b49537e749f6c38604f48f38eb25455c54d13c93a09e9e24691880bad52352edcd8dec07e7dd00f4eb5f9740f4596dd5b97255126d9a7455512b1bc68d23c50066039179163437247f5fafb719d557b870eafa452a7a52475f97a278a0dc14d0d156811059751d3233a868cbc4ecc50c9a85ac6dcfba555b2bc3ad15e34e1d2ce86b7cda60ab6928aa032d443511b946575b696a7624075bdae09ff03efc46284d7aad6b4cf453be45f54d0e5209fb070f8f8573542557765252c094f4d5f46cc445b85444001591dc0a82bc38373c827d8079a3618b4fef0b68e847c6a3fc3d483ca5cc32a30db2ee426323b09f23fc3f67a75cbe1e6d2a3a8cfee8dcc2911e5a1a2a2c3d0866688893252b3d495011ac1a1a6d3a8dc00c47e7deb93ad4092eae88c8017fca7abf3c5e48d15a59eaed24b1fcb8747b73b470ca9a7293978351031b4923471bd8dd56671fb8f1dc7d05ae3d8f813e5d4746be5c7a6a514c9144a69638d54ae80b19551a0158a2f19661a514d978ff0063efd8af5e3f3eb0e5ea0acf48a2511a2de495cb15fd2bc358dcd80f7e1438eb7f670e9bf2157e648186a0aee0a0f5b4d3aa8fd623035e93f816f7b1e9d7bfc3d40a8867aea8a3a69e08a52be591292495c2c7295b472d7f8cd9204d57f106d6e7eb61eee3b0310d9c75e07d0e7a0d77ded51b9667a31ae48d212924965214005511117d10c31dafa147bd8cafc5d3f1481789cf45af6a61a0ea5edadbb5bea8f1fb865936cd739521624af1a69e5907042c758139fc5cfbd15a0607e2e944cc65889f319e8c566698d0646b290ae94132cb17e900452167d42fcb050d6b0fc8ff000f6f29aa8af1e90f114e9215c0364113824aab0e47a4afe4901b9b7d40ff0078f7602a31d78500e9877354a50e2b215eec1529a867aa95ef71e3a685a595c8b5c044427fd707dac817b80ea921a2f5a306fec87f797776f4cce5e114b479dddbb93334d5d5313cb1575255ee2a986011012faa309359d183445ac6c0fb72601a79741ad0d3aaa6b318ed34fe7d23e9e9a7929aa68e5a7a9a78684c11e2abd31c934352ef23c5240b4d4f0b451d52a3ea2da5c1506dc5c14e48a9a71a70ff0067ab8a2950a3b7f9f52325250c4f07d9425992389aba496488c9241140eb1d3c145392f1d523db53465003a00d2c54b32c2aab47ab0f2a7f80f56634638ec0387af4d99078114c5147555b445c8831351206a1d690af8125f12c4b342960d29e10b682c74d8fbf02284eba311e9d344e95fb7a68a59aa6a29d65a796be966a1c94f155534cc9289a6023b1a933012c2954a000005166162def60d0034ed34f97f3e1d6d941d3a8752aa6ad66a3ad923a1a1c6d485a8aa9296aa4904d153c09a64fbaa5585cfde34a164584f95a520904021bdde95001274e3853ab14603583434e1d62a3cfe5e8e4ae82ba8e804591c463f271bd2434c698a155a69ea20a4a978753f9114583a0529aae3eaad040d560b51c29f3fb7a6d98fc4c31ebd74998ae96a5eb28962984714025d65ab47ddca4524d054899a426a5d002de12423f04806cb7a7701a052bd34ac490ab5a7fab8f52a7832d53572cb55969e9de911c789cc8cf155c750b0168607b9acb1fd688a6c96bfe936ab33a80ba45287f2e94ead0942dc0d3aea870d5759475286a722d94a350d5af233434f052997eee3a9a4444bb534c82ce8f72d22d89365bb352eb55a63f9ffc57562ae403abae12e3b2125556c29538dca79e3fb85ab8aa0ad5c954a0ad5bd7d32de4a458deea2393c4b72a7f4823dba8d228150694ff00563a60ab7c51b01ebd3b478d8692b66ca5e9fee55dd292a85609df1a9510a473978fcf12c9a64516742c40e08e16fa2a3f4c95ee3c4756d0ab53d36c4945435b959deb2b2b72115cd4cf0471bca6a1690b4f327dc3c8ee87ca2ec5145db4006e96b51d9cea1c4572387a75a5a9a81961fcfa565b1f2cd875a649228e7a74535d96678d0154f352d42484c54d045505ec78f42b5c9beaf7e08a7bb5d5fcc7975734249f3a749e9e2868274ab928b17004f3cfa7ede679604759d5a0a493c75325441253abaf3280644d37e149f142c4957c7aff009fa77569cd28475dc5591d1bd33857434959462b2be9b1d0b4b5895b1b185de8de70cca8b280de30bcb5cd83003c622d5d34e1d35ac02bda6a7d3ac793c8512d2d6c3458eaba8793ed6b320b3d2c33bc94d515529a62ab1f02a635631e965baab922d7215df0d10559bcbcb87faabd68e4d34f5ccff0c8a8254aa86232433c03198e7a292384680f3e89a2d2ad4ff69e63e4d21559e40472c34fa85e95fecc7edaf5e2df8581e3d3562c51cf571455193a3869649201252a4a6093596f2b515334a1629a5e490de4d4e400c05d6c99e86a147edeb6400482481e5d61abc54d423563eae6a8758deb23ab969c96903d5b305b4324b0a4d1954521791c5ede9bdc923b4703f3eab55434a9f5ea4fdc319444d3538a8a9323bcf52c5e27a78e02f234d12205a7266203043cb9047fb55c87075051523f675b57968c428a57ac9056aa4d4b4e2a629583346d099274869d8f0d2c71bac8f3c9a46b1c924023e96be8b4da457cff00d54eacd915271d4cacd71c69163ea69563ab9de38e3f2f9e48e7752869a269ccb1c52d43125aca630b604dedef68aa055f8d78f5a505f80eb053d0d72d2e420cbb3474d43e29e9e659402a180f1108b22c622864501e2d3607f3eeeeba98953434e3fe4ebc50692a4d187fab8f58a29a99aba7d3514ea692060258743ba288d4f8de53a1504cd210a578b1007162195346a483bbcbad2271a0c7501d1324941514993f34eb3d5a35040f4d024416469228bcaecfe3a880705d2f71f4bf3edd7d7a4d00a75e2f43a471ebffd0ac9832d422aab15f255429d6999e614f048b0d6c8fa5529a99888e36823921b3a9b58dc126e7d8a59484521715e1f6f4a58a9a631d719f718992964591c8ac020681436a9628a19cab5753a49e4bd22825197d3a492470a3deb20952b8ead1121aa05075823c829829a9a2a8abab914dd6a26431d3c8c18ad32472b4658ac214ad988d4c07e01bfaa9c5b0c3e7d58e93552d9eb1e46b85152c54f3c2f5156018e233af8229eb25b96908851a6a9d4aa154b0d371a8dbd646801534048ff567aa06a60354f5c856e5e2d1198a0a2349aa2aea6558d7cf5260403ed879228a78cc926b209572013c6a6b55915db5a31c70ea8cc7cc79ff00a8f50443987a5967c7d7cd51535133a57ba2c778ea5dda0f0a4127825969423de420fa435dc0f5d9da3041c687cfa7b1e5f17522831eb5428aab2505524f039125154bf922952256a68eb6301e22d2328b1424a2a1bb13760284af8802007e7e7d50f73f0ede9c2be9e1a4a882458559a24963334755246f5b513c62a9e9569e4679238a181ff6d83025cd98124fb70206619a75b001c56bd7b1d2c710874d7676929a469ea35e44f99f1cf577d322460b471d34ef35a40418037d34960051aa6819c6aeb5a01514241ea7e4e5a6c7d29f1d7d4cd5e68a411c8d25347414b90aa9516966fb6089551c5053da5773aaecc745b81ef4c684500551fea3d68b32823e5d33d4e22be8c3d54514335455cb1d15149898fee593190a17cae42916b0c6d503442cd217d202bf31d85fde850c8e34e08f3eaac09c0635a75927a88deb5215817ed95ca4d5e29656a9ad8d55e3a649a5a8966a186089556d2236953720004fbf212da4800632074eaa9c6ac8e9ab25458f8f134b2d39ab92a694d748f0c72fdb54abce8cc90c4d2b306895033c8e80862b626e4696f4e8652a454f566a0ae814af50f2591a7fe1f363a829aab1b535353474b05150b52fdb2bd3205a8fba90c6af495124a9aacba75f005c11654a19a87c871ff274db13a4a8146e9e7134ab2545254c8d49901053474942f570a51498fa58a4fdf87534ce69a983427548a2c1ef6faeaf6d06ee7a83a7aa286206a275758c57454c6bb222bf2149534b0ce4c12afdde2da4ae9f4b40850e906ae49daecab1141a5cb904fb6998b2858c8f4ce0f4ed18935a8a0e9cb1d861490ac38f9eb6a28a5996693eee6a592a65a38a47acab8a1a584cef4de58a25d64190cb753ac036162da9947a1eac294ad0d4f53ab25c8c66a2aa0c654d45355468eb5546b3342f244f1231cb51879fed9d4335d535332afea4bdbdf998a828cf4eefe5f23d564d24054346e93f512d637a2b64c755bd63186866024c73d0553bf91fc54b0a4b124513cc01b963ead45cf1a761828afc55f435af5a28eb5278f5d4f2c73c91cb57163a38944d41e1a0a98e1a96a8a7559bcd148f18356b1e95b9b804fa57d22e95526ac0a67a6f5121aa33d47c255fdcd7c6b4b5754d8ea5a27a89e19e3e2b2b1a6f3ab3d0b6b9239cd532067258694d36e57c7b22babb543018f90e9c040f3c8e9d60ab95928ca55628d591348ef24b46b4d24f3c9582178a29699a59c453ceaaac6d63617e174d02b1a9d355ff0007d9d7b593503ac21e4871e62a6a6582a162549048159ab722acea8d473bc51c2d12c4ca4a1bab8fa5d481efce34fc75d35c7dbd6d3bf0c4823a891fd9c4697ee30fe49a0350af515b4c53281da56020c64f0c4c95b0cd2ea1a8ab031dae57f3b44056a573feae3d380e9a0573f307a5c54613155994c6cf4116468ea2186132c7a5aa2131a4dfe434f253b8a3abab93ce0329171ab80c0fa7df995877035f5ff57cbaa1a315c67ad84ff95666ebf31d1dbf367e42769e6da3bf2aabe9cb48ee22a3af48263ada611caa567475d2ea1940b5ac2fed7336ab689c71c8af4cb29593babaa9d5cc61995840028bf8d058f2000b6361cdc5eded375b1e7d0ab8dd2ea9704b05009fe857ea0df83fe1ff0011ed8619f975bf2a74b1a3e60310d0c586b5d2565d4b73a78176472dc5bfa7fafed33f10687aa9f43d123f999db95585c6d074c6dcaa232599862c8ef79a96405a1c6b1d547826b5ca9aad3ae55bdc28b7d1bdb6e68a4819e9758c2b249e230c0e1f6f415f4675724d538ccd55c568aacac688d1a95460a085e57826dc123dfa2422953927a5f3cb40c2991d59ce136963e828a18228d22332a878a48629e07016f778251a030617d4855adf9f6e6ba6070e899dcb915e1d752e12a281a49a8c322c25b5470092a60217fb494e5856429637b2f97e9f4f7e326af88754afcba6bad159351551a6920a9250abc70cea64175f56aa697c3542e09b829f4f76a281c3cbab2d0115e935b5b254d1d404c7c16af8048b534895094b91a37b59a4a681a4a77ada69403711b1603823db2c3c89c75b218d69c3aeb70986ade9a4ad56a6116903cb25e7aa9f512a6384fee6ae6d617b7f5b7bf0c569d6d1b4d7f3e842a69e178e955c26b8e18aeb222ba3b2a0d4aebc9bdc9bd8fe3def34e3d35f9e7ae798a48eb68dcc9414ab0cb0351d514694c73d254a18e44a9846a8e48c3bdc020dbfa73eeae892a346e2aac287ece9c8a468e44743420d41f4a7fc574077c6a58b6dee1ed6db0f5169f1191a22ab246d14831ea6614d242e976292acc3571a86907f36f61fd8ad8d9bee36c5be1931f6797424e62b83771ed9774f8a3fe7e7d18fcfe5211531aaa9210dc958ca02000031f259ad7fc9e79f622ff07418f4e99e5abc9559a78e869a491d9c9660098a3541eb7791aca428fa73f9f7b017353d787a75d4b8babada98e3a890c891c83cb1c120679a4b0282aaa349869a0d605d54bb93f8f7e0680d067ad578e7a79a9a68696d23b4712842ba697caaa43c64697ab766ab96edfe28bfed36f7ae141d7bd7a81114a752cac23fd9b8517e5dd8fd6c3ea889f822ff005f7e26bf6f5baf59f1142a692a2ae45d2f33109a8d8696bdbf511c303c9f7e3c71d68915af40676d6cb5c846b574ea04b46ad51038faa54c7fb9148a7eb74900208f6e8ab8cf11c7ece94c2e006563c7acd3d636776c6dcdccce92cd2d0c54b9096303435740ac9331504a8ff298e41cfe4fbda1e23a608a1a749476d4e93596edaacca54d813a7e8bfe3f9f6f8e3c73d6bd4745bbe57eed9f65fc70ee6dcd4d2082a70fd7fb87ed64245c5555d2be3e9b4f218b892a81fafd7dafb5fed437974dcdc00eb4adaaa497278e47a59249c4225678b26419e92a272ffe534b4e2a55e596096a0c91ab5c926d62782d574b17a02493c387572c55695e14ebd46f8a514a63ce4754c266fd8a0a7748e4063091cb22806382a6a60d4423382a12ccca74d982650cdda295f3ce7ede9b07490f4c1ea2d12c4de49ffe02b85a9a78a69689bc9295964035d451ad32414f342ca1d5d34172420f512956001ac8686b8f4afd9d6d496ad067fcfc3a64fe132e425aa86a9ea69ea605acae5a6aa9c418d092c1a69dc3a4f2b198496d4aa45cdc9f507f7555209d26b8ea8636d5ddfea3d63c2e4a8e7a7c83d7d0c38ef03d3554011a74c8564ad615a1a05f01a88e52ba828901475009b917758963e101c079f0eafac50ea391fe0e9634345446b0d43c3356539a08aaaa2582aa3785a69241322b4b52b24b1c52408aaca596c54adc720323512a950a4f4e1d22a00c1e93fe1c619e87cf5b8d3454b56cb5101a949a566ac634fe01e4867a98e1324a88d1b7e9faae9e4b5c285ed706a2bf60ff0067ad6a88a64e3fc3d4d8ebe6c556b454b48f2a4153353c5514549051a4027962bd24756cb1c1308924593d44c6405e7d2a4e8888d0a9cd6a7ecea8ec4ff64b8ea6543308ea0f86b269de06a0fbd4a7a792aeb6be688ad3491c4ccb1d2c74f1aa8d6b679edf5e134eea4ea183c4fd9d6ce92a06ac9e9930f595dfc5a39aa618b253e429d157cf5d28a2a6179219695943055ae8654fa5bd248239e0690a95a9142a3881fe1eb4a58b10335c7cba975d8987f874be3c742b3d1ad7aabc94ec35554cc5e568aa94318c4b0a98cb49200cc7936b7bf7e2346a927cf1ff0017d5f00691c294e9351d761e7a7a9a0a658ca9a85307db354781218e18cc94f1d9e2f40a51acb02c432585cfb74ea8d42c841c7e7f60ebc4175a57a93938aa296ba08c511210bc52d7c94ce0d52466e28eaeaa2aad5142b2b5e4671ea2c00f495b6815146ae69ebe5f675a11b47424509e9fa9a695a82a69a4a4596bb18f77f3324d4f251bab2473254471345cb9d089e93203ac00a4d9b0c9e2034a8f5ff63ad025f86181fdbd2772746ec2a1a97cd458faaa489ab853d4bb133c4864829e99e78a748d229112442a635e4fa8693edd5750680827e7e5d695642e75134afaf53317415628292b32222aaaf869d209c0f054493f8c182240d4b2cf299ec346a2b1967170483abdb0841a96240afaf0e9fa22d0e9cf99e9ce6a2c656fd9d2cd388e9e469879a292749e1926a79629d4bdd5842a1556ce3c6ac58b13ad807d172dab04e687f974cb48f332a6a000e1d27f254f51a684e420926805408654c554d2491a42299d0ab53d42a34444680b2b1527513c90fef6cc1814462a01f31d6d751c135f3eb34aec94ed4f49153478ea71f738c796ae492a4492c5e215483cc88f548d2143180cba436ae3c9664e95504f13c7874dc84d6b5ee18a7588d6c11252d2e4dc4aaeced4f926356b2d43891d4d3ca123a78d1f56a8d9bd40ff005fa95d69eeac6323d787575522ae40e9460aca9054d0d1e3aba9d619dc4d4fa805b2b23c132d422c9526a2a068f482432862c3fb2f062c7b9687f975ea1505803d262aaa526ab934d155415f0944a7f144ed0d65f52218d5e4744991bd0e4329727fb37e2a1e840635e3f9754462cc5b3a47f87a978f9a8238c5157d355472d34724d2c4f0ac2a181fd942b2147e649b4821ac39637fecd1a46040c69cf9f5b74634238753239d95a56a7869b2cb5b04950b02085eaa358bf72a8492cbe149046ea796e2e75116e05f5b08f531a1fdbd694b155edc750a5ca4f9181cd49a4c754c20ad352d3d04931a813c3117a29cc092af9f8f1ab162345ec4580f6cab02e4a8aafcf8ff003e9cff004c0e3a80f52b41251c7534149594f50658e45a5a75a3459a44572a9608f36a3c6b62bc2dfe84fb7f582b5a106bfb3aaab2863406bc7affd1ac4867aafe23591351aad1c14c0455b254214a788a7db699238c4bae9de57f5baaea606f704dfd8a49420eae07a51de413c57d7a9988621aa2b63a7a3ad991c99640b4eb0494b6d13c94ecf4daa2795a31640012c80d881cd1d9468ed343d6f510001c3acf4d3d6f892a699e2a415d958a0831b5475c949e2479aaaa809a15a58d624551761a589d3c156beb4c60aab8af5660a699cf5c26acae9eb95a0657a782a283c350c1bc0e0931d2ad9a35b4eb4e480d1920b91abd3ac7b72375140a31d3210b10c452869d325653cb95abc754c1924aca8a7cacb5b55054f9434113de15151502c29ea6464e515482a2f6fd63de81018f6e29feaff8be9dd259a8d8e9da0a75c8d666321e5a782911a4c3e220a5823a7aa8667a744ac9e549656914cd27afcb2adb49bfd59d45158aa13a6a09eb4d2281a80cf0affc575dd1edea6c7d2473ff0016aaad123d4c73bce125f4b0585652be491a9cde6575be83228374f51b39573a8003e5e5fcfada8a00c4d053af2512d733fdcd75240156a282068a0602b74cad0cf5704a4127c4e34a35c70f73a758b686b0410b8f3f91ebc00a13c0f4e51636a26ab2d0cb1d3c7e09e5535f3cf2031d1461dcc32c3169265666525e3b0f25ee4907decd4a924777a0eae81994027a6c923cc4d57ae8a29a68238b54d47e08ebe8ce4aa4398bf6ea1a392614d4e81d2ec0796ca4806dedb908a2a30f2e3e9d50fc44af023fc1d61c8f8ab69e9a924802d0d30871f1fdbb7832b591c0eaf5113d0fee7f0e712a17934b13c8040d43dd4280da8310f4eaa8a6aa6bc7a8b4f919b10f2415154aff006f1bcb578a89163a0f1cb2a1c65490f148a144ce04ab1aa820f24dac5f042aead1d3c005183c3a6bfe299cca56d5d4c9928e9a2868e48168f2700869ead00515344924040886917846af56abfd357ba285e052809e9a0727b8d3a67a8a98f2b5743054d0364a77680d3ccb0acc0e4a30a7cd2c919a2a98aae040a4b9262250fa0925fdea5d2b565908c797cbaf30a9042e3a53d6d1a4f8a1163a3a8a6ca222d0ae5c2b6462a569e5960fbbad8e68e7124aa91ba9726620317bc7c0f757908541e67f2eb6afd8c78d318eb1d58ae1b6a8e3ab81245a7ada7a9865a28a1797230c74e527a5530f95897adb85672fe906e3807de98ea543a684f5e1dc295ee3d3c453c551518e821a98dca51564b2214489239ea5bc74e9907686488cf4d4d0158849a9e302e02dc7bb6965352001a78fcfade557bfe1af9752e2a9c863a5a682abd556b3c79097eda7aa7a29a9a969a48964aa5528669d5d9e53662c145fc67df8b8aad463cea3f675562fdc7cfa6a15be29e8abd929c43502a6b696b2a11ea5eb5e79046d514e07917edaa1bc6754ba5ee96162029deb8c9d04569c470a7a74e6a3405ce7d7ac14b2bd5d0bc72a62269a86ae47aca06a734b591c2c449f770d434f2a4d2d2c919f1c9112ccac5486170d7654055c2fd99e9b434258b50753e2aea1a3a1afada28682aa48eb1a964a8ab4fb092ae492d3c53d1ce5cb408da40d4aceaa392a45c969734665cffab1f3eb553a890b8eb1d4d3c7a71702e369a6a99a49eaa443590cd4c1a672b52f042b155218e851599c797438172bab56aa85534043569d5a8722b9a7532a23c6c34a9483115723a252992a68eaf541044efe54966a5994a2ca843185934320e1ff003eee5485346ed19eacbc01f2a7e7d30514b8cc8642689a96ba8ab69a929fc30b55d44f108e2ab59eaa381e91966486681974a2e950e6c3829a760eba3094100fd9d52abdb46eff003af4b4a3af8e3cdd757415c8f0d7368824aaa792da62814cd4f493c96f1be3d4ea0cda8b13ac83a437bd786d520fc35fcfab2330753515cf5789fca1f7047365fb6b03aea5e3ca5260f2304754e253352f824a67a80e094aa86aa625d2505b52f373ed7000da1551f0b9fe7d56535915bce94eaf8b0b7d4a180f492004008fa7e9fa9b10c0dbda115eeaf5ae23a15b1d220b0370aca243622e8aa353e92dc1361f4ff78f6d3549a139eb60569d4ecfeeea4d8bb4f716fcaf8daa130d4734d4149053b4b257e49d0252d3c7046a59f4b956600702e6df5f69e4a633423ad842eea8be67aaf3c0f5fe4778ee7fef46e5a3ab9721b86aa7afaaa9ac576324d50c66222f25ef12021001c2a8007ba5431e39e8c94784b41e5d5816d0d85063b6f2c31c491c891acb09f19d492c60321e3f3a940ff63edcc2115c9e91c9331735e1d0c1839cd55142e41f2210ac0fd41b152390093c1079f6d9a57a4e464d3a7366649d9f492b228376f4eae2de96fd4b71f8febeeb41c3ad7974c9518fa698b43514f0cd1dc008f1f93d176b0d562eba35fe0f1ef609f5eb7f97484aaeaec3653272c52c8f4c1d44b4acb23b48ae1c0611c8cde446d27e97fc7bf57d0f4e8971a59074ef8eea24c6cc67fe20d50d1312a644bb2a8e011229665005bfd8fbd54f5a6901a00b8e9eab765e563225c6d552abe91ae19dea1a194817ba370d1127fa6a1f5e3dfab4271d37514a11d448f1fbba9e26865c4524e1948f24597458e4d439223921494127e97f7ef9756d2a7fd10744637ef69d77c76f92b8ccf6e9a14a4d9bd9987c7e3f2ad2481931e69eaa0a39b24268c1f20a096489e6e05e326d723d904978b63bc2ac8b48675001f2d40e3fcdd0b2d6c3f7aec0e90bd6e6d989a7a83e43fc9d58bd34187ac09937a8a5caa56c3154d356c16aca2969274f253d4d1b095209e9e685c329e411fe3ec41e7d043cf233d395453a94681a6966545bc7142520a6241062631c27d61948b9d5c81efdd6f1d3754ba431c291811ea5d25632aa8ac927907a41b5bf17fea3dfabd68749dc9d51aaafa0a342c116f513023d3a621760402588666fc9b7bd8182c7add31d70a906694c28dcbc891e8208e1541d571c80757f41ef4a699ebd4e958910869618400b6018a8b91fec4f04dedef5d6bd4f4c99aa18ab68eb2165b9685f4dc0bdf41e05f9b93f8f6e00574b1e1d6c7af452fac2b64a96ed5d8f22305c6653f8e615647bc714530f156c1147feeb0d55086fe84c87e87dd89a38269f974fcaa34c6e0711d3b311e032300918a72c2da17f49d17d37b801c717e49e7da814047a74c9e3d5607f350ec0a1daff0011779e36a6b23a69779e476f6d4a5a713470d556a556522acc97db2b32c8d1d3d052b3ccea18449c9f6636a3123d3001ff003749e46a38a9c57ad5a62a4a58e2a9350d23c2d34294de67fba590d1abd2c11c12c6a8b338b6bbb90ac05ee7db2d02921aa0b0e948560acc4798ea0ccd253c99482976fd534153f675934b550c281a8aca2a659a962a8d090b3b6968fd474dc587afda631a9729afbf8d0571d55d4963518e9c217c854c592931914526868eb2334f2494a92d3c07ed2574957d6be41106f178eecf7d45ace0ec98854303f9d7fcbd5056a698a7506aa5dc30d150570c7a1c8cd57f74ad2c68d8fada45122a432d64aacca04b4a1aef765bb70001ab45a265d671e5d5d6bf19e1fe5e9b24a5cccf94a0ae92430b5534b0d46331b20f053d3e46359f4cb2490c9e030b842ac9f503916d0defde22b7701c3aa3aa970a4f5c529e947db5456890c30643214d50e69999de5111aab1a7864d6ea9392d1b7ead29aac6edef41854afc26829feaf2eac59740a8fb07511e0c7a51d3351d352d4d34eaf9052e85244c8c6ffe6ea609d92395279435d59c07b807459585d808ea4b541fe7d7828500328ad3f2eb9504b4d2508ac2b45471c94baea68e7926a62ef4c66352ff006d79e8cc4566e34dada4fe037b4eb973dc73d5751c8a63a9299586b28ea59b551d3bc54d538d38cf14ad57534dad2b201e68c4ad2b7a5909e635520dd890d62942a158d4f99c7546a514eaf3e23acd926af8e1a62a925343551415858c24b354d9a514d1d21757816a8c075eb0ece5480bea1abdc43313927f6f5b234c80d0f0c75cebdaa667c654d6cf578fdb991c6d451d4d3d139975d550491d3c152f0ccb0b8c5bae99c39009e058fbb853e40509f3f23d3b92c7b4e9a0e9250d46371f3ad0e3c9f2e4dd4cd16462969a48e9a4698cb04334aed34ceea0a9998a85171e9516f7a20022490127f68eb65594054207e7d4fc861a6a8a5a5a5a8fb2788a18c63e0ac65fd98a24295735524a5caa4ee802c8c92313652432fbd56a11c2104ffa88eb4e598e7c871e9cf15908a3871b498dabc5a1a3a88e3ca253c3909ef215b4ab0c8e91411c80bb21532da5918900871eee54a23160727e5fcfad2302400d907a9d5b5d23cb0cb3550ada292789545350144d0c891ced511491dea511c87d6108b0b9d366f7e0aa1496a03d5988c12c49eba821a5a192510e849aa7cad36aa9a7a66150b199291cc34fe4547d21c273cc7753a585fde8c3ad9643ddfe0ea95c535541afdbd349aba2359aa018aa833cfaa91f44d3515656c950a92cd5358278929d564958f8cb1248039570c2ceacc6bc0d3857ab54050d4eea1ea065b409296b12926ad351155e3eabc3086913fc99a37c8b2537dbceab04ac80c8ccc1d5816d579afed2a0543641ce69fb3aa06d46aaf46e9aa295a4a0821af9aae0440f879a39a85a665992f2c45a56512225ac14d8302e41046b07c63620d0d5788cfaf55d2a58d5a87a9b472d554e5196483ef156952a75c84534a2aa10f05418219501a7d3a2fe195508039b7aefe551e104fc55fcfab600624e3a9f485a448d22a84a0a575a8922ca494f549347514fe409e7f2c68226a98c846200e07a872fedca8cafe21fe0eaa0975720903cba76634792c5ac356a165fb4d7f754d28887de5d95295a48ecb37a0871c862058fab590d2c4ba8396a8ff0f56e205168dd37253d3a504ed5c1a96a9638e289a0796b22a90d12a4a92a2791f5e9e156e45ec5ac75fbd39432000507993d5aa687a8c98d313530a7823a4f0cd0524267691354850cdf755ab27940a79f488da3274a0009bd9af6d6ba73522bd368a5064e7d3ae71c953054c54f3cd4d1541a9647860a8d5494c94e1a46ad740be17fb65660c9c1653fd47b6d63243123b7a7151a858f1ff2753a4fb3ae9aaea69b45463e5a74696591b44940fa0c4d04ce034908127a830f5952a4d96e3dbba495080d0f55a1ad2b8af5ffd9);
INSERT INTO `persona` (`idPersona`, `Documento`, `idTipoDocumento`, `Nombre`, `Apellidos`, `NombreCompleto`, `direccion`, `Telefono`, `Sexo`, `FechaNacimiento`, `correo`, `Estado`, `foto`) VALUES
(2, '31166115', 1, 'Hilda Nelly', 'Isaac Guevara', 'Hilda Nelly Isaac Guevara', 'asfsdf', 'sdfsdf', 'Femenino', '2017-07-12', 'sdfsdf', 'A', NULL),
(3, '1', 1, 'Gym1', 'Gym', 'Gym1 Gym', 'Gym', '123', 'Masculino', '2017-09-13', 'Gym', 'A', NULL),
(4, '3235828', 1, 'cecilio', 'lopez', 'cecilio lopez', 'pruebas', '3156879852', 'Masculino', '1987-09-15', '', 'I', NULL),
(6, '1', 1, 'JUAN', 'JUNA', 'JUAN JUNA', '', '8778', 'M', NULL, '', 'A', NULL),
(7, '233', 1, 'hyh', 'hyh', 'hyh hyh', '', '2323', 'M', NULL, '', 'A', NULL),
(8, '21323', 1, 'frf', 'fr', 'frf fr', '', '232', 'M', NULL, '', 'A', NULL),
(12, '124344', 1, 'frfrf', 'frfr', 'frfrf frfr', '', '3445', 'M', NULL, '', 'A', NULL),
(13, '66122', 1, 'ttt', 'tttt', 'ttt tttt', '', '1222', 'M', NULL, '', 'A', NULL),
(14, '3235828', 1, 'ghjgfjhg', 'hgjghjgh', 'ghjgfjhg hgjghjgh', 'jghjh', 'hjghjghj', 'Masculino', '2018-02-10', 'gfjghkgfk', 'A', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `cod_producto` int(11) NOT NULL,
  `idCategoria` int(11) NOT NULL,
  `serieProducto` varchar(20) NOT NULL,
  `Nombreproducto` varchar(50) NOT NULL,
  `idIva` int(11) NOT NULL,
  `costo` decimal(14,2) NOT NULL,
  `precio_venta` decimal(14,0) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `porcentajeDescuento` int(11) NOT NULL,
  `valorTotal` decimal(14,2) NOT NULL,
  `stock` int(11) NOT NULL,
  `estado` varchar(2) NOT NULL,
  `cod_unidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`cod_producto`, `idCategoria`, `serieProducto`, `Nombreproducto`, `idIva`, `costo`, `precio_venta`, `cantidad`, `porcentajeDescuento`, `valorTotal`, `stock`, `estado`, `cod_unidad`) VALUES
(1, 1, '1', 'COCA COLA', 1, '1000.00', '1200', 86, 0, '1200.00', 10, 'A', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productomovimiento`
--

CREATE TABLE `productomovimiento` (
  `cod_productoMov` decimal(10,0) NOT NULL,
  `idSalidaEntrada` decimal(10,0) NOT NULL,
  `cod_producto` decimal(10,0) NOT NULL,
  `idCategoria` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `idProveedor` int(11) NOT NULL,
  `idempresaproveedor` decimal(10,0) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`idProveedor`, `idempresaproveedor`, `idPersona`, `estado`) VALUES
(7, '1', 12, 'A'),
(8, '1', 13, 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `idRol` int(11) NOT NULL,
  `Descripcion` varchar(60) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`idRol`, `Descripcion`, `estado`) VALUES
(1, 'Root', 'A'),
(2, 'Administrador', 'A'),
(3, 'Cliente', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rolxuser`
--

CREATE TABLE `rolxuser` (
  `idRolxUser` int(11) NOT NULL,
  `idRol` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `idSede` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `rolxuser`
--

INSERT INTO `rolxuser` (`idRolxUser`, `idRol`, `idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`) VALUES
(1, 1, 1, 'mherrera', 2, 1, 1),
(2, 2, 2, 'fdsaf', 2, 1, 2),
(3, 2, 3, 'Gym', 1, 1, 3),
(4, 2, 4, 'clopez', 2, 1, 4),
(5, 3, 5, '20180210093400', 1, 1, 14);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rutdiaejercicio`
--

CREATE TABLE `rutdiaejercicio` (
  `idRutDiaejercicio` int(11) NOT NULL,
  `idRutina` int(11) NOT NULL,
  `idEjercicios` int(11) NOT NULL,
  `IdMusculo` int(11) NOT NULL,
  `Series` int(11) NOT NULL,
  `repeticiones` int(11) NOT NULL,
  `Observaciones` varchar(100) NOT NULL,
  `Estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rutina`
--

CREATE TABLE `rutina` (
  `idRutina` int(11) NOT NULL,
  `Descripcion` varchar(60) NOT NULL,
  `FechaCreacion` datetime NOT NULL,
  `Estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rutinadias`
--

CREATE TABLE `rutinadias` (
  `idRutina` int(11) NOT NULL,
  `idDias` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rutinauser`
--

CREATE TABLE `rutinauser` (
  `idRutinaUser` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `idSede` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `idRutina` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `salidaentradaproductos`
--

CREATE TABLE `salidaentradaproductos` (
  `idSalidaEntrada` decimal(10,0) NOT NULL,
  `descripcion` varchar(20) NOT NULL,
  `tipoaccion` varchar(10) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `tipoProducto` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sedes`
--

CREATE TABLE `sedes` (
  `idSede` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `Estado` varchar(2) NOT NULL,
  `Nombre` varchar(60) NOT NULL,
  `direccion` varchar(60) NOT NULL,
  `Telefono` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `sedes`
--

INSERT INTO `sedes` (`idSede`, `idempresa`, `Estado`, `Nombre`, `direccion`, `Telefono`) VALUES
(1, 1, 'A', 'Sede1 Gym1', 'pruebas sede 1', '222222222'),
(2, 1, 'I', 'sede 2', 'prueba edit', 'jhj'),
(3, 1, 'I', 'prueba', 'prueba', '132164654');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sub_menus`
--

CREATE TABLE `sub_menus` (
  `id_submenu` int(11) NOT NULL,
  `id_menu` int(11) NOT NULL,
  `sub_menu` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `sub_menus`
--

INSERT INTO `sub_menus` (`id_submenu`, `id_menu`, `sub_menu`) VALUES
(1, 1, 'Lista de Usuarios'),
(2, 1, 'Verificar Asistencias'),
(3, 1, 'Asistencia Manual'),
(4, 2, 'Caja'),
(5, 3, 'Mi Caja'),
(6, 4, 'Lista de Empresas'),
(7, 4, 'Lista de Roles'),
(8, 4, 'Lista Perfiles'),
(9, 4, 'Asignar Perfil a Rol'),
(10, 4, 'Lista Proveedores'),
(11, 4, 'Lista de Musculos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tabla`
--

CREATE TABLE `tabla` (
  `formulario` varchar(30) NOT NULL,
  `tabla` varchar(30) NOT NULL,
  `NombreForm` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tabla`
--

INSERT INTO `tabla` (`formulario`, `tabla`, `NombreForm`) VALUES
('form11', 'cpritor', 'Primer Formulario'),
('form2', 'cpt_rhjgutj', 'Formulario 2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tabla_campos`
--

CREATE TABLE `tabla_campos` (
  `tabla` varchar(30) NOT NULL,
  `nombreCampo` varchar(50) NOT NULL,
  `tipo_campos` varchar(30) NOT NULL,
  `int` int(11) NOT NULL,
  `formulario` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tabla_campos`
--

INSERT INTO `tabla_campos` (`tabla`, `nombreCampo`, `tipo_campos`, `int`, `formulario`) VALUES
('cpt_rhtrabajador', 'nombre', 'varchar', 30, 'form11'),
('cpt_rhtrabajador', 'apellido', 'varchar', 30, 'form11'),
('cpt_rhperson', 'documento', 'varchar', 2, 'form11');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiopventa`
--

CREATE TABLE `tiopventa` (
  `idTipoVenta` decimal(10,0) NOT NULL,
  `descripcion` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tiopventa`
--

INSERT INTO `tiopventa` (`idTipoVenta`, `descripcion`) VALUES
('1', 'venta');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipodocumento`
--

CREATE TABLE `tipodocumento` (
  `idTipoDocumento` int(11) NOT NULL,
  `Descripcion` varchar(60) NOT NULL,
  `Estado` varchar(2) NOT NULL,
  `siglas` varchar(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tipodocumento`
--

INSERT INTO `tipodocumento` (`idTipoDocumento`, `Descripcion`, `Estado`, `siglas`) VALUES
(1, 'Cedula Ciudadania', 'A', 'CC'),
(2, 'Cedula Extranjeria', 'A', 'CE');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipopago`
--

CREATE TABLE `tipopago` (
  `idTipoPago` int(11) NOT NULL,
  `Descripcion` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tipopago`
--

INSERT INTO `tipopago` (`idTipoPago`, `Descripcion`) VALUES
(1, 'Efectivo'),
(2, 'Tarjeta Credito'),
(3, 'Tarjeta Debito');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiposervice`
--

CREATE TABLE `tiposervice` (
  `idTipoService` int(11) NOT NULL,
  `Descripcion` varchar(60) NOT NULL,
  `Valor` double NOT NULL,
  `porcentaje` int(11) NOT NULL,
  `Estado` varchar(2) NOT NULL,
  `Cant_Dias` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tiposervice`
--

INSERT INTO `tiposervice` (`idTipoService`, `Descripcion`, `Valor`, `porcentaje`, `Estado`, `Cant_Dias`) VALUES
(1, 'Dia', 4000, 0, 'A', 1),
(2, 'Mensualidad', 35000, 5, 'A', 30),
(3, 'Quincena', 20000, 2, 'A', 15),
(4, 'Hora Feliz', 25000, 0, 'A', 30);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidad`
--

CREATE TABLE `unidad` (
  `cod_unidad` int(11) NOT NULL,
  `Descripcion` varchar(15) NOT NULL,
  `siglas` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `unidad`
--

INSERT INTO `unidad` (`cod_unidad`, `Descripcion`, `siglas`) VALUES
(1, 'Mili Litros', 'ML');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `idSede` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `clave` varchar(60) NOT NULL,
  `NickName` varchar(80) NOT NULL,
  `Estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`, `clave`, `NickName`, `Estado`) VALUES
(1, 'mherrera', 2, 1, 1, '123456', 'Mauricio', 'A'),
(2, 'fdsaf', 2, 1, 2, 'sfdsa', 'sdafsd', 'A'),
(3, 'Gym', 1, 1, 3, 'Gym', 'Gym1', 'A'),
(4, 'clopez', 2, 1, 4, '123456', 'cecilio', 'I'),
(5, '20180210093400', 1, 1, 14, '123456', 'ghjgfjhg', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `cod_factura` int(11) NOT NULL,
  `fecha_venta` date NOT NULL,
  `idTipoVenta` decimal(10,0) NOT NULL,
  `idPersonaCliente` int(11) NOT NULL,
  `valorNeto` decimal(14,2) NOT NULL,
  `valoriva` decimal(14,2) NOT NULL,
  `PorcentajeDescuento` int(11) NOT NULL,
  `valorDescuento` decimal(14,2) NOT NULL,
  `total_venta` decimal(14,2) NOT NULL,
  `Devuelta` decimal(14,2) NOT NULL,
  `efectivo` int(11) NOT NULL,
  `idTipoPago` decimal(10,0) NOT NULL,
  `idCaja` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `idSede` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`cod_factura`, `fecha_venta`, `idTipoVenta`, `idPersonaCliente`, `valorNeto`, `valoriva`, `PorcentajeDescuento`, `valorDescuento`, `total_venta`, `Devuelta`, `efectivo`, `idTipoPago`, `idCaja`, `idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`) VALUES
(24, '2018-05-20', '1', 2, '1200.00', '0.00', 0, '0.00', '1200.00', '-1200.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(25, '2018-05-20', '1', 1, '1200.00', '0.00', 0, '0.00', '1200.00', '13800.00', 15000, '1', 1, 1, 'mherrera', 2, 1, 1),
(26, '2018-05-20', '1', 1, '1200.00', '0.00', 0, '0.00', '1200.00', '-1200.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(28, '2018-05-20', '1', 1, '1200.00', '0.00', 0, '0.00', '1200.00', '-1200.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(29, '2018-05-20', '1', 1, '2400.00', '0.00', 0, '0.00', '2400.00', '-2400.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(31, '2018-05-20', '1', 1, '1200.00', '0.00', 0, '0.00', '1200.00', '-1200.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(32, '2018-05-20', '1', 1, '1200.00', '0.00', 0, '0.00', '1200.00', '-1200.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(33, '2018-05-20', '1', 1, '1200.00', '0.00', 0, '0.00', '1200.00', '-1200.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(34, '2018-05-20', '1', 1, '0.00', '0.00', 0, '0.00', '0.00', '0.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(35, '2018-05-20', '1', 1, '1200.00', '0.00', 0, '0.00', '1200.00', '-1200.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(36, '2018-05-20', '1', 1, '0.00', '0.00', 0, '0.00', '0.00', '0.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(38, '2018-05-20', '1', 1, '1200.00', '0.00', 0, '0.00', '1200.00', '-1200.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(39, '2018-05-20', '1', 1, '1200.00', '0.00', 0, '0.00', '1200.00', '-1200.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(40, '2018-05-20', '1', 1, '1200.00', '0.00', 0, '0.00', '1200.00', '-1200.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(41, '2018-05-20', '1', 1, '1200.00', '0.00', 0, '0.00', '1200.00', '-1200.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1),
(42, '2018-05-20', '1', 1, '1200.00', '0.00', 0, '0.00', '1200.00', '-1200.00', 0, '1', 1, 1, 'mherrera', 2, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventaproducto`
--

CREATE TABLE `ventaproducto` (
  `idVentaproducto` int(11) NOT NULL,
  `cod_factura` int(11) NOT NULL,
  `cantidadVenta` int(11) NOT NULL,
  `valoriva` decimal(14,2) NOT NULL,
  `ValorTotal` decimal(14,2) NOT NULL,
  `valorproducto` decimal(14,2) NOT NULL,
  `cod_producto` decimal(10,0) NOT NULL,
  `idCategoria` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `ventaproducto`
--

INSERT INTO `ventaproducto` (`idVentaproducto`, `cod_factura`, `cantidadVenta`, `valoriva`, `ValorTotal`, `valorproducto`, `cod_producto`, `idCategoria`) VALUES
(1, 28, 1, '0.00', '1200.00', '1200.00', '1', '1'),
(2, 29, 2, '0.00', '2400.00', '1200.00', '1', '1'),
(3, 31, 1, '0.00', '1200.00', '1200.00', '1', '1'),
(4, 32, 1, '0.00', '1200.00', '1200.00', '1', '1'),
(5, 33, 1, '0.00', '1200.00', '1200.00', '1', '1'),
(6, 35, 1, '0.00', '1200.00', '1200.00', '1', '1'),
(7, 36, 1, '0.00', '1200.00', '1200.00', '1', '1'),
(8, 38, 1, '0.00', '1200.00', '1200.00', '1', '1'),
(9, 39, 1, '0.00', '1200.00', '1200.00', '1', '1'),
(10, 40, 1, '0.00', '1200.00', '1200.00', '1', '1'),
(11, 41, 1, '0.00', '1200.00', '1200.00', '1', '1'),
(12, 42, 1, '0.00', '1200.00', '1200.00', '1', '1');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`idAsistencia`,`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`),
  ADD KEY `usuario_asistencia_fk` (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`);

--
-- Indices de la tabla `bodega`
--
ALTER TABLE `bodega`
  ADD PRIMARY KEY (`idBodega`,`idempresa`,`idSede`);

--
-- Indices de la tabla `cajaxuser`
--
ALTER TABLE `cajaxuser`
  ADD PRIMARY KEY (`idCaja`,`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`),
  ADD KEY `usuario_cajaxuser_fk` (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`idCategoria`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`IdCliente`),
  ADD KEY `persona_cliente_fk` (`idPersona`);

--
-- Indices de la tabla `compra_detalle`
--
ALTER TABLE `compra_detalle`
  ADD PRIMARY KEY (`idCompra`,`serie_producto`,`idDetalle`),
  ADD KEY `idDetalle` (`idDetalle`);

--
-- Indices de la tabla `compra_productos`
--
ALTER TABLE `compra_productos`
  ADD PRIMARY KEY (`idCompra`,`cod_factura`,`cod_proveedor`),
  ADD KEY `cod_proveedor` (`cod_proveedor`);

--
-- Indices de la tabla `dias`
--
ALTER TABLE `dias`
  ADD PRIMARY KEY (`idDias`);

--
-- Indices de la tabla `ejercicios`
--
ALTER TABLE `ejercicios`
  ADD PRIMARY KEY (`idEjercicios`,`IdMusculo`),
  ADD KEY `musculos_ejercicios_fk` (`IdMusculo`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`idempresa`);

--
-- Indices de la tabla `empresaproveedor`
--
ALTER TABLE `empresaproveedor`
  ADD PRIMARY KEY (`idempresaproveedor`);

--
-- Indices de la tabla `horarioservice`
--
ALTER TABLE `horarioservice`
  ADD PRIMARY KEY (`idhorario`),
  ADD KEY `idTipoService` (`idTipoService`);

--
-- Indices de la tabla `huellas`
--
ALTER TABLE `huellas`
  ADD PRIMARY KEY (`idHuella`,`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`),
  ADD KEY `usuario_huellas_fk` (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`);

--
-- Indices de la tabla `iva`
--
ALTER TABLE `iva`
  ADD PRIMARY KEY (`idIva`);

--
-- Indices de la tabla `log_errores`
--
ALTER TABLE `log_errores`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `medidas`
--
ALTER TABLE `medidas`
  ADD PRIMARY KEY (`idMedida`,`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`),
  ADD KEY `medidas_ibfk_1` (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`);

--
-- Indices de la tabla `medidaxmusculo`
--
ALTER TABLE `medidaxmusculo`
  ADD PRIMARY KEY (`idMedidaXMusculo`,`IdMusculo`,`idMedida`),
  ADD KEY `musculos_medidaxmusculo_fk` (`IdMusculo`),
  ADD KEY `idMedida` (`idMedida`);

--
-- Indices de la tabla `menus`
--
ALTER TABLE `menus`
  ADD PRIMARY KEY (`id_menu`);

--
-- Indices de la tabla `menus_usuarios`
--
ALTER TABLE `menus_usuarios`
  ADD PRIMARY KEY (`id_submenu`,`id_menu`,`idUsuario`),
  ADD KEY `idUsuario` (`idUsuario`);

--
-- Indices de la tabla `musculos`
--
ALTER TABLE `musculos`
  ADD PRIMARY KEY (`IdMusculo`);

--
-- Indices de la tabla `numeradores`
--
ALTER TABLE `numeradores`
  ADD PRIMARY KEY (`tipoNumerador`,`rango_inicial`);

--
-- Indices de la tabla `numeradorespendientes`
--
ALTER TABLE `numeradorespendientes`
  ADD PRIMARY KEY (`tipoNumerador`,`secuencia`);

--
-- Indices de la tabla `pagoservice`
--
ALTER TABLE `pagoservice`
  ADD PRIMARY KEY (`idPago`,`idUsuarioCliente`,`usuarioCliente`,`idSedeCliente`,`idempresaCliente`,`idPersonaCliente`,`idCaja`,`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`),
  ADD KEY `tipopago_pagoservice_fk` (`idTipoPago`),
  ADD KEY `tiposervice_pagoservice_fk` (`idTipoService`),
  ADD KEY `usuario_pagoservice_fk` (`idUsuarioCliente`,`usuarioCliente`,`idSedeCliente`,`idempresaCliente`,`idPersonaCliente`),
  ADD KEY `cajaxuser_pagoservice_fk` (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`,`idCaja`);

--
-- Indices de la tabla `perfiles`
--
ALTER TABLE `perfiles`
  ADD PRIMARY KEY (`id_perfil`);

--
-- Indices de la tabla `perfiles_x_rol`
--
ALTER TABLE `perfiles_x_rol`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_perfil` (`id_perfil`),
  ADD KEY `id_rol` (`id_rol`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`idPersona`),
  ADD KEY `tipodocumento_persona_fk` (`idTipoDocumento`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`cod_producto`,`idCategoria`),
  ADD KEY `unidad_producto_fk` (`cod_unidad`),
  ADD KEY `iva_producto_fk` (`idIva`),
  ADD KEY `categoria_producto_fk` (`idCategoria`);

--
-- Indices de la tabla `productomovimiento`
--
ALTER TABLE `productomovimiento`
  ADD PRIMARY KEY (`cod_productoMov`),
  ADD KEY `salidaentradaproductos_productomovimiento_fk` (`idSalidaEntrada`),
  ADD KEY `producto_productomovimiento_fk` (`cod_producto`,`idCategoria`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`idProveedor`),
  ADD KEY `empresaproveedor_proveedor_fk` (`idempresaproveedor`),
  ADD KEY `persona_proveedor_fk` (`idPersona`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`idRol`);

--
-- Indices de la tabla `rolxuser`
--
ALTER TABLE `rolxuser`
  ADD PRIMARY KEY (`idRolxUser`),
  ADD KEY `rol_rolxuser_fk` (`idRol`),
  ADD KEY `usuario_rolxuser_fk` (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`);

--
-- Indices de la tabla `rutdiaejercicio`
--
ALTER TABLE `rutdiaejercicio`
  ADD PRIMARY KEY (`idRutDiaejercicio`,`idRutina`,`idEjercicios`,`IdMusculo`),
  ADD KEY `ejercicios_rutdiaejercicio_fk` (`idEjercicios`,`IdMusculo`),
  ADD KEY `rutinadias_rutdiaejercicio_fk` (`idRutina`);

--
-- Indices de la tabla `rutina`
--
ALTER TABLE `rutina`
  ADD PRIMARY KEY (`idRutina`);

--
-- Indices de la tabla `rutinadias`
--
ALTER TABLE `rutinadias`
  ADD PRIMARY KEY (`idRutina`),
  ADD KEY `dias_rutinadias_fk` (`idDias`);

--
-- Indices de la tabla `rutinauser`
--
ALTER TABLE `rutinauser`
  ADD PRIMARY KEY (`idRutinaUser`),
  ADD KEY `rutina_rutinauser_fk` (`idRutina`),
  ADD KEY `usuario_rutinauser_fk` (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`);

--
-- Indices de la tabla `salidaentradaproductos`
--
ALTER TABLE `salidaentradaproductos`
  ADD PRIMARY KEY (`idSalidaEntrada`);

--
-- Indices de la tabla `sedes`
--
ALTER TABLE `sedes`
  ADD PRIMARY KEY (`idSede`,`idempresa`),
  ADD KEY `empresa_sedes_fk` (`idempresa`);

--
-- Indices de la tabla `sub_menus`
--
ALTER TABLE `sub_menus`
  ADD PRIMARY KEY (`id_submenu`,`id_menu`),
  ADD KEY `id_menu` (`id_menu`);

--
-- Indices de la tabla `tiopventa`
--
ALTER TABLE `tiopventa`
  ADD PRIMARY KEY (`idTipoVenta`);

--
-- Indices de la tabla `tipodocumento`
--
ALTER TABLE `tipodocumento`
  ADD PRIMARY KEY (`idTipoDocumento`);

--
-- Indices de la tabla `tipopago`
--
ALTER TABLE `tipopago`
  ADD PRIMARY KEY (`idTipoPago`);

--
-- Indices de la tabla `tiposervice`
--
ALTER TABLE `tiposervice`
  ADD PRIMARY KEY (`idTipoService`);

--
-- Indices de la tabla `unidad`
--
ALTER TABLE `unidad`
  ADD PRIMARY KEY (`cod_unidad`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`),
  ADD KEY `persona_usuario_fk` (`idPersona`),
  ADD KEY `sedes_usuario_fk` (`idSede`,`idempresa`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`cod_factura`),
  ADD KEY `tiopventa_venta_fk` (`idTipoVenta`),
  ADD KEY `cajaxuser_venta_fk` (`idCaja`,`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`);

--
-- Indices de la tabla `ventaproducto`
--
ALTER TABLE `ventaproducto`
  ADD PRIMARY KEY (`idVentaproducto`,`cod_factura`),
  ADD KEY `producto_ventaproducto_fk` (`cod_producto`,`idCategoria`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `idAsistencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `cajaxuser`
--
ALTER TABLE `cajaxuser`
  MODIFY `idCaja` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `idCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `IdCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `compra_detalle`
--
ALTER TABLE `compra_detalle`
  MODIFY `idDetalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `compra_productos`
--
ALTER TABLE `compra_productos`
  MODIFY `idCompra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `dias`
--
ALTER TABLE `dias`
  MODIFY `idDias` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `ejercicios`
--
ALTER TABLE `ejercicios`
  MODIFY `idEjercicios` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `idempresa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `empresaproveedor`
--
ALTER TABLE `empresaproveedor`
  MODIFY `idempresaproveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `horarioservice`
--
ALTER TABLE `horarioservice`
  MODIFY `idhorario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `huellas`
--
ALTER TABLE `huellas`
  MODIFY `idHuella` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `log_errores`
--
ALTER TABLE `log_errores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `medidas`
--
ALTER TABLE `medidas`
  MODIFY `idMedida` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `medidaxmusculo`
--
ALTER TABLE `medidaxmusculo`
  MODIFY `idMedidaXMusculo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `menus`
--
ALTER TABLE `menus`
  MODIFY `id_menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `musculos`
--
ALTER TABLE `musculos`
  MODIFY `IdMusculo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `pagoservice`
--
ALTER TABLE `pagoservice`
  MODIFY `idPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `perfiles`
--
ALTER TABLE `perfiles`
  MODIFY `id_perfil` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `perfiles_x_rol`
--
ALTER TABLE `perfiles_x_rol`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `idPersona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `cod_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `idProveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `idRol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `rolxuser`
--
ALTER TABLE `rolxuser`
  MODIFY `idRolxUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `rutdiaejercicio`
--
ALTER TABLE `rutdiaejercicio`
  MODIFY `idRutDiaejercicio` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `rutina`
--
ALTER TABLE `rutina`
  MODIFY `idRutina` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `rutinauser`
--
ALTER TABLE `rutinauser`
  MODIFY `idRutinaUser` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sedes`
--
ALTER TABLE `sedes`
  MODIFY `idSede` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `sub_menus`
--
ALTER TABLE `sub_menus`
  MODIFY `id_submenu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `tipodocumento`
--
ALTER TABLE `tipodocumento`
  MODIFY `idTipoDocumento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tipopago`
--
ALTER TABLE `tipopago`
  MODIFY `idTipoPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `tiposervice`
--
ALTER TABLE `tiposervice`
  MODIFY `idTipoService` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `ventaproducto`
--
ALTER TABLE `ventaproducto`
  MODIFY `idVentaproducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD CONSTRAINT `usuario_asistencia_fk` FOREIGN KEY (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`) REFERENCES `usuario` (`idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `cajaxuser`
--
ALTER TABLE `cajaxuser`
  ADD CONSTRAINT `usuario_cajaxuser_fk` FOREIGN KEY (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`) REFERENCES `usuario` (`idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `persona_cliente_fk` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ejercicios`
--
ALTER TABLE `ejercicios`
  ADD CONSTRAINT `musculos_ejercicios_fk` FOREIGN KEY (`IdMusculo`) REFERENCES `musculos` (`IdMusculo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `huellas`
--
ALTER TABLE `huellas`
  ADD CONSTRAINT `usuario_huellas_fk` FOREIGN KEY (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`) REFERENCES `usuario` (`idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `menus_usuarios`
--
ALTER TABLE `menus_usuarios`
  ADD CONSTRAINT `menus_usuarios_ibfk_1` FOREIGN KEY (`id_submenu`,`id_menu`) REFERENCES `sub_menus` (`id_submenu`, `id_menu`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `menus_usuarios_ibfk_2` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `pagoservice`
--
ALTER TABLE `pagoservice`
  ADD CONSTRAINT `cajaxuser_pagoservice_fk` FOREIGN KEY (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`,`idCaja`) REFERENCES `cajaxuser` (`idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`, `idCaja`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tipopago_pagoservice_fk` FOREIGN KEY (`idTipoPago`) REFERENCES `tipopago` (`idTipoPago`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tiposervice_pagoservice_fk` FOREIGN KEY (`idTipoService`) REFERENCES `tiposervice` (`idTipoService`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `usuario_pagoservice_fk` FOREIGN KEY (`idUsuarioCliente`,`usuarioCliente`,`idSedeCliente`,`idempresaCliente`,`idPersonaCliente`) REFERENCES `usuario` (`idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `perfiles_x_rol`
--
ALTER TABLE `perfiles_x_rol`
  ADD CONSTRAINT `perfiles_x_rol_ibfk_1` FOREIGN KEY (`id_perfil`) REFERENCES `perfiles` (`id_perfil`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `perfiles_x_rol_ibfk_2` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`idRol`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `persona`
--
ALTER TABLE `persona`
  ADD CONSTRAINT `tipodocumento_persona_fk` FOREIGN KEY (`idTipoDocumento`) REFERENCES `tipodocumento` (`idTipoDocumento`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `productomovimiento`
--
ALTER TABLE `productomovimiento`
  ADD CONSTRAINT `salidaentradaproductos_productomovimiento_fk` FOREIGN KEY (`idSalidaEntrada`) REFERENCES `salidaentradaproductos` (`idSalidaEntrada`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD CONSTRAINT `persona_proveedor_fk` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `rolxuser`
--
ALTER TABLE `rolxuser`
  ADD CONSTRAINT `rol_rolxuser_fk` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usuario_rolxuser_fk` FOREIGN KEY (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`) REFERENCES `usuario` (`idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `rutdiaejercicio`
--
ALTER TABLE `rutdiaejercicio`
  ADD CONSTRAINT `ejercicios_rutdiaejercicio_fk` FOREIGN KEY (`idEjercicios`,`IdMusculo`) REFERENCES `ejercicios` (`idEjercicios`, `IdMusculo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `rutinadias_rutdiaejercicio_fk` FOREIGN KEY (`idRutina`) REFERENCES `rutinadias` (`idRutina`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `rutinadias`
--
ALTER TABLE `rutinadias`
  ADD CONSTRAINT `dias_rutinadias_fk` FOREIGN KEY (`idDias`) REFERENCES `dias` (`idDias`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `rutina_rutinadias_fk` FOREIGN KEY (`idRutina`) REFERENCES `rutina` (`idRutina`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `rutinauser`
--
ALTER TABLE `rutinauser`
  ADD CONSTRAINT `rutina_rutinauser_fk` FOREIGN KEY (`idRutina`) REFERENCES `rutina` (`idRutina`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `usuario_rutinauser_fk` FOREIGN KEY (`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`) REFERENCES `usuario` (`idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `sedes`
--
ALTER TABLE `sedes`
  ADD CONSTRAINT `empresa_sedes_fk` FOREIGN KEY (`idempresa`) REFERENCES `empresa` (`idempresa`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `sub_menus`
--
ALTER TABLE `sub_menus`
  ADD CONSTRAINT `sub_menus_ibfk_1` FOREIGN KEY (`id_menu`) REFERENCES `menus` (`id_menu`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `persona_usuario_fk` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `sedes_usuario_fk` FOREIGN KEY (`idSede`,`idempresa`) REFERENCES `sedes` (`idSede`, `idempresa`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `cajaxuser_venta_fk` FOREIGN KEY (`idCaja`,`idUsuario`,`usuario`,`idSede`,`idempresa`,`idPersona`) REFERENCES `cajaxuser` (`idCaja`, `idUsuario`, `usuario`, `idSede`, `idempresa`, `idPersona`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tiopventa_venta_fk` FOREIGN KEY (`idTipoVenta`) REFERENCES `tiopventa` (`idTipoVenta`) ON DELETE NO ACTION ON UPDATE NO ACTION;

DELIMITER $$
--
-- Eventos
--
CREATE DEFINER=`root`@`localhost` EVENT `cerrarCajas` ON SCHEDULE EVERY 30 MINUTE STARTS '2017-11-03 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO call cerrarCajasAuto()$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
