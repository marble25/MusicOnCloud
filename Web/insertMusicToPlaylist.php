<?
$password = "고추장반딱이";
$pw_encode = md5($password);

if($_POST["pw"] == $pw_encode) { 
	include 'common.php';
} else {
	echo "Fail No 2";
}

$songIds = explode("/", htmlspecialchars($_POST["musicId"]));
foreach($playIds as $value) {
	$query = "INSERT INTO `PLAYLISTS_MUSICS` (`playId`, `songId`) VALUES ('".htmlspecialchars($_POST["playId"])."', '".$value."');";
	mysqli_query($conn, $query) or die("Fail No 3");
}
echo "Success";

mysqli_close($conn);

?>