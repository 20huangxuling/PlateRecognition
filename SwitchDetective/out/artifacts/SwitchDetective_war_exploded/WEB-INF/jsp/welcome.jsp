<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>电力压板识别</title>    

<script src = "js/jquery.min.js"></script>
<script src = "js/bootstrap.min.js"></script>
<link rel = "stylesheet" href = "css/bootstrap.min.css"/>
<!--  <script src = "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>-->
<!--  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"/>-->


<style>
	.main-container { 
		position:relative;
		background:#F7FAFC; 
		height:600px; 
		width:800px; 
		margin:0 auto;  
		z-index:1 
	}
	
	.display-container {
		position:absolute;
		height:450px;
		width:800px;
		top:0px;
		left:0px;
		right:0px;
		margin:auto;
	}
	
	.img-container { 
		position:absolute; 
		height:300px;
		width:400px;
		top:0px;
		bottom:0px;
		left:0px; 
		right:0px; 
		background: #F8F8F8;
		box-shadow:0px 0px 10px #D8D8D8 inset; 
		margin:auto 
	}
	
	.showImg { 
		max-width:400px; 
		position:absolute; 
		top:0; 
		bottom:0; 
		left:0; 
		right:0; 
		margin:auto 
	}
	
	.imgBox {
		width:200px;
		height:150px;
		overflow:hidden;
		cursor:pointer;
		position:absolute;
		top:0px;
		bottom:0px;
		margin:auto;
	}
	.imgBox img {
		width:100px;
		height:100px;
		position:absolute;
		transition:all .4s;
		-moz-transition:all .4s;
		-webkit-transition:all .4s;
		-o-transition:all .4s;
		left:0px;
		right:0px;
		top:0px;
		bottom:0px;
		margin:auto;
	}
	.imgBox img:hover {
		transform:scale(1.2);
	}
	
	.control-container {
		height:150px;
		width: 800px;
		position:absolute;
		bottom:0px;
		margin:auto;
	}
	
	.button {
		display: inline-block;
		padding: 6px 12px;
		width: 125px;
		height: 50px;
		margin:auto;
		margin-bottom: 0;
		font-size: 20px;
		font-weight: 400;
		letter-spacing: 20px;
		text-indent: 20px;
		line-height: 1.8;
		text-align: center;
		white-space: nowrap;
		-ms-touch-action: manipulation;
		touch-action: manipulation;
		cursor: pointer;
		border: 3px solid transparent;
		border-radius: 10px;
		background: transparent;
		border-color: #66CCFF;
		color: #66CCFF;
	}
	
	.button:hover,.button:active,.button:focus {
		transform: scale(1.05);
		color: #00CCFF;
		border-color: #00CCFF;
		box-shadow: 0 1px 1px 1px #CCEEFF;
	}
	
	.submit-button {
		position:absolute;
		bottom:75px;
		right:175px;
	}
		
	.reset-button {
		position:absolute;
		bottom:75px;
		left:0px;
		right:0px;
	}
	
	.upload-button {
		position:absolute;
		bottom:75px;
		left:175px;
	}
	
	.upload-count {
		position: absolute;
		bottom: 30px;
		right: 180px;
		width: 120px;
		height: 40px;
		color: #00CCFF;
		font-size: 18px;
		line-height: 40px;
		letter-spacing: 3px;
		text-ident:3px;
		text-align: center;
	}
</style>
<!--  <script src = "https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
-->

<script type = "text/javascript">
var imgs = [];
var uploadedCount = 0;

$(function() {
	index = 0;
	
	$("#uploadButton").on("click", function() {
		$(this).parent().children("input:first").click();		
	});
	
	$("#file").on("change", uploadImg);
	
	$("#submitButton").on("click", verify);
	
	$("#resetButton").on("click", resetImgCntnr);
});

