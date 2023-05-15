<?php

require_once 'conexion.php';

$q = 'SELECT * FROM vendedor';
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $json[] = array('id'=>$row['idVendedor'], 'nombreUsuario'=>$row['nombreUsuario'], 'pwd'=>$row['pwd'], 'idTienda'=>$row['idTienda']);
}

echo json_encode($json);

?>