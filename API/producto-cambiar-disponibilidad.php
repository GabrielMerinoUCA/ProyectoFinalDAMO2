<?php

require_once 'conexion.php';

$idProducto = $_POST['idProducto'];

//Cambiar la disponibilidad del prducto a 0 si esta en 1 o en 1 si esta en 0

$q = "SELECT * FROM producto WHERE idProducto = '$idProducto'";
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $disponibilidad = $row['disponibilidad'];
}

if($disponibilidad == 0){
    $q = "UPDATE producto SET disponibilidad = '1' WHERE idProducto = '$idProducto'";
    $query = mysqli_query($con, $q);
}else{
    $q = "UPDATE producto SET disponibilidad = '0' WHERE idProducto = '$idProducto'";
    $query = mysqli_query($con, $q);
}

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>