<?php

require_once 'conexion.php';

$idRegistro = $_POST['idRegistro'];

$q = "SELECT * FROM registro WHERE idRegistro = '$idRegistro'";
$query = mysqli_query($con, $q);

$registroObtenido = $query ->fetch_array();

//Insertar datos del registro en la tabla tienda

$nombreTienda = $registroObtenido['nombreTienda'];
$horaApertura = $registroObtenido['horaApertura'];
$horaCierre = $registroObtenido['horaCierre'];

$q = "INSERT INTO tienda (nombre, horaApertura, horaCierre, estado) VALUES ('$nombreTienda', '$horaApertura', '$horaCierre', '0')";
$query = mysqli_query($con, $q);

if($query == true){
    $insercionTienda = true;
}

//Obtener id de la tienda recien creada

$q = "SELECT * FROM tienda WHERE nombre = '$nombreTienda'";
$query = mysqli_query($con, $q);

$tiendaObtenida = $query ->fetch_array();

$idTienda = $tiendaObtenida['idTienda'];

//Insertar datos del registro en la tabla vendedor

$nombreUsuario = $registroObtenido['nombreUsuario'];
$pwd = $registroObtenido['pwd'];

$q = "INSERT INTO vendedor (nombreUsuario, pwd, idTienda) VALUES ('$nombreUsuario', '$pwd', '$idTienda')";
$query = mysqli_query($con, $q);

if($query == true){
    $insercionVendedor = true;
}

//Cambiar estado del registro a 1

$q = "UPDATE registro SET estado = '1' WHERE idRegistro = '$idRegistro'";
$query = mysqli_query($con, $q);

if($insercionTienda == true && $insercionVendedor == true && $query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>