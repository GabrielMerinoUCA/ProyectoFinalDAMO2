<?php

require_once 'conexion.php';

$horaPedido = $_POST['horaPedido'];
$idCliente = $_POST['idCliente'];
$idProducto = $_POST['idProducto'];
$estado = $_POST['estado'];

$q = 'INSERT INTO orden(horaPedido, idCliente, idProducto, estado) VALUES ("' . $horaPedido . '","' . $idCliente . '","' . $idProducto . '","' . $estado . '")';
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>