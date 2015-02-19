<html>

<head>
    <title>Demo Web Service</title>
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>    
    <script src="/js/home-control.js"></script>
</head>

<body>    
    
    <div>
        This is a simple page to demonstrate the web UI development. 
        The key tools and techniques used include:
        <ul>
            <li>HTML - Obviously</li>
            <li><a href="http://freemarker.org/">FreeMarker</a></li>
            <li><a href="http://jquery.com/">JQuery</a></li>
            <li><a href="http://api.jquery.com/jquery.ajax/">JQuery - AJAX</a></li>
        </ul>
    </div>

    <hr>

    <div>
        <div>
            <label>Photo List</label>
            <table border="1">            
                <tr>
                    <td>ID</td>
                    <td>Name</td> 
                    <td>Owner</td> 
                    <td>Creation Time</td>
                    <td>Delete</td>
                </tr>
                <#list photos as photo>
                        <tr>
                            <td>${photo.id}</td>
                            <td>${photo.name}</td>
                            <td>${photo.owner}</td>
                            <td>${photo.creationTime}</td>
                            <td><button onclick="deletePhoto('${photo.id}')">Delete</button></td>
                        </tr>
                </#list>
            </table>
        </div>
        
        <hr>
        
        <div>
            <label>Add Photos</label>
            <table border="1">
                <tr>
                    <td>ID</td>
                    <td>Name</td> 
                    <td>Owner</td>                     
                    <td>Add</td>
                </tr>                
                <tr>
                    <td><input type="text" id="input_id"></td>
                    <td><input type="text" id="input_name"></td>
                    <td><input type="text" id="input_owner"></td>                    
                    <td><button onclick="addPhoto()">Add</button></td>
                </tr>
            </table>
        </div>

        <hr>

        <div>
            <label>Query Photo</label>
            <input type="text" id="query_id"><button onclick="getPhoto()">Get</button>
            <table border="1">
                <tr>
                    <td>ID</td>
                    <td>Name</td>
                    <td>Owner</td>
                </tr>
                <tr>
                    <td><label id="result_id"></td>
                    <td><label id="result_name"></td>
                    <td><label id="result_owner"></td>
                </tr>
            </table>
        </div>
    </div>
    
    <form method="POST" enctype="multipart/form-data"
		action="/upload">
		File to upload: <input type="file" name="file"><br /> Name: <input
			type="text" name="name"><br /> <br /> <input type="submit"
			value="Upload"> Press here to upload the file!
	</form>
    
</body>

</html>