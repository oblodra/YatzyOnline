$(function() {
	
	$('#btn-submit').click(function(e) {
		e.preventDefault();

		$.ajax({
			url : 'player',
			data : {
				'action' : 'GetHighScore',
				'id' : $('#playerID').val()
			}
		}).success(function(result) {
			var json = JSON.parse(result);
			//clear possible previous result
			$("#playResult").html("");
			var $table = $("<table>").appendTo($("#playResult"));
			$("<thead>").appendTo($table);
			$("<tr>").appendTo($table)	
                .append($("<th>").text("FirstName")) 
                .append($("<th>").text("LastName"))
                .append($("<th>").text("Score"))
                .append($("<th>").text("GameID"));  			
			$("</tr>").appendTo($table)
				$("</thead>").appendTo($table);
			$("<tbody>").appendTo($table);
	        $.each(json, function(index, product) {    
	            $("<tr>").appendTo($table)                     
	                .append($("<td>").text(product.FirstName))        
	                .append($("<td>").text(product.LastName))      
	                .append($("<td>").text(product.Score))
	                .append($("<td>").text(product.GameID))
	                .append($("</tr>"));    
	        });
	        $("<tbody>").appendTo($table)
	        	.append($("</table>"));

			//$('#employee-table').show();
			
			//$('#ajax-result').html(JSON.stringify(json, null, 4));
			//$('#ajax-result').html(result);
		});
	});
})