function uploadImg(){
	var display = 0;
	var input = $("#file")[0];
		
	for(let i = 0; i < input.files.length; i++){
		if(uploadedCount + 1 <= 5){
			if(!/image\/\w+/i.test(input.files[i].type)){
				alert(input.files[i].name+"的格式不正确");
				continue;
			}
			
			uploadedCount++;
						
			imgs.push(input.files[i]);
			var reader = new FileReader();
			
			reader.onload = function(event){
				var base64 = event.target.result;
				var result;
				if(display == 0){
					var result = '<div ' +
					'class = "viewImg active"><img src="' + base64 + '" class = "showImg"/></div>';
					display = 1;
				} else{
					var result = '<div style = "display:none"' +
					'class = "viewImg"><img src="' + base64 + '" class = "showImg"/></div>';
				}
				$("#imgContainer").append(result);
			};
			
			reader.readAsDataURL(input.files[i]);
			
		} else {
			alert("一次最多识别5张图片");
			break;
		}
	}
	
	$("#uploadCount").text('已上传'+uploadedCount+'/'+5);
	
	return ;
}

function resetImgCntnr() {
	uploadedCount = 0;
	imgs = [];
	$("#uploadCount").text("");
	$("#imgContainer").empty();
	
}

//上传图片到服务器进行识别
function verify() {
	if(imgs.length <= 0) {
		alert("请先选择图片");
		return ;
	}
	
	var count = 0;
	var rtImgs = [];
	var display = 0;
	
	$("#imgContainer").empty();
	
	for(let i = 0; i < imgs.length; i++){
		var fd = new FormData();
		fd.append("file", imgs[i]);
	
		$.ajax({
			type : "POST",	
			dataType : "json",
			url : "http://182.92.226.1/web/image/identification.do",
			data : fd,
			async : true,
			processData : false,
			contentType : false,
			success : function(result) {
				count++;
				var rtImg = result["path_Image_Detected"];
				rtImgs.push(rtImg);
				
				if(display == 0){
					var result = '<div ' +
					'class = "rtImg active"><img src="' + rtImg + '" class = "showImg"/></div>';
					display = 1;
				} else {
					var result = '<div ' +
					'class = "rtImg active"><img src="' + rtImg + '" class = "showImg"/></div>';
				}
				
				$("#imgContainer").append(result);				
				$("#uploadCount").text('已识别'+count+'/'+imgs.length);
			
				
			},
			error : function() {
				flag++;
				alert('图'+i+'识别失败！');
			}
		});
	}
}

function preImg(){
	var cur = $(".active");
	var pre = cur.prev();
	
	if(pre.length != 0){
		cur.removeClass("active");
		cur.attr("style", "display: none")
		pre.addClass("active");
		pre.attr("style", "");
	} else {
		var lastBro = cur.parent().children("div:last-child");
		if(lastBro.length != 0 && lastBro != cur){
			cur.removeClass("active");
			cur.attr("style", "display: none");
			lastBro.addClass("active");
			lastBro.attr("style", "");
		}
	}
}

function nextImg(){
	var cur = $(".active");
	var next = cur.next();
	
	if(next.length != 0){
		cur.removeClass("active");
		cur.attr("style", "display: none")
		next.addClass("active");
		next.attr("style", "");
	} else {
		var firstBro = cur.parent().children("div:first-child");
		if(firstBro.length != 0 && firstBro != cur){
			cur.removeClass("active");
			cur.attr("style", "display: none");
			firstBro.addClass("active");
			firstBro.attr("style", "");
		}
	}
}

</script>

</head>

<body>

<div id = "mainControler" class = "main-container">
	<div id = "displayContainer" class = "display-container">
		<a id = "leftButton" class = "imgBox" style = "left:15px" role = "button" onclick = "preImg()">
			<img src = "img/left.png"  />
		</a>
		<a id = "rightButton" class = "imgBox" style = "right:15px" role = "button" onclick = "nextImg()">
			<img src = "img/right.png" />
		</a>
		<div id = "imgContainer" class = "img-container"></div>
		<div id = "uploadCount" class = "upload-count"></div>
	</div>
	
	<div id = "controlContainer" class = "control-container">
		<div id = "uploadPart">
			<button id = "uploadButton" class = "button upload-button">上传</button>
			<input id = "file" name = "file" type = "file" style = "display:none" multiple accept = "image/*"></input>
		</div>
		<button id = "resetButton" class = "button reset-button" type = "button" >重置</button>	
		<button id = "submitButton" class = "button submit-button" type = "button" >识别</button>	
	</div>
	
	
</div>


</body>
</html>