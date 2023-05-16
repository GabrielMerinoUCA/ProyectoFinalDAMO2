<?php

require_once 'conexion.php';

$horaPedido = $_POST['horaPedido'];
$idCliente = $_POST['idCliente'];
$idProducto = $_POST['idProducto'];
$cantidad = $_POST['cantidad'];

$q = "INSERT INTO orden (horaPedido, idCliente, idProducto, cantidad) VALUES ('$horaPedido', '$idCliente', '$idProducto', '$cantidad')";
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>