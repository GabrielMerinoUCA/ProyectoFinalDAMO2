<?php

require_once 'conexion.php';

$q = "SELECT * FROM registro ORDER BY estado ASC";
$query = mysqli_query($con, $q);

while($row = $query ->fetch_array()){
    $json[] = array(
        'idRegistro' => $row['idRegistro'],
        'nombre' => $row['nombre'],
        'apellido' => $row['apellido'],
        'nombreTienda' => $row['nombreTienda'],
        'nombreUsuario' => $row['nombreUsuario'],
        'horaApertura' => $row['horaApertura'],
        'horaCierre' => $row['horaCierre'],
        'pwd' => $row['pwd'],
        'estado' => $row['estado']
    );
}

echo json_encode($json);

?>