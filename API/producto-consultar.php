<?php
require_once 'conexion.php';

$idTienda = $_POST['idTienda'];

$q = 'SELECT * FROM producto WHERE idTienda = ' . $idTienda;
$query = mysqli_query($con, $q);

$json = array();

while($row = $query->fetch_array()){
    $json[] = array('id'=>$row['idProducto'], 'nombre'=>$row['nombre'], 'precio'=>$row['precio'], 'descripcion'=>$row['descripcion'], 'imagen'=>base64_encode($row['imagen']), 'idTienda'=>$row['idTienda'], 'disponibilidad'=>$row['disponibilidad'], 'tiempoEstimado'=>$row['tiempoEstimado'] );
}

if($json == null){
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>