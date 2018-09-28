<%@ page language="java"  contentType="text/html; charset=UTF-8" %>

<html>
<body>
<h2>店铺测试</h2>




上传文件  CDN
<form name="form3" action="/file/uploadFileCDN.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="springmvc上传文件" />
</form>


</body>
</html>
