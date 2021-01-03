<?
$db_host = "localhost";
$db_user = "root";
$db_passwd = "ljiho1509";
$db_name = "MUSIC_ON_CLOUD";
$conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name);
if(mysqli_connect_errno($conn)) {
	echo "Fail No 1\n";
	exit();
}
?>