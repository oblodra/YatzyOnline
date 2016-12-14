$(function() {

	$('#btn-submit').click(function(e) {
		e.preventDefault();

		$.ajax({
			url : 'player',
			data : {
				'action' : 'GetGameDataById',
				'id' : $('#gameID').val()
			}
		}).success(function(result) {
			var json = JSON.parse(result);
			//clear possible previous result
			$("#playResult").html("");
			var $table = $("<table>").appendTo($("#playResult"));
			$("<thead>").appendTo($table);
			$("<tr>").appendTo($table)	
                .append($("<th>").text("TurnNumber")) 
                .append($("<th>").text("FirstName")) 
                .append($("<th>").text("LastName"))                 
                .append($("<th>").text("Dice1"))
                .append($("<th>").text("Dice2")) 
                .append($("<th>").text("Dice3")) 
                .append($("<th>").text("Dice4")) 
                .append($("<th>").text("Dice5")) 
                .append($("<th>").text("ScoreType")) 
                .append($("<th>").text("PointsEndOfTurn"));  			
			$("</tr>").appendTo($table)
				$("</thead>").appendTo($table);
			$("<tbody>").appendTo($table);
	        $.each(json, function(index, product) {    
	            $("<tr>").appendTo($table)                     
	                .append($("<td>").text(product.TurnNumber))        
	                .append($("<td>").text(product.FirstName))      
	                .append($("<td>").text(product.LastName))
	                .append($("<td>").text(product.Dice1))        
	                .append($("<td>").text(product.Dice2))      
	                .append($("<td>").text(product.Dice3))
	                .append($("<td>").text(product.Dice4))      
	                .append($("<td>").text(product.Dice5))
	                .append($("<td>").text(product.ScoreType))      
	                .append($("<td>").text(product.PointsEndOfTurn))
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

