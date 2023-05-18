<?php
$con = mysqli_connect('localhost', 'root', '', 'orderfy', '3306');

if(!$con){
    echo 'Error al conectar';
}