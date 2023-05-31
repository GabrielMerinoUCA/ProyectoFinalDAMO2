<?php
require_once 'conexion.php';

$idVendedor = $_POST['idVendedor'];

$q = "SELECT idTienda from vendedor where idVendedor = " . $idVendedor;
$query = mysqli_query($con, $q);

// Obtener el id de tienda del vendedor
$tiendaObtenida = $query -> fetch_array();
$idTienda = $tiendaObtenida['idTienda'];

$q = "DELETE FROM tienda WHERE idTienda = " . $idTienda ;
$query = mysqli_query($con, $q);

$q = "DELETE FROM vendedor WHERE idVendedor = " . $idVendedor;
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>