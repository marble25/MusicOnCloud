<?
$password = "고추장반딱이";
$pw_encode = md5($password);

if($_POST["pw"] == $pw_encode) { 
	include 'common.php';
} else {
	echo "Fail No 2";
}

$query = "SELECT * FROM `USERINFO` WHERE `userName`='".htmlspecialchars($_POST['userName'])."';";
$result = mysqli_query($conn, $query);

if(mysqli_num_rows($result) == 0) {
	$query = "INSERT INTO `USERINFO` (`userName`) VALUES ('".htmlspecialchars($_POST["userName"])."');";
	if(mysqli_query($conn, $query)) {
		$query = "SELECT * FROM `USERINFO` WHERE `userName`='".htmlspecialchars($_POST['userName'])."';";
		$result = mysqli_query($conn, $query);
		$row = mysqli_fetch_row($result);

		echo "Success/".$row[0];
	} else {
		echo "Fail No 3";
	}
} else {
	echo "Same Name Already Exists";
}

mysqli_close($conn);

?>