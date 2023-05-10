<?php
require_once 'conexion.php';

$q = 'SELECT * FROM cliente';
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    echo 'Hola';
    $json[] = array('id'=>$row['idCliente'], 'nombre'=>$row['nombre'], 'apellido'=>$row['apellido'], 'pwd'=>$row['pwd'], 'nombreUsuario'=>$row['nombreUsuario']);
}

echo json_encode($json);
?>