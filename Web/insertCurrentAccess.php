<?
$password = "고추장반딱이";
$pw_encode = md5($password);

if($_POST["pw"] == $pw_encode) { 
	include 'common.php';
} else {
	echo "Fail No 2";
}
$date = date('Y-m-d H:i:s');

$query = "SELECT * FROM `USERINFO` WHERE `userName`='".htmlspecialchars($_POST['userName'])."';";
$result = mysqli_query($conn, $query);

if(isset($result)) {
	$row = mysqli_fetch_row($result);
	$query = "INSERT INTO `USERACCESS` (`userId`, `currentTime`, `phoneName`) VALUES (".$row[0].", '".$date."', '".htmlspecialchars($_POST["phoneName"])."');";
	if(mysqli_query($conn, $query)) {
		echo "Success";
	} else {
		echo "Fail No 3";
	}
} else {
	echo "Fail No 4";
}

mysqli_close($conn);

?>