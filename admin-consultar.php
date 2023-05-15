<?php

require_once 'conexion.php';

$q = 'SELECT * FROM administrador';
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $json[] = array('id'=>$row['idAdministrador'], 'nombre'=>$row['nombre'], 'apellido'=>$row['apellido'], 'nombreUsuario'=>$row['nombreUsuario'], 'pwd'=>$row['pwd']);
}

echo json_encode($json);

?>