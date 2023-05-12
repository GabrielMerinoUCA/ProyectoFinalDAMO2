<?php
require_once 'conexion.php';

$q = 'SELECT * FROM tienda';
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $json[] = array('id'=>$row['idTienda'], 'nombre'=>$row['nombre'], 'horaApertura'=>$row['horaApertura'], 'horaCierre'=>$row['horaCierre'], 'logo'=>$row['logo'], 'estado'=>$row['estado']);
}

echo json_encode($json);
?>