<?php

require_once 'conexion.php';

$q = 'SELECT * FROM cliente';
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $json[] = array('id'=>$row['idCliente'], 'nombre'=>$row['nombre'], 'apellido'=>$row['apellido'], 'nombreUsuaario'=>$row['nombreUsuario'], 'pwd'=>$row['pwd']);
}

echo json_encode($json);

?>