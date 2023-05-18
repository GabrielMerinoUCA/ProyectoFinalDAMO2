<?php

require_once 'conexion.php';

$idCliente = $_POST['idCliente'];

$q = "SELECT * FROM orden WHERE idCliente = '$idCliente' ORDER BY estado ASC";
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $json[] = array('id'=>$row['idOrden'], 'idProducto'=>$row['idProducto'], 'idCliente'=>$row['idCliente'], 'horaPedido'=>$row['horaPedido'], 'cantidad'=>$row['cantidad'], 'horaReclamo'=>$row['horaReclamo'], 'estado'=>$row['estado']);
}

echo json_encode($json);

?>