<?php

require_once 'conexion.php';

$idTienda = $_POST['idTienda'];

$q = "SELECT * FROM orden WHERE idProducto IN (SELECT idProducto FROM producto WHERE idTienda = '$idTienda')";
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $json[] = array('id'=>$row['idOrden'], 'idProducto'=>$row['idProducto'], 'idCliente'=>$row['idCliente'], 'horaPedido'=>$row['horaPedido'], 'horaReclamo'=>$row['horaReclamo'], 'estado'=>$row['estado']);
}

echo json_encode($json);

?>