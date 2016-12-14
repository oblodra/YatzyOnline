$(function() {

	$('#singlePlay-submit').click(function(e) {
		e.preventDefault();

		$.ajax({
			url : 'player',
			data : {
				'action' : 'GetPlayerById',
				'id' : $('#playerID').val()
			}
		}).success(function(result) {
			var json = JSON.parse(result);
			//clear possible previous result
			$("#playResult").html("");
			var $table = $("<table>").appendTo($("#playResult"));
			$("<thead>").appendTo($table);
			$("<tr>").appendTo($table)	
                .append($("<th>").text("PlayerID")) 
                .append($("<th>").text("FirstName")) 
                .append($("<th>").text("LastName"));  			
			$("</tr>").appendTo($table)
				$("</thead>").appendTo($table);
			$("<tbody>").appendTo($table);
			$("<tr>").appendTo($table)                     
				.append($("<td>").text(json.PlayerID))        
				.append($("<td>").text(json.FirstName))      
				.append($("<td>").text(json.LastName))
				.append($("</tr>"));    
	        $("<tbody>").appendTo($table)
	        	.append($("</table>"));

			//$('#employee-table').show();
		});
	});
	
	$('#multiPlay-submit').click(function(e) {
		e.preventDefault();

		$.ajax({
			url : 'player',
			data : {
				'action' : 'GetAllPlayers',
				'id' : $('#playerID').val()
			}
		}).success(function(result) {
			var json = JSON.parse(result);
			//clear possible previous result
			$("#playResult").html("");
			var $table = $("<table>").appendTo($("#playResult"));
			$("<thead>").appendTo($table);
			$("<tr>").appendTo($table)	
                .append($("<th>").text("PlayerID")) 
                .append($("<th>").text("FirstName")) 
                .append($("<th>").text("LastName"));  			
			$("</tr>").appendTo($table)
				$("</thead>").appendTo($table);
			$("<tbody>").appendTo($table);
	        $.each(json, function(index, product) {    
	            $("<tr>").appendTo($table)                     
	                .append($("<td>").text(product.PlayerID))        
	                .append($("<td>").text(product.FirstName))      
	                .append($("<td>").text(product.LastName))
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

