<?
$password = "고추장반딱이";
$pw_encode = md5($password);

if($_POST["pw"] == $pw_encode) { 
	include 'common.php';
} else {
	echo "Fail No 2";
}

$query = "SELECT * FROM `MUSICS` WHERE `musicid`=".htmlspecialchars($_POST['musicId']).";";
$result = mysqli_query($conn, $query);

if(isset($result)) {
	if(mysqli_num_rows($result) != 0) {
		echo "Success/";
		while($row = mysqli_fetch_row($result)) {
			printf("%s/%s/%s/%s/", $row[2], $row[3], $row[4], $row[5]);
		}
	} else {
		echo "No Music Available";
	}
} else {
	echo "Fail No 3";
}

mysqli_close($conn);

?>