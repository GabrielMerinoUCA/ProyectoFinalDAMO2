<?php

require_once 'conexion.php';

$idTienda = $_POST['idTienda'];

//Cambiar el estado de la tienda a 0 si esta en 1 o en 1 si esta en 0

$q = "SELECT * FROM tienda WHERE idTienda = '$idTienda'";
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $estado = $row['estado'];
}

if($estado == 0){
    $q = "UPDATE tienda SET estado = '1' WHERE idTienda = '$idTienda'";
    $query = mysqli_query($con, $q);
}else{
    $q = "UPDATE tienda SET estado = '0' WHERE idTienda = '$idTienda'";
    $query = mysqli_query($con, $q);
}

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>