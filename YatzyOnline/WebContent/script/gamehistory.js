$(function() {

	$('#btn-submit').click(function(e) {
		e.preventDefault();

		$.ajax({
			url : 'player',
			data : {
				'action' : 'GetGameHistoryById',
				'id' : $('#playerID').val()
			}
		}).success(function(result) {
			var json = JSON.parse(result);
			//clear possible previous result
			$("#playResult").html("");
			var $table = $("<table>").appendTo($("#playResult"));
			$("<thead>").appendTo($table);
			$("<tr>").appendTo($table)	
                .append($("<th>").text("GameID")) 
                .append($("<th>").text("WinnerFirstName")) 
                .append($("<th>").text("WinnerLastName"))                 
                .append($("<th>").text("MaxScore")) 
                .append($("<th>").text("LoserFirstName")) 
                .append($("<th>").text("LoserLastName")) 
                .append($("<th>").text("MinScore")) 
                .append($("<th>").text("date"));  			
			$("</tr>").appendTo($table)
				$("</thead>").appendTo($table);
			$("<tbody>").appendTo($table);
	        $.each(json, function(index, product) {    
	            $("<tr>").appendTo($table)                     
	                .append($("<td>").text(product.GameID))        
	                .append($("<td>").text(product.WinnerFirstName))      
	                .append($("<td>").text(product.WinnerLastName))
	                .append($("<td>").text(product.MaxScore))        
	                .append($("<td>").text(product.LoserFirstName))      
	                .append($("<td>").text(product.LoserLastName))
	                .append($("<td>").text(product.MinScore))      
	                .append($("<td>").text(product.date))
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

