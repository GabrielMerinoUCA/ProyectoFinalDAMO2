<?php

require_once 'conexion.php';

//Consultar todos los usuarios, es decir clientes y vendedores

$q = 'SELECT * FROM vendedor';
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $json[] = array('id'=>$row['idVendedor'], 'nombreUsuario'=>$row['nombreUsuario'], 'pwd'=>$row['pwd'], 'idTienda'=>$row['idTienda']);
}

$q = 'SELECT * FROM cliente';
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $json[] = array('id'=>$row['idCliente'], 'nombre'=>$row['nombre'], 'apellido'=>$row['apellido'], 'nombreUsuaario'=>$row['nombreUsuario'], 'pwd'=>$row['pwd']);
}

echo json_encode($json);

?>